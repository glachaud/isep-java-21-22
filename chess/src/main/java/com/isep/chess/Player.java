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
    this.b = b;
    Square[][] board = b.getBoardArray();
    for (int row = 0; row < Board.BOARD_SIZE; row++) {
      for (int column = 0; column < Board.BOARD_SIZE; column++) {
        squares.add(board[row][column]);
        this.availableMoves.put(board[row][column], new LinkedList<>());
      }
    }
  }

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
   * @param opponentAvailableMoves {@link Map} of {@link Square} indicating which opponent's piece can reach square.
   * @return {@code true} if the king is in check, {@code false} otherwise.
   */
  public boolean inCheck(Map<Square, List<Piece>> opponentAvailableMoves) {
    Square kingPosition = king.getPosition();
    if (opponentAvailableMoves.get(kingPosition).isEmpty()) {
      movableSquares.addAll(squares);
      return false;
    }
    return true;
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


