package covua.ai;

import java.util.List;

import covua.PieceColor;

public class Alphabeta {

	public static int alphabeta(Node node, int depth, int alpha, int beta, boolean isMax) {
		if (node.GameOver() || depth == 0) {
			return Evaluator.heurictis(node.getState().getBoard(), PieceColor.BLACK);
		}
		if (isMax) {
			int mx = Integer.MIN_VALUE;
			List<Node> listCon = node.generationChild();
			for (Node nodeChild : listCon) {
				mx = Math.max(mx, alphabeta(nodeChild, depth - 1, alpha, beta, false));
				alpha = Math.max(alpha, mx);
				if (beta <= alpha) {
					break;
				}
			}
			return mx;
		} else {
			int mn = Integer.MAX_VALUE;
			List<Node> listCon = node.generationChild();
			for (Node nodeChild : listCon) {
				mn = Math.min(mn, alphabeta(nodeChild, depth - 1, alpha, beta, true));
				beta = Math.min(beta, mn);
				if (beta <= alpha) {
					break;
				}
			}
			return mn;
		}
	}

	public static Node bestMove(Node node, int depth) {
		Node bestNode = null;
		int bestValue = Integer.MIN_VALUE;
		
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;

		List<Node> children = node.generationChild();

		for (Node child : children) {
			// Lúc nào node hiện tại cũng là max nên chạy
			// hàm này thì nó sẽ là false trước thôi nha
			// false vì sau Black là White
			int value = alphabeta(child, depth - 1, alpha, beta, false);

			if (value > bestValue) {
				bestValue = value;
				bestNode = child;
			}
			
			// Cập nhật alpha cho node gốc (Max) để tối ưu hóa cho các nhánh con tiếp theo
			alpha = Math.max(alpha, bestValue);
		}
		return bestNode;
	}

}