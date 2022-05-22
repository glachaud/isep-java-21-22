package com.isep.chess;

public class Board {
  private Square[][] board;
  public static final int BOARD_SIZE = 8;

  public Square[][] getBoardArray(){
    return this.board;
  }
}
