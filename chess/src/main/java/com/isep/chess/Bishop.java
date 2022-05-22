package com.isep.chess;

import java.util.List;

public class Bishop extends Piece {
  public Bishop(int color, Square currentSquare) {
    super(color, currentSquare);
  }

  @Override
  public List<Square> getLegalPositions(Board b) {
    Square[][] board = b.getBoardArray();
    int row = this.getPosition().getRow();
    int column = this.getPosition().getCol();
    return getDiagonalOccupations(board, row, column);
  }
}
