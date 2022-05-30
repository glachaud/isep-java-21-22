package com.isep.chess;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class Piece {
  private final int color;
  private Square currentSquare;

  public Piece(int color, Square currentSquare) {
    this.color = color;
    this.currentSquare = currentSquare;
  }

  public boolean move(Square fin) {
    Piece occup = fin.getOccupyingPiece();
    if (occup != null) {
      if (occup.getColor() == this.color)
        return false;  // cannot move on a square occupied by our own pieces.
      else
        fin.capture(this);  // we capture the piece (be careful about the king)
    }
    currentSquare.removePiece();
    this.currentSquare = fin;
    currentSquare.setOccupyingPiece(this);
    return true;
  }

  /**
   * Computes the limits of the horizontal and vertical moves of a piece on the board.
   *
   * @param board  represents the squares of the chess board, which can be occupied or empty.
   * @param row    corresponds to the arab numerals in chess notation, e.g. the 1 in "a1"
   * @param column corresponds to the letters in chess notation, e.g. the "a" in "a1"
   * @return {@code int[]} containing 4 values: minRow, maxRow, minCol, maxCol
   */
  public int[] getLinearOccupations(Square[][] board, int row, int column) {
    int minRow = 0;
    int maxRow = Board.BOARD_SIZE;
    int minCol = 0;
    int maxCol = Board.BOARD_SIZE;
    for (int i = 0; i < row; i++) {
      if (board[i][column].isOccupied()) {
        if (board[i][column].getOccupyingPiece().getColor() != this.color) {
          minRow = i;
        } else minRow = i - 1;
      }
    }
    for (int i = Board.BOARD_SIZE; i > row; i--) {
      if (board[i][column].isOccupied()) {
        if (board[i][column].getOccupyingPiece().getColor() != this.color) {
          maxRow = i;
        } else maxRow = i + 1;
      }
    }
    for (int j = 0; j < column; j++) {
      if (board[row][j].isOccupied()) {
        if (board[row][j].getOccupyingPiece().getColor() != this.color) {
          minCol = j;
        } else minCol = j - 1;
      }
    }
    for (int j = Board.BOARD_SIZE; j > column; j--) {
      if (board[row][j].isOccupied()) {
        if (board[row][j].getOccupyingPiece().getColor() != this.color) {
          maxCol = j;
        } else maxCol = j + 1;
      }
    }
    return new int[]{minRow, maxRow, minCol, maxCol};
  }

  /**
   * Returns the list of squares that can be reached by a diagonal move from the current
   * position of the piece.
   *
   * @param board  represents the squares of the chess board, which can be occupied or empty.
   * @param row    corresponds to the arab numerals in chess notation, e.g. the 1 in "a1"
   * @param column corresponds to the letters in chess notation, e.g. the "a" in "a1"
   * @return {@code List<@{link Square>}} of all the diagonal positions reachable from the current position.
   */
  public List<Square> getDiagonalOccupations(Square[][] board, int row, int column) {
    List<Square> diagonalPositions = new LinkedList<>();
    // Checks upper diagonals
    for (int i = row - 1; i >= 0; i--) {
      // Checks the upper left diagonal
      for (int j = column - 1; j >= 0; j--) {
        if (checkAddAndStopPosition(board, i, j, diagonalPositions))
          break;
      }
      // Checks the upper right diagonal
      for (int j = column + 1; j < Board.BOARD_SIZE; j++) {
        if (checkAddAndStopPosition(board, i, j, diagonalPositions))
          break;
      }
    }
    // Checks lower diagonals
    for (int i = row + 1; i < Board.BOARD_SIZE; i++) {
      // Checks the lower left diagonal
      for (int j = column - 1; j >= 0; j--) {
        if (checkAddAndStopPosition(board, i, j, diagonalPositions))
          break;
      }
      // Checks the lower right diagonal
      for (int j = column + 1; j < Board.BOARD_SIZE; j++) {
        if (checkAddAndStopPosition(board, i, j, diagonalPositions))
          break;
      }
    }
    return diagonalPositions;
  }

  protected boolean positionInBounds(int row, int column) {
    return row >= 0 && row < Board.BOARD_SIZE && column >= 0 && column <= Board.BOARD_SIZE;
  }

  public boolean checkAddAndStopPosition(Square[][] board, int row, int column, List<Square> diagonalPositions) {
    if (board[row][column].isOccupied()) {
      if (board[row][column].getOccupyingPiece().getColor() == this.color) {
        return true;
      } else {
        diagonalPositions.add(board[row][column]);
        return true;
      }
    }
    diagonalPositions.add(board[row][column]);
    return false;

  }

  /**
   * Returns the list of {@link Square} that stand between the piece and its prey in
   * either a horizontal or vertical way.
   * It is important for finding a way to block a check.
   * @param b represents the squares of the chess board, which can be occupied or empty.
   * @param p represents the piece to be captured, or checked if it is the king.
   * @return a list of {@link Square} that can block the capture.
   */
  public List<Square> getLinearBlocks(Board b, Piece p) {
    List<Square> blockableSquares = new LinkedList<>();
    int row = this.getPosition().getRow();
    int col = this.getPosition().getCol();
    int prey_row = this.getPosition().getRow();
    int prey_col = this.getPosition().getCol();
    Square[][] board = b.getBoardArray();

    if (row == prey_row) {
      int min_col = Math.min(col, prey_col);
      int max_col = Math.max(col, prey_col);
      blockableSquares.addAll(Arrays.asList(board[row]).subList(min_col + 1, max_col));
    }
    if (col == prey_col) {
      int min_row = Math.min(row, prey_row);
      int max_row = Math.max(row, prey_row);
      for (int i = min_row + 1; i < max_row; i++)
        blockableSquares.add(board[i][col]);
    }
    return blockableSquares;
  }

  /**
   * Returns the list of {@link Square} that stand between the piece and its prey in
   * a diagonal way.
   * It is important for finding a way to block a check. The four for loops describe
   * either of the relative positions between the two pieces: upper right diagonal,
   * lower left diagonal, etc.
   * @param b represents the squares of the chess board, which can be occupied or empty.
   * @param p represents the piece to be captured, or checked if it is the king.
   * @return a list of {@link Square} that can block the capture.
   */
  public List<Square> getDiagonalBlocks(Board b, Piece p) {
    List<Square> blockableSquares = new LinkedList<>();
    int row = this.getPosition().getRow();
    int col = this.getPosition().getCol();
    int prey_row = this.getPosition().getRow();
    int prey_col = this.getPosition().getCol();
    Square[][] board = b.getBoardArray();

    if (row > prey_row && col > prey_col) {
      for (int i = row + 1, j = col + 1; i < prey_row && j < prey_col; i++, j++) {
        blockableSquares.add(board[i][j]);
      }
    }
    if (row > prey_row && col < prey_col) {
      for (int i = row + 1, j = col - 1; i < prey_row && j > prey_col; i++, j--) {
        blockableSquares.add(board[i][j]);
      }
    }
    if (row < prey_row && col > prey_col) {
      for (int i = row - 1, j = col + 1; i > prey_row && j < prey_col; i--, j++) {
        blockableSquares.add(board[i][j]);
      }
    }
    if (row < prey_row && col < prey_col) {
      for (int i = row - 1, j = col - 1; i > prey_row && j > prey_col; i--, j--) {
        blockableSquares.add(board[i][j]);
      }
    }
    return blockableSquares;
  }

  public abstract List<Square> getLegalPositions(Board b);

  abstract List<Square> getBlockableSquares(Board b, Piece prey);

  public void setPosition(Square square) {
    this.currentSquare = square;
  }

  public Square getPosition() {
    return currentSquare;
  }

  public int getColor() {
    return color;
  }
}
