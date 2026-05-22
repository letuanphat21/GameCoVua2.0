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

		while (row != to.getRow() || col != to.getColumn()) {
			if (board[row][col] != null) {
				return false;
			}
			row += rowDir;
			col += colDir;
		}
		return true;
	}

	public static boolean canLandOn(Piece destination, PieceColor moverColor) {
		if (destination == null) {
			return true;
		}
		return destination.getColor() != moverColor;
	}

	/**
	 * @param straightOnly true = chỉ ngang/dọc (Xe); false = thẳng hoặc chéo (Hậu)
	 */
	public static boolean isValidSlidingMove(Position from, Position to, Piece[][] board, PieceColor color,
			boolean straightOnly) {
		if (from.equals(to)) {
			return false;
		}

		boolean straight = isSameRankOrFile(from, to);
		boolean diagonal = isOnDiagonal(from, to);

		if (straightOnly) {
			if (!straight) {
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
