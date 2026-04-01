package covua.chess;

import java.util.Objects;

import covua.PieceColor;
import covua.Position;

public abstract class Piece implements Cloneable {
	protected Position position;
	protected PieceColor color;

	public Piece(PieceColor color, Position position) {
		this.color = color;
		this.position = position;
	}

	@Override
	public Piece clone() {
		try {
			return (Piece) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public PieceColor getColor() {
		return color;
	}

	public void setColor(PieceColor color) {
		this.color = color;
	}

	public abstract boolean isValidMove(Position newPosition, Piece[][] board);

	public boolean isSameMove(Position newPosition) {
		return this.getPosition().equals(newPosition);
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, position);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Piece other = (Piece) obj;
		return color == other.color && Objects.equals(position, other.position);
	}
	
	

}
