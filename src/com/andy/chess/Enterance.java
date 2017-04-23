package com.andy.chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.LinkedList;

@SuppressWarnings("serial")
public class Enterance extends JFrame implements ActionListener {

	public static final int BOARD_ROW = 10;
	public static final int BOARD_COLUMN = 9;
	public static final int BOARD_EDGE_X = 41;
	public static final int BOARD_EDGE_Y = 48;

	Board board = null;
	Record record = null;
	JMenuBar bar;
	JMenu fileMenu;
	Container container = null;
	JMenuItem makeRecord, saveRecord, demoRecord;
	JFileChooser fileChooser = null;
	Demonstrate demonstrate = null;
	LinkedList<Step> demoRecordList = null;

	public Enterance() {
		bar = new JMenuBar();
		fileMenu = new JMenu("Menu");
		makeRecord = new JMenuItem("Make new record");
		saveRecord = new JMenuItem("Save record");
		demoRecord = new JMenuItem("Load record");
		fileMenu.add(makeRecord);
		fileMenu.add(saveRecord);
		fileMenu.add(demoRecord);
		bar.add(fileMenu);
		setJMenuBar(bar);
		makeRecord.addActionListener(this);
		saveRecord.addActionListener(this);
		demoRecord.addActionListener(this);
		setTitle("MMChess - " + makeRecord.getText());
		board = new Board();
		record = board.boardRecord;
		container = getContentPane();
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, record, board);
		split.setDividerSize(5);
		split.setDividerLocation(200);
		split.setEnabled(false);
		container.add(split, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(60, 20, 844, 700);
		setResizable(false);
		fileChooser = new JFileChooser();
		container.validate();
		validate();
	}

	public static void main(String[] args) {
		new Enterance();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == makeRecord) {
			container.removeAll();
			setTitle("MMChess - " + makeRecord.getText());
			board = new Board();
			record = board.boardRecord;
			JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, record, board);
			split.setDividerSize(5);
			split.setDividerLocation(200);
			split.setEnabled(false);
			container.add(split, BorderLayout.CENTER);
			saveRecord.setEnabled(true);
			validate();
		}
		if (e.getSource() == saveRecord) {
			int status = fileChooser.showSaveDialog(null);
			File fileToSave = fileChooser.getSelectedFile();
			if (fileToSave != null && status == JFileChooser.APPROVE_OPTION) {
				try {
					ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileToSave));
					out.writeObject(record.getChessRecord());
					out.close();
				} catch (IOException iox) {
					JOptionPane.showMessageDialog(null, "Fail to save the record!");
				}
			}
		}
		if (e.getSource() == demoRecord) {
			container.removeAll();
			container.repaint();
			container.validate();
			validate();
			saveRecord.setEnabled(false);
			int status = fileChooser.showOpenDialog(null);
			File fileToOpen = fileChooser.getSelectedFile();
			if (fileToOpen != null && status == JFileChooser.APPROVE_OPTION) {
				try {
					ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileToOpen));
					demoRecordList = (LinkedList<Step>) in.readObject();
					in.close();
					board = new Board();
					demonstrate = new Demonstrate(board);
					demonstrate.setDemoRecord(demoRecordList);
					container.add(demonstrate, BorderLayout.CENTER);
					container.validate();
					validate();
					this.setTitle(demoRecord.getText() + " - " + fileToOpen);
				} catch (Exception event) {
					JOptionPane.showMessageDialog(null, "Fail to load the record!");
				}
			} else {
				JOptionPane.showMessageDialog(null, "No file selected!");
			}
		}

	}

}
