package com.isep.chess;

import java.util.LinkedList;
import java.util.List;

public class Queen extends Piece {
  public Queen(int color, Square currentSquare) {
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
    legalPositions.addAll(getDiagonalOccupations(board, row, col));
    return legalPositions;
  }

  @Override
  List<Square> getBlockableSquares(Board b, Piece prey) {
    List<Square> blockableSquares = new LinkedList<>();
    blockableSquares.addAll(getLinearBlocks(b, prey));
    blockableSquares.addAll(getDiagonalBlocks(b, prey));
    return blockableSquares;
  }
}
