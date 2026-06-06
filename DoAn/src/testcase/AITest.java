package testcase;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import covua.Board;
import covua.ChessGame;
import covua.PieceColor;
import covua.Position;
import covua.ai.Alphabeta;
import covua.ai.Minimax;
import covua.ai.Node;
import covua.chess.King;
import covua.chess.Piece;
import covua.chess.Queen;
import covua.chess.Rook;

public class AITest {

    private ChessGame game;

    @BeforeEach
    public void setUp() {
        // Khởi tạo một ván cờ mới trước mỗi Test Case
        game = new ChessGame();
        game.setAiMode(true);
    }

    /**
     * TC ID: TC_AI_01
     * * Mô tả Test Case: Kiểm tra AI thực hiện nước đi hợp lệ khi đến lượt (BLACK).
     * * Tiền đề (Pre-condition): Hệ thống cờ vua khởi tạo ở trạng thái mặc định ban đầu.
     * * Dữ liệu test: Tọa độ nước đi của người chơi Trắng từ e2 (6, 4) đến e4 (4, 4), độ sâu cây quyết định depth = 3.
     * * Bước thực hiện (Steps):
     * 1. Gọi hàm makeMove thực hiện nước khai cuộc cho quân Trắng để chuyển lượt sang Đen.
     * 2. Khởi tạo cấu trúc cây quyết định Node gốc từ trạng thái trận đấu hiện tại.
     * 3. Gọi hàm Alphabeta.bestMove(root, 3) để AI tính toán nước đi tối ưu nhất.
     * * Kết quả mong đợi: AI tìm kiếm được một nước đi hợp lệ, trạng thái bàn cờ mới được sinh ra và lượt chơi tự động chuyển lại về cho quân Trắng.
     */
    @Test
    void test_TC_AI_01_ValidMoveWhenTurn() {
        boolean whiteMoved = game.makeMove(new Position(6, 4), new Position(4, 4));
        assertTrue(whiteMoved, "Quân Trắng phải đi được nước khai cuộc hợp lệ");
        assertEquals(PieceColor.BLACK, game.getCurrentPlayerColor(), "Hiện tại phải là lượt của quân Đen (AI)");

        Node root = new Node(new ChessGame(game));
        Node bestMoveNode = Alphabeta.bestMove(root, 3);

        assertNotNull(bestMoveNode, "AI phải tìm kiếm được một nước đi tốt nhất");
        assertNotNull(bestMoveNode.getState(), "Trạng thái bàn cờ mới của AI không được rỗng");
        
        game.copyFrom(bestMoveNode.getState());
        assertEquals(PieceColor.WHITE, game.getCurrentPlayerColor(), "Sau khi AI hoàn thành nước đi, lượt phải thuộc về quân Trắng");
    }

    /**
     * TC ID: TC_AI_02
     * * Mô tả Test Case: Kiểm tra tính năng AI ưu tiên ăn quân đối phương có giá trị cao để giành lợi thế điểm số.
     * * Tiền đề (Pre-condition): Bàn cờ được dọn sạch hoàn toàn; hai quân Vua (Trắng, Đen) được đặt ở các góc an toàn để tránh lỗi hệ thống.
     * * Dữ liệu test: Đặt Xe Đen tại (4, 4) và Hậu Trắng tại (2, 4). Cấu hình lượt đi cho BLACK (AI), độ sâu depth = 2.
     * * Bước thực hiện (Steps):
     * 1. Giải phóng các ô trên bàn cờ về null và đặt 2 quân Vua vào bàn cờ.
     * 2. Đặt Xe Đen và Hậu Trắng vào thế cờ đe dọa (Xe Đen có thể ăn dọc thẳng lên Hậu Trắng).
     * 3. Chuyển whiteTurn = false và gọi Alphabeta.bestMove(root, 2).
     * * Kết quả mong đợi: Nút tốt nhất thu được từ AI phải chứa trạng thái quân Xe Đen di chuyển đè lên tọa độ (2, 4) để tiêu diệt Hậu Trắng.
     */
    @Test
    void test_TC_AI_02_CapturePriority() {
        Board customBoard = game.getBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                customBoard.setPiece(i, j, null);
            }
        }

        customBoard.setPiece(0, 0, new King(PieceColor.BLACK, new Position(0, 0)));
        customBoard.setPiece(7, 7, new King(PieceColor.WHITE, new Position(7, 7)));

        Position blackRookPos = new Position(4, 4);
        Position whiteQueenPos = new Position(2, 4); 
        
        customBoard.setPiece(blackRookPos.getRow(), blackRookPos.getColumn(), new Rook(PieceColor.BLACK, blackRookPos));
        customBoard.setPiece(whiteQueenPos.getRow(), whiteQueenPos.getColumn(), new Queen(PieceColor.WHITE, whiteQueenPos));   
        game.setWhiteTurn(false); 
        Node root = new Node(new ChessGame(game));
        Node bestMoveNode = Alphabeta.bestMove(root, 2);

        assertNotNull(bestMoveNode, "AI phải tìm được nước đi");
        
        Piece pieceAtTarget = bestMoveNode.getState().getBoard().getPiece(whiteQueenPos.getRow(), whiteQueenPos.getColumn());
        assertNotNull(pieceAtTarget, "Ô mục tiêu phải có quân cờ đứng sau khi AI thực hiện nước đi");
        assertEquals(PieceColor.BLACK, pieceAtTarget.getColor(), "Quân cờ tại ô mục tiêu phải là quân Đen");
        assertTrue(pieceAtTarget instanceof Rook, "AI phải chọn giải pháp lấy Xe ăn quân Hậu đối phương để có điểm heuristic cao nhất");
    }

    /**
     * TC ID: TC_AI_03
     * * Mô tả Test Case: Kiểm tra AI nhận diện trạng thái bị Chiếu tướng (Check) và bắt buộc phải chọn nước đi hợp lệ cứu Vua.
     * * Tiền đề (Pre-condition): Bàn cờ trống không có vật cản; quân Vua Trắng nằm ở vị trí biệt lập an toàn (7, 7).
     * * Dữ liệu test: Đặt Vua Đen tại (0, 4) và Hậu Trắng tại (4, 4) tạo thế chiếu trực diện. Lượt đi của Đen (AI), depth = 2.
     * * Bước thực hiện (Steps):
     * 1. Khởi tạo bàn cờ trống, đặt Vua Trắng, Vua Đen và Hậu Trắng vào vị trí chỉ định.
     * 2. Gọi hàm kiểm tra isInCheck(BLACK) để xác định tiền đề bị chiếu là đúng.
     * 3. Chuyển lượt sang Đen và thực hiện hàm tính toán Alphabeta.bestMove(root, 2).
     * * Kết quả mong đợi: Nước đi AI chọn phải đưa Vua Đen thoát khỏi vùng nguy hiểm, hàm isInCheck(BLACK) sau nước đi phải trả về false.
     */
    @Test
    void test_TC_AI_03_RespondToCheck() {
        Board customBoard = game.getBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                customBoard.setPiece(i, j, null);
            }
        }

        customBoard.setPiece(7, 7, new King(PieceColor.WHITE, new Position(7, 7)));

        Position blackKingPos = new Position(0, 4);
        Position whiteQueenPos = new Position(4, 4); 
        
        customBoard.setPiece(blackKingPos.getRow(), blackKingPos.getColumn(), new King(PieceColor.BLACK, blackKingPos));
        customBoard.setPiece(whiteQueenPos.getRow(), whiteQueenPos.getColumn(), new Queen(PieceColor.WHITE, whiteQueenPos));
        
        assertTrue(game.isInCheck(PieceColor.BLACK), "Tiền đề: Vua của AI phải đang bị chiếu tướng trực diện");
        game.setWhiteTurn(false); 
        Node root = new Node(new ChessGame(game));
        Node bestMoveNode = Alphabeta.bestMove(root, 2);
        
        assertNotNull(bestMoveNode, "AI phải đưa ra nước đi phản hồi");
        
        assertFalse(bestMoveNode.getState().isInCheck(PieceColor.BLACK), "Sau nước đi của AI, Vua Đen bắt buộc phải thoát khỏi thế bị chiếu");
    }

    /**
     * TC ID: TC_AI_04
     * * Mô tả Test Case: Kiểm tra AI nhận diện được cơ hội dứt điểm trận đấu bằng nước đi Chiếu hết (Checkmate).
     * * Tiền đề (Pre-condition): Bàn cờ ở trạng thái giả lập bao vây thắt nút cổ chai xung quanh Vua Trắng.
     * * Dữ liệu test: Vua Trắng tại (7, 4), Hậu Đen tại (6, 3), Xe Đen tại (5, 4). Lượt đi quân Đen (AI), depth = 2.
     * * Bước thực hiện (Steps):
     * 1. Sắp xếp các quân cờ vào thế trận dứt điểm phối hợp quân giữa Hậu và Xe Đen đè lên Vua Trắng.
     * 2. Chuyển lượt chơi sang quân Đen (whiteTurn = false).
     * 3. Chạy thuật toán tìm nước đi tối ưu nhất Alphabeta.bestMove(root, 2).
     * * Kết quả mong đợi: AI tự động phân tích điểm số tuyệt đối từ việc kết thúc ván cờ và trả về trạng thái cờ khiến Vua Trắng bị isCheckmate = true.
     */
    @Test
    void test_TC_AI_04_CheckmateMove() {
        Board customBoard = game.getBoard();
        
        Position whiteKingPos = new Position(7, 4);
        Position blackQueenPos = new Position(6, 3); 
        Position blackRookPos = new Position(5, 4);
        
        customBoard.setPiece(whiteKingPos.getRow(), whiteKingPos.getColumn(), new King(PieceColor.WHITE, whiteKingPos));
        customBoard.setPiece(blackQueenPos.getRow(), blackQueenPos.getColumn(), new Queen(PieceColor.BLACK, blackQueenPos));
        customBoard.setPiece(blackRookPos.getRow(), blackRookPos.getColumn(), new Rook(PieceColor.BLACK, blackRookPos));
        
        game.setWhiteTurn(false); 

        Node root = new Node(new ChessGame(game));
        Node bestMoveNode = Alphabeta.bestMove(root, 2);

        assertNotNull(bestMoveNode);
        assertTrue(bestMoveNode.getState().isCheckmate(PieceColor.WHITE), "AI phải chọn nước đi tối ưu nhất để kết thúc trận đấu bằng Checkmate");
    }

    /**
     * TC ID: TC_AI_05
     * * Mô tả Test Case: Kiểm tra hiệu năng xử lý của AI ở Độ sâu thấp (Depth = 1).
     * * Tiền đề (Pre-condition): Bàn cờ khởi tạo với đầy đủ 32 quân cờ ở các vị trí mặc định ban đầu.
     * * Dữ liệu test: Cấu hình lượt chơi quân Đen (AI), tham số độ sâu depth = 1, ngưỡng giới hạn thời gian 100 mili giây.
     * * Bước thực hiện (Steps):
     * 1. Đặt thuộc tính lượt đi sang cho quân Đen.
     * 2. Khởi tạo Node chứa trạng thái ván cờ hiện hành.
     * 3. Đóng gói lời gọi hàm Alphabeta.bestMove(root, 1) vào cấu trúc assertTimeout của JUnit 5.
     * * Kết quả mong đợi: Hàm xử lý hoàn thành nhanh chóng, trả về kết quả nước đi hợp lệ trước khi chạm mốc thời gian giới hạn 100ms.
     */
    @Test
    void test_TC_AI_05_PerformanceDepth1() {
        game.setWhiteTurn(false);
        Node root = new Node(new ChessGame(game));

        assertTimeout(Duration.ofMillis(100), () -> {
            Node best = Alphabeta.bestMove(root, 1);
            assertNotNull(best, "AI ở Depth 1 vẫn phải đưa ra nước đi hợp lệ");
        }, "Thời gian phản hồi ở Depth 1 phải cực kỳ nhanh (< 100ms)");
    }

    /**
     * TC ID: TC_AI_06
     * * Mô tả Test Case: Kiểm tra hiệu năng tính toán của AI ở Độ sâu trung bình tiêu chuẩn (Depth = 3).
     * * Tiền đề (Pre-condition): Trận đấu ở trạng thái mặc định với toàn bộ cấu trúc các quân cờ ban đầu.
     * * Dữ liệu test: Cấu hình lượt chơi quân Đen (AI), tham số độ sâu depth = 3, ngưỡng giới hạn thời gian tối đa là 3 giây.
     * * Bước thực hiện (Steps):
     * 1. Ép lượt chơi hiện tại thuộc về AI (whiteTurn = false).
     * 2. Tạo đối tượng Node quản lý trạng thái từ bàn cờ gốc.
     * 3. Thực thi assertTimeout với thời gian giới hạn 3 giây cho khối lệnh tìm kiếm nước đi Alphabeta.bestMove(root, 3).
     * * Kết quả mong đợi: AI đưa ra được phản hồi thông minh, nước đi không null và tổng thời gian duyệt cây quyết định phải nhỏ hơn 3 giây.
     */
    @Test
    void test_TC_AI_06_PerformanceDepth3To4() {
        game.setWhiteTurn(false);
        Node root = new Node(new ChessGame(game));

        assertTimeout(Duration.ofSeconds(3), () -> {
            Node best = Alphabeta.bestMove(root, 3);
            assertNotNull(best, "AI ở Depth 3 phải đưa ra nước đi thông minh hợp lệ");
        }, "Thời gian phản hồi ở độ sâu trung bình phải nằm trong ngưỡng cho phép (< 3 giây)");
    }

    /**
     * TC ID: TC_AI_07
     * * Mô tả Test Case: Kiểm tra độ ổn định và khả năng chịu tải của AI ở Độ sâu cực đại (Depth = 5).
     * * Tiền đề (Pre-condition): Bàn cờ khởi tạo với cấu hình đầy đủ quân mặc định.
     * * Dữ liệu test: Cấu hình lượt chơi quân Đen (AI), độ sâu cây quyết định depth = 5, ngưỡng thời gian an toàn chống treo hệ thống là 60 giây.
     * * Bước thực hiện (Steps):
     * 1. Thiết lập whiteTurn về giá trị false để nhường quyền xử lý cho AI.
     * 2. Đóng gói hàm Alphabeta.bestMove(root, 5) vào bộ giám sát thời gian assertTimeout.
     * * Kết quả mong đợi: Hệ thống chịu tải thuật toán đệ quy tốt, không xảy ra hiện tượng tràn bộ nhớ (Out Of Memory Error) hay vòng lặp vô hạn gây treo máy, nước đi trả về thành công trong vòng 60 giây.
     */
    @Test
    void test_TC_AI_07_PerformanceMaxDepth() {
        game.setWhiteTurn(false);
        Node root = new Node(new ChessGame(game));

        assertTimeout(Duration.ofSeconds(60), () -> {
            Node best = Alphabeta.bestMove(root, 5);
            assertNotNull(best, "Hệ thống chịu tải tốt, không crash và AI tìm được nước đi thành công");
        }, "Hệ thống không bị treo hoặc Out of Memory khi nâng độ sâu tính toán lên tối đa");
    }

    /**
     * TC ID: TC_AI_08
     * * Mô tả Test Case: Kiểm tra tính hiệu quả và chính xác của giải thuật cắt tỉa Alpha-Beta so với Minimax thuần túy.
     * * Tiền đề (Pre-condition): Hai cây quyết định giống hệt nhau được nhân bản từ một trạng thái bàn cờ ban đầu.
     * * Dữ liệu test: Lượt đi của quân Đen, cấu hình chung độ sâu depth = 3 cho cả hai thuật toán.
     * * Bước thực hiện (Steps):
     * 1. Đo thời gian hệ thống chạy thuật toán cắt tỉa Alpha-Beta bằng System.nanoTime().
     * 2. Đo thời gian hệ thống chạy thuật toán Minimax duyệt đầy đủ bằng System.nanoTime().
     * 3. So sánh đối chiếu hai kết quả thời gian thực thi thu được.
     * * Kết quả mong đợi: Cả 2 thuật toán đều chọn ra được giải pháp nước đi chất lượng tốt, đồng thời thuật toán Alpha-Beta Pruning có thời gian xử lý nhanh và tối ưu hơn đáng kể so với Minimax thuần túy nhờ loại bỏ các nhánh thừa.
     */
    @Test
    void test_TC_AI_08_AlphaBetaPruningEfficiency() {
        game.setWhiteTurn(false);
        Node rootForAlphaBeta = new Node(new ChessGame(game));
        Node rootForMinimax = new Node(new ChessGame(game));

        long startAlphaBeta = System.nanoTime();
        Node resAlphaBeta = Alphabeta.bestMove(rootForAlphaBeta, 3);
        long durationAlphaBeta = System.nanoTime() - startAlphaBeta;

        long startMinimax = System.nanoTime();
        Node resMinimax = Minimax.bestMove(rootForMinimax, 3);
        long durationMinimax = System.nanoTime() - startMinimax;

        assertNotNull(resAlphaBeta);
        assertNotNull(resMinimax);
        
        assertNotNull(resAlphaBeta.getState());
        assertNotNull(resMinimax.getState());
        
        assertTrue(durationAlphaBeta <= durationMinimax, "Alpha-Beta Pruning phải có tốc độ xử lý nhanh hơn hoặc bằng Minimax nhờ loại bỏ các Node thừa");
    }

    /**
     * TC ID: TC_AI_09
     * * Mô tả Test Case: Kiểm tra tính đúng đắn khi thay đổi độ sâu cấu hình (Depth) động của AI trực tiếp trong trận đấu.
     * * Tiền đề (Pre-condition): Bàn cờ khởi tạo với toàn bộ trạng thái quân mặc định ban đầu.
     * * Dữ liệu test: Lượt đi quân Đen (AI). Lần lượt thực hiện tính toán ở 2 mốc cấu hình khác nhau: depth = 1 (Dễ) và depth = 3 (Trung bình).
     * * Bước thực hiện (Steps):
     * 1. Chạy hàm bestMove với tham số độ sâu là 1 và đo thời gian xử lý (durationLow).
     * 2. Chạy hàm bestMove với tham số độ sâu tăng lên là 3 và đo thời gian xử lý (durationHigh).
     * 3. So sánh hai khoảng thời gian đo được.
     * * Kết quả mong đợi: Hệ thống nhận diện chính xác sự thay đổi cấu hình độ sâu; ở độ sâu cao hơn (depth = 3), thuật toán bắt buộc phải duyệt lượng Node lớn hơn nên thời gian durationHigh phải lớn hơn durationLow một cách rõ rệt.
     */
    @Test
    void test_TC_AI_09_DynamicDepthChange() {
        game.setWhiteTurn(false);
        Node root = new Node(new ChessGame(game));

        long startTimeLow = System.currentTimeMillis();
        Alphabeta.bestMove(root, 1);
        long durationLow = System.currentTimeMillis() - startTimeLow;

        long startTimeHigh = System.currentTimeMillis();
        Alphabeta.bestMove(root, 3);
        long durationHigh = System.currentTimeMillis() - startTimeHigh;

        assertTrue(durationHigh > durationLow, "Khi thay đổi nâng cao độ sâu cấu hình, AI phải tính toán lâu hơn và kỹ hơn");
    }
}