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

	private static final int[][] ROOK_TABLE = {
		{  0,  0,  0,  0,  0,  0,  0,  0},
		{  5, 10, 10, 10, 10, 10, 10,  5},
		{ -5,  0,  0,  0,  0,  0,  0, -5},
		{ -5,  0,  0,  0,  0,  0,  0, -5},
		{ -5,  0,  0,  0,  0,  0,  0, -5},
		{ -5,  0,  0,  0,  0,  0,  0, -5},
		{ -5,  0,  0,  0,  0,  0,  0, -5},
		{  0,  0,  0,  5,  5,  0,  0,  0}
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

	public static int heurictis(Board board, PieceColor aiColor) {
		int score = 0;
		int whiteBishops = 0;
		int blackBishops = 0;

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Piece p = board.getPiece(row, col);
				if (p == null)
					continue;

				// 1. Điểm cơ bản + điểm vị trí ô cờ đứng
				int value = getPieceValue(p) + getPiecePositionalValue(p, row, col);

				// 2. Nâng cấp chiến thuật dựa trên thuộc tính p.hasMoved()
				if (p.hasMoved()) {
					if (p instanceof King || p instanceof Rook) {
						// Phạt điểm nếu Vua/Xe dịch chuyển sớm làm mất quyền Nhập Thành cứu Vua
						value -= 40; 
					} else if (p instanceof Knight || p instanceof Bishop) {
						// Thưởng điểm nhẹ vì quân nhẹ đã chủ động phát triển, rời ô xuất phát
						value += 10;
					}
				}

				// 3. Đếm số lượng Tượng để tính cặp Tượng
				if (p instanceof Bishop) {
					if (p.getColor() == PieceColor.WHITE) whiteBishops++;
					else blackBishops++;
				}

				// 4. Kiểm tra cấu trúc Tốt bảo vệ Vua
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

		// 5. Thưởng điểm "Cặp Tượng" (Bishop Pair) - Giữ 2 tượng có lợi thế ép sân rất lớn
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
		if (p instanceof Rook)   return ROOK_TABLE[row][col];
		if (p instanceof Queen)  return QUEEN_TABLE[row][col];
		if (p instanceof King)   return KING_MIDDLE_TABLE[row][col];
		return 0;
	}

	// Tận dụng mảng bàn cờ để quét cấu trúc Tốt bảo vệ trực diện Vua
	private static int checkKingSafety(Board board, Piece king, int kingRow, int kingCol) {
		int safetyScore = 0;
		// Xác định hướng Tốt phe mình che chắn
		int direction = (king.getColor() == PieceColor.WHITE) ? -1 : 1; 

		for (int dCol = -1; dCol <= 1; dCol++) {
			int targetRow = kingRow + direction;
			int targetCol = kingCol + dCol;

			if (targetRow >= 0 && targetRow < 8 && targetCol >= 0 && targetCol < 8) {
				Piece shield = board.getPiece(targetRow, targetCol);
				if (shield instanceof Pawn && shield.getColor() == king.getColor()) {
					safetyScore += 15; // Thưởng điểm nếu có Tốt làm lá chắn bảo vệ quanh Vua
				}
			}
		}
		return safetyScore;
	}
}
