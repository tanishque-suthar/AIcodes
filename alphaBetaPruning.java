import java.util.Scanner;

public class alphaBetaPruning {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the starting position (integer): ");
		int startPosition = scanner.nextInt();
		
		System.out.print("Enter the maximum depth to search: ");
		int maxDepth = scanner.nextInt();
		
		System.out.print("Enter the terminal node threshold: ");
		int terminalThreshold = scanner.nextInt();
		
		// Run the algorithm with user input
		int result = alphaBetaSearch(startPosition, maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, true, terminalThreshold);
		System.out.println("Optimal value: " + result);
		
		scanner.close();
	}

	public static int alphaBetaSearch(int position, int depth, int alpha, int beta, boolean maximizingPlayer, int terminalThreshold) {
		// Base case: if we've reached a leaf node or maximum depth
		if (depth == 0 || position > terminalThreshold) {
			System.out.println("Evaluating leaf node at position " + position + " with value " + (position % 10));
			return position % 10; // Simple evaluation function
		}
		
		int[] children = {position * 2 + 1, position * 2 + 2}; // Simple child generation
		System.out.println((maximizingPlayer ? "MAX" : "MIN") + " node at position " + position + ", depth " + depth);
		
		if (maximizingPlayer) {
			int maxEval = Integer.MIN_VALUE;
			for (int child : children) {
				System.out.println("Exploring child " + child + " of node " + position);
				int eval = alphaBetaSearch(child, depth - 1, alpha, beta, false, terminalThreshold);
				maxEval = Math.max(maxEval, eval);
				alpha = Math.max(alpha, eval);
				System.out.println("Updated alpha to " + alpha);
				if (beta <= alpha) {
					System.out.println("Beta cutoff at node " + position);
					break;
				}
			}
			return maxEval;
		} else {
			int minEval = Integer.MAX_VALUE;
			for (int child : children) {
				System.out.println("Exploring child " + child + " of node " + position);
				int eval = alphaBetaSearch(child, depth - 1, alpha, beta, true, terminalThreshold);
				minEval = Math.min(minEval, eval);
				beta = Math.min(beta, eval);
				System.out.println("Updated beta to " + beta);
				if (beta <= alpha) {
					System.out.println("Alpha cutoff at node " + position);
					break;
				}
			}
			return minEval;
		}
	}
}