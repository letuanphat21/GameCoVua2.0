package covua.chess;

import covua.PieceColor;
import covua.Position;

public class King extends Piece {

	public King(PieceColor color, Position position) {
		super(color, position);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValidMove(Position newPosition, Piece[][] board) {

		if (super.isSameMove(newPosition))
			return false;

		int rowNext = Math.abs(position.getRow() - newPosition.getRow());
		int colNext = Math.abs(position.getColumn() - newPosition.getColumn());

		boolean isOneSteps = rowNext <= 1 && colNext <= 1;
		if (isOneSteps) {
			Piece destinationPiece = board[newPosition.getRow()][newPosition.getColumn()];
			return destinationPiece == null || canCapture(destinationPiece);
		}

		// Castling layout validation
		int startingRow = (color == PieceColor.WHITE) ? 7 : 0;
		if (position.getRow() == startingRow && position.getColumn() == 4 && newPosition.getRow() == startingRow) {
			if (colNext == 2) {
				int direction = (newPosition.getColumn() - position.getColumn()) > 0 ? 1 : -1;
				int rookCol = direction > 0 ? 7 : 0;
				Piece rook = board[startingRow][rookCol];
				if (rook instanceof Rook && rook.getColor() == color) {
					// Verify that squares between King and Rook are empty
					if (direction > 0) {
						return board[startingRow][5] == null && board[startingRow][6] == null;
					} else {
						return board[startingRow][1] == null && board[startingRow][2] == null && board[startingRow][3] == null;
					}
				}
			}
		}

		return false;
	}

	@Override
	public King clone() {
		return (King) super.clone();
	}

}
