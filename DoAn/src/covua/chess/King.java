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
		if (!isOneSteps)
			return false;

		Piece destinationPiece = board[newPosition.getRow()][newPosition.getColumn()];
		return destinationPiece == null || canCapture(destinationPiece);
	}

}
