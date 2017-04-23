package com.andy.chess;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.text.*;

@SuppressWarnings("serial")
public class Demonstrate extends JPanel implements ActionListener, Runnable {
	JTextPane demoText = null;
	JScrollPane demoTextScrollPane = null;
	JButton demoNextStep = null;
	JButton demoReplay = null;
	JButton demoAutoPlay = null;
	JButton demoPausePlay = null;
	JTextField demoTimeInterval = null;
	JSplitPane leftPane = null, demoPane = null;
	Thread autoPlayThread = null;
	char[] num = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };
	int index = -1;
	int time = 1000;
	boolean isPaused = false;

	Board board = null;
	LinkedList<Step> moveRecord = null;

	public Demonstrate(Board board) {
		this.board = board;
		demoNextStep = new JButton("Next");
		demoReplay = new JButton("Replay");
		demoAutoPlay = new JButton("AutoPlay");
		demoPausePlay = new JButton("Pause");
		demoNextStep.addActionListener(this);
		demoReplay.addActionListener(this);
		demoAutoPlay.addActionListener(this);
		demoPausePlay.addActionListener(this);
		autoPlayThread = new Thread(this);
		demoText = new JTextPane();
		demoTimeInterval = new JTextField("2");
		demoTextScrollPane = new JScrollPane(demoText);
		setLayout(new BorderLayout());
		JPanel leftBottomPane = new JPanel(new GridLayout(3, 2));
		leftBottomPane.add(demoNextStep);
		leftBottomPane.add(demoReplay);
		leftBottomPane.add(demoAutoPlay);
		leftBottomPane.add(demoPausePlay);
		leftBottomPane.add(new JLabel("Time Interval(s)"));
		leftBottomPane.add(demoTimeInterval);
		leftPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, demoTextScrollPane, leftBottomPane);
		demoPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, board);
		leftPane.setDividerSize(5);
		leftPane.setDividerLocation(550);
		demoPane.setDividerSize(5);
		demoPane.setDividerLocation(200);
		demoPane.setEnabled(false);
		add(demoPane, BorderLayout.CENTER);
		validate();
		insertMsg("Start!\n", true, Color.GRAY, 18);
	}

	public void setDemoRecord(LinkedList<Step> record) {
		this.moveRecord = record;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == demoNextStep) {
			index++;
			if (index < moveRecord.size()) {
				demoOneStep(index);
			} else {
				insertMsg("Demonstration Over!\n", false, Color.GRAY, 15);
				JOptionPane.showMessageDialog(null, "Demonstration Finish!");
				board.turnToBlack = false;
				board.turnToRed = false;
			}
		}
		if (e.getSource() == demoReplay) {
			demoPane.remove(board);
			board = new Board();
			demoPane.setDividerSize(5);
			demoPane.setDividerLocation(200);
			demoPane.setEnabled(false);
			demoPane.setRightComponent(board);
			demoPane.validate();
			index = -1;
			demoText.setText(null);
			insertMsg("Start!\n", true, Color.GRAY, 18);
		}
		if (e.getSource() == demoAutoPlay) {
			demoNextStep.setEnabled(false);
			demoReplay.setEnabled(false);
			try {
				time = 1000 * Integer.parseInt(demoTimeInterval.getText().trim());
			} catch (Exception e2) {
				time = 1000;
			}
			if (!autoPlayThread.isAlive()) {
				autoPlayThread = new Thread(this);
				demoPane.remove(board);
				board = new Board();
				demoPane.setDividerSize(5);
				demoPane.setDividerLocation(200);
				demoPane.setEnabled(false);
				demoPane.setRightComponent(board);
				demoPane.validate();
				index = -1;
				demoText.setText(null);
				insertMsg("Start!\n", true, Color.GRAY, 18);
				autoPlayThread.start();
			}
		}
		if (e.getSource() == demoPausePlay) {
			if (e.getActionCommand().equals("Pause")) {
				isPaused = true;
				demoPausePlay.setText("Continue");
			}
			if (e.getActionCommand().equals("Continue")) {
				isPaused = false;
				autoPlayThread.interrupt();
				demoPausePlay.setText("Pause");
			}
		}
	}

	public void demoOneStep(int index) {
		Step step = moveRecord.get(index);
		java.awt.Point startPoint = step.startPoint, endPoint = step.endPoint;
		int sX = startPoint.x;
		int sY = startPoint.y;
		int eX = endPoint.x;
		int eY = endPoint.y;
		Pieces piece = board.boardPoint[sX][sY].getPiece();
		if (board.boardPoint[eX][eY].isChessExist()) {
			Pieces eatenPiece = board.boardPoint[eX][eY].getPiece();
			board.boardPoint[eX][eY].removePiece(board, eatenPiece);
			board.boardPoint[eX][eY].setPieceLocate(board, piece);
			board.boardPoint[sX][sY].setChessed(false);
			insertMsg("MOVE: ", true, Color.BLACK, 14);
			String oneStep = piece.getPieceName() + " - " + num[sX] + (sY + 1) + " to " + num[eX] + (eY + 1) + "\n";
			if (piece.getPieceStyle().equals("red")) {
				insertMsg(oneStep, false, Color.RED, 15);
			} else {
				insertMsg(oneStep, false, Color.BLACK, 15);
			}
			if (eatenPiece.getPieceStyle().equals("red")) {
				insertMsg(eatenPiece.getPieceName(), false, Color.RED, 15);
			} else {
				insertMsg(eatenPiece.getPieceName(), false, Color.BLACK, 15);
			}
			String eatMsg = " had been eaten!\n";
			insertMsg(eatMsg, false, Color.BLUE, 15);
		} else {
			board.boardPoint[eX][eY].setPieceLocate(board, piece);
			board.boardPoint[sX][sY].setChessed(false);
			insertMsg("MOVE: ", true, Color.BLACK, 14);
			String oneStep = piece.getPieceName() + " - " + num[sX] + (sY + 1) + " to " + num[eX] + (eY + 1) + "\n";
			if (piece.getPieceStyle().equals("red")) {
				insertMsg(oneStep, false, Color.RED, 15);
			} else {
				insertMsg(oneStep, false, Color.BLACK, 15);
			}
		}
	}

	public void insertMsg(String str, boolean isBold, Color color, int fontSize) {
		StyledDocument doc = demoText.getStyledDocument();
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

	@Override
	public synchronized void run() {
		for (index = 0; index < moveRecord.size(); index++) {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while (isPaused == true) {
				try {
					wait();
				} catch (InterruptedException e) {
					notifyAll();
				}
			}
			demoOneStep(index);

		}
		if (index >= moveRecord.size()) {
			insertMsg("Demonstration Over!\n", false, Color.GRAY, 15);
			JOptionPane.showMessageDialog(null, "Demonstration Finish!");
			board.turnToBlack = false;
			board.turnToRed = false;
			demoNextStep.setEnabled(true);
			demoReplay.setEnabled(true);
		}

	}

}
