package covua;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import covua.chess.King;
import covua.chess.Bishop;
import covua.chess.Knight;
import covua.chess.Pawn;
import covua.chess.Piece;
import covua.chess.Queen;
import covua.chess.Rook;
//  1.1.9: Hệ thống tạo ChessGame với Board mới
public class ChessGame {
	private Board board;
	private boolean whiteTurn = true;
	private Position selectedPosition;
	private Position lastMoveSource;
	private Position lastMoveTarget;
	// 9.2.2. Danh sách historyMoves giữ trạng thái rỗng khi chưa có nước đi hợp lệ.
	private List<String> historyMoves = new ArrayList<>();
	private boolean aiMode;


	public ChessGame() {
		// 9.2.1. Hệ thống khởi tạo ván cờ mới hoặc User chưa thực hiện bất kỳ nước đi hợp lệ nào.
		this.board = new Board();
	}

	public ChessGame(Board board) {
		this.board = board;
	}

	public ChessGame(ChessGame other) {
		this.board = new Board(other.board);
		this.whiteTurn = other.whiteTurn;
		this.selectedPosition = null;
		this.lastMoveSource = other.lastMoveSource;
		this.lastMoveTarget = other.lastMoveTarget;
		this.historyMoves = new ArrayList<>(other.historyMoves);
		this.aiMode = other.aiMode;
	}

	public void copyFrom(ChessGame other) {
		this.board = new Board(other.board);
		this.whiteTurn = other.whiteTurn;
		this.selectedPosition = null;
		this.lastMoveSource = other.lastMoveSource;
		this.lastMoveTarget = other.lastMoveTarget;
		this.historyMoves = new ArrayList<>(other.historyMoves);
		this.aiMode = other.aiMode;
	}

	public void setAiMode(boolean aiMode) {
		this.aiMode = aiMode;
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



	// 9.1.8. ChessGame gọi makeMove(selectedPosition, newPosition) để kiểm tra tính hợp lệ của nước đi.
	// method chọn quân đi í
	public boolean makeMove(Position start, Position end) {
		Piece movingPiece = board.getPiece(start.getRow(), start.getColumn());

		if (movingPiece == null || movingPiece.getColor() != (whiteTurn ? PieceColor.WHITE : PieceColor.BLACK)) {
			return false;
		}
		
		// 3.1.12: King gọi method  isValidMove(end, board).
		// 3.2.12: King gọi method  isValidMove(end, board).
		
		//3.1.13: Trả về true vì ô đích nằm trong phạm vi 1 ô xung quanh và không trùng ô xuất phát.
		//3.2.13: Trả về true vì Vua đi ngang 2 ô và các ô nằm giữa Vua và Xe đều trống.
		if (!movingPiece.isValidMove(end, board.getPieces())) {
			return false;
		}
		

		if (movingPiece instanceof King && Math.abs(start.getColumn() - end.getColumn()) == 2) {
			//3.2.14: ChessGame gọi method canCastle(start, end)
			if (!canCastle(start, end)) {
				return false;
			}
		} else {
			// 6.1.18: ChessGame khởi tạo đối tượng copy (giả lập) và di chuyển thử để gọi copy.isInCheck().
			
			// 3.1.14:  Khởi tạo bản sao giả lập ChessGame copy.
			ChessGame copy = new ChessGame(this);
			// 3.1.15: ChessGame Gọi copy.getBoard().movePiece(start, end).
			
			//3.2.15: Board gọi Gọi board.movePiece(start, end). Phát hiện Vua đi 2 ô ngang nên tự động di 
			//chuyển quân Xe tương ứng về vị trí chuẩn bên cạnh Vua.
			copy.getBoard().movePiece(start, end);

			// 6.1.19: Hệ thống gọi copy.isInCheck và xác nhận nước đi này không làm Vua bị chiếu.
			
			// 3.1.16:  Xác nhận Vua không bị chiếu sau nước đi này (copy.isInCheck == false).
			if (copy.isInCheck(movingPiece.getColor())) {
				return false;
			}
		}

		//Dánh cho online
		lastMoveSource = start;
	    lastMoveTarget = end;

	    Piece capturedPiece = board.getPiece(end.getRow(), end.getColumn());
	    boolean castling = isCastlingMove(movingPiece, start, end);
	    // 9.1.9. Nếu nước đi hợp lệ, ChessGame tạo chuỗi mô tả nước đi gồm màu quân, ký hiệu quân cờ,
	    // tọa độ bắt đầu, tọa độ kết thúc và thông tin phụ nếu có như bắt quân hoặc nhập thành.
	    // 9.1.10. ChessGame gọi historyMoves.add(moveNotation) để lưu nước đi mới vào danh sách lịch sử.
	    historyMoves.add(formatMoveNotation(aiMode ? "Player" : null, movingPiece, start, end, capturedPiece, castling));

		// 6.1.20: Hệ thống gọi board.movePiece(start, end).
	    
	    // 3.1.17 : Board gọi movePiece(start, end) cập nhật vị trí Vua trên mảng pieces.
		// 9.1.11. ChessGame gọi board.movePiece(start, end) để cập nhật trạng thái bàn cờ
		// và đổi lượt chơi whiteTurn = !whiteTurn.
		board.movePiece(start, end);
		// 6.1.23: ChessGame thực hiện đổi lượt chơi: whiteTurn = !whiteTurn.
		
		// 3.1.19: đổi lượt chơi (whiteTurn = !whiteTurn).
		whiteTurn = !whiteTurn;
		
		
		return true;
	}

	public void makeMoveForAI(Position start, Position end) {
		Piece movingPiece = board.getPiece(start.getRow(), start.getColumn());
		if (movingPiece == null || !movingPiece.isValidMove(end, board.getPieces())) {
			return;
		}

		if (movingPiece instanceof King && Math.abs(start.getColumn() - end.getColumn()) == 2) {
			if (!canCastle(start, end)) {
				return;
			}
		} else {
			ChessGame copy = new ChessGame(this);
			copy.getBoard().movePiece(start, end);

			if (copy.isInCheck(movingPiece.getColor())) {
				return;
			}
		}

		lastMoveSource = start;
		lastMoveTarget = end;
		Piece capturedPiece = board.getPiece(end.getRow(), end.getColumn());
		boolean castling = isCastlingMove(movingPiece, start, end);
		// 9.4.3. Nước đi của AI đã được ghi vào historyMoves trong quá trình makeMoveForAI() với tiền tố "AI".
		historyMoves.add(formatMoveNotation("AI", movingPiece, start, end, capturedPiece, castling));

		board.movePiece(start, end);
		whiteTurn = !whiteTurn;
	}

	private boolean isCastlingMove(Piece movingPiece, Position start, Position end) {
		return movingPiece instanceof King && Math.abs(start.getColumn() - end.getColumn()) == 2;
	}

	private String formatMoveNotation(String playerName, Piece movingPiece, Position start, Position end,
			Piece capturedPiece, boolean castling) {
		String pieceIcon = getPieceIcon(movingPiece);
		String colorName = movingPiece.getColor() == PieceColor.WHITE ? "White" : "Black";
		String details = formatMoveDetails(end, capturedPiece, castling);
		if (playerName == null || playerName.isEmpty()) {
			return String.format("%s %s %s->%s%s",
					colorName,
					pieceIcon,
					start.toAlgebraic(),
					end.toAlgebraic(),
					details);
		}
		return String.format("%s %s %s %s->%s%s",
				playerName,
				colorName,
				pieceIcon,
				start.toAlgebraic(),
				end.toAlgebraic(),
				details);
	}

	private String formatMoveDetails(Position end, Piece capturedPiece, boolean castling) {
		if (castling) {
			String side = end.getColumn() == 6 ? "kingside" : "queenside";
			return " (" + side + " castling)";
		}

		if (capturedPiece != null) {
			return " (captures " + getPieceIcon(capturedPiece) + ")";
		}

		return "";
	}

	private String getPieceIcon(Piece piece) {
		if (piece instanceof Pawn) {
			return "\u265F";
		}
		if (piece instanceof Rook) {
			return "\u265C";
		}
		if (piece instanceof Knight) {
			return "\u265E";
		}
		if (piece instanceof Bishop) {
			return "\u265D";
		}
		if (piece instanceof Queen) {
			return "\u265B";
		}
		if (piece instanceof King) {
			return "\u265A";
		}
		return piece.getClass().getSimpleName();
	}

	// Method coi thử quân nào chiếu vua của hay không
	public boolean isInCheck(PieceColor kingColor) {
		Position kingPosition = findKingPosition(kingColor);
		return isSquareAttacked(kingPosition, kingColor);
	}

	public boolean isSquareAttacked(Position pos, PieceColor defenderColor) {
		Piece originalPiece = board.getPiece(pos.getRow(), pos.getColumn());

		// Temporarily place a dummy Pawn of the defender's color
		// so that enemy pawns/pieces can detect a capture opportunity/valid move path.
		Pawn dummy = new Pawn(defenderColor, pos);
		board.setPiece(pos.getRow(), pos.getColumn(), dummy);

		boolean attacked = false;
		int n = board.getPieces().length;
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {
				Piece piece = board.getPiece(row, col);
				if (piece != null && piece.getColor() != defenderColor) {
					if (piece.isValidMove(pos, board.getPieces())) {
						attacked = true;
						break;
					}
				}
			}
			if (attacked) break;
		}

		board.setPiece(pos.getRow(), pos.getColumn(), originalPiece);
		return attacked;
	}

	public boolean canCastle(Position start, Position end) {
		Piece king = board.getPiece(start.getRow(), start.getColumn());
		if (!(king instanceof King)) {
			return false;
		}

		// King must not have moved
		if (king.hasMoved()) {
			return false;
		}

		// King must not currently be in check
		if (isInCheck(king.getColor())) {
			return false;
		}

		int row = start.getRow();
		int startCol = start.getColumn();
		int endCol = end.getColumn();
		int direction = (endCol - startCol) > 0 ? 1 : -1;
		int rookCol = direction > 0 ? 7 : 0;

		Piece rook = board.getPiece(row, rookCol);
		if (!(rook instanceof covua.chess.Rook) || rook.getColor() != king.getColor() || rook.hasMoved()) {
			return false;
		}

		// Verify intermediate squares are empty and not under attack
		if (direction > 0) {
			// King-side: columns 5 (f) and 6 (g) must be empty
			if (board.getPiece(row, 5) != null || board.getPiece(row, 6) != null) {
				return false;
			}
			// Squares passed through and landed on must not be attacked
			if (isSquareAttacked(new Position(row, 5), king.getColor()) ||
				isSquareAttacked(new Position(row, 6), king.getColor())) {
				return false;
			}
		} else {
			// Queen-side: columns 1 (b), 2 (c), and 3 (d) must be empty
			if (board.getPiece(row, 1) != null || board.getPiece(row, 2) != null || board.getPiece(row, 3) != null) {
				return false;
			}
			// Squares passed through and landed on must not be attacked (c and d)
			if (isSquareAttacked(new Position(row, 3), king.getColor()) ||
				isSquareAttacked(new Position(row, 2), king.getColor())) {
				return false;
			}
		}

		return true;
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
		// 8.5.17b / 8.8.17: Hệ thống gọi phương thức resetGame() để khởi tạo lại dữ liệu bàn cờ mặc định ban đầu.
		// 9.3.4. ChessGame.resetGame() tạo lại Board mới, xóa toàn bộ historyMoves bằng historyMoves.clear()
		// và đặt whiteTurn = true.
		this.board = new Board();
		historyMoves.clear();
		this.whiteTurn = true;
	}

	public PieceColor getCurrentPlayerColor() {
		return whiteTurn ? PieceColor.WHITE : PieceColor.BLACK;
	}

	public boolean isPieceSelected() {
		return selectedPosition != null;
	}

	public boolean handleSquareSelection(int row, int col) {
		Position newPos = new Position(row, col);

		// 6.1.3: Hệ thống kiểm tra selectedPosition == null, xác nhận người dùng đang chọn quân Xe lần đầu.
		if (selectedPosition == null) {
			// 6.1.4: Hệ thống gọi board.getPiece(row, col) để lấy thông tin quân Xe tại vị trí vừa chọn.
			
			// 3.1.2: Board gọi getPiece(row,col).
			Piece selectedPiece = board.getPiece(row, col);

			// 6.1.5: Hệ thống xác nhận quân Xe tồn tại và đúng màu của lượt hiện tại (whiteTurn).
			// 6.2.1: Hệ thống phát hiện quân cờ tại ô chọn là null hoặc không đúng màu của lượt hiện tại (whiteTurn).
			
			// 3.1.3: ChessGame Xác nhận quân tại ô được click là quân Vua (King) và đúng màu của lượt đi (whiteTurn).
			if (selectedPiece != null && selectedPiece.getColor() == (whiteTurn ? PieceColor.WHITE : PieceColor.BLACK)) {

				// 6.1.6: Hệ thống gán vị trí vừa chọn vào biến selectedPosition.
				// 3.1.4: ChessGame gán vị trí hiện tại của Vua vào selectedPosition selectedPosition = newPos.
				// 9.1.5. ChessGame phát hiện selectedPosition == null, kiểm tra ô được chọn có quân cờ đúng màu
				// lượt hiện tại, sau đó lưu vị trí quân vào selectedPosition.
				selectedPosition = newPos;
				// 3.1.5: Trả về giá trị false.
				return false;
			}

			// 6.2.2: Phương thức handleSquareSelection trả về false.
			// 6.2.3: (Giao diện) ChessGameGUI không thực hiện highlight, người dùng phải chọn lại quân cờ đúng.
			return false;
		}

		// 6.1.9: Hệ thống gọi lại phương thức handleSquareSelection(row, col), lúc này selectedPosition != null.
		else {

			// 6.8.1: Hệ thống xác nhận destinationPiece.getColor() == getCurrentPlayerColor()
			Piece destinationPiece = board.getPiece(row, col);
			if(destinationPiece != null && destinationPiece.getColor() == getCurrentPlayerColor()) {
				// 6.8.2: Hệ thống hủy lệnh di chuyển cũ (makeMove trả về false).
				// 6.8.3: (Logic xử lý chọn lại) Hệ thống gán selectedPosition = newPosition và cập nhật lại vùng
				//highlight cho quân cờ mới vừa chọn tại ô đích.
				selectedPosition = newPos;
				return false;
			}

			// 6.1.10: Hệ thống gọi phương thức makeMove(selectedPosition, newPosition) trong ChessGame.
			// 9.1.6. User click tiếp vào ô đích muốn di chuyển.
			// 9.1.8. ChessGame gọi makeMove(selectedPosition, newPosition) để kiểm tra tính hợp lệ của nước đi.
			
			// 3.1.11: ChessGame gọi method makeMove(selectedPosition, newPos).
			// 3.2.11: ChessGame gọi method makeMove(selectedPosition, newPos).
			boolean success = makeMove(selectedPosition, newPos);

			// 6.3.3: Phương thức makeMove nhận kết quả false và trả về false.
			// 6.3.4: Hệ thống đặt lại selectedPosition = null, gọi clearHighlights() và chờ người dùng chọn lại từ đầu.
			// 6.5.3: makeMove trả về false và không cập nhật dữ liệu trên bàn cờ thực (board).
			// 6.5.4: Hệ thống đặt selectedPosition = null và gọi clearHighlights().
			selectedPosition = null;
			return success;
		}
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

			// Add castling moves if legal
			int row = position.getRow();
			Position kingSideTarget = new Position(row, 6);
			if (canCastle(position, kingSideTarget)) {
				legalMoves.add(kingSideTarget);
			}
			Position queenSideTarget = new Position(row, 2);
			if (canCastle(position, queenSideTarget)) {
				legalMoves.add(queenSideTarget);
			}
			break;
		}
//		3.1.7: Trả về danh sách các nước đi hợp lệ.
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
		// 8.4.13: Phương thức checkGameDraw() tiến hành đối chiếu tính trùng lặp của các trạng thái bàn cờ
		//(listState.get(1), get(3), get(5))
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
