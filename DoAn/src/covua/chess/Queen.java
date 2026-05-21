package covua.chess;

import covua.PieceColor;
import covua.Position;

public class Queen extends Piece {

	public Queen(PieceColor color, Position position) {
		super(color, position);
	}

	@Override
	public boolean isValidMove(Position newPosition, Piece[][] board) {
		if (super.isSameMove(newPosition)) {
			return false;
		}
		return LineMovement.isValidSlidingMove(position, newPosition, board, color, false);
	}

}
