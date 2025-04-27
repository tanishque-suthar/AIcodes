import java.util.Scanner;

public class alphaBetaPruning {

	static int minimax(int[] arr, int start, int end, boolean isMaximizing, int alpha, int beta, int depth) {
		System.out.println("D" + depth + " | " + (isMaximizing ? "Max" : "Min"));
		if (start == end) {
			return arr[start];
		}

		int mid = (start + end) / 2;

		if (isMaximizing) {
			int maxEval = Integer.MIN_VALUE;

			// First child evaluation
			int leftEval = minimax(arr, start, mid, false, alpha, beta, depth + 1);
			maxEval = Math.max(maxEval, leftEval);
			alpha = Math.max(alpha, maxEval);
			System.out.println("a: " + alpha + " | b: " + beta);
			// Check for pruning
			if (beta <= alpha) {
				System.out.println("PRUNING at D " + depth + " (MAX node)");
				return maxEval;
			}

			// Second child evaluation
			int rightEval = minimax(arr, mid + 1, end, false, alpha, beta, depth + 1);
			maxEval = Math.max(maxEval, rightEval);

			return maxEval;
		} else {
			int minEval = Integer.MAX_VALUE;

			// First child evaluation
			int leftEval = minimax(arr, start, mid, true, alpha, beta, depth + 1);
			minEval = Math.min(minEval, leftEval);
			beta = Math.min(beta, minEval);
			System.out.println("a: " + alpha + " | b: " + beta);
			// Check for pruning
			if (beta <= alpha) {
				System.out.println("PRUNING at D " + depth + " (MIN node)");
				return minEval;
			}

			// Second child evaluation
			int rightEval = minimax(arr, mid + 1, end, true, alpha, beta, depth + 1);
			minEval = Math.min(minEval, rightEval);

			return minEval;
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter number of leaf nodes: ");
		int n = sc.nextInt();
		int[] arr = new int[n];

		System.out.print("Enter values for leaf nodes: ");
		for (int i = 0; i < n; i++) {
			arr[i] = sc.nextInt();
		}

		System.out.println("\nRunning Alpha-Beta Pruning:");
		int result = minimax(arr, 0, n - 1, true, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
		System.out.println("\nOptimal value: " + result);

		sc.close();
	}
}