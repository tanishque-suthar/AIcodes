import java.util.*;

public class dfs {
    private static boolean[] visited;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter number of vertices: ");
        int vertices = scanner.nextInt();
        
        int[][] adjacencyMatrix = new int[vertices][vertices];
        
        System.out.println("Enter the adjacency matrix (0 for no edge, 1 for edge):");
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                adjacencyMatrix[i][j] = scanner.nextInt();
            }
        }
        
        System.out.print("Enter starting vertex (0 to " + (vertices - 1) + "): ");
        int startVertex = scanner.nextInt();
        
        System.out.println("\nDepth First Traversal starting from vertex " + startVertex + ":");
        visited = new boolean[vertices];
        dfsTraversal(adjacencyMatrix, startVertex);
        
        scanner.close();
    }
    
    private static void dfsTraversal(int[][] adjacencyMatrix, int vertex) {
        int vertices = adjacencyMatrix.length;
        visited[vertex] = true;
        System.out.print(vertex + " ");
        
        for (int i = 0; i < vertices; i++) {
            if (adjacencyMatrix[vertex][i] == 1 && !visited[i]) {
                dfsTraversal(adjacencyMatrix, i);
            }
        }
    }
}