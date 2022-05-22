package com.isep.chess;

import java.util.LinkedList;
import java.util.List;

public class King extends Piece {
  public King(int color, Square currentSquare) {
    super(color, currentSquare);
  }

  @Override
  public List<Square> getLegalPositions(Board b) {
    List<Square> legalPositions = new LinkedList<>();
    Square[][] board = b.getBoardArray();
    int row = this.getPosition().getRow();
    int col = this.getPosition().getCol();

    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        if (i != j && positionInBounds(row + i, col + j)) {
          if (!board[row + i][col + j].isOccupied() || board[row + i][col + j].getOccupyingPiece().getColor() != this.getColor())
            legalPositions.add(board[row + i][col + j]);
        }
      }
    }
    return legalPositions;
  }
}
