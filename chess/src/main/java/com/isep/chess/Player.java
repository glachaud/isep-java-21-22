package com.isep.chess;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Player {
  private final int color;
  private Board b;
  private List<Square> squares;
  private List<Piece> alivePieces;
  private King king;
  private Map<Square, List<Piece>> availableMoves;
  private List<Square> movableSquares;

  public Player(int color, Board b) {
    this.color = color;
    this.availableMoves = new HashMap<>();
    this.movableSquares = new LinkedList<>();
    this.squares = new LinkedList<>();
    this.b = b;
    Square[][] board = b.getBoardArray();
    for (int row = 0; row < Board.BOARD_SIZE; row++) {
      for (int column = 0; column < Board.BOARD_SIZE; column++) {
        squares.add(board[row][column]);
        this.availableMoves.put(board[row][column], new LinkedList<>());
      }
    }
  }

  /**
   * Determines all the legal moves for a player.
   * This is represented by {@link Map} object that maps each {@link Square}
   * to a list of {@link Piece} that can reach it in one move.
   */
  public void update() {
    // Empty the list of availableMoves at each turn
    for (List<Piece> pieces : availableMoves.values()) {
      pieces.removeAll(pieces);
    }
    this.movableSquares.removeAll(movableSquares);
    // Add all the availableMoves (except for the King's)
    alivePieces.removeIf((piece -> !piece.getClass().equals(King.class) && piece.getPosition() == null));
    for (Piece p : alivePieces) {
      for (Square legalMoves : p.getLegalPositions(b)) {
        this.availableMoves.get(legalMoves).add(p);
      }
    }
  }

  /**
   * Determines whether the king is in check.
   * If the king is not in check, all the squares are movable, provided that the move does not lead to
   * a check position to the player.
   *
   * @param opponent white if current player is black, and vice versa.
   * @return {@code true} if the king is in check, {@code false} otherwise.
   */
  public boolean inCheck(Player opponent) {
    Map<Square, List<Piece>> opponentAvailableMoves = opponent.getAvailableMoves();
    Square kingPosition = king.getPosition();
    if (opponentAvailableMoves.get(kingPosition).isEmpty()) {
      movableSquares.addAll(squares);
      return false;
    }
    return true;
  }

  /**
   * Determines whether the player is checkmated.
   * For a player to be checkmated, they have to be in check and have no way
   * to break the check, such as by escaping, capturing or blocking the threats.
   *
   * @param opponent white if current player is black, and vice versa
   * @return true if the player is checkmated, false otherwise.
   */
  public boolean isCheckMated(Player opponent) {
    boolean checkmate = true;
    if (!inCheck(opponent.getAvailableMoves())) return false;
    if (canEvade(opponent)) checkmate = false;
    List<Piece> threats = opponent.getAvailableMoves().get(king.getPosition());
    if (canCapture(opponent)) checkmate = false;
    if (canBlock(opponent)) checkmate = false;
    return checkmate;
  }

  /**
   * Determines whether a move is valid, i.e. the move does not result
   * in a check for the player who performed it.
   *
   * @param p        piece that is moved
   * @param sq       new position for the piece.
   * @param opponent White is current instance is Black, and vice versa.
   * @return {@code true} if the move is valid, {@code false} otherwise.
   */
  public boolean testMove(Piece p, Square sq, Player opponent) {
    Piece c = sq.getOccupyingPiece();
    boolean moveTest = true;
    Square init = p.getPosition();

    p.move(sq);
    update();
    opponent.update();
    if (inCheck(opponent.getAvailableMoves())) {
      moveTest = false;
    }

    p.move(init);
    if (c != null)
      sq.setOccupyingPiece(c);
    update();
    opponent.update();
    movableSquares.addAll(squares);
    return moveTest;
  }

  /**
   * Determines if the king can escape the check position.
   * The method iterates through all the legal moves for the king. It tests
   * whether the king can actually move, i.e. if the move prolongs the check; if
   * it is the case, the position is recorded.
   *
   * @param opponent White if the instance is Black, and vice versa.
   * @return true if the king can escape, false otherwise.
   */
  public boolean canEvade(Player opponent) {
    boolean evade = false;
    for (Square kingMove : king.getLegalPositions(b)) {
      if (testMove(king, kingMove, opponent)) {
        if (opponent.getAvailableMoves().get(kingMove).isEmpty()) {
          movableSquares.add(kingMove);
          evade = true;
        }
      }
    }
    return evade;
  }

  /**
   * Determines whether a check position can be saved by capturing the
   * offending piece.
   *
   * @param opponent White if the current instance is Black, and vice versa
   * @return true if the threat can be captured, false otherwise.
   */
  public boolean canCapture(Player opponent) {
    // if there is more than one threat, all is lost
    boolean capture = false;
    List<Piece> threats = opponent.getAvailableMoves().get(king.getPosition());
    if (threats.size() == 1) {
      Square threatSquare = threats.get(0).getPosition();
      if (king.getLegalPositions(b).contains(threatSquare)) {
        movableSquares.add(threatSquare);
        if (testMove(king, threatSquare, opponent))
          capture = true;
      }
      if (!availableMoves.get(threatSquare).isEmpty()) {
        movableSquares.add(threatSquare);
        for (Piece p : availableMoves.get(threatSquare)) {
          if (testMove(p, threatSquare, opponent))
            capture = true;
        }
      }
    }
    return capture;
  }

  /**
   * Determines whether a check position can be saved by moving a piece
   * between the king and the threatening piece.
   *
   * @param opponent White if the current instance is Black, and vice versa
   * @return true if the threat can be blocked, false otherwise.
   */
  public boolean canBlock(Player opponent) {
    boolean blockable = false;
    List<Piece> threats = opponent.getAvailableMoves().get(king.getPosition());
    if (threats.size() == 1) {
      Piece threateningPiece = threats.get(0);
      for (Square sq : threateningPiece.getBlockableSquares(b, king)) {
        if (!availableMoves.get(sq).isEmpty()) {
          movableSquares.add(sq);
          for (Piece p : availableMoves.get(sq)) {
            if (testMove(p, sq, opponent))
              blockable = true;
          }
        }
      }
    }
    return blockable;
  }

  /**
   * Updates the {@link List<Square>} object that represents the squares on
   * which the player can move.
   * @param opponent white if current player is black, and vice versa.
   * @return squares on which the player can move.
   */
  public List<Square> getAllowableSquares(Player opponent){
    movableSquares.removeAll(movableSquares);
    if(inCheck(opponent))
      isCheckMated(opponent);
    return movableSquares;
  }

  public int getColor() {
    return color;
  }

  public List<Piece> getPieces() {
    return alivePieces;
  }

  public King getKing() {
    return king;
  }

  public Map<Square, List<Piece>> getAvailableMoves() {
    return availableMoves;
  }
}


