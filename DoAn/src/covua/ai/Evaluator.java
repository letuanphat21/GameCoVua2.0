package covua.ai;

import covua.Board;
import covua.PieceColor;
import covua.chess.Bishop;
import covua.chess.King;
import covua.chess.Knight;
import covua.chess.Pawn;
import covua.chess.Piece;
import covua.chess.Queen;
import covua.chess.Rook;

public class Evaluator {

	private static final int[][] PAWN_TABLE = {
		{ 0,  0,  0,  0,  0,  0,  0,  0},
		{50, 50, 50, 50, 50, 50, 50, 50},
		{100, 10, 20, 30, 30, 20, 10, 10},
		{ 5,  5, 10, 25, 25, 10,  5,  5},
		{ 0,  0,  0, 20, 20,  0,  0,  0},
		{ 5, -5,-10,  0,  0,-10, -5,  5},
		{ 5, 10, 10,-20,-20, 10, 10,  5},
		{ 0,  0,  0,  0,  0,  0,  0,  0}
	};

	private static final int[][] KNIGHT_TABLE = {
		{-50,-40,-30,-30,-30,-30,-40,-50},
		{-40,-20,  0,  0,  0,  0,-20,-40},
		{-30,  0, 10, 15, 15, 10,  0,-30},
		{-30,  5, 15, 20, 20, 15,  5,-30},
		{-30,  0, 15, 20, 20, 15,  0,-30},
		{-30,  5, 10, 15, 15, 10,  5,-30},
		{-40,-20,  0,  5,  5,  0,-20,-40},
		{-50,-40,-30,-30,-30,-30,-40,-50}
	};

	private static final int[][] BISHOP_TABLE = {
		{-20,-10,-10,-10,-10,-10,-10,-20},
		{-10,  0,  0,  0,  0,  0,  0,-10},
		{-10,  0,  5, 10, 10,  5,  0,-10},
		{-10,  5,  5, 10, 10,  5,  5,-10},
		{-10,  0, 10, 10, 10, 10,  0,-10},
		{-10, 10, 10, 10, 10, 10, 10,-10},
		{-10,  5,  0,  0,  0,  0,  5,-10},
		{-20,-10,-10,-10,-10,-10,-10,-20}
	};

	private static final int[][] QUEEN_TABLE = {
		{-20,-10,-10, -5, -5,-10,-10,-20},
		{-10,  0,  0,  0,  0,  0,  0,-10},
		{-10,  0,  5,  5,  5,  5,  0,-10},
		{ -5,  0,  5,  5,  5,  5,  0, -5},
		{  0,  0,  5,  5,  5,  5,  0,  0},
		{-10,  5,  5,  5,  5,  5,  0,-10},
		{-10,  0,  5,  0,  0,  0,  0,-10},
		{-20,-10,-10, -5, -5,-10,-10,-20}
	};

	private static final int[][] KING_MIDDLE_TABLE = {
		{-30,-40,-40,-50,-50,-40,-40,-30},
		{-30,-40,-40,-50,-50,-40,-40,-30},
		{-30,-40,-40,-50,-50,-40,-40,-30},
		{-30,-40,-40,-50,-50,-40,-40,-30},
		{-20,-30,-30,-40,-40,-30,-30,-20},
		{-10,-20,-20,-20,-20,-20,-20,-10},
		{ 20, 20,  0,  0,  0,  0, 20, 20},
		{ 20, 30, 10,  0,  0, 10, 30, 20}
	};

	private static final int[][] ROOK_PST = {
		{  0,  0,  0,  0,  0,  0,  0,  0 }, 
		{  5, 10, 10, 10, 10, 10, 10,  5 },
		{ -5,  0,  0,  0,  0,  0,  0, -5 }, 
		{ -5,  0,  0,  0,  0,  0,  0, -5 }, 
		{ -5,  0,  0,  0,  0,  0,  0, -5 }, 
		{ -5,  0,  0,  0,  0,  0,  0, -5 }, 
		{ -5,  0,  0,  0,  0,  0,  0, -5 },
		{  0,  0,  0,  5,  5,  0,  0,  0 }  
	};

	public static int heurictis(Board board, PieceColor aiColor) {
		int score = 0;
		int whiteBishops = 0;
		int blackBishops = 0;

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Piece p = board.getPiece(row, col);
				if (p == null)
					continue;

				// 1. Lấy giá trị cơ bản + điểm vị trí ô cờ đứng
				int value = getPieceValue(p) + getPiecePositionalValue(p, row, col);

				if (p instanceof Rook) {
					value += getRookBonus(board, p, row, col);
				}

				// 3. Nâng cấp chiến thuật dựa trên thuộc tính p.hasMoved()
				if (p.hasMoved()) {
					if (p instanceof King || p instanceof Rook) {
						// Phạt điểm nếu Vua/Xe dịch chuyển sớm làm mất quyền Nhập Thành
						value -= 40; 
					} else if (p instanceof Knight || p instanceof Bishop) {
						// Thưởng điểm nhẹ vì quân nhẹ đã chủ động phát triển
						value += 10;
					}
				}

				// 4. Đếm số lượng Tượng để tính cặp Tượng
				if (p instanceof Bishop) {
					if (p.getColor() == PieceColor.WHITE) whiteBishops++;
					else blackBishops++;
				}

				// 5. Kiểm tra cấu trúc Tốt bảo vệ Vua
				if (p instanceof King) {
					value += checkKingSafety(board, p, row, col);
				}

				// Tính toán cộng/trừ điểm theo phe AI
				if (p.getColor() == aiColor) {
					score += value;
				} else {
					score -= value;
				}
			}
		}

		// 6. Thưởng điểm "Cặp Tượng" (Bishop Pair)
		if (whiteBishops >= 2) score += (aiColor == PieceColor.WHITE) ? 40 : -40;
		if (blackBishops >= 2) score += (aiColor == PieceColor.BLACK) ? 40 : -40;

		return score;
	}

	private static int getPieceValue(Piece p) {
		if (p instanceof Pawn)   return 100;
		if (p instanceof Knight) return 320;
		if (p instanceof Bishop) return 330;
		if (p instanceof Rook)   return 500;
		if (p instanceof Queen)  return 900;
		if (p instanceof King)   return 20000;
		return 0;
	}

	private static int getPiecePositionalValue(Piece p, int row, int col) {
		if (p instanceof Pawn)   return PAWN_TABLE[row][col];
		if (p instanceof Knight) return KNIGHT_TABLE[row][col];
		if (p instanceof Bishop) return BISHOP_TABLE[row][col];
		if (p instanceof Queen)  return QUEEN_TABLE[row][col];
		if (p instanceof King)   return KING_MIDDLE_TABLE[row][col];
		return 0;
	}


	/**
	 * Computes positional bonuses and penalties for a Rook piece.
	 * Satisfies the requirement: "Xe ở cột mở / hàng 7 (tốt) được cộng điểm nhẹ; xe bị kẹt giảm điểm."
	 */
	private static int getRookBonus(Board board, Piece p, int row, int col) {
		if (p == null) {
			return 0;
		}

		int bonus = 0;
		PieceColor color = p.getColor();

		// 1. Piece-Square Table (PST) Bonus
		// If White, use standard row index. If Black, use 7 - row to mirror.
		int pstRow = (color == PieceColor.WHITE) ? row : (7 - row);
		bonus += ROOK_PST[pstRow][col];

		// 2. 7th Rank Bonus
		// Row 1 is the 7th rank for White; Row 6 is the 7th rank for Black.
		if ((color == PieceColor.WHITE && row == 1) || (color == PieceColor.BLACK && row == 6)) {
			bonus += 20;
		}

		// 3. Open and Semi-Open Column (File) Bonus
		if (isOpenColumn(board, col)) {
			bonus += 15;
		} else if (isSemiOpenColumn(board, col, color)) {
			bonus += 10;
		}

		// 4. Trapped/Restricted Mobility Penalty
		int mobility = getRookMobility(board, row, col, color);
		if (mobility <= 3) {
			bonus -= 20; // Trapped Rook penalty
		}

		return bonus;
	}

	/**
	 * Checks if a column has no Pawns of either color.
	 */
	private static boolean isOpenColumn(Board board, int col) {
		for (int r = 0; r < 8; r++) {
			Piece p = board.getPiece(r, col);
			if (p instanceof Pawn) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if a column has no friendly Pawns.
	 */
	private static boolean isSemiOpenColumn(Board board, int col, PieceColor friendlyColor) {
		for (int r = 0; r < 8; r++) {
			Piece p = board.getPiece(r, col);
			if (p instanceof Pawn && p.getColor() == friendlyColor) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Counts how many squares a Rook can move to or capture on.
	 */
	private static int getRookMobility(Board board, int row, int col, PieceColor color) {
		int moves = 0;
		int[][] dirs = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
		for (int[] dir : dirs) {
			int r = row + dir[0];
			int c = col + dir[1];
			while (r >= 0 && r < 8 && c >= 0 && c < 8) {
				Piece p = board.getPiece(r, c);
				if (p == null) {
					moves++;
				} else {
					if (p.getColor() != color && !(p instanceof King)) {
						moves++;
					}
					break; // Path is blocked
				}
				r += dir[0];
				c += dir[1];
			}
		}
		return moves;
	}

	private static int checkKingSafety(Board board, Piece king, int kingRow, int kingCol) {
		int safetyScore = 0;
		int direction = (king.getColor() == PieceColor.WHITE) ? -1 : 1; 

		for (int dCol = -1; dCol <= 1; dCol++) {
			int targetRow = kingRow + direction;
			int targetCol = kingCol + dCol;

			if (targetRow >= 0 && targetRow < 8 && targetCol >= 0 && targetCol < 8) {
				Piece shield = board.getPiece(targetRow, targetCol);
				if (shield instanceof Pawn && shield.getColor() == king.getColor()) {
					safetyScore += 15; 
				}
			}
		}
		return safetyScore;
	}
}