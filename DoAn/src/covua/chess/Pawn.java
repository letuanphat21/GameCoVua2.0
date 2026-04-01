package covua.chess;

import covua.PieceColor;
import covua.Position;

public class Pawn extends Piece {

	public Pawn(PieceColor color, Position position) {
		super(color, position);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValidMove(Position newPosition, Piece[][] board) {
		
		if(super.isSameMove(newPosition)) {
			return false;
		}
		
		int rowDirection = this.color == PieceColor.WHITE ? -1 : 1;

		int rowNext = (newPosition.getRow() - this.position.getRow()) * rowDirection;
		int colNext = newPosition.getColumn() - this.position.getColumn();

		// Di chuyển lên 1 bước
		if (colNext == 0 && rowNext == 1 && board[newPosition.getRow()][newPosition.getColumn()] == null) {
			return true;
		}

		// Di chuyển lúc đầu có thể đi hai bước
		boolean isStarting = (this.color == PieceColor.WHITE && this.position.getRow() == 6)
				|| (this.color == PieceColor.BLACK && this.position.getRow() == 1);

		if (colNext == 0 && rowNext == 2 && board[newPosition.getRow()][newPosition.getColumn()] == null
				&& isStarting) {
			// Kiểm tra chỗ này coi ô giữa 2 ô có null không
			// Null => thì di chuyển được
			// Not nutt => không di chuyển được
			int middle = this.position.getRow() + rowDirection;
			if (board[middle][this.getPosition().getColumn()] == null) {
				return true;
			}
		}

		// Check di chuyển chéo
		if (Math.abs(colNext) == 1 && rowNext == 1 && board[newPosition.getRow()][newPosition.getColumn()] != null
				&& board[newPosition.getRow()][newPosition.getColumn()].color != this.color) {
			return true;
		}
//		if(board[newPosition.getRow()][newPosition.getColumn()] instanceof King) {
//			return false;
//		}
		return false;

	}

}
