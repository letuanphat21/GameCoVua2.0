package covua.ai;

import java.util.ArrayList;
import java.util.List;

import covua.ChessGame;
import covua.Position;
import covua.chess.Piece;

public class Node {
	private ChessGame state;

	public Node() {
		this.state = new ChessGame();
	}

	public Node(ChessGame state) {
		this.state = state;
	}

	public ChessGame getState() {
		return state;
	}

	public boolean GameOver() {
		return state.isCheckmate(state.getCurrentPlayerColor());
	}

	public Piece[][] copy(Piece[][] array) {
		int lenght = array.length;
		Piece[][] rs = new Piece[lenght][lenght];
		for (int i = 0; i < lenght; i++) {
			for (int j = 0; j < lenght; j++) {
				rs[i][j] = (array[i][j] == null) ? null : array[i][j].clone();
			}
		}
		return rs;
	}

	public List<Node> generationChild() {
		List<Node> list = new ArrayList<Node>();

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Position selected_assume_start = new Position(row, col);
				Piece piece = state.getBoard().getPiece(row, col);
				if (piece == null)
					continue;
				if (piece.getColor() != state.getCurrentPlayerColor())
					continue;
				List<Position> legalMoves = state.getLegalMovesForPieceAt(selected_assume_start);
				for (Position position_end : legalMoves) {
					ChessGame newState = new ChessGame(state);
					newState.makeMoveForAI(selected_assume_start, position_end);
					list.add(new Node(newState));
				}

			}

		}
		return list;
	}

	public static void main(String[] args) {
		Node node_init = new Node();
//		node_init.getState().getBoard().print(node_init.getState().getBoard().getPieces());
		List<Node> ls = node_init.generationChild();
		for (Node node : ls) {
			System.out.println(node);
		}
	}

}
