package covua.chess;

import covua.PieceColor;
import covua.Position;

/**
 * Logic đi theo hàng/cột (Xe) hoặc hàng/cột/chéo (Hậu): không nhảy qua quân, ăn quân địch.
 */
public final class LineMovement {

	private LineMovement() {
	}

	public static boolean isSameRankOrFile(Position from, Position to) {
		return from.getRow() == to.getRow() || from.getColumn() == to.getColumn();
	}

	public static boolean isOnDiagonal(Position from, Position to) {
		return Math.abs(from.getRow() - to.getRow()) == Math.abs(from.getColumn() - to.getColumn());
	}

	public static boolean isPathClear(Position from, Position to, Piece[][] board) {
		int rowDir = Integer.compare(to.getRow(), from.getRow());
		int colDir = Integer.compare(to.getColumn(), from.getColumn());
		int row = from.getRow() + rowDir;
		int col = from.getColumn() + colDir;
		// 6.1.14: Xe chạy vòng lặp for kiểm tra các ô nằm giữa vị trí bắt đầu và đích.
		while (row != to.getRow() || col != to.getColumn()) {
			if (board[row][col] != null) {
				// 6.4.1: Trong vòng lặp for, hệ thống phát hiện ít nhất một ô giữa vị trí bắt đầu và đích có chứa quân cờ (!= null).
				// 6.4.2: Vòng lặp dừng lại, isValidMove trả về false.
				return false;
			}
			row += rowDir;
			col += colDir;
		}
		// 6.1.15: Hệ thống xác nhận tất cả các ô trên đường đi đều bằng null.
		return true;
	}

	public static boolean canLandOn(Piece destination, PieceColor moverColor) {
		// 6.1.16: Hệ thống kiểm tra ô đích và xác nhận ô đích đang trống (null).
		if (destination == null) {
			// 6.1.17: Phương thức isValidMove trả về true.
			return true;
		}
		// 6.6.1: Hệ thống xác nhận destinationPiece != null và có màu khác với quân đang di chuyển (destinationPiece.getColor() != this.color).
		return destination.getColor() != moverColor && !(destination instanceof King);
	}

	/**
	 * @param straightOnly true = chỉ ngang/dọc (Xe); false = thẳng hoặc chéo (Hậu)
	 */
	public static boolean isValidSlidingMove(Position from, Position to, Piece[][] board, PieceColor color,
			boolean straightOnly) {
		// 6.1.12: Xe gọi super.isSameMove(newPosition) và xác nhận ô đích không trùng ô hiện tại.
		// 6.7.1: Xe gọi isSameMove(newPosition) và nhận kết quả là true.
		if (from.equals(to)) {
			// 6.7.2: Phương thức isValidMove trả về false.
			return false;
		}

		// 6.1.13: Xe kiểm tra và xác nhận logic di chuyển thẳng (cùng row hoặc cùng column).
		boolean straight = isSameRankOrFile(from, to);
		boolean diagonal = isOnDiagonal(from, to);

		if (straightOnly) {
			if (!straight) {
				// 6.3.1: Xe phát hiện ô đích không cùng hàng và cũng không cùng cột với vị trí hiện tại.
				// 6.3.2: Phương thức isValidMove trả về false.
				return false;
			}
		} else if (!straight && !diagonal) {
			return false;
		}

		if (!isPathClear(from, to, board)) {
			return false;
		}

		return canLandOn(board[to.getRow()][to.getColumn()], color);
	}

}
