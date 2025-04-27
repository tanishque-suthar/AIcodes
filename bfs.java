import java.util.*;

public class bfs {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of vertices: ");
        int vertices = sc.nextInt();
        int[][] adjacencyMatrix = new int[vertices][vertices];
        System.out.println("Enter adjacency matrix (1 if edge exists, 0 otherwise):");
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                adjacencyMatrix[i][j] = sc.nextInt();
            }
        }
        
        System.out.print("Enter starting vertex (0 to " + (vertices - 1) + "): ");
        int startVertex = sc.nextInt();
        bfsTraversal(adjacencyMatrix, startVertex);
        sc.close();
    }
    
    public static void bfsTraversal(int[][] adjacencyMatrix, int startVertex) {
        int vertices = adjacencyMatrix.length;
        boolean[] visited = new boolean[vertices];
        Queue<Integer> queue = new LinkedList<>();
        
        visited[startVertex] = true;
        queue.add(startVertex);
        System.out.println("BFS Traversal starting from vertex " + startVertex + ":");
        
        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            System.out.print(currentVertex + " - ");
            for (int i = 0; i < vertices; i++) {
                if (adjacencyMatrix[currentVertex][i] == 1 && !visited[i]) {
                    visited[i] = true;
                    queue.add(i);
                }
            }
        }
        
        boolean allVisited = true;
        for (boolean v : visited) {
            if (!v) {
                allVisited = false;
                break;
            }
        }
        
        if (!allVisited) {
            System.out.println("\nNote: Not all vertices were visited. Graph might be disconnected.");
        } else {
            System.out.println("\nAll vertices were visited.");
        }
    }
}