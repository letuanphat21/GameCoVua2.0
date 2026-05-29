package covua.ai;

import java.util.List;


import covua.PieceColor;

public class Minimax {

	public static int minimax(Node node, int depth, boolean isMax) {
		if (node.GameOver() || depth == 0) {
			return Evaluator.heurictis(node.getState().getBoard(), PieceColor.BLACK);
		}
		if (isMax) {
			int mx = Integer.MIN_VALUE;
			List<Node> listCon = node.generationChild();
			for (Node nodeChild : listCon) {
				mx = Math.max(mx, minimax(nodeChild, depth - 1, false));
			}
			return mx;
		} else {
			int mn = Integer.MAX_VALUE;
			List<Node> listCon = node.generationChild();
			for (Node nodeChild : listCon) {
				mn = Math.min(mn, minimax(nodeChild, depth - 1, true));
			}
			// 8.2.7: Hệ thống trả về diểm số heuristic
			return mn;
		}

	}

	public static Node bestMove(Node node, int depth) {
		Node bestNode = null;
		int bestValue = Integer.MIN_VALUE;

		List<Node> children = node.generationChild();

		for (Node child : children) {
			// Lúc nào node hiện tại cũng là max nên chạy
			// hàm này thì nó sẽ là false trước thôi nha
			// false vì sau Black là White
			int value = minimax(child, depth - 1, false);

			if (value > bestValue) {
				bestValue = value;
				bestNode = child;
			}
		}
		return bestNode;
	}

}
