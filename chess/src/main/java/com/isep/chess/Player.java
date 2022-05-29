package com.isep.chess;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Player {
  private final int color;
  private Board b;
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
    for (int i = 0; i < Board.BOARD_SIZE; i++) {
      for (int j = 0; j < Board.BOARD_SIZE; j++) {
        this.availableMoves.put(board[i][j], new LinkedList<>());
      }
    }
  }

  public void update() {
    // Empty the list of availableMoves at each turn
    for (List<Piece> pieces : availableMoves.values()){
      pieces.removeAll(pieces);
    }
    this.movableSquares.removeAll(movableSquares);
    // Add all the availableMoves (except for the King's)
    alivePieces.removeIf((piece -> !piece.getClass().equals(King.class) && piece.getPosition() == null));
    for(Piece p: alivePieces){
      for(Square legalMoves : p.getLegalPositions(b)){
        this.availableMoves.get(legalMoves).add(p);
      }
    }
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


