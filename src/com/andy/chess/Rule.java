package com.andy.chess;

public class Rule {
	Board board = null;
	Pieces piece = null;
	Point point[][];
	boolean isMoveable = false;
	int sX, sY, eX, eY;

	public Rule(Board board, Point point[][]) {
		this.board = board;
		this.point = point;
	}

	public boolean canPieceMove(Pieces piece, int sX, int sY, int eX, int eY) {
		isMoveable = false;
		this.piece = piece;
		this.sX = sX;
		this.sY = sY;
		this.eX = eX;
		this.eY = eY;
		int maxX = Math.max(sX, eX);
		int minX = Math.min(sX, eX);
		int maxY = Math.max(sY, eY);
		int minY = Math.min(sY, eY);
		if (piece.getPieceName().equals("车") || piece.getPieceName().equals("車")) {
			if (sX == eX) {
				isMoveable = true;
				for (int i = minY + 1; i < maxY; i++) {
					if (point[sX][i].isChessExist()) {
						isMoveable = false;
						break;
					}
				}
			} else if (sY == eY) {
				isMoveable = true;
				for (int i = minX + 1; i < maxX; i++) {
					if (point[i][sY].isChessExist()) {
						isMoveable = false;
						break;
					}
				}
			} else {
				isMoveable = false;
			}
		} else if (piece.getPieceName().equals("马") || piece.getPieceName().equals("馬")) {
			int lengthX = Math.abs(sX - eX);
			int lengthY = Math.abs(sY - eY);
			if (lengthX == 2 && lengthY == 1) {
				if (eX > sX) {
					if (point[sX + 1][sY].isChessExist())
						isMoveable = false;
					else
						isMoveable = true;
				}
				if (eX < sX) {
					if (point[sX - 1][sY].isChessExist())
						isMoveable = false;
					else
						isMoveable = true;
				}
			} else if (lengthX == 1 && lengthY == 2) {
				if (eY > sY) {
					if (point[sX][sY + 1].isChessExist())
						isMoveable = false;
					else
						isMoveable = true;
				}
				if (eY < sY) {
					if (point[sX][sY - 1].isChessExist())
						isMoveable = false;
					else
						isMoveable = true;
				}
			} else {
				isMoveable = false;
			}
		} else if (piece.getPieceName().equals("象") || piece.getPieceName().equals("相")) {
			int lengthX = Math.abs(sX - eX);
			int lengthY = Math.abs(sY - eY);
			int centerX = (sX + eX) / 2;
			int centerY = (sY + eY) / 2;
			if (piece.getPieceName().equals("象") && lengthX == 2 && lengthY == 2 && eX < 5) {
				if (point[centerX][centerY].isChessExist())
					isMoveable = false;
				else
					isMoveable = true;
			} else if (piece.getPieceName().equals("相") && lengthX == 2 && lengthY == 2 && eX >= 5) {
				if (point[centerX][centerY].isChessExist()) {
					isMoveable = false;
				} else
					isMoveable = true;
			} else {
				isMoveable = false;
			}
		} else if (piece.getPieceName().equals("士") || piece.getPieceName().equals("仕")) {
			int lengthX = Math.abs(sX - eX);
			int lengthY = Math.abs(sY - eY);
			if (piece.getPieceName().equals("士") && eX < 3 && lengthX == 1 && lengthY == 1 && eY > 2 && eY < 6) {
				isMoveable = true;
			} else if (piece.getPieceName().equals("仕") && eX > 6 && lengthX == 1 && lengthY == 1 && eY > 2 && eY < 6) {
				isMoveable = true;
			} else {
				isMoveable = false;
			}
		} else if (piece.getPieceName().equals("将") || piece.getPieceName().equals("帅")) {
			int lengthX = Math.abs(sX - eX);
			int lengthY = Math.abs(sY - eY);
			if (piece.getPieceName().equals("将") && eX < 3 && eY > 2 && eY < 6
					&& ((lengthX == 1 && lengthY == 0) || (lengthX == 0 && lengthY == 1)))
				isMoveable = true;
			else if (piece.getPieceName().equals("帅") && eX > 6 && eY > 2 && eY < 6
					&& ((lengthX == 1 && lengthY == 0) || (lengthX == 0 && lengthY == 1)))
				isMoveable = true;
			else
				isMoveable = false;
		} else if (piece.getPieceName().equals("炮")) {
			int pieceCount = 0;
			if (sX == eX) {
				for (int i = minY + 1; i < maxY; i++) {
					if (point[sX][i].isChessExist())
						pieceCount++;
				}
				if (pieceCount == 1 && point[eX][eY].isChessExist()) {
					isMoveable = true;
					System.out.println("pc=1");
				} else if (pieceCount == 0 && !point[eX][eY].isChessExist()) {
					isMoveable = true;
					System.out.println(minY + " " + maxY + sY + " " + eY);
					System.out.println(point[6][2].isChessExist());
				} else
					isMoveable = false;
			} else if (sY == eY) {
				for (int i = minX + 1; i < maxX; i++) {
					if (point[i][sY].isChessExist())
						pieceCount++;
				}
				if (pieceCount == 1 && point[eX][eY].isChessExist())
					isMoveable = true;
				else if (pieceCount == 0 && !point[eX][eY].isChessExist())
					isMoveable = true;
				else
					isMoveable = false;
			} else {
				isMoveable = false;
			}
		} else if (piece.getPieceName().equals("卒")) {
			int lengthY = Math.abs(sY - eY);
			if (eX < 5) {
				if (eX - sX == 1 && lengthY == 0)
					isMoveable = true;
				else
					isMoveable = false;
			} else if (eX >= 5) {
				if (eX - sX == 1 && lengthY == 0)
					isMoveable = true;
				else if (sX == eX && lengthY == 1)
					isMoveable = true;
				else
					isMoveable = false;
			}
		} else if (piece.getPieceName().equals("兵")) {
			int lengthY = Math.abs(sY - eY);
			if (eX >= 5) {
				if (sX - eX == 1 && lengthY == 0)
					isMoveable = true;
				else
					isMoveable = false;
			} else if (eX < 5) {
				if (sX - eX == 1 && lengthY == 0)
					isMoveable = true;
				else if (sX == eX && lengthY == 1)
					isMoveable = true;
				else
					isMoveable = false;
			}
		}
		return isMoveable;
	}

	public boolean isOver(Pieces piece) {
		this.piece = piece;
		if (piece.getPieceName() == "帅" || piece.getPieceName() == "将") {
			return true;
		}
		return false;
	}

}
