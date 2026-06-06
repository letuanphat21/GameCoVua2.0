package testcase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import covua.Board;
import covua.ChessGame;
import covua.PieceColor;
import covua.Position;
import covua.chess.King;
import covua.chess.Queen;
import covua.chess.Rook;

public class HistoryMoveTest {
    private ChessGame game;

    @BeforeEach
    public void setUp() {
        game = new ChessGame();
    }

    /**
     * TC-HM-001: Kiểm tra lịch sử ban đầu rỗng và ghi lại một nước đi hợp lệ.
     * Vị trí hiện tại: Tốt Trắng e2 (Position(6, 4))
     * Vị trí đích: e4 (Position(4, 4))
     */
    @Test
    public void testTC001_RecordValidPlayerMove() {
        assertTrue(game.getHistoryMoves().isEmpty(), "Lịch sử phải rỗng khi khởi tạo ván cờ mới.");

        boolean moved = game.makeMove(new Position(6, 4), new Position(4, 4));

        assertTrue(moved, "Nước đi e2 -> e4 phải hợp lệ.");
        assertEquals(1, game.getHistoryMoves().size(), "Lịch sử phải thêm đúng 1 dòng sau nước đi hợp lệ.");
        assertTrue(game.getHistoryMoves().get(0).contains("White"), "Dòng lịch sử phải ghi màu quân vừa đi.");
        assertTrue(game.getHistoryMoves().get(0).contains("e2->e4"), "Dòng lịch sử phải ghi đúng tọa độ e2 -> e4.");
    }

    /**
     * TC-HM-002: Kiểm tra nước đi không hợp lệ không được ghi vào lịch sử.
     * Vị trí hiện tại: Tốt Trắng e2 (Position(6, 4))
     * Vị trí đích: e5 (Position(3, 4))
     */
    @Test
    public void testTC002_InvalidMoveDoesNotChangeHistory() {
        boolean moved = game.makeMove(new Position(6, 4), new Position(3, 4));

        assertFalse(moved, "Tốt Trắng không được đi thẳng 3 ô từ e2 đến e5.");
        assertTrue(game.getHistoryMoves().isEmpty(), "Nước đi không hợp lệ không được ghi vào lịch sử.");
    }

    /**
     * TC-HM-003: Kiểm tra lịch sử ghi chú thích ăn quân.
     * Trạng thái: Xe Trắng ở a1 có thể ăn Hậu Đen ở a4 trên bàn cờ trống.
     */
    @Test
    public void testTC003_RecordCaptureMove() {
        clearBoard(game.getBoard());
        game.getBoard().setPiece(7, 7, new King(PieceColor.WHITE, new Position(7, 7)));
        game.getBoard().setPiece(0, 7, new King(PieceColor.BLACK, new Position(0, 7)));
        game.getBoard().setPiece(7, 0, new Rook(PieceColor.WHITE, new Position(7, 0)));
        game.getBoard().setPiece(4, 0, new Queen(PieceColor.BLACK, new Position(4, 0)));

        boolean moved = game.makeMove(new Position(7, 0), new Position(4, 0));

        assertTrue(moved, "Xe Trắng phải ăn được Hậu Đen trên cùng cột.");
        assertEquals(1, game.getHistoryMoves().size(), "Lịch sử phải có đúng 1 nước ăn quân.");
        assertTrue(game.getHistoryMoves().get(0).contains("a1->a4"), "Lịch sử phải ghi đúng tọa độ a1 -> a4.");
        assertTrue(game.getHistoryMoves().get(0).contains("(captures "), "Lịch sử phải ghi chú thích captures khi ăn quân.");
    }

    /**
     * TC-HM-004: Kiểm tra lịch sử ghi chú thích nhập thành cánh Vua.
     * Vị trí hiện tại: Vua Trắng e1, Xe Trắng h1
     * Vị trí đích: g1
     */
    @Test
    public void testTC004_RecordKingSideCastling() {
        clearBoard(game.getBoard());
        game.getBoard().setPiece(7, 4, new King(PieceColor.WHITE, new Position(7, 4)));
        game.getBoard().setPiece(7, 7, new Rook(PieceColor.WHITE, new Position(7, 7)));
        game.getBoard().setPiece(0, 4, new King(PieceColor.BLACK, new Position(0, 4)));

        boolean moved = game.makeMove(new Position(7, 4), new Position(7, 6));

        assertTrue(moved, "Vua Trắng phải nhập thành cánh Vua hợp lệ.");
        assertEquals(1, game.getHistoryMoves().size(), "Lịch sử phải có đúng 1 nước nhập thành.");
        assertTrue(game.getHistoryMoves().get(0).contains("e1->g1"), "Lịch sử phải ghi đúng tọa độ e1 -> g1.");
        assertTrue(game.getHistoryMoves().get(0).contains("(kingside castling)"),
                "Lịch sử phải ghi chú thích kingside castling.");
    }

    /**
     * TC-HM-005: Kiểm tra lịch sử ghi nhiều nước đi hợp lệ theo đúng thứ tự.
     * Nước 1: Tốt Trắng e2 -> e4
     * Nước 2: Tốt Đen e7 -> e5
     */
    @Test
    public void testTC005_RecordMultipleMovesInOrder() {
        assertTrue(game.makeMove(new Position(6, 4), new Position(4, 4)));
        assertTrue(game.makeMove(new Position(1, 4), new Position(3, 4)));

        assertEquals(2, game.getHistoryMoves().size(), "Lịch sử phải ghi đúng 2 nước đi hợp lệ.");
        assertTrue(game.getHistoryMoves().get(0).contains("e2->e4"), "Nước đầu tiên phải là e2 -> e4.");
        assertTrue(game.getHistoryMoves().get(1).contains("e7->e5"), "Nước thứ hai phải là e7 -> e5.");
    }

    /**
     * TC-HM-006: Kiểm tra resetGame xóa lịch sử và đưa lượt cho quân Trắng.
     */
    @Test
    public void testTC006_ResetGameClearsHistoryAndTurn() {
        assertTrue(game.makeMove(new Position(6, 4), new Position(4, 4)));
        assertEquals(PieceColor.BLACK, game.getCurrentPlayerColor(), "Tiền đề: sau nước Trắng, đến lượt Đen.");

        game.resetGame();

        assertTrue(game.getHistoryMoves().isEmpty(), "resetGame phải xóa lịch sử nước đi.");
        assertEquals(PieceColor.WHITE, game.getCurrentPlayerColor(), "resetGame phải đưa lượt về quân Trắng.");
    }

    /**
     * TC-HM-007: Kiểm tra copy constructor sao chép lịch sử độc lập với game gốc.
     */
    @Test
    public void testTC007_CopyConstructorKeepsIndependentHistory() {
        assertTrue(game.makeMove(new Position(6, 4), new Position(4, 4)));

        ChessGame copiedGame = new ChessGame(game);
        assertTrue(game.makeMove(new Position(1, 4), new Position(3, 4)));

        assertEquals(2, game.getHistoryMoves().size(), "Game gốc có thêm nước mới sau khi được sao chép.");
        assertEquals(1, copiedGame.getHistoryMoves().size(), "Game copy phải giữ nguyên lịch sử tại thời điểm sao chép.");
        assertTrue(copiedGame.getHistoryMoves().get(0).contains("e2->e4"),
                "Game copy phải giữ đúng nội dung lịch sử e2 -> e4.");
    }

    /**
     * TC-HM-008: Kiểm tra lịch sử trong chế độ AI phân biệt nước Player và nước AI.
     */
    @Test
    public void testTC008_AiModeRecordsPlayerAndAiLabels() {
        game.setAiMode(true);
        assertTrue(game.makeMove(new Position(6, 4), new Position(4, 4)));

        game.makeMoveForAI(new Position(1, 4), new Position(3, 4));

        assertEquals(2, game.getHistoryMoves().size(), "Lịch sử chế độ AI phải ghi cả nước Player và AI.");
        assertTrue(game.getHistoryMoves().get(0).startsWith("Player White"),
                "Nước người chơi trong chế độ AI phải có nhãn Player.");
        assertTrue(game.getHistoryMoves().get(0).contains("e2->e4"), "Nước Player phải ghi đúng e2 -> e4.");
        assertTrue(game.getHistoryMoves().get(1).startsWith("AI Black"), "Nước máy phải có nhãn AI.");
        assertTrue(game.getHistoryMoves().get(1).contains("e7->e5"), "Nước AI phải ghi đúng e7 -> e5.");
    }

    private void clearBoard(Board board) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board.setPiece(row, col, null);
            }
        }
    }
}
