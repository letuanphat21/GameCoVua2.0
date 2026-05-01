package covua.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import covua.Board;
import covua.ChessGame;
import covua.PieceColor;
import covua.Position;
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
	
	public ChessGameGUI(MainFrame mainFrame,boolean isAi) {
	    this.mainFrame = mainFrame;
	    this.isAi = isAi;

	    setLayout(new BorderLayout()); 

	    JPanel boardPanel = new JPanel(new GridLayout(8, 8));
	    boardPanel.setPreferredSize(new Dimension(640, 640));

	    JScrollPane lichSuPane = new JScrollPane(historyView);
	    lichSuPane.setPreferredSize(new Dimension(200, 640));

	    add(boardPanel, BorderLayout.CENTER); 
	    add(lichSuPane, BorderLayout.EAST);   

	    initializeBoard(boardPanel);
	
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

	private void initializeBoard(JPanel boardPanel) {
	    for (int row = 0; row < squares.length; row++) {
	        for (int col = 0; col < squares.length; col++) {
	            final int finalRow = row;
	            final int finalCol = col;

	            ChessSquareComponent square = new ChessSquareComponent(row, col);

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
	    refreshBoard();
	}

	private void refreshBoard() {
		Board board = game.getBoard();
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
		for (String move : game.getHistoryMoves()) {
			historyModel.addElement(move);
		}
	}

	private void handleSquareClick(int row, int col) {

		boolean moveResult = game.handleSquareSelection(row, col);
		clearHighlights();
		if (moveResult) {
			// LẤY TỌA ĐỘ ĐÃ CHỌN
//	        Position selectedPos = game.getLastMoveSource();
//	        Position newPos = game.getLastMoveTarget();
	        //  KHÔNG ĐỤNG VÀO LOGIC CỦ
	        
	        
			refreshBoard();
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
			checkGameOverAfterMove();
			if(isAi ) {
				if (game.getCurrentPlayerColor() == PieceColor.BLACK) {
					makeAIMove();
				}
			}
			

		} else if (game.isPieceSelected()) {
//			System.out.println(game.getSelectedPosition());
			// thằng này sẽ hiện lên những nước đi hợp lệ
			// của vị trị mà ta kích vào nếu chỗ đó có quân
			// của ta
			highlightLegalMoves(new Position(row, col));
		}
		refreshBoard();
	}

	private void makeAIMove() {
		Node root = new Node(new ChessGame(this.game));
		Node best = Minimax.bestMove(root, 3);
		if (best != null) {
			this.game.copyFrom(best.getState());
			listState.add(new ChessGame(this.game));
			refreshBoard();
			checkGameState();
			if (listState.size() == 6) {
				checkGameDraw();
				listState.clear();
			}
			checkGameOverAfterMove();
		}
	}

	public void checkGameDraw() {
		if (listState.get(1).equals(listState.get(3)) && listState.get(1).equals(listState.get(5))) {
			int response = JOptionPane.showConfirmDialog(this, "Draw", "Game Over", JOptionPane.YES_NO_OPTION);
			if (response == JOptionPane.YES_OPTION) {
				resetGame();
			} else {
				System.exit(0);
			}
		}
	}

	private void checkGameState() {
		// Cần kiểm tra lại method này
		PieceColor currentPlayer = game.getCurrentPlayerColor();
		boolean inCheck = game.isInCheck(currentPlayer);

		if (inCheck) {
			Position kingPosition = game.findKingPosition(currentPlayer);
			squares[kingPosition.getRow()][kingPosition.getColumn()].setBackground(Color.RED);
		}

	}

	private void highlightLegalMoves(Position position) {
		List<Position> legalMoves = game.getLegalMovesForPieceAt(position);
		for (Position move : legalMoves) {
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
	public JMenuBar createMenuBar() {
	    JMenuBar menuBar = new JMenuBar();
	    JMenu gameMenu = new JMenu("Game");
	    JMenuItem resetItem = new JMenuItem("Reset");
	    JMenuItem backHome = new JMenuItem("home");
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
	    menuBar.add(gameMenu);
	    return menuBar;
	}
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
		if (game.isCheckmate(loser)) {
			int response = JOptionPane.showConfirmDialog(this, "Checkmate! " + opposite + " wins. Play again?",
					"Game Over", JOptionPane.YES_NO_OPTION);
			if (response == JOptionPane.YES_OPTION) {
				resetGame();
			} else {
				System.exit(0);
			}
		}
	}

//	public static void main(String[] args) {
//		SwingUtilities.invokeLater(ChessGameGUI::new);
//	}

}
