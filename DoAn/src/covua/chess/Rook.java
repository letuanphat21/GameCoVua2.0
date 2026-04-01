package covua.chess;

import covua.PieceColor;
import covua.Position;

public class Rook extends Piece {

	public Rook(PieceColor color, Position position) {
		super(color, position);
		this.color = color;
		this.position = position;
	}

	@Override
	public boolean isValidMove(Position newPosition, Piece[][] board) {
		if(super.isSameMove(newPosition)) {
			return false;
		}
		
		if (position.getRow() == newPosition.getRow()) {
			int columnStart = Math.min(position.getColumn(), newPosition.getColumn()) + 1;
			int columnEnd = Math.max(position.getColumn(), newPosition.getColumn());

			for (int column = columnStart; column < columnEnd; column++) {
				if (board[position.getRow()][column] != null) {
					return false;
				}
			}
		} else if (position.getColumn() == newPosition.getColumn()) {
			int rowStart = Math.min(position.getRow(), newPosition.getRow()) + 1;
			int rowEnd = Math.max(position.getRow(), newPosition.getRow());
			for (int row = rowStart; row < rowEnd; row++) {
				if(board[row][position.getColumn()]!=null) {
					return false;
				}
			}
		}else {
			return false;
		}
		Piece destinationPiece = board[newPosition.getRow()][newPosition.getColumn()];
//		if(destinationPiece instanceof King) {
//			return false;
//		}
		if(destinationPiece == null) {
			return true;
		}else if(destinationPiece.getColor()!= this.color) {
			return true;
		}
		return false;
		
		
	}

}
