package com.isep.chess;

public class Square {
  private int color;
  private Piece occupyingPiece;

  private int row;
  private int col;

  public Piece getOccupyingPiece() {
    return occupyingPiece;
  }

  public void setOccupyingPiece(Piece p) {
    this.occupyingPiece = p;
    p.setPosition(this);
  }

  public boolean isOccupied() {
    return (this.occupyingPiece != null);
  }

  public Piece removePiece(){
    Piece p = this.occupyingPiece;
    this.occupyingPiece = null;
    return p;
  }

  public void capture(Piece p){
    Piece k = this.occupyingPiece;
    //TODO (glachaud) do something to remove the piece from the white or black pieces.
    this.occupyingPiece = p;
  }

}
