package covua;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import covua.chess.King;
import covua.chess.Piece;

public class ChessGame {
	private Board board;
	private boolean whiteTurn = true;
	private Position selectedPosition;
	private Position lastMoveSource;
	private Position lastMoveTarget;
	private List<String> historyMoves = new ArrayList<>();

	
	public ChessGame() {
		this.board = new Board();
	}

	public ChessGame(Board board) {
		this.board = board;
	}

	public ChessGame(ChessGame other) {
		this.board = new Board(other.board);
		this.whiteTurn = other.whiteTurn;
		this.selectedPosition = null;
	}

	public void copyFrom(ChessGame other) {
		this.board = new Board(other.board);
		this.whiteTurn = other.whiteTurn;
	}

	public void setWhiteTurn(boolean whiteTurn) {
		this.whiteTurn = whiteTurn;
	}
	
	//Danh cho online 2vs
	public Position getLastMoveSource() {
	    return lastMoveSource;
	}

	public Position getLastMoveTarget() {
	    return lastMoveTarget;
	}
	
	public void applyMove(String moveStr) {
	    // Lấy toàn bộ số nguyên từ chuỗi theo thứ tự
	    List<Integer> nums = new ArrayList<>();
	    Matcher m = Pattern.compile("\\d+").matcher(moveStr);

	    while (m.find()) {
	        nums.add(Integer.parseInt(m.group()));
	    }

	    if (nums.size() != 4) {
	        System.out.println("Move parse error: " + moveStr);
	        return;
	    }

	    int fromRow = nums.get(0);
	    int fromCol = nums.get(1);
	    int toRow   = nums.get(2);
	    int toCol   = nums.get(3);

	    Position from = new Position(fromRow, fromCol);
	    Position to = new Position(toRow, toCol);

	    lastMoveSource = from;
	    lastMoveTarget = to;

	    makeMove(from, to);
	    selectedPosition = null;
	}
	
	/////
	
	
	
	//9.1.4. Hệ thống kiểm tra tính hợp lệ của nước đi, 
	//nếu nước đi hợp lệ thì hệ thống gọi hàm makeMove(Position, Position) 
	//nội bộ để thực thi và tiếp tục bước tiếp theo.
	// method chọn quân đi í
	public boolean makeMove(Position start, Position end) {
		Piece movingPiece = board.getPiece(start.getRow(), start.getColumn());

		if (movingPiece == null || movingPiece.getColor() != (whiteTurn ? PieceColor.WHITE : PieceColor.BLACK)) {
			return false;
		}

		if (!movingPiece.isValidMove(end, board.getPieces())) {
			return false;

		}
		ChessGame copy = new ChessGame(this);
		copy.getBoard().movePiece(start, end);

		if (copy.isInCheck(movingPiece.getColor())) {
			return false;
		}
		//Dánh cho online
		lastMoveSource = start;
	    lastMoveTarget = end;
  		/////9.1.5. ChessGame tạo một chuỗi mô tả nước đi (bao gồm màu quân, loại quân và tọa độ từ đâu đến đâu) 
	    /// rồi gọi historyMoves.add(moveNotation) để lưu lại trong danh sách lịch sử.
	    
		String name = movingPiece.getClass().getSimpleName();
	    String moveNotation = String.format(
	    		 "%s [%s] (%d,%d) → (%d,%d)",
	            movingPiece.getColor().toString(),
	            name,
	            start.getRow(), start.getColumn(),
	            end.getRow(), end.getColumn()
	        );
	    historyMoves.add(moveNotation);
	    
		board.movePiece(start, end);
		whiteTurn = !whiteTurn;
		return true;
		
	}

	public void makeMoveForAI(Position start, Position end) {
		Piece movingPiece = board.getPiece(start.getRow(), start.getColumn());
		if (movingPiece.isValidMove(end, board.getPieces())) {
			board.movePiece(start, end);

			whiteTurn = !whiteTurn;
		}
	}

	// Method coi thử quân nào chiếu vua của hay không
	public boolean isInCheck(PieceColor kingColor) {
		Position kingPosition = findKingPosition(kingColor);
		int n = board.getPieces().length;
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {
				Piece piece = board.getPiece(row, col);

				if (piece != null && piece.getColor() != kingColor) {
					if (piece.isValidMove(kingPosition, board.getPieces())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// Lấy vị trì của vua
	public Position findKingPosition(PieceColor color) {
		int n = board.getPieces().length;
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {
				Piece piece = board.getPiece(row, col);
				if (piece instanceof King && piece.getColor() == color) {
					return new Position(row, col);
				}
			}
		}
		throw new RuntimeException("Không tìm thấy King");
	}

	// Coi vua có bị chiếu hết không
	public boolean isCheckmate(PieceColor kingColor) {
		if (!isInCheck(kingColor)) {
	        return false;
	    }

	    // Duyệt tất cả quân cùng màu với vua
	    int n = board.getPieces().length;
	    for (int row = 0; row < n; row++) {
	        for (int col = 0; col < n; col++) {
	            Piece piece = board.getPiece(row, col);
	            if (piece == null || piece.getColor() != kingColor) continue;

	            Position from = new Position(row, col);
	            // Lấy tất cả nước đi hợp lệ của quân đó
	            List<Position> moves = getLegalMovesForPieceAt(from);
	            if (!moves.isEmpty()) {
	                return false; // Còn ít nhất 1 nước đi cứu được vua
	            }
	        }
	    }

	    return true; // Không có nước nào thoát được

	}

	// method kiểm tra xem nước đi mới có làm cho vua bị chiếu không
	private boolean wouldBeInCheckAfterMove(PieceColor kingColor, Position from, Position to) {
		Piece temp = board.getPiece(to.getRow(), to.getColumn());

		board.setPiece(to.getRow(), to.getColumn(), board.getPiece(from.getRow(), from.getColumn()));
		board.setPiece(from.getRow(), from.getColumn(), null);

		boolean isCheck = isInCheck(kingColor);

		board.setPiece(from.getRow(), from.getColumn(), board.getPiece(to.getRow(), to.getColumn()));

		board.setPiece(to.getRow(), to.getColumn(), temp);

		return isCheck;

	}

	public boolean isPositionOnBoard(Position position) {
		return position.getRow() >= 0 && position.getRow() < board.getPieces().length && position.getColumn() >= 0
				&& position.getColumn() < board.getPieces()[0].length;
	}

	public Board getBoard() {
		return board;
	}

	public Position getSelectedPosition() {
		return selectedPosition;
	}

	public void setSelectedPosition(Position selectedPosition) {
		this.selectedPosition = selectedPosition;
	}

	public void resetGame() {
		this.board = new Board();
		this.whiteTurn = true;
	}

	public PieceColor getCurrentPlayerColor() {
		return whiteTurn ? PieceColor.WHITE : PieceColor.BLACK;
	}

	public boolean isPieceSelected() {
		return selectedPosition != null;
	}

	public boolean handleSquareSelection(int row, int col) {
		if (selectedPosition == null) {
			Piece selectedPiece = board.getPiece(row, col);

			if (selectedPiece != null
					&& selectedPiece.getColor() == (whiteTurn ? PieceColor.WHITE : PieceColor.BLACK)) {
				selectedPosition = new Position(row, col);
				return false;
			}
		} else {
			boolean moveMade = makeMove(selectedPosition, new Position(row, col));
			selectedPosition = null;
			return moveMade;
		}
		return false;
	}

	public List<Position> getLegalMovesForPieceAt(Position position) {
		Piece selectedPiece = board.getPiece(position.getRow(), position.getColumn());
		if (selectedPiece == null)
			return new ArrayList<Position>();

		List<Position> legalMoves = new ArrayList<Position>();

		switch (selectedPiece.getClass().getSimpleName()) {
		case "Pawn":
			addPawnMoves(position, selectedPiece.getColor(), legalMoves);
			break;
		case "Rook":
			addLineMoves(position, new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } }, legalMoves);
			break;
		case "Knight":
			addSingleMoves(position, new int[][] { { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 }, { 1, 2 }, { -1, 2 },
					{ 1, -2 }, { -1, -2 } }, legalMoves);
			break;
		case "Bishop":
			addLineMoves(position, new int[][] { { 1, 1 }, { -1, -1 }, { 1, -1 }, { -1, 1 } }, legalMoves);
			break;
		case "Queen":
			addLineMoves(position, new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }, { 1, 1 }, { -1, -1 },
					{ 1, -1 }, { -1, 1 } }, legalMoves);
			break;
		case "King":
			addSingleMoves(position, new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }, { 1, 1 }, { -1, -1 },
					{ 1, -1 }, { -1, 1 } }, legalMoves);
			break;
		}
		return legalMoves;
	}

	private boolean isLegalMoveAfterSimulation(Position from, Position to) {
		ChessGame copy = new ChessGame(this);
		copy.getBoard().movePiece(from, to);

		Piece moving = board.getPiece(from.getRow(), from.getColumn());
		PieceColor kingColor = moving.getColor();

		return !copy.isInCheck(kingColor);
	}

	private void addSingleMoves(Position position, int[][] moves, List<Position> legalMoves) {
		for (int[] move : moves) {
			Position newPos = new Position(position.getRow() + move[0], position.getColumn() + move[1]);

			if (!isPositionOnBoard(newPos)) {
				continue;
			}

			Piece target = board.getPiece(newPos.getRow(), newPos.getColumn());

			if (target instanceof King) {
				continue;
			}

			if (target == null
					|| target.getColor() != board.getPiece(position.getRow(), position.getColumn()).getColor()) {
				if (isLegalMoveAfterSimulation(position, newPos)) {
					legalMoves.add(newPos);
				}

			}
		}

	}

	private void addLineMoves(Position position, int[][] directions, List<Position> legalMoves) {
		for (int[] d : directions) {
			Position newPos = new Position(position.getRow() + d[0], position.getColumn() + d[1]);

			while (isPositionOnBoard(newPos)) {
				if (board.getPiece(newPos.getRow(), newPos.getColumn()) == null) {
					if (isLegalMoveAfterSimulation(position, newPos)) {
						legalMoves.add(newPos);
					}

					newPos = new Position(newPos.getRow() + d[0], newPos.getColumn() + d[1]);
				} else {
					if (board.getPiece(newPos.getRow(), newPos.getColumn()).getColor() != board
							.getPiece(position.getRow(), position.getColumn()).getColor()) {

						Piece target = board.getPiece(newPos.getRow(), newPos.getColumn());
						if (target instanceof King) {
							break;
						}
						if (isLegalMoveAfterSimulation(position, newPos)) {
							legalMoves.add(newPos);
						}
					}
					break;
				}
			}
		}

	}

	private void addPawnMoves(Position position, PieceColor color, List<Position> legalMoves) {
		int direction = color == PieceColor.WHITE ? -1 : 1;

		// Đi một bước
		Position newPos = new Position(position.getRow() + direction, position.getColumn());
		if (isPositionOnBoard(newPos) && board.getPiece(newPos.getRow(), newPos.getColumn()) == null) {
			if (isLegalMoveAfterSimulation(position, newPos)) {
				legalMoves.add(newPos);
			}

		}

		// Đi hai bước khi ở đầu
		if ((color == PieceColor.WHITE && position.getRow() == 6)
				|| (color == PieceColor.BLACK && position.getRow() == 1)) {

			newPos = new Position(position.getRow() + 2 * direction, position.getColumn());
			Position intermediatePos = new Position(position.getRow() + direction, position.getColumn());

			if (isPositionOnBoard(newPos) && board.getPiece(newPos.getRow(), newPos.getColumn()) == null
					&& board.getPiece(intermediatePos.getRow(), intermediatePos.getColumn()) == null) {
				if (isLegalMoveAfterSimulation(position, newPos)) {
					legalMoves.add(newPos);
				}

			}
		}
		int[] captureCols = { position.getColumn() - 1, position.getColumn() + 1 };

		for (int col : captureCols) {
			newPos = new Position(position.getRow() + direction, col);
			if (isPositionOnBoard(newPos)) {
				Piece target = board.getPiece(newPos.getRow(), newPos.getColumn());
				if (target != null && target.getColor() != color) {
					if (target instanceof King) {
						continue;
					}
					if (isLegalMoveAfterSimulation(position, newPos)) {
						legalMoves.add(newPos);
					}

				}
			}
		}

	}

	@Override
	public int hashCode() {
		return Objects.hash(board, selectedPosition, whiteTurn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChessGame other = (ChessGame) obj;
		return Objects.equals(board, other.board) && Objects.equals(selectedPosition, other.selectedPosition)
				&& whiteTurn == other.whiteTurn;
	}

	public List<String> getHistoryMoves() {
		return historyMoves;
	}
	
	public void resetHistory() {
		historyMoves.clear();
	}

}
