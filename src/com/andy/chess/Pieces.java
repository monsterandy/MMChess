package com.andy.chess;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Pieces extends JLabel {
	public static final int PIECE_SIZE = 50;
	public static final float PIECE_EDGE_WIDTH = 3.0f;
	String pieceName;
	Color pieceBackColor = null;
	Color pieceForeColor;
	String pieceStyle = null;
	Board board = null;

	public Pieces(String name, Color fc, Color bc, Board board) {
		this.pieceName = name;
		this.pieceBackColor = bc;
		this.pieceForeColor = fc;
		this.board = board;
		setSize(20, 20);
		setBackground(pieceBackColor);
		addMouseMotionListener(board);
		addMouseListener(board);
	}

	public int getPieceSize() {
		return PIECE_SIZE;
	}

	public String getPieceName() {
		return pieceName;
	}

	public Color getPieceColor() {
		return pieceForeColor;
	}

	public void setPieceStyle(String style) {
		pieceStyle = style;
	}

	public String getPieceStyle() {
		return pieceStyle;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(board.pieceImg, 2, 2, PIECE_SIZE - 2, PIECE_SIZE - 2, null);
		g.setColor(pieceForeColor);
		g.setFont(new Font("STBaoliTC-Regular", Font.PLAIN, 36));
		g.drawString(pieceName, 9, PIECE_SIZE - 13);
		g.setColor(new Color(254, 243, 219));
		// GRaphics2D
		((Graphics2D) g).setStroke(new BasicStroke(PIECE_EDGE_WIDTH));
		((Graphics2D) g).drawOval(2, 2, PIECE_SIZE - 2, PIECE_SIZE - 2);
	}
}
