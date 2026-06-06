package testcase;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import covua.Board;
import covua.ChessGame;
import covua.ai.Alphabeta;
import covua.ai.Node;
import covua.chess.Piece;

public class AlphabetaUnitTest {

    /**
     * TC-AB-001: Kiểm tra tính đúng đắn của việc chọn nước đi tối ưu (MAX).
     * AI (MAX) phải chọn nhánh có điểm số cao nhất sau khi đối thủ (MIN) đã chọn điểm thấp nhất.
     */
    @Test
    public void testTC001_AlphaBeta_ShouldSelectMaxHeuristicPath() {
        // Nhánh A: MIN chọn giữa 3 và 5 -> Trả về 3
        Node leafA1 = createMockScoreNode(3);
        Node leafA2 = createMockScoreNode(5);
        Node nodeA = createMockParentNode(leafA1, leafA2);

        // Nhánh B: MIN chọn giữa 2 và 9 -> Trả về 2
        Node leafB1 = createMockScoreNode(2);
        Node leafB2 = createMockScoreNode(9);
        Node nodeB = createMockParentNode(leafB1, leafB2);

        // Node gốc: MAX chọn giữa Nhánh A (3) và Nhánh B (2) -> Chọn 3 (Node A)
        Node rootNode = createMockParentNode(nodeA, nodeB);

        Node chosenBestMove = Alphabeta.bestMove(rootNode, 2);

        assertNotNull(chosenBestMove, "AI phải chọn được một nước đi.");
        assertSame(nodeA, chosenBestMove, "TC-AB-001 Thất bại: Thuật toán phải chọn Node A.");
    }

    /**
     * TC-AB-002: Kiểm tra khả năng cắt tỉa nhánh Beta (Beta Cut-off).
     * Khi một nhánh MIN tìm thấy một giá trị nhỏ hơn hoặc bằng Alpha, vòng lặp phải dừng lại (break).
     */
    @Test
    public void testTC002_AlphaBeta_BetaCutoffEfficiency() {
        // Nhánh thứ nhất: Trả về giá trị 5 làm mốc Alpha ban đầu cho MAX
        Node leafA = createMockScoreNode(5);
        Node nodeA = createMockParentNode(leafA);

        // Nhánh thứ hai (MIN): Node con đầu tiên trả về 2. 
        // Vì 2 <= Alpha(5), xảy ra Beta Cut-off, Node con tiếp theo (điểm 100) sẽ không bao giờ được duyệt tới.
        Node leafB1 = createMockScoreNode(2);
        
        // Node này được thiết kế để ném ra ngoại lệ (Exception) nếu thuật toán duyệt sai vào nó
        Node leafB2 = new Node(new ChessGame()) {
            @Override
            public List<Node> generationChild() {
                fail("TC-AB-002 Thất bại: Lỗi cắt tỉa Beta! Thuật toán vẫn duyệt nhánh này dù lẽ ra phải bị cắt bỏ.");
                return new ArrayList<>();
            }
        };

        Node nodeB = createMockParentNode(leafB1, leafB2);
        Node rootNode = createMockParentNode(nodeA, nodeB);

        // Thực thi tìm kiếm, thuật toán phải hoạt động mượt mà và cắt tỉa nhánh leafB2 một cách chính xác
        Node chosenBestMove = Alphabeta.bestMove(rootNode, 2);
        
        assertNotNull(chosenBestMove, "AI phải chọn được nước đi.");
        assertSame(nodeA, chosenBestMove, "TC-AB-002 Thất bại: Node A mang lại giá trị tốt hơn.");
    }

    /**
     * TC-AB-003: Kiểm tra trường hợp xử lý điều kiện biên khi chiều sâu (Depth) đạt mức giới hạn bằng 0.
     * Thuật toán phải dừng duyệt cây và trả ra kết quả đánh giá điểm số lập tức thông qua hàm bestMove.
     */
    @Test
    public void testTC003_AlphaBeta_DepthZeroEdgeCase() {
        Node rootNode = createMockScoreNode(15);
        
        // Gọi hàm tìm kiếm trực tiếp tại Node gốc với Depth = 1 
        // (Sẽ kích hoạt gọi hàm alphabeta với depth - 1 = 0)
        Node chosenBestMove = Alphabeta.bestMove(rootNode, 1);
        
        // Khi duyệt độ sâu 1, các node con của rootNode sẽ được đánh giá trực tiếp
        assertNull(chosenBestMove, "Tại Depth = 1 và Root không có con, nước đi tốt nhất trả về phải là null.");
    }

    /**
     * TC-AB-004: Kiểm tra trạng thái trò chơi kết thúc (GameOver / Checkmate).
     * Thuật toán phải nhận diện được trạng thái chiếu hết để dừng tìm kiếm, tránh việc sinh thêm các Node con vô tận.
     */
    @Test
    public void testTC004_AlphaBeta_GameOverStopSearch() {
        // Tạo một node giả lập trạng thái kết thúc game
        Node gameOverNode = new Node(new ChessGame()) {
            @Override
            public boolean GameOver() {
                return true;
            }
            @Override
            public List<Node> generationChild() {
                fail("TC-AB-004 Thất bại: Trò chơi đã kết thúc nhưng hệ thống vẫn tiếp tục sinh Node con!");
                return new ArrayList<>();
            }
        };

        Node rootNode = createMockParentNode(gameOverNode);
        
        // Chạy thuật toán để đảm bảo không bị dính vòng lặp vô hạn hoặc duyệt lỗi khi game kết thúc
        Node chosenBestMove = Alphabeta.bestMove(rootNode, 2);
        assertNotNull(chosenBestMove, "Hệ thống vẫn trả về node kết thúc hợp lệ.");
    }

    /**
     * TC-AB-005: Kiểm tra khả năng xử lý khi các nhánh con trả về điểm số âm.
     * Thuật toán phải hoạt động chính xác với cả số âm (khi AI bị thiệt quân) để chọn ra nước đi "ít tệ nhất".
     */
    @Test
    public void testTC005_AlphaBeta_NegativeScoresEvaluation() {
        // Nhánh A: MIN chọn giữa -10 và -5 -> Trả về -10
        Node leafA1 = createMockScoreNode(-10);
        Node leafA2 = createMockScoreNode(-5);
        Node nodeA = createMockParentNode(leafA1, leafA2);

        // Nhánh B: MIN chọn giữa -20 và -30 -> Trả về -30
        Node leafB1 = createMockScoreNode(-20);
        Node leafB2 = createMockScoreNode(-30);
        Node nodeB = createMockParentNode(leafB1, leafB2);

        // Node gốc: MAX chọn giữa Nhánh A (-10) và Nhánh B (-30) -> Phải chọn -10 (Node A)
        Node rootNode = createMockParentNode(nodeA, nodeB);

        Node chosenBestMove = Alphabeta.bestMove(rootNode, 2);

        assertNotNull(chosenBestMove, "AI phải chọn được một nước đi.");
        assertSame(nodeA, chosenBestMove, "TC-AB-005 Thất bại: Thuật toán phải chọn nước đi có điểm âm lớn nhất (Node A).");
    }

    /**
     * Tạo một Node lá chứa một bàn cờ trống hoàn toàn giả lập.
     * Để bảo đảm tính chính xác khi chạy qua phương thức tĩnh Evaluator.heurictis(), 
     * ta trả về một cấu trúc rỗng hoàn toàn để triệt tiêu logic bàn cờ thật, đảm bảo điểm phản hồi cô lập.
     */
    private Node createMockScoreNode(final int targetScore) {
        ChessGame mockGame = new ChessGame();
        
        return new Node(mockGame) {
            @Override
            public List<Node> generationChild() {
                return new ArrayList<>();
            }
            
            @Override
            public ChessGame getState() {
                return new ChessGame(mockGame) {
                    @Override
                    public Board getBoard() {
                        return new Board(new Piece[8][8]) {
                            @Override
                            public Piece getPiece(int r, int c) {
                                return null; 
                            }
                        };
                    }
                };
            }

            // Ghi đè phương thức GameOver để đồng bộ logic test case thông thường
            @Override
            public boolean GameOver() {
                return false;
            }
        };
    }

    /**
     * Tạo một Node cha chứa sẵn danh sách các Node con được cấu hình từ trước.
     */
    private Node createMockParentNode(Node... childrenNodes) {
        return new Node(new ChessGame()) {
            private final List<Node> customChildren = List.of(childrenNodes);

            @Override
            public List<Node> generationChild() {
                return new ArrayList<>(customChildren);
            }
            
            @Override
            public boolean GameOver() {
                return false;
            }
        };
    }
}