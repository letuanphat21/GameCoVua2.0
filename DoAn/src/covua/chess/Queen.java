package covua.chess;

import covua.PieceColor;
import covua.Position;

public class Queen extends Piece {

	public Queen(PieceColor color, Position position) {
		super(color, position);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValidMove(Position newPosition, Piece[][] board) {
		if (super.isSameMove(newPosition))
			return false;

		int rowNext = Math.abs(newPosition.getRow() - this.position.getRow());
		int colNext = Math.abs(newPosition.getColumn() - this.position.getColumn());

		boolean straightLine = this.position.getRow() == newPosition.getRow()
				|| this.position.getColumn() == newPosition.getColumn();

		boolean diagonal = rowNext == colNext;

		if (!straightLine && !diagonal) {
			return false;
		}
		int rowDirection = Integer.compare(newPosition.getRow(), this.position.getRow());
		int colDirection = Integer.compare(newPosition.getColumn(), this.position.getColumn());

		int currentRow = this.position.getRow() + rowDirection;
		int currentCol = this.position.getColumn() + colDirection;

		while (currentRow != newPosition.getRow() || currentCol != newPosition.getColumn()) {
			if (board[currentRow][currentCol] != null) {
				return false;
			}
			currentRow += rowDirection;
			currentCol += colDirection;
		}

		Piece destinationPiece = board[newPosition.getRow()][newPosition.getColumn()];
//		if(destinationPiece instanceof King) {
//			return false;
//		}
		if (destinationPiece == null) {
			return true;
		} else if (destinationPiece.getColor() != this.color) {
			return true;
		}
		return false;

	}

}
