package covua.chess;



import covua.PieceColor;

import covua.Position;



public class Rook extends Piece {



	public Rook(PieceColor color, Position position) {

		super(color, position);

	}



	@Override

	public boolean isValidMove(Position newPosition, Piece[][] board) {
		// 6.1.12: Xe gọi super.isSameMove(newPosition) và xác nhận ô đích không trùng ô hiện tại.
		// 6.7.1: Xe gọi isSameMove(newPosition) và nhận kết quả là true.
		if (super.isSameMove(newPosition)) {
			// 6.7.2: Phương thức isValidMove trả về false.
			return false;

		}

		return LineMovement.isValidSlidingMove(position, newPosition, board, color, true);

	}

	@Override
	public Rook clone() {
		return (Rook) super.clone();
	}

}

