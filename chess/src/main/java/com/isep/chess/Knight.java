package com.isep.chess;

import java.util.LinkedList;
import java.util.List;

public class Knight extends Piece {
  public Knight(int color, Square currentSquare) {
    super(color, currentSquare);
  }

  @Override
  public List<Square> getLegalPositions(Board b) {
    List<Square> legalPositions = new LinkedList<>();
    Square[][] board = b.getBoardArray();
    int row = this.getPosition().getRow();
    int col = this.getPosition().getCol();
    int[] legalRowOffsets = {-2, -2, -1, -1, 1, 1, 2, 2};
    int[] legalColOffsets = {-1, 1, -2, 2, -2, 2, -1, 1};
    for (int i = 0; i < legalColOffsets.length; i++) {
      if (positionInBounds(row + legalRowOffsets[i], col + legalColOffsets[i]))
        legalPositions.add(board[row + legalRowOffsets[i]][col + legalColOffsets[i]]);
    }
    return legalPositions;
  }
}
