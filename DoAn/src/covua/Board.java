package covua;

import java.util.Arrays;

import covua.chess.Bishop;
import covua.chess.King;
import covua.chess.Knight;
import covua.chess.Pawn;
import covua.chess.Piece;
import covua.chess.Queen;
import covua.chess.Rook;

public class Board {
	private Piece[][] pieces;

	public Board() {
		this.pieces = new Piece[8][8];
		setupPieces();
	}

	public Board(Piece[][] pieces) {
		this.pieces = pieces;
	}
	

	public Board(Board other) {
		this.pieces = new Piece[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				this.pieces[i][j] = other.pieces[i][j] == null ? null : other.pieces[i][j].clone();
			}
		}
	}

	public void setupPieces() {

		// Rocks
		pieces[0][0] = new Rook(PieceColor.BLACK, new Position(0, 0));
		pieces[0][7] = new Rook(PieceColor.BLACK, new Position(0, 7));

		pieces[7][0] = new Rook(PieceColor.WHITE, new Position(7, 0));
		pieces[7][7] = new Rook(PieceColor.WHITE, new Position(7, 7));

		// Place Knights
		pieces[0][1] = new Knight(PieceColor.BLACK, new Position(0, 1));
		pieces[0][6] = new Knight(PieceColor.BLACK, new Position(0, 6));
		pieces[7][1] = new Knight(PieceColor.WHITE, new Position(7, 1));
		pieces[7][6] = new Knight(PieceColor.WHITE, new Position(7, 6));

		// Place Bishops
		pieces[0][2] = new Bishop(PieceColor.BLACK, new Position(0, 2));
		pieces[0][5] = new Bishop(PieceColor.BLACK, new Position(0, 5));
		pieces[7][2] = new Bishop(PieceColor.WHITE, new Position(7, 2));
		pieces[7][5] = new Bishop(PieceColor.WHITE, new Position(7, 5));

		// Place Queens
		pieces[0][3] = new Queen(PieceColor.BLACK, new Position(0, 3));
		pieces[7][3] = new Queen(PieceColor.WHITE, new Position(7, 3));

		// Place Kings
		pieces[0][4] = new King(PieceColor.BLACK, new Position(0, 4));
		pieces[7][4] = new King(PieceColor.WHITE, new Position(7, 4));

		// Place Pawns
		for (int i = 0; i < 8; i++) {
			pieces[1][i] = new Pawn(PieceColor.BLACK, new Position(1, i));
			pieces[6][i] = new Pawn(PieceColor.WHITE, new Position(6, i));
		}
	}

	public void movePiece(Position start, Position end) {
		Piece movingPiece = pieces[start.getRow()][start.getColumn()];
		if (movingPiece != null && movingPiece.isValidMove(end, pieces)) {
			Piece target = pieces[end.getRow()][end.getColumn()];
			if (target instanceof King) {
				return;
			}
			
			if (movingPiece instanceof King && Math.abs(start.getColumn() - end.getColumn()) == 2) {
				// Castling!
				pieces[end.getRow()][end.getColumn()] = movingPiece;
				pieces[end.getRow()][end.getColumn()].setPosition(end);
				pieces[end.getRow()][end.getColumn()].setHasMoved(true);
				pieces[start.getRow()][start.getColumn()] = null;
				
				int row = start.getRow();
				int direction = (end.getColumn() - start.getColumn()) > 0 ? 1 : -1;
				int rookStartCol = direction > 0 ? 7 : 0;
				int rookEndCol = direction > 0 ? 5 : 3;
				
				Piece rook = pieces[row][rookStartCol];
				if (rook != null) {
					pieces[row][rookEndCol] = rook;
					pieces[row][rookEndCol].setPosition(new Position(row, rookEndCol));
					pieces[row][rookEndCol].setHasMoved(true);
					pieces[row][rookStartCol] = null;
				}
				return;
			}
			
			// 6.1.21: Lớp Board cập nhật mảng pieces: Ô đích nhận giá trị quân Xe, vị trí cũ được gán null.
			// 6.6.3: Hệ thống tiếp tục các bước từ 6.1.18. Tại bước 6.1.21, quân Xe ghi đè lên quân địch, 
			// quân địch bị loại khỏi danh sách các quân trên bàn cờ.
			pieces[end.getRow()][end.getColumn()] = movingPiece;
			// 6.1.22: Hệ thống gọi setPosition(end) của quân Xe để cập nhật tọa độ mới.
			pieces[end.getRow()][end.getColumn()].setPosition(end);
			pieces[end.getRow()][end.getColumn()].setHasMoved(true);
			// 6.1.21: Lớp Board cập nhật mảng pieces: Ô đích nhận giá trị quân Xe, vị trí cũ được gán null.
			pieces[start.getRow()][start.getColumn()] = null;
		}
	}

	public Piece[][] getPieces() {
		return pieces;
	}

	public Piece getPiece(int row, int column) {
		return pieces[row][column];
	}

	public void setPiece(int row, int column, Piece piece) {
		pieces[row][column] = piece;
		if (piece != null) {
			piece.setPosition(new Position(row, column));
		}
	}

	public void print(Piece[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				System.out.print(array[i][j]);
			}
			System.out.println();
		}
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(pieces);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		return Arrays.deepEquals(pieces, other.pieces);
	}

	public static void main(String[] args) {
		Board pieces = new Board();
		pieces.print(pieces.getPieces());
	}

}
