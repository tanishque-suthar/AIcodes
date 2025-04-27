import java.util.Scanner;

public class iddfs {
    private static int[][] adjacencyMatrix;
    private static int numNodes;
    private static boolean[] visited;
    private static boolean targetFound;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the number of nodes: ");
        numNodes = scanner.nextInt();
        
        adjacencyMatrix = new int[numNodes][numNodes];
        
        System.out.println("Enter the adjacency matrix (1 if edge exists, 0 if not):");
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                adjacencyMatrix[i][j] = scanner.nextInt();
            }
        }
        
        System.out.print("Enter the source node (0 to " + (numNodes - 1) + "): ");
        int source = scanner.nextInt();
        
        System.out.print("Enter the target node (0 to " + (numNodes - 1) + "): ");
        int target = scanner.nextInt();
        
        System.out.println("\nPerforming Iterative Deepening DFS from node " + source + " to find node " + target);
        
        int maxDepth = findMaxDepth(source, target);
        if (maxDepth != -1) {
            System.out.println("Target node " + target + " found at depth " + maxDepth);
        } else {
            System.out.println("Target node " + target + " is not reachable from source node " + source);
        }
        
        scanner.close();
    }
    
    private static int findMaxDepth(int source, int target) {
        int depth = 0;
        while (true) {
            System.out.println("Trying with maximum depth: " + depth);
            visited = new boolean[numNodes];
            targetFound = false;
            
            dfs(source, target, depth);
            
            if (targetFound) {
                return depth;
            }
            
            if (depth >= numNodes - 1) {
                return -1; // Target not found within the maximum possible depth
            }
            
            depth++;
        }
    }
    
    private static void dfs(int current, int target, int depthLimit) {
        if (targetFound) {
            return;
        }
        
        if (current == target) {
            targetFound = true;
            return;
        }
        
        if (depthLimit <= 0) {
            return;
        }
        
        visited[current] = true;
        
        for (int i = 0; i < numNodes; i++) {
            if (adjacencyMatrix[current][i] == 1 && !visited[i]) {
                dfs(i, target, depthLimit - 1);
            }
        }
        
        visited[current] = false; // Backtrack
    }
}