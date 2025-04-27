import java.util.*;

public class ucs {
    static class Node implements Comparable<Node> {
        int vertex;
        int cost;
        
        public Node(int vertex, int cost) {
            this.vertex = vertex;
            this.cost = cost;
        }
        
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.cost, other.cost);
        }
    }
    
    public static void uniformCostSearch(int[][] graph, int start, int goal, int n) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        boolean[] visited = new boolean[n];
        int[] costs = new int[n];
        int[] parent = new int[n];
        
        // Initialize costs array with infinity
        Arrays.fill(costs, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        
        // Add starting node to priority queue
        pq.add(new Node(start, 0));
        costs[start] = 0;
        
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int vertex = current.vertex;
            
            // If goal is reached, break the loop
            if (vertex == goal) {
                break;
            }
            
            if (visited[vertex]) continue;
            visited[vertex] = true;
            
            // Explore all neighbors
            for (int i = 0; i < n; i++) {
                if (graph[vertex][i] > 0 && !visited[i]) {
                    int newCost = costs[vertex] + graph[vertex][i];
                    
                    if (newCost < costs[i]) {
                        costs[i] = newCost;
                        parent[i] = vertex;
                        pq.add(new Node(i, newCost));
                    }
                }
            }
        }
        
        // Print the result
        if (costs[goal] != Integer.MAX_VALUE) {
            System.out.println("Shortest path cost from " + start + " to " + goal + " is: " + costs[goal]);
            
            // Print path
            List<Integer> path = new ArrayList<>();
            int current = goal;
            while (current != -1) {
                path.add(current);
                current = parent[current];
            }
            
            System.out.print("Path: ");
            for (int i = path.size() - 1; i >= 0; i--) {
                System.out.print(path.get(i));
                if (i > 0) System.out.print(" -> ");
            }
            System.out.println();
        } else {
            System.out.println("There is no path from " + start + " to " + goal);
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter number of vertices: ");
        int n = scanner.nextInt();
        
        int[][] graph = new int[n][n];
        
        System.out.println("Enter the adjacency matrix (enter 0 for no edge):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = scanner.nextInt();
            }
        }
        
        System.out.print("Enter start vertex: ");
        int start = scanner.nextInt();
        
        System.out.print("Enter goal vertex: ");
        int goal = scanner.nextInt();
        
        uniformCostSearch(graph, start, goal, n);
        
        scanner.close();
    }
}