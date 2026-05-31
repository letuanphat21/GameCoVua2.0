package covua.chess;


import covua.PieceColor;
import covua.Position;

public class Bishop extends Piece {

	public Bishop(PieceColor color, Position position) {
		super(color, position);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValidMove(Position newPosition, Piece[][] board) {
		
		if(super.isSameMove(newPosition)) return false;
		
		int rowNext = Math.abs(position.getRow() - newPosition.getRow());
		int colNext =Math.abs(position.getColumn() - newPosition.getColumn());
		
		if(rowNext != colNext) {
			return false;
		}
		
		int rowStep = newPosition.getRow() > position.getRow()?1:-1;
		int colStep = newPosition.getColumn() > position.getColumn()?1:-1;
		
		int steps = rowNext -1;
		
		for (int i = 1; i <= steps; i++) {
			if (board[position.getRow() + i * rowStep][position.getColumn() + i * colStep] != null) {
				return false;
			}
		}
		Piece destionationPiece = board[newPosition.getRow()][newPosition.getColumn()];
		return destionationPiece == null || canCapture(destionationPiece);
		
		
		
		
		
	}

}
