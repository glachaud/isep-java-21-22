package com.isep.chess;

import java.util.LinkedList;
import java.util.List;

public class Rook extends Piece {
  public Rook(int color, Square currentSquare) {
    super(color, currentSquare);
  }

  @Override
  public List<Square> getLegalPositions(Board b) {
    List<Square> legalPositions = new LinkedList<>();
    Square[][] board = b.getBoardArray();
    int row = this.getPosition().getRow();
    int col = this.getPosition().getCol();

    int[] positionLimits = getLinearOccupations(board, row, col);
    for (int i = positionLimits[0]; i <= positionLimits[1]; i++) {
      if (i != row) legalPositions.add(board[i][col]);
    }
    for (int j = positionLimits[2]; j <= positionLimits[3]; j++) {
      if (j != col) legalPositions.add(board[row][j]);
    }
    return legalPositions;
  }
}