package com.andy.chess;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
	public static final int BOARD_ROW = 10;
	public static final int BOARD_COLUMN = 9;
	public static final int BOARD_EDGE_X = 68;
	public static final int BOARD_EDGE_Y = 46;
	public static final int BOARD_UNIT_SIZE = 64;

	public Point boardPoint[][];
	private Image backgroundImg;
	public Image pieceImg;
	Pieces tempPiece = null;

	Rule boardRule = null;
	public Record boardRecord = null;
	boolean turnToRed = true, turnToBlack = false;
	int startX, startY, endX, endY;

	Pieces rChe1, rChe2, rMa1, rMa2, rXiang1, rXiang2, rShuai, rShi1, rShi2, rPao1, rPao2, rBin1, rBin2, rBin3, rBin4,
			rBin5;
	Pieces bChe1, bChe2, bMa1, bMa2, bXiang1, bXiang2, bJiang, bShi1, bShi2, bPao1, bPao2, bZu1, bZu2, bZu3, bZu4, bZu5;

	public Board() {
		setLayout(null);
		addMouseListener(this);
		addMouseMotionListener(this);
		Color bg = new Color(254, 243, 219);
		boardPoint = new Point[BOARD_ROW][BOARD_COLUMN];
		for (int i = 0; i < BOARD_ROW; i++) {
			for (int j = 0; j < BOARD_COLUMN; j++) {
				boardPoint[i][j] = new Point(BOARD_EDGE_X + 64 * j, BOARD_EDGE_Y + 64 * i, false);
			}
		}
		backgroundImg = Toolkit.getDefaultToolkit()
				.getImage("/Users/Andy/Documents/workspace/MMChess/src/background.jpg");
		pieceImg = Toolkit.getDefaultToolkit().getImage("/Users/Andy/Documents/workspace/MMChess/src/piecebg.png");

		boardRule = new Rule(this, boardPoint);
		boardRecord = new Record(this, boardPoint);
		// Initialize the pieces
		rChe1 = new Pieces("車", Color.RED, bg, this);
		rChe1.setPieceStyle("red");
		rChe2 = new Pieces("車", Color.RED, bg, this);
		rChe2.setPieceStyle("red");
		rMa1 = new Pieces("馬", Color.RED, bg, this);
		rMa1.setPieceStyle("red");
		rMa2 = new Pieces("馬", Color.RED, bg, this);
		rMa2.setPieceStyle("red");
		rXiang1 = new Pieces("相", Color.RED, bg, this);
		rXiang1.setPieceStyle("red");
		rXiang2 = new Pieces("相", Color.RED, bg, this);
		rXiang2.setPieceStyle("red");
		rShuai = new Pieces("帅", Color.RED, bg, this);
		rShuai.setPieceStyle("red");
		rShi1 = new Pieces("仕", Color.RED, bg, this);
		rShi1.setPieceStyle("red");
		rShi2 = new Pieces("仕", Color.RED, bg, this);
		rShi2.setPieceStyle("red");
		rPao1 = new Pieces("炮", Color.RED, bg, this);
		rPao1.setPieceStyle("red");
		rPao2 = new Pieces("炮", Color.RED, bg, this);
		rPao2.setPieceStyle("red");
		rBin1 = new Pieces("兵", Color.RED, bg, this);
		rBin1.setPieceStyle("red");
		rBin2 = new Pieces("兵", Color.RED, bg, this);
		rBin2.setPieceStyle("red");
		rBin3 = new Pieces("兵", Color.RED, bg, this);
		rBin3.setPieceStyle("red");
		rBin4 = new Pieces("兵", Color.RED, bg, this);
		rBin4.setPieceStyle("red");
		rBin5 = new Pieces("兵", Color.RED, bg, this);
		rBin5.setPieceStyle("red");

		bChe1 = new Pieces("车", Color.BLACK, bg, this);
		bChe1.setPieceStyle("black");
		bChe2 = new Pieces("车", Color.BLACK, bg, this);
		bChe2.setPieceStyle("black");
		bMa1 = new Pieces("马", Color.BLACK, bg, this);
		bMa1.setPieceStyle("black");
		bMa2 = new Pieces("马", Color.BLACK, bg, this);
		bMa2.setPieceStyle("black");
		bXiang1 = new Pieces("象", Color.BLACK, bg, this);
		bXiang1.setPieceStyle("black");
		bXiang2 = new Pieces("象", Color.BLACK, bg, this);
		bXiang2.setPieceStyle("black");
		bJiang = new Pieces("将", Color.BLACK, bg, this);
		bJiang.setPieceStyle("black");
		bShi1 = new Pieces("士", Color.BLACK, bg, this);
		bShi1.setPieceStyle("black");
		bShi2 = new Pieces("士", Color.BLACK, bg, this);
		bShi2.setPieceStyle("black");
		bPao1 = new Pieces("炮", Color.BLACK, bg, this);
		bPao1.setPieceStyle("black");
		bPao2 = new Pieces("炮", Color.BLACK, bg, this);
		bPao2.setPieceStyle("black");
		bZu1 = new Pieces("卒", Color.BLACK, bg, this);
		bZu1.setPieceStyle("black");
		bZu2 = new Pieces("卒", Color.BLACK, bg, this);
		bZu2.setPieceStyle("black");
		bZu3 = new Pieces("卒", Color.BLACK, bg, this);
		bZu3.setPieceStyle("black");
		bZu4 = new Pieces("卒", Color.BLACK, bg, this);
		bZu4.setPieceStyle("black");
		bZu5 = new Pieces("卒", Color.BLACK, bg, this);
		bZu5.setPieceStyle("black");

		boardPoint[0][0].setPieceLocate(this, bChe1);
		boardPoint[0][1].setPieceLocate(this, bMa1);
		boardPoint[0][2].setPieceLocate(this, bXiang1);
		boardPoint[0][3].setPieceLocate(this, bShi1);
		boardPoint[0][4].setPieceLocate(this, bJiang);
		boardPoint[0][5].setPieceLocate(this, bShi2);
		boardPoint[0][6].setPieceLocate(this, bXiang2);
		boardPoint[0][7].setPieceLocate(this, bMa2);
		boardPoint[0][8].setPieceLocate(this, bChe2);
		boardPoint[2][1].setPieceLocate(this, bPao1);
		boardPoint[2][7].setPieceLocate(this, bPao2);
		boardPoint[3][0].setPieceLocate(this, bZu1);
		boardPoint[3][2].setPieceLocate(this, bZu2);
		boardPoint[3][4].setPieceLocate(this, bZu3);
		boardPoint[3][6].setPieceLocate(this, bZu4);
		boardPoint[3][8].setPieceLocate(this, bZu5);

		boardPoint[6][0].setPieceLocate(this, rBin1);
		boardPoint[6][2].setPieceLocate(this, rBin2);
		boardPoint[6][4].setPieceLocate(this, rBin3);
		boardPoint[6][6].setPieceLocate(this, rBin4);
		boardPoint[6][8].setPieceLocate(this, rBin5);
		boardPoint[7][1].setPieceLocate(this, rPao1);
		boardPoint[7][7].setPieceLocate(this, rPao2);
		boardPoint[9][0].setPieceLocate(this, rChe1);
		boardPoint[9][1].setPieceLocate(this, rMa1);
		boardPoint[9][2].setPieceLocate(this, rXiang1);
		boardPoint[9][3].setPieceLocate(this, rShi1);
		boardPoint[9][4].setPieceLocate(this, rShuai);
		boardPoint[9][5].setPieceLocate(this, rShi2);
		boardPoint[9][6].setPieceLocate(this, rXiang2);
		boardPoint[9][7].setPieceLocate(this, rMa2);
		boardPoint[9][8].setPieceLocate(this, rChe2);

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Set the background image
		int bgImgWidth = backgroundImg.getWidth(this);
		int bgImgHeight = backgroundImg.getHeight(this);
		int windowWidth = getWidth();
		int windowHeight = getHeight();
		int x = (windowWidth - bgImgWidth) / 2;
		int y = (windowHeight - bgImgHeight) / 2;
		g.drawImage(backgroundImg, x, y, null);
		// Draw the row and column
		g.setFont(new Font("Consolas", Font.PLAIN, 15));
		for (int i = 1; i <= BOARD_COLUMN; i++) {
			g.drawString("" + i, i * BOARD_UNIT_SIZE - 4, BOARD_UNIT_SIZE / 5);
		}
		int j = 1;
		for (char c = 'A'; c <= 'J'; c++, j++) {
			g.drawString("" + c, BOARD_UNIT_SIZE / 3, j * BOARD_UNIT_SIZE - 17);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Pieces piece = null;
		Rectangle rect = null;
		if (e.getSource() == this) {
			if (tempPiece != null) {
				for (int i = 0; i < BOARD_ROW; i++) {
					for (int j = 0; j < BOARD_COLUMN; j++) {
						int x = boardPoint[i][j].getX() - 5;
						int y = boardPoint[i][j].getY() - 6;
						rect = new Rectangle(x - 25, y - 25, 50, 50);
						if (rect.contains(e.getX(), e.getY())) {
							endX = i;
							endY = j;
							break;
						}
					}
				}

				boolean canMove = boardRule.canPieceMove(tempPiece, startX, startY, endX, endY);
				if (canMove) {
					boardPoint[endX][endY].setPieceLocate(this, tempPiece);
					boardPoint[endX][endY].setChessed(true);
					boardPoint[startX][startY].setChessed(false);
					boardRecord.recordStep(tempPiece, startX, startY, endX, endY);
					boardRecord.recordPieceEaten("null");
					if (tempPiece.getPieceStyle().equals("red")) {
						turnToRed = false;
						turnToBlack = true;
					}
					if (tempPiece.getPieceStyle().equals("black")) {
						turnToRed = true;
						turnToBlack = false;
					}
				}

				tempPiece.setBorder(null);
				tempPiece = null;
			}
		}
		if (e.getSource() instanceof Pieces) {
			piece = (Pieces) e.getSource();

			if (tempPiece != null) {
				if ((turnToRed && piece.getPieceStyle().equals("red"))
						|| (turnToBlack && piece.getPieceStyle().equals("black"))) {
					//
				} else if ((turnToRed && piece.getPieceStyle().equals("black"))
						|| (turnToBlack && piece.getPieceStyle().equals("red"))) {
					rect = piece.getBounds();
					for (int i = 0; i < BOARD_ROW; i++) {
						for (int j = 0; j < BOARD_COLUMN; j++) {
							int x = boardPoint[i][j].getX();
							int y = boardPoint[i][j].getY();
							if (rect.contains(x, y)) {
								endX = i;
								endY = j;
								break;
							}
						}
					}
					boolean canMove = boardRule.canPieceMove(tempPiece, startX, startY, endX, endY);
					if (canMove) {
						boardPoint[endX][endY].removePiece(this, piece);
						boardPoint[endX][endY].setPieceLocate(this, tempPiece);
						boardPoint[startX][startY].setChessed(false);
						boardRecord.recordStep(tempPiece, startX, startY, endX, endY);
						boardRecord.recordPieceEaten(piece);
						if (boardRule.isOver(piece)) {
							if (piece.getPieceStyle() == "red") {
								JOptionPane.showMessageDialog(null, "Black Win！");
								turnToBlack = false;
							} else {
								JOptionPane.showMessageDialog(null, "Red Win！");
								turnToRed = false;
							}
						} else {
							piece = null;
							if (tempPiece.getPieceStyle().equals("red")) {
								turnToRed = false;
								turnToBlack = true;
							}
							if (tempPiece.getPieceStyle().equals("black")) {
								turnToRed = true;
								turnToBlack = false;
							}
						}
					}
				}

				tempPiece.setBorder(null);
				tempPiece = null;
			}
			if (piece != null) {
				if (turnToRed && piece.getPieceStyle().equals("red")) {
					piece.setBorder(BorderFactory.createLineBorder(Color.RED, 4, true));
					tempPiece = piece;
				}
				if (turnToBlack && piece.getPieceStyle().equals("black")) {
					piece.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4, true));
					tempPiece = piece;
				}

				rect = piece.getBounds();
				for (int i = 0; i < BOARD_ROW; i++) {
					for (int j = 0; j < BOARD_COLUMN; j++) {
						int x = boardPoint[i][j].getX();
						int y = boardPoint[i][j].getY();
						if (rect.contains(x, y)) {
							startX = i;
							startY = j;
							break;
						}
					}
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
