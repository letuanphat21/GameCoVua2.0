package covua;

import java.util.Objects;

public class Position {
	private int row;
	private int column;
	
	 public Position(int row, int column) {
	      this.row = row;
	      this.column = column;
	  }

	  public int getRow() {
	      return row;
	  }

	  public int getColumn() {
	      return column;
	  }
	  public static Position fromAlgebraic(String alg) {
		    // e2 → col = 4, row = 6
		    int col = alg.charAt(0) - 'a';
		    int row = 8 - Character.getNumericValue(alg.charAt(1));
		    return new Position(row, col);
		}

	  @Override
	  public int hashCode() {
		return Objects.hash(column, row);
	  }

	  @Override
	  public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		return column == other.column && row == other.row;
	  }

	  @Override
	  public String toString() {
		return "Position [row=" + row + ", column=" + column + "]";
	  }
	  
	  
}
