package com.andy.chess;

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.text.*;

@SuppressWarnings("serial")
public class Record extends JPanel implements ActionListener {

	JTextPane recordText = null;
	JScrollPane recordTextScrollPane = null;
	JButton recordUndoButton = null;
	Board board = null;
	Point[][] point = null;
	LinkedList<Step> moveRecord = null;
	LinkedList<Object> pieceEaten = null;
	char[] num = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };

	public Record(Board board, Point[][] point) {
		this.board = board;
		this.point = point;
		moveRecord = new LinkedList<Step>();
		pieceEaten = new LinkedList<Object>();
		recordText = new JTextPane();
		recordTextScrollPane = new JScrollPane(recordText);
		recordUndoButton = new JButton("Undo");
		recordUndoButton.setFont(new Font("Consolas", Font.PLAIN, 18));
		setLayout(new BorderLayout());
		add(recordTextScrollPane, BorderLayout.CENTER);
		add(recordUndoButton, BorderLayout.SOUTH);
		insertMsg("Start!\n", true, Color.GRAY, 18);
		recordUndoButton.addActionListener(this);
	}

	public void recordStep(Pieces piece, int sX, int sY, int eX, int eY) {
		java.awt.Point startPoint = new java.awt.Point(sX, sY);
		java.awt.Point endPoint = new java.awt.Point(eX, eY);
		Step step = new Step(startPoint, endPoint);
		moveRecord.add(step);
		insertMsg("MOVE: ", true, Color.BLACK, 14);
		String oneStep = piece.getPieceName() + " - " + num[sX] + (sY + 1) + " to " + num[eX] + (eY + 1) + "\n";
		if (piece.getPieceStyle().equals("red")) {
			insertMsg(oneStep, false, Color.RED, 15);
		} else {
			insertMsg(oneStep, false, Color.BLACK, 15);
		}
	}

	public void recordPieceEaten(Object object) {
		pieceEaten.add(object);
		if (object != "null") {
			Pieces piece = (Pieces) object;
			if (piece.getPieceStyle().equals("red")) {
				insertMsg(piece.getPieceName(), false, Color.RED, 15);
			} else {
				insertMsg(piece.getPieceName(), false, Color.BLACK, 15);
			}
			String eatMsg = " had been eaten!\n";
			insertMsg(eatMsg, false, Color.BLUE, 15);
		}
	}

	public void insertMsg(String str, boolean isBold, Color color, int fontSize) {
		StyledDocument doc = recordText.getStyledDocument();
		SimpleAttributeSet attr = new SimpleAttributeSet();
		StyleConstants.setBold(attr, isBold);
		StyleConstants.setForeground(attr, color);
		StyleConstants.setFontSize(attr, fontSize);
		try {
			doc.insertString(doc.getLength(), str, attr);
		} catch (BadLocationException ble) {
			System.out.println("BadLocationException:   " + ble);
		}
	}

	public LinkedList<Step> getChessRecord() {
		return moveRecord;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (moveRecord.size() > 0) {
			Step lastStep = moveRecord.getLast();
			moveRecord.removeLast();
			int startX = lastStep.startPoint.x;
			int startY = lastStep.startPoint.y;
			int endX = lastStep.endPoint.x;
			int endY = lastStep.endPoint.y;
			Pieces endPiece = point[endX][endY].getPiece();
			point[startX][startY].setPieceLocate(board, endPiece);
			if (endPiece.getPieceStyle().equals("red")) {
				insertMsg("Red", false, Color.RED, 15);
			} else {
				insertMsg("Black", false, Color.BLACK, 15);
			}
			insertMsg(" retract a false move!\n", false, Color.CYAN, 15);
			Object eatenPiece = pieceEaten.getLast();
			pieceEaten.removeLast();
			if (eatenPiece == "null") {
				point[endX][endY].setChessed(false);
			} else {
				point[endX][endY].setPieceLocate(board, (Pieces) eatenPiece);
				point[endX][endY].setChessed(true);
			}
			if (endPiece.getPieceStyle().equals("red")) {
				board.turnToRed = true;
				board.turnToBlack = false;
			} else {
				board.turnToRed = false;
				board.turnToBlack = true;
			}
		}

	}

}

@SuppressWarnings("serial")
class Step implements Serializable {
	public java.awt.Point startPoint, endPoint;

	public Step(java.awt.Point start, java.awt.Point end) {
		this.startPoint = start;
		this.endPoint = end;
	}

}