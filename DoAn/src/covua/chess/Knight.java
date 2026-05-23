package covua.chess;



import covua.PieceColor;
import covua.Position;

public class Knight extends Piece {

	public Knight(PieceColor color, Position position) {
		super(color, position);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValidMove(Position newPosition, Piece[][] board) {
		if(super.isSameMove(newPosition))return false;
		
		int rowNext =Math.abs(this.position.getRow() - newPosition.getRow());
		int colNext = Math.abs(this.getPosition().getColumn() - newPosition.getColumn());
		
		// Checj L
		boolean isValidMove = (rowNext == 2 && colNext ==1)|| (rowNext==1 && colNext==2);
		if(!isValidMove) {
			return false;
		}
		
		Piece destinationPiece = board[newPosition.getRow()][newPosition.getColumn()];
		return destinationPiece == null || canCapture(destinationPiece);
		
	}

}
