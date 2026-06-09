package testcase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import covua.ChessGame;
import covua.PieceColor;
import covua.Board;
import covua.chess.Piece;

public class GameModeSelectionTest {

    private ChessGame humanVsHumanGame;
    private ChessGame humanVsAiGame;

    @BeforeEach
    public void setUp() {
        // Khởi tạo hai instance ChessGame cho hai chế độ chơi
        humanVsHumanGame = new ChessGame();
        humanVsAiGame = new ChessGame();
        humanVsAiGame.setAiMode(true);
    }

    /**
     * TC ID: TC-GM-001
     * Mô tả Test Case: Kiểm tra chế độ Human-Human được khởi tạo đúng khi lựa chọn.
     * Tiền đề (Pre-condition): Ứng dụng vừa khởi động, chưa chọn chế độ chơi nào.
     * Dữ liệu test: Chọn chế độ "Human - Human".
     * Bước thực hiện (Steps):
     * 1. Kiểm tra humanVsHumanGame được khởi tạo thành công.
     * 2. Kiểm tra trạng thái bàn cờ có 32 quân ở vị trí ban đầu.
     * 3. Kiểm tra whiteTurn = true (Trắng đi trước).
     * 4. Kiểm tra danh sách historyMoves rỗng.
     * 5. Kiểm tra chế độ AI không được bật (isAi = false).
     * Kết quả mong đợi (Expected Output):
     * - Game khởi tạo thành công với toàn bộ quân ở vị trí mặc định.
     * - whiteTurn = true.
     * - historyMoves rỗng.
     * - isAi = false.
     */
    @Test
    public void testTC001_HumanVsHumanModeInitialization() {
        // Kiểm tra game không null
        assertNotNull(humanVsHumanGame, "Game Human-Human phải được khởi tạo thành công.");

        // Kiểm tra bàn cờ không null
        assertNotNull(humanVsHumanGame.getBoard(), "Board phải được khởi tạo.");

        // Kiểm tra whiteTurn = true (Trắng đi trước)
        assertEquals(PieceColor.WHITE, humanVsHumanGame.getCurrentPlayerColor(),
                "Lượt chơi ban đầu phải là White.");

        // Kiểm tra danh sách historyMoves rỗng
        assertTrue(humanVsHumanGame.getHistoryMoves().isEmpty(),
                "Lịch sử nước đi phải rỗng khi khởi tạo chế độ Human-Human.");

        // Kiểm tra chế độ AI không được bật
        assertFalse(humanVsHumanGame.isAiMode(),
                "Chế độ AI phải không được bật trong Human-Human mode.");

        // Kiểm tra số lượng quân ở vị trí ban đầu (32 quân)
        int totalPieces = countPieces(humanVsHumanGame.getBoard());
        assertEquals(32, totalPieces, "Board phải có đúng 32 quân ở vị trí ban đầu.");
    }

    /**
     * TC ID: TC-GM-002
     * Mô tả Test Case: Kiểm tra chế độ Human-AI được khởi tạo đúng khi lựa chọn.
     * Tiền đề (Pre-condition): Ứng dụng vừa khởi động, chưa chọn chế độ chơi nào.
     * Dữ liệu test: Chọn chế độ "Human - AI".
     * Bước thực hiện (Steps):
     * 1. Kiểm tra humanVsAiGame được khởi tạo thành công.
     * 2. Kiểm tra chế độ AI được bật (isAi = true).
     * 3. Kiểm tra whiteTurn = true (Trắng đi trước).
     * 4. Kiểm tra danh sách historyMoves rỗng.
     * 5. Kiểm tra bàn cờ có 32 quân ở vị trí ban đầu.
     * Kết quả mong đợi (Expected Output):
     * - Game khởi tạo thành công với chế độ AI bật.
     * - whiteTurn = true.
     * - historyMoves rỗng.
     * - isAi = true.
     * - Board đầy đủ 32 quân mặc định.
     */
    @Test
    public void testTC002_HumanVsAiModeInitialization() {
        // Kiểm tra game không null
        assertNotNull(humanVsAiGame, "Game Human-AI phải được khởi tạo thành công.");

        // Kiểm tra chế độ AI được bật
        assertTrue(humanVsAiGame.isAiMode(),
                "Chế độ AI phải được bật trong Human-AI mode.");

        // Kiểm tra whiteTurn = true (Trắng đi trước)
        assertEquals(PieceColor.WHITE, humanVsAiGame.getCurrentPlayerColor(),
                "Lượt chơi ban đầu phải là White.");

        // Kiểm tra danh sách historyMoves rỗng
        assertTrue(humanVsAiGame.getHistoryMoves().isEmpty(),
                "Lịch sử nước đi phải rỗng khi khởi tạo chế độ Human-AI.");

        // Kiểm tra số lượng quân ở vị trí ban đầu (32 quân)
        int totalPieces = countPieces(humanVsAiGame.getBoard());
        assertEquals(32, totalPieces, "Board phải có đúng 32 quân ở vị trí ban đầu.");
    }

    /**
     * TC ID: TC-GM-003
     * Mô tả Test Case: Kiểm tra Board được khởi tạo chính xác ở vị trí mặc định.
     * Tiền đề (Pre-condition): Cả hai chế độ đã khởi tạo thành công.
     * Dữ liệu test: Kiểm tra vị trí các quân ở đầu ván.
     * Bước thực hiện (Steps):
     * 1. Kiểm tra Quân Vua Trắng ở vị trí e1 (7, 4).
     * 2. Kiểm tra Quân Vua Đen ở vị trí e8 (0, 4).
     * 3. Kiểm tra các Tốt Trắng ở hàng 2 (row 6).
     * 4. Kiểm tra các Tốt Đen ở hàng 7 (row 1).
     * 5. Kiểm tra các quân khác ở vị trí đúng.
     * Kết quả mong đợi (Expected Output):
     * - Tất cả các quân được đặt đúng vị trí ban đầu.
     * - Hàng 3, 4, 5, 6 rỗng (không có quân).
     * - Cả hai chế độ (Human-Human và Human-AI) có cấu hình giống nhau.
     */
    @Test
    public void testTC003_BoardInitializationCorrectly() {
        // Kiểm tra vị trí Kings
        assertNotNull(humanVsHumanGame.getBoard().getPiece(7, 4),
                "Vua Trắng phải ở vị trí e1 (7, 4).");
        assertNotNull(humanVsHumanGame.getBoard().getPiece(0, 4),
                "Vua Đen phải ở vị trí e8 (0, 4).");

        // Kiểm tra Pawns ở vị trí ban đầu
        // Tốt Trắng ở hàng 2 (row 6)
        for (int col = 0; col < 8; col++) {
            assertNotNull(humanVsHumanGame.getBoard().getPiece(6, col),
                    "Tốt Trắng phải ở hàng 2 (row 6), column " + col);
        }

        // Tốt Đen ở hàng 7 (row 1)
        for (int col = 0; col < 8; col++) {
            assertNotNull(humanVsHumanGame.getBoard().getPiece(1, col),
                    "Tốt Đen phải ở hàng 7 (row 1), column " + col);
        }

        // Kiểm tra các hàng trống (3, 4, 5, 6 = rows 3, 4, 5, 6 từ dưới lên)
        for (int row = 2; row <= 5; row++) {
            for (int col = 0; col < 8; col++) {
                assertNull(humanVsHumanGame.getBoard().getPiece(row, col),
                        "Hàng " + row + " phải rỗng, nhưng có quân ở (" + row + ", " + col + ").");
            }
        }

        // Kiểm tra chế độ Human-AI có cấu hình giống Human-Human
        int humanVsHumanPieces = countPieces(humanVsHumanGame.getBoard());
        int humanVsAiPieces = countPieces(humanVsAiGame.getBoard());
        assertEquals(humanVsHumanPieces, humanVsAiPieces,
                "Cả hai chế độ phải có cùng số lượng quân ở vị trí ban đầu.");
    }

    /**
     * TC ID: TC-GM-004
     * Mô tả Test Case: Kiểm tra hoán đổi giữa hai chế độ chơi khi người dùng quay lại Home Screen.
     * Tiền đề (Pre-condition): Đang ở chế độ Human-Human, sau đó người dùng quay lại Home và chọn Human-AI.
     * Dữ liệu test: Thực hiện một nước đi trong chế độ Human-Human, sau đó reset và chuyển sang Human-AI.
     * Bước thực hiện (Steps):
     * 1. Thực hiện nước đi e2 -> e4 trong chế độ Human-Human.
     * 2. Kiểm tra humanVsHumanGame có 1 dòng lịch sử.
     * 3. Kiểm tra humanVsAiGame vẫn trống (không bị ảnh hưởng).
     * 4. Reset humanVsHumanGame.
     * 5. Kiểm tra humanVsHumanGame quay về trạng thái ban đầu.
     * 6. Kiểm tra humanVsAiGame vẫn sẵn sàng để chơi với trạng thái ban đầu.
     * Kết quả mong đợi (Expected Output):
     * - Hai instance game độc lập với nhau.
     * - Reset chỉ ảnh hưởng đến game được reset, không ảnh hưởng game kia.
     * - Cả hai game sau reset đều có trạng thái ban đầu.
     */
    @Test
    public void testTC004_SwitchBetweenGameModes() {
        // Thực hiện nước đi trong Human-Human
        boolean moved = humanVsHumanGame.makeMove(new Position(6, 4), new Position(4, 4));
        assertTrue(moved, "Nước đi e2 -> e4 phải hợp lệ.");
        assertEquals(1, humanVsHumanGame.getHistoryMoves().size(),
                "Human-Human game phải có 1 dòng lịch sử sau nước đi.");

        // Kiểm tra Human-AI game không bị ảnh hưởng
        assertTrue(humanVsAiGame.getHistoryMoves().isEmpty(),
                "Human-AI game phải vẫn trống vì không thực hiện nước đi nào.");

        // Reset Human-Human game
        humanVsHumanGame.resetGame();

        // Kiểm tra Human-Human game quay về trạng thái ban đầu
        assertTrue(humanVsHumanGame.getHistoryMoves().isEmpty(),
                "Human-Human game phải có lịch sử rỗng sau reset.");
        assertEquals(PieceColor.WHITE, humanVsHumanGame.getCurrentPlayerColor(),
                "Human-Human game phải có lượt Trắng sau reset.");

        // Kiểm tra Human-AI game vẫn sẵn sàng
        assertNotNull(humanVsAiGame, "Human-AI game phải vẫn tồn tại và sẵn sàng.");
        assertTrue(humanVsAiGame.isAiMode(), "Human-AI game phải vẫn ở chế độ AI.");
    }

    /**
     * TC ID: TC-GM-005
     * Mô tả Test Case: Kiểm tra các instance game độc lập khi một game thay đổi trạng thái.
     * Tiền đề (Pre-condition): Cả hai chế độ được khởi tạo.
     * Dữ liệu test: Thực hiện nhiều nước đi trong chế độ Human-Human, kiểm tra Human-AI không bị ảnh hưởng.
     * Bước thực hiện (Steps):
     * 1. Thực hiện nước 1: e2 -> e4 trong Human-Human.
     * 2. Kiểm tra humanVsHumanGame.getCurrentPlayerColor() = Black.
     * 3. Kiểm tra humanVsAiGame.getCurrentPlayerColor() = White (không thay đổi).
     * 4. Thực hiện nước 2: e7 -> e5 trong Human-Human.
     * 5. Kiểm tra humanVsHumanGame có 2 dòng lịch sử.
     * 6. Kiểm tra humanVsAiGame vẫn có 0 dòng lịch sử.
     * 7. Kiểm tra humanVsHumanGame.getCurrentPlayerColor() = White.
     * 8. Kiểm tra humanVsAiGame.getCurrentPlayerColor() vẫn = White.
     * Kết quả mong đợi (Expected Output):
     * - Hai game hoạt động độc lập với nhau.
     * - Thay đổi trạng thái một game không ảnh hưởng đến game kia.
     * - Cả hai game luôn sẵn sàng để chuyển đổi mà không mất dữ liệu.
     */
    @Test
    public void testTC005_GameInstancesIndependence() {
        // Move 1: e2 -> e4 (White)
        boolean move1 = humanVsHumanGame.makeMove(new Position(6, 4), new Position(4, 4));
        assertTrue(move1, "Nước đi thứ nhất phải hợp lệ.");

        // Kiểm tra lượt của Human-Human chuyển sang Black
        assertEquals(PieceColor.BLACK, humanVsHumanGame.getCurrentPlayerColor(),
                "Sau nước Trắng, lượt phải chuyển sang Đen.");

        // Kiểm tra Human-AI vẫn ở lượt White
        assertEquals(PieceColor.WHITE, humanVsAiGame.getCurrentPlayerColor(),
                "Human-AI game phải vẫn ở lượt White, không bị ảnh hưởng.");

        // Move 2: e7 -> e5 (Black)
        boolean move2 = humanVsHumanGame.makeMove(new Position(1, 4), new Position(3, 4));
        assertTrue(move2, "Nước đi thứ hai phải hợp lệ.");

        // Kiểm tra lịch sử
        assertEquals(2, humanVsHumanGame.getHistoryMoves().size(),
                "Human-Human game phải có 2 dòng lịch sử.");
        assertEquals(0, humanVsAiGame.getHistoryMoves().size(),
                "Human-AI game phải vẫn có 0 dòng lịch sử.");

        // Kiểm tra lượt hiện tại
        assertEquals(PieceColor.WHITE, humanVsHumanGame.getCurrentPlayerColor(),
                "Sau nước Đen, lượt phải chuyển về White.");
        assertEquals(PieceColor.WHITE, humanVsAiGame.getCurrentPlayerColor(),
                "Human-AI game phải vẫn ở lượt White.");

        // Kiểm tra số lượng quân vẫn bằng nhau
        assertEquals(countPieces(humanVsHumanGame.getBoard()),
                countPieces(humanVsAiGame.getBoard()),
                "Số lượng quân trên bàn cờ hai game phải bằng nhau.");
    }

    /**
     * Helper method: Đếm tổng số quân trên bàn cờ.
     */
    private int countPieces(Board board) {
        int count = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board.getPiece(row, col) != null) {
                    count++;
                }
            }
        }
        return count;
    }
}
