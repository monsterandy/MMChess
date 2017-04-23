package com.andy.chess;

public class Point {
	private int x;
	private int y;
	private boolean isChessed;

	Pieces piece = null;
	Board board = null;

	public Point(int x, int y, boolean ice) {
		this.x = x;
		this.y = y;
		isChessed = ice;
	}

	public void setPieceLocate(Board board, Pieces piece) {
		this.board = board;
		this.piece = piece;
		piece.setBounds(x - Board.BOARD_UNIT_SIZE / 2, y - Board.BOARD_UNIT_SIZE / 2, Pieces.PIECE_SIZE + 2,
				Pieces.PIECE_SIZE + 2);
		isChessed = true;
		board.add(piece);
		board.validate();
	}

	public void removePiece(Board board, Pieces piece) {
		this.board = board;
		this.piece = piece;
		board.remove(piece);
		isChessed = false;
		board.validate();
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isChessExist() {
		return isChessed;
	}

	public void setChessed(boolean ice) {
		isChessed = ice;
	}

	public Pieces getPiece() {
		return piece;
	}

}
