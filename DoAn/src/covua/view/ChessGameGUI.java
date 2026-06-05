package covua.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingWorker;

import covua.Board;
import covua.ChessGame;
import covua.PieceColor;
import covua.Position;
import covua.ai.Alphabeta;
import covua.ai.Minimax;
import covua.ai.Node;
import covua.chess.Bishop;
import covua.chess.King;
import covua.chess.Knight;
import covua.chess.Pawn;
import covua.chess.Piece;
import covua.chess.Queen;
import covua.chess.Rook;

public class ChessGameGUI extends JPanel {
	private final ChessSquareComponent[][] squares = new ChessSquareComponent[8][8];

	private final ChessGame game = new ChessGame();
	private List<ChessGame> listState = new ArrayList<ChessGame>();
	private boolean isAi;
	private boolean useAlphabeta = true;
	private int aiDepth = 3;
	private MainFrame mainFrame;
//	private ChessClient client;
	private Position selectedPos = null;
	private final Map<Class<? extends Piece>, String> pieceUnicodeMap = new HashMap<Class<? extends Piece>, String>() {
		{
			put(Pawn.class, "\u265F");
			put(Rook.class, "\u265C");
			put(Knight.class, "\u265E");
			put(Bishop.class, "\u265D");
			put(Queen.class, "\u265B");
			put(King.class, "\u265A");
		}
	};

	private DefaultListModel<String> historyModel = new DefaultListModel<>();
	private JList<String> historyView = new JList<>(historyModel);
	private JLabel turnLabel = new JLabel("", SwingConstants.CENTER);
	private static final Color WHITE_MOVE_BACKGROUND = new Color(245, 248, 255);
	private static final Color BLACK_MOVE_BACKGROUND = new Color(48, 52, 63);
	private static final Color WHITE_MOVE_FOREGROUND = new Color(30, 44, 70);
	private static final Color BLACK_MOVE_FOREGROUND = new Color(245, 247, 250);
	private static final Color TURN_WHITE_BACKGROUND = new Color(255, 255, 255);
	private static final Color TURN_BLACK_BACKGROUND = new Color(35, 39, 48);
	//9.1.9. Giao diện vùng lịch sử nước đi (JList historyView)
	//hiển thị lại toàn bộ chuỗi các nước đi đang có trong historyMoves của ChessGame.

    //1.1.5: ChessGameGUI khởi tạo layout BorderLayout
	public ChessGameGUI(MainFrame mainFrame,boolean isAi) {
	    this.mainFrame = mainFrame;
	    this.isAi = isAi;
	    this.game.setAiMode(isAi);

	    setLayout(new BorderLayout());

	    JPanel boardPanel = new JPanel(new GridLayout(8, 8));
	    boardPanel.setPreferredSize(new Dimension(640, 640));

        //1.1.6: Cấu hình vùng lịch sử nước đi
	    configureHistoryView();

		//9.1.10. User trực quan thấy toàn bộ lịch sử nước đi cập nhật theo thứ tự
		//(từng dòng, mỗi dòng biểu diễn 1 nước) ở vùng lịch sử bên phải giao diện.
	    JPanel historyPanel = new JPanel(new BorderLayout());
	    JPanel historyHeader = new JPanel(new BorderLayout());
	    JLabel historyTitle = new JLabel("Move History", SwingConstants.CENTER);
	    historyTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
	    historyTitle.setBorder(new EmptyBorder(8, 0, 8, 0));
	    turnLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
	    turnLabel.setOpaque(true);
	    turnLabel.setBorder(new EmptyBorder(7, 0, 7, 0));
	    turnLabel.setVisible(!isAi);
	    historyHeader.add(turnLabel, BorderLayout.NORTH);
	    historyHeader.add(historyTitle, BorderLayout.CENTER);
	    JScrollPane lichSuPane = new JScrollPane(historyView);
	    historyPanel.add(historyHeader, BorderLayout.NORTH);
	    historyPanel.add(lichSuPane, BorderLayout.CENTER);
	    historyPanel.setPreferredSize(new Dimension(200, 640));

	    add(boardPanel, BorderLayout.CENTER);
	    add(historyPanel, BorderLayout.EAST);
// 1.1.7: Khởi tạo bàn cờ 8x
	    initializeBoard(boardPanel);

	}
//1.1.6: Cấu hình vùng lịch sử nước đi
	private void configureHistoryView() {
		historyView.setFixedCellHeight(30);
		historyView.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
		historyView.setSelectionBackground(new Color(70, 130, 180));
		historyView.setSelectionForeground(Color.WHITE);
		historyView.setCellRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index,
					boolean isSelected, boolean cellHasFocus) {
				JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				label.setOpaque(true);
				label.setHorizontalAlignment(SwingConstants.LEFT);
				label.setFont(list.getFont());

				if (!isSelected) {
					boolean isWhiteMove = index % 2 == 0;
					label.setBackground(isWhiteMove ? WHITE_MOVE_BACKGROUND : BLACK_MOVE_BACKGROUND);
					label.setForeground(isWhiteMove ? WHITE_MOVE_FOREGROUND : BLACK_MOVE_FOREGROUND);
				}

				label.setText("  " + value);
				return label;
			}
		});
	}
//	public ChessGameGUI(MainFrame mainFrame,boolean isAi,ChessClient client) {
//		this.mainFrame=mainFrame;
//		this.isAi = isAi;
//		this.client = client;
//		setPreferredSize(new Dimension(640, 640));
//		setLayout(new GridLayout(8, 8));
//		initializeBoard();
//
//
//	}
//	public void setClient(ChessClient client) {
//	    this.client = client;
//	}
	public void receiveNetworkMove(String move) {
	    // ví dụ move = "e2 e4"
	    game.applyMove(move);    // Tự bạn implement
	    refreshBoard();
	}

    //1.1.7: Khởi tạo bàn cờ 8x8
	private void initializeBoard(JPanel boardPanel) {
	    for (int row = 0; row < squares.length; row++) {
	        for (int col = 0; col < squares.length; col++) {
	            final int finalRow = row;
	            final int finalCol = col;

	            ChessSquareComponent square = new ChessSquareComponent(row, col);
	            
//	            3.1.0 Người chơi dùng chuột click vào ô chứa quân Vua.
	            
	            // 3.1.9: Người chơi click chọn ô đích cách Vua 1 ô xung quanh.
	            
	            // 3.2.9: Người chơi click chọn ô đích cách Vua đúng 2 ô ngang trên hàng xuất phát (cột 6 cho nhập thành gần, cột 2 cho nhập thành xa).
	            square.addMouseListener(new MouseAdapter() {
	                @Override
	                public void mouseClicked(MouseEvent e) {
	                    handleSquareClick(finalRow, finalCol);
	                }
	            });

	            boardPanel.add(square);
	            squares[row][col] = square;
	        }
	    }
        //1.1.8: Vẽ lại bàn cờ với các quân ở vị trí ban đầu
	    refreshBoard();
	}

	//9.1.6. Kết quả thực thi nước đi trả về thành công,
	//ChessGameGUI gọi hàm refreshBoard() để bắt đầu cập nhật giao diện.
    //1.1.8: Cập nhật giao diện bàn cờ
	private void refreshBoard() {
		Board board = game.getBoard();
		//9.1.7. Hệ thống vẽ lại tất cả các quân cờ ở đúng trạng thái mới trên vùng giao diện bàn cờ.

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Piece piece = board.getPiece(row, col);
				if (piece != null) {
					String symbol = pieceUnicodeMap.get(piece.getClass());
					Color color = (piece.getColor() == PieceColor.WHITE) ? Color.WHITE : Color.BLACK;
					squares[row][col].setPieceSymbol(symbol, color);
				} else {
					squares[row][col].clearPieceSymbol();
				}
			}
		}
		historyModel.clear();
		//9.1.8. Hệ thống thông qua game.getHistoryMoves() để lấy lên toàn bộ lịch sử nước đi hiện tại,
		//xóa dữ liệu cũ và cập nhật dữ liệu mới vào historyModel.

		// 8.1.12: Hệ thống cập nhật lịch sử nước đi (historyView) ở vùng giao diện bên phải
		int moveNumber = 1;
		for (String move : game.getHistoryMoves()) {
			historyModel.addElement(moveNumber + ". " + move);
			moveNumber++;
		}
		updateTurnLabel();
	}
    // 1.1.11: Cập nhật turnLabel
	private void updateTurnLabel() {
		if (isAi) {
			return;
		}

		if (game.getCurrentPlayerColor() == PieceColor.WHITE) {
			turnLabel.setText("Turn: White");
			turnLabel.setBackground(TURN_WHITE_BACKGROUND);
			turnLabel.setForeground(Color.BLACK);
		} else {
			turnLabel.setText("Turn: Black");
			turnLabel.setBackground(TURN_BLACK_BACKGROUND);
			turnLabel.setForeground(Color.WHITE);
		}
	}

	//9.1.3. ChessGameGUI gọi tiếp game.handleSquareSelection(row, col) để xác thực và thực thi nước đi.
	//1.1.2: Xử lý click vào ô bàn cờ
    private void handleSquareClick(int row, int col) {
		// 6.1.1: Hệ thống gọi phương thức handleSquareClick(int row, int col) trong lớp ChessGameGUI.
		// 6.1.2: ChessGameGUI chuyển tiếp yêu cầu đến phương thức handleSquareSelection(row, col) trong lớp ChessGame.
    	
    	//3.1.1: ChessGame Gọi handleSquareSelection(row, col). Do selectedPosition == null.
    	//3.1.10: ChessGame gọi hàm handleSquareSelection(row, col). Do selectedPosition != null.
    	
    	//3.2.10: ChessGame gọi hàm handleSquareSelection(row, col). Do selectedPosition != null.
		boolean moveResult = game.handleSquareSelection(row, col);
		
		// 6.3.4: Hệ thống đặt lại selectedPosition = null, gọi clearHighlights() và chờ người dùng chọn lại từ đầu.
		// 6.5.4: Hệ thống đặt selectedPosition = null và gọi clearHighlights().
		clearHighlights();
		if (moveResult) {
			// LẤY TỌA ĐỘ ĐÃ CHỌN
//	        Position selectedPos = game.getLastMoveSource();
//	        Position newPos = game.getLastMoveTarget();
	        //  KHÔNG ĐỤNG VÀO LOGIC CỦ

	        // 6.1.25: (Giao diện) ChessGameGUI gọi refreshBoard() để vẽ lại bàn cờ
			
			// 3.1.20: ChessGameGUI gọi refreshBoard().
			
			// 3.2.18: Gọi refreshBoard() vẽ lại bàn cờ
			refreshBoard();
			//3.1.21: Kết thúc usecase.
			//3.2.19: Kết thúc usecase
			
			// 6.1.26: Hệ thống gọi checkGameState(), nếu đối phương bị chiếu, hệ thống tô đỏ ô chứa Vua đối phương.
			checkGameState();
			if (listState.size() == 6) {
				checkGameDraw();
				listState.clear();
			}
			//  GỬI MOVE ONLINE (nếu có client)
//	        String moveText = selectedPos.toString() + " " + newPos.toString();
//	        if (client != null) {
//	            client.sendMove(moveText);
//	        }
			// 6.9.1: Hệ thống gọi checkGameOverAfterMove().
			// (Bên trong hàm này sẽ thực hiện 6.9.2 và 6.9.3: Nếu phát hiện trạng thái isCheckmate
			// hoặc checkGameDraw (hòa), hệ thống hiển thị thông báo kết thúc trận đấu qua JOptionPane.
			// Nếu người dùng chọn "Yes", hệ thống gọi resetGame() để khởi tạo lại bàn cờ mới).
			checkGameOverAfterMove();
			//9.1.4. Hệ thống kiểm tra tính hợp lệ của nước đi,
			//nếu nước đi hợp lệ thì hệ thống gọi hàm makeMove(Position, Position)
			//nội bộ để thực thi và tiếp tục bước tiếp theo.
			// 6.1.27: Hệ thống kiểm tra isAi, nếu là lượt của máy, hệ thống kích hoạt makeAIMove().

			// 8.1.0: Usecase bắt đầu sau khi người chơi thực hiện xong nước đi hợp lệ của mình
			//(Kết thúc Use Case di chuyển quân cờ của User, hệ thống kiểm tra thấy isAi == true và lượt đi tiếp theo
			//là của quân Đen PieceColor.BLACK).
			if(isAi ) {
				if (game.getCurrentPlayerColor() == PieceColor.BLACK) {
					// 8.1.1: ChessGameGUI gọi phương thức makeAIMove().
					makeAIMove();
				}
			}


		} else if (game.isPieceSelected()) {
//			System.out.println(game.getSelectedPosition());
			// thằng này sẽ hiện lên những nước đi hợp lệ
			// của vị trị mà ta kích vào nếu chỗ đó có quân
			// của ta
			// 6.1.7: (Giao diện) ChessGameGUI gọi highlightLegalMoves() để tô màu xanh các ô đích hợp lệ cho quân Xe đã chọn.
			highlightLegalMoves(new Position(row, col));
		}
		// 6.1.25: (Giao diện) ChessGameGUI gọi refreshBoard() để vẽ lại bàn cờ
		refreshBoard();
	}

	private void makeAIMove() {
	    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	    SwingWorker<Node, Void> worker = new SwingWorker<Node, Void>() {

	        @Override
	        protected Node doInBackground() throws Exception {
	            // 8.1.2: Hệ thống khởi trạng thái giả lập ban đầu bằng cách tạo đối tượng sao chép:
	            //Node root = new Node(new ChessGame(this.game))
	            // 8.1.3: Hệ thống trả về đối tượng sao chép root
	            Node root = new Node(new ChessGame(ChessGameGUI.this.game));
	            Node best = null;

	            // 8.1.4: Hệ thống kiểm tra biến cờ useAlphabeta để xác định chạy AI nào (mặc định là Alphabeta)
	            if (useAlphabeta) {
	                // 8.1.5: Hệ thống thực hiện hàm Alphabeta.bestMove(root, depth) để tiến hành tính toán nước đi tốt nhất
	                // 8.1.6: Hệ thống tiến hành duyệt cây quyết định và tính toán điểm số bàn cờ dựa trên hàm đánh giá trọng
	                //số quân cờ (Evaluator.heurictis).
	                best = Alphabeta.bestMove(root, aiDepth); // Chạy Alphabeta
	            } else {
	                // 8.2.4 Hệ thống kiểm tra useAlphabeta == false (lúc này chạy Minimax)
	                // 8.2.5: Hệ thống chuyển sang gọi phương thức Minimax.bestMove(root, depth) để tính toán nước đi
	                // 8.2.6: Hệ thống tiến hành duyệt cây quyết định và tính toán điểm số bàn cờ dựa trên hàm đánh giá trọng
	                //số quân cờ (Evaluator.heurictis).
	                best = Minimax.bestMove(root, aiDepth);   // Chạy Minimax
	            }

	            return best;
	        }

	        @Override
	        protected void done() {
	            try {
	                Node best = get();

	                // 8.1.8 / 8.2.8: Hệ thống trả về kết quả nước đi tốt nhất best (kiểu Node)
	                if (best != null) {
	                    // 8.1.9: ChessGameGUI cập nhật trạng thái bàn cờ thực tế bằng cách sao chép dữ liệu từ
	                	//AI: this.game.copyFrom(best.getState()).
	                    ChessGameGUI.this.game.copyFrom(best.getState());
	                    // 8.1.10: Hệ thống lưu lại trạng thái mới vào danh sách kiểm tra hòa: listState.add(new ChessGame(this.game)).
	                    listState.add(new ChessGame(ChessGameGUI.this.game));
	                    // 8.1.11: ChessGameGUI gọi phương thức refreshBoard() để xóa dữ liệu cũ, vẽ lại toàn bộ quân cờ
	                    refreshBoard();
	                    // 8.1.13: Hệ thống gọi phương thức checkGameState()
	                    //để kiểm tra xem nước đi của AI có chiếu Vua đối phương (Trắng) hay không. Nếu có, tô đỏ ô chứa Vua Trắng.
	                    checkGameState();
	                    // 8.1.14: Hệ thống kiểm tra điều kiện kích hoạt luật hòa: Kiểm tra nếu kích thước bộ nhớ
	                    //trạng thái chưa đủ điều kiện hòa (listState.size() != 6).
	                    if (listState.size() == 6) {
	                        // 8.4.12 Hệ thống phát hiện listState.size() == 6 và tự động gọi phương thức checkGameDraw().
	                        checkGameDraw();
	                        listState.clear();
	                    }
	                    // 8.1.15: Hệ thống gọi phương thức checkGameOverAfterMove() để xác định xem người chơi Trắng
	                    //có bị chiếu hết hay không
	                    checkGameOverAfterMove();
	                } else {
	                    // 8.3.8: Hệ thống nhận kết quả trả về best == null
	                    // 8.3.9: Hệ thống bỏ qua các bước cập nhật dữ liệu và vẽ lại bàn cờ (từ bước 8.1.9 đến 8.1.13)
	                    // 8.3.10: Hệ thống chuyển thẳng đến bước 8.1.14 để kiểm tra trận đấu có kết thúc hay chưa
	                    if (listState.size() == 6) {
	                        checkGameDraw();
	                        listState.clear();
	                    }
	                    checkGameOverAfterMove();
	                }
	                // 8.1.16: Kết thúc usecase. Lượt chơi quay lại cho người chơi (Trắng) nếu game còn tiếp diễn

	            } catch (Exception e) {
	                e.printStackTrace();
	            } finally {
	                ChessGameGUI.this.setCursor(java.awt.Cursor.getDefaultCursor());
	            }
	        }
	    };

	    worker.execute();
	}

	public void checkGameDraw() {
		// 8.4.15: Phương thức checkGameDraw() tiến hành đối chiếu tính trùng lặp của các trạng thái bàn cờ
		//(listState.get(1), get(3), get(5)):
		if (listState.get(1).equals(listState.get(3)) && listState.get(1).equals(listState.get(5))) {
			// 8.4.15b: Thỏa mãn điều kiện lặp lại (Hòa cờ)
			// 8.4.16b: Hệ thống hiển thị một hộp thoại thông báo (JOptionPane.showConfirmDialog) với nội dung "Draw",
			//tiêu đề "Game Over" cùng hai nút lựa chọn YES (Chơi lại ván mới) và NO (Thoát chương trình).
			int response = JOptionPane.showConfirmDialog(this, "Draw", "Game Over", JOptionPane.YES_NO_OPTION);
			// Luồng thay thế ghi nhận phản hồi lựa chọn từ người dùng tại bước 8.4.14b
			if (response == JOptionPane.YES_OPTION) {
				// 8.5.17b: Người dùng nhấn Yes
				// 8.5.18b: Hệ thống thực hiện giải phóng bộ nhớ cũ bằng cách làm trống danh sách listState.
				listState.clear();
				// 8.5.19b: Hệ thống gọi phương thức resetGame() để khởi tạo lại dữ liệu bàn cờ mặc định ban đầu.
				// 8.5.20b: Hệ thống gọi clearHighlights() để xóa các ô màu đỏ/xanh trên bàn cờ.
				// 8.5.21b: Hệ thống gọi refreshBoard() để làm trống danh sách lịch sử nước đi.
				resetGame();
				// 8.5.22b: Kết thúc usecase
			} else {
				// 8.6.17b: Người dùng nhấn No
				// 8.6.18b: Hệ thống thực thi lệnh System.exit(0) để đóng toàn bộ ứng dụng.
				System.exit(0);
				// 8.6.19b: Kết thúc usecase.
			}
		} else {
			// 8.4.13a: Trường hợp không thỏa điều kiện lặp lại (Không hòa):
			// 8.4.14a: Hệ thống thực hiện xóa danh sách trạng thái cũ: listState.clear()
			listState.clear();
			// 8.4.15a: Hệ thống quay trở lại bước 8.1.13 của luồng cơ bản (Trận đấu tiếp tục)
		}
	}

	private void checkGameState() {
		// Cần kiểm tra lại method này
		PieceColor currentPlayer = game.getCurrentPlayerColor();
		boolean inCheck = game.isInCheck(currentPlayer);

		if (inCheck) {
			// 8.1.11: Hệ thống gọi phương thức checkGameState() để kiểm tra xem nước đi của AI có chiếu Vua đối phương (Trắng)
			//hay không. Nếu có, tô đỏ ô chứa Vua Trắng.
			Position kingPosition = game.findKingPosition(currentPlayer);
			squares[kingPosition.getRow()][kingPosition.getColumn()].setBackground(Color.RED);
		}

	}

	private void highlightLegalMoves(Position position) {
		// 3.1.6: ChessGameGUI ->ChessGame gọi hàm getLegalMovesForPieceAt(position) tính toán các ô đi hợp lệ.
		List<Position> legalMoves = game.getLegalMovesForPieceAt(position);
		for (Position move : legalMoves) {
			// 3.1.8: ChessGameGUI tô sáng màu xanh các ô gợi í nước đi này trên bàn cờ.
			squares[move.getRow()][move.getColumn()].setBackground(Color.GREEN);
		}
	}

	private void clearHighlights() {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				squares[row][col].setBackground((row + col) % 2 == 0 ? Color.LIGHT_GRAY : new Color(205, 133, 63));
			}
		}
	}

//	private void addGameResetOption() {
//		JMenuBar menuBar = new JMenuBar();
//		JMenu gameMenu = new JMenu("Game");
//		JMenuItem resetItem = new JMenuItem("Reset");
//		resetItem.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				resetGame();
//			}
//		});
//		gameMenu.add(resetItem);
//		menuBar.add(gameMenu);
//		this.setJMenuBar(menuBar);
//	}

    //1.1.10: Tạo Menu Bar
	public JMenuBar createMenuBar() {
	    JMenuBar menuBar = new JMenuBar();
	    JMenu gameMenu = new JMenu("Game");
	    JMenu aiMenu = new JMenu("Chọn AI");
	    JMenu depthMenu = new JMenu("Độ sâu AI");
	    JMenuItem resetItem = new JMenuItem("Reset");
	    JMenuItem backHome = new JMenuItem("home");


	    // Bổ sung thêm tùy chọn giữa hai thuật toán Minimax và Alpha-beta
	    JRadioButtonMenuItem alphabetaItem = new JRadioButtonMenuItem("Thuật toán Alpha-Beta", true);
	    JRadioButtonMenuItem minimaxItem = new JRadioButtonMenuItem("Thuật toán Minimax", false);

 		ButtonGroup aiGroup = new ButtonGroup();
 		aiGroup.add(alphabetaItem);
 		aiGroup.add(minimaxItem);

 		alphabetaItem.addActionListener(e -> {
 			useAlphabeta = true;
 			JOptionPane.showMessageDialog(this, "Đã chuyển sang AI: Alpha-Beta");
 		});

 		minimaxItem.addActionListener(e -> {
 			useAlphabeta = false;
 			JOptionPane.showMessageDialog(this, "Đã chuyển sang AI: Minimax");
 		});

 		aiMenu.add(alphabetaItem);
 		aiMenu.add(minimaxItem);

 		// Thực hiện thêm các option chỉnh độ khó theo mong muốn của người dùng
 		ButtonGroup depthGroup = new ButtonGroup();
 		// Độ sâu được quy định theo mức độ:
 		// Dễ: 1 -> 2
 		// Bình thường: 3 -> 4
 		// Khó: 5 -> 6
 		for (int i = 1; i <= 6; i++) {
 	        int depthValue = i;
 	        JRadioButtonMenuItem depthItem = new JRadioButtonMenuItem("Độ sâu: " + depthValue, depthValue == 3);
 	        depthGroup.add(depthItem);

 	        depthItem.addActionListener(e -> {
 	            aiDepth = depthValue;
 	            JOptionPane.showMessageDialog(this, "Đã đổi độ sâu AI (Depth) thành: " + aiDepth);
 	        });
 	        depthMenu.add(depthItem);
 	    }

	    resetItem.addActionListener(e -> resetGame());
	    backHome.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetGame();
				mainFrame.goBack("home");

			}
		});
	    gameMenu.add(backHome);
	    gameMenu.add(resetItem);
	    gameMenu.addSeparator();
		gameMenu.add(aiMenu);
		gameMenu.add(depthMenu);
	    menuBar.add(gameMenu);
	    return menuBar;
	}

    //1.1.12: Reset game
	private void resetGame() {
		game.resetGame();
		clearHighlights();
		refreshBoard();
	}

	private void checkGameOver() {
		// Cần kiểm tra lại method này
		if (game.isCheckmate(game.getCurrentPlayerColor())) {
			int response = JOptionPane.showConfirmDialog(this, "Checkmate! Would you like to play again ?", "Game Over",
					JOptionPane.YES_NO_OPTION);
			if (response == JOptionPane.YES_OPTION) {
				resetGame();
			} else {
				System.exit(0);
			}
		}
	}

	private void checkGameOverAfterMove() {
		PieceColor loser = game.getCurrentPlayerColor(); // người sắp đi
		String opposite = "White";
		if (loser == PieceColor.BLACK) {
			opposite = "White";
		} else {
			opposite = "Black";
		}

		// Luồng thay thế khi nước đi AI chiếu hết người chơi, xảy ra tại bước 8.1.13
		// 8.7.15: Phương thức checkGameOverAfterMove() xác định người chơi tiếp theo (Trắng)
		//đã rơi vào trạng thái bị chiếu hết (game.isCheckmate(loser) == true)
		if (game.isCheckmate(loser)) {
			// 8.7.16: Hệ thống hiển thị một hộp thoại thông báo (JOptionPane.showConfirmDialog) với
			//nội dung "Checkmate! Black wins. Play again?", tiêu đề "Game Over" kèm hai lựa chọn YES và NO.
			int response = JOptionPane.showConfirmDialog(this, "Checkmate! " + opposite + " wins. Play again?",
					"Game Over", JOptionPane.YES_NO_OPTION);
			if (response == JOptionPane.YES_OPTION) {
				// 8.8.17: Người dùng nhấn Yes
				// 8.8.18: Hệ thống thực hiện làm trống danh sách listState để chuẩn bị cho ván đấu mới.
				listState.clear();
				// 8.8.19: Hệ thống gọi phương thức resetGame() để làm mới bàn cờ và đặt lại lượt đi đầu tiên cho quân Trắng.
				resetGame();
				// 8.8.20: Kết thúc usecase
			} else {
				// 8.9.17: Người dùng nhấn No
				// 8.9.18: Hệ thống thực thi lệnh System.exit(0) để tắt chương trình ngay lập tức.
				System.exit(0);
				// 8.9.19: Kết thúc usecase
			}
		}
	}

//	public static void main(String[] args) {
//		SwingUtilities.invokeLater(ChessGameGUI::new);
//	}

}
