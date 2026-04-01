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

	public static int heurictis(Board board, PieceColor aiColor) {
		int score = 0;

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Piece p = board.getPiece(row, col);
				if (p == null)
					continue;

				int value = getPieceValue(p);

				if (p.getColor() == aiColor)
					score += value;
				else
					score -= value;
			}
		}

		return score;
	}

	private static int getPieceValue(Piece p) {
		if (p instanceof Pawn)
			return 100;
		if (p instanceof Knight)
			return 320;
		if (p instanceof Bishop)
			return 330;
		if (p instanceof Rook)
			return 500;
		if (p instanceof Queen)
			return 900;
		if (p instanceof King)
			return 20000;
		return 0;
	}

}
