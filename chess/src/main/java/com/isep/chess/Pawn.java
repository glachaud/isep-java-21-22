package com.isep.chess;

import java.util.LinkedList;
import java.util.List;

public class Pawn extends Piece {
  private boolean wasMoved;

  public Pawn(int color, Square currentSquare) {
    super(color, currentSquare);
    this.wasMoved = false;
  }

  @Override
  public boolean move(Square fin) {
    boolean b = super.move(fin);
    this.wasMoved = true;
    return b;
  }

  @Override
  public List<Square> getLegalPositions(Board b) {
    List<Square> legalPositions = new LinkedList<>();
    Square[][] board = b.getBoardArray();
    int row = this.getPosition().getRow();
    int col = this.getPosition().getCol();
    int color = this.getColor();
    // Normal pawn move
    if (positionInBounds(row + color, col)) {
      if (!board[row + color][col].isOccupied())
        legalPositions.add(board[row + color][col]);
    }
    // Pawn move available only if pawn has not already moved
    if (positionInBounds(row + 2 * color, col)) {
      if (!board[row + 2 * color][col].isOccupied())
        legalPositions.add(board[row + 2 * color][col]);
    }
    // Pawn right attack
    if (positionInBounds(row + color, col + 1)) {
      if (board[row + color][col + 1].isOccupied() && board[row + color][col + 1].getColor() != color)
        legalPositions.add(board[row + color][col + 1]);
    }
    // Pawn left attack
    if (positionInBounds(row + color, col - 1)) {
      if (board[row + color][col - 1].isOccupied() && board[row + color][col - 1].getColor() != color)
        legalPositions.add(board[row + color][col - 1]);
    }
    return legalPositions;
  }
}
