package testcase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import covua.Board;
import covua.PieceColor;
import covua.Position;
import covua.chess.King;
import covua.chess.Pawn;
import covua.chess.Piece;
import covua.chess.Rook;

public class KingMoveTest  {
    private Piece[][] pieces;
    private Board board;

    @BeforeEach
    public void setUp() {
        pieces = new Piece[8][8];
        board = new Board(pieces);
    }

    /**
     * TC-001: Kiểm thử di chuyển thông thường của Vua 1 ô tới ô trống
     * Vị trí hiện tại: e4 (Position(4, 4))
     * Vị trí đích: e5 (Position(3, 4))
     */
    @Test
    public void testTC001_NormalMove() {
        King king = new King(PieceColor.WHITE, new Position(4, 4));
        board.setPiece(4, 4, king);

        boolean isValid = king.isValidMove(new Position(3, 4), pieces);
        assertTrue(isValid, "Quân Vua phải di chuyển được 1 ô lên trên (e4 -> e5) khi ô đích trống.");
    }

    /**
     * TC-002: Kiểm thử Vua di chuyển quá 1 ô (không hợp lệ)
     * Vị trí hiện tại: e4 (Position(4, 4))
     * Vị trí đích: e6 (Position(2, 4))
     */
    @Test
    public void testTC002_InvalidDistanceMove() {
        King king = new King(PieceColor.WHITE, new Position(4, 4));
        board.setPiece(4, 4, king);

        boolean isValid = king.isValidMove(new Position(2, 4), pieces);
        assertFalse(isValid, "Quân Vua không được phép di chuyển quá 1 ô (e4 -> e6).");
    }

    /**
     * TC-003: Kiểm thử Vua ăn quân đối phương nằm trong phạm vi 1 ô
     * Trạng thái: Có Tốt Đen (Black Pawn) tại d5 (Position(3, 3))
     * Vị trí hiện tại Vua Trắng: e4 (Position(4, 4))
     * Vị trí đích: d5 (Position(3, 3))
     */
    @Test
    public void testTC003_CaptureMove() {
        King king = new King(PieceColor.WHITE, new Position(4, 4));
        board.setPiece(4, 4, king);

        Pawn blackPawn = new Pawn(PieceColor.BLACK, new Position(3, 3));
        board.setPiece(3, 3, blackPawn);

        boolean isValid = king.isValidMove(new Position(3, 3), pieces);
        assertTrue(isValid, "Quân Vua phải ăn được quân Tốt đối phương nằm chéo cách 1 ô.");
    }

    /**
     * TC-004: Kiểm thử di chuyển quân Vua đè lên quân đồng minh (không hợp lệ)
     * Trạng thái: Có Tốt Trắng (White Pawn) tại e5 (Position(3, 4))
     * Vị trí hiện tại Vua Trắng: e4 (Position(4, 4))
     * Vị trí đích: e5 (Position(3, 4))
     */
    @Test
    public void testTC004_FriendlyBlockMove() {
        King king = new King(PieceColor.WHITE, new Position(4, 4));
        board.setPiece(4, 4, king);

        Pawn whitePawn = new Pawn(PieceColor.WHITE, new Position(3, 4));
        board.setPiece(3, 4, whitePawn);

        boolean isValid = king.isValidMove(new Position(3, 4), pieces);
        assertFalse(isValid, "Quân Vua không được phép đi đè lên quân đồng minh cùng màu.");
    }

    /**
     * TC-005: Kiểm thử tính năng nhập thành cánh Vua (King-side Castling)
     * Vị trí hiện tại Vua Trắng: e1 (Position(7, 4))
     * Vị trí Xe Trắng: h1 (Position(7, 7))
     * Vị trí đích của Vua: g1 (Position(7, 6))
     * Điều kiện: các ô trung gian f1 (7, 5) và g1 (7, 6) đều trống.
     */
    @Test
    public void testTC005_ValidKingSideCastling() {
        King king = new King(PieceColor.WHITE, new Position(7, 4));
        board.setPiece(7, 4, king);

        Rook rook = new Rook(PieceColor.WHITE, new Position(7, 7));
        board.setPiece(7, 7, rook);

        boolean isValid = king.isValidMove(new Position(7, 6), pieces);
        assertTrue(isValid, "Vua Trắng nhập thành cánh Vua hợp lệ (e1 -> g1).");
    }

    /**
     * TC-006: Kiểm thử tính năng nhập thành cánh Hậu (Queen-side Castling)
     * Vị trí hiện tại Vua Trắng: e1 (Position(7, 4))
     * Vị trí Xe Trắng: a1 (Position(7, 0))
     * Vị trí đích của Vua: c1 (Position(7, 2))
     * Điều kiện: các ô trung gian b1 (7, 1), c1 (7, 2), d1 (7, 3) đều trống.
     */
    @Test
    public void testTC006_ValidQueenSideCastling() {
        King king = new King(PieceColor.WHITE, new Position(7, 4));
        board.setPiece(7, 4, king);

        Rook rook = new Rook(PieceColor.WHITE, new Position(7, 0));
        board.setPiece(7, 0, rook);

        boolean isValid = king.isValidMove(new Position(7, 2), pieces);
        assertTrue(isValid, "Vua Trắng nhập thành cánh Hậu hợp lệ (e1 -> c1).");
    }

    /**
     * TC-007: Kiểm thử nhập thành không hợp lệ khi bị cản bởi quân khác
     * Vị trí hiện tại Vua Trắng: e1 (Position(7, 4))
     * Vị trí Xe Trắng: h1 (Position(7, 7))
     * Quân cản: Tốt Trắng tại f1 (Position(7, 5))
     */
    @Test
    public void testTC007_InvalidCastlingBlocked() {
        King king = new King(PieceColor.WHITE, new Position(7, 4));
        board.setPiece(7, 4, king);

        Rook rook = new Rook(PieceColor.WHITE, new Position(7, 7));
        board.setPiece(7, 7, rook);

        // Đặt thêm một quân cản tại f1 (7, 5)
        Pawn blockingPawn = new Pawn(PieceColor.WHITE, new Position(7, 5));
        board.setPiece(7, 5, blockingPawn);

        boolean isValid = king.isValidMove(new Position(7, 6), pieces);
        assertFalse(isValid, "Nhập thành không được phép khi các ô giữa Vua và Xe bị cản.");
    }

    /**
     * TC-008: Kiểm thử nhập thành không hợp lệ khi không có Xe hoặc Xe khác màu
     */
    @Test
    public void testTC008_InvalidCastlingNoRook() {
        King king = new King(PieceColor.WHITE, new Position(7, 4));
        board.setPiece(7, 4, king);

        // Không đặt xe hoặc đặt xe khác màu tại h1
        Rook blackRook = new Rook(PieceColor.BLACK, new Position(7, 7));
        board.setPiece(7, 7, blackRook);

        boolean isValid = king.isValidMove(new Position(7, 6), pieces);
        assertFalse(isValid, "Nhập thành không được phép khi Xe ở góc là xe đối phương hoặc không có xe.");
    }
}
