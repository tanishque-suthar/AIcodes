import java.util.*;

public class astarMatrix {
    static class Node {
        int id, g, h, f;
        Node parent;

        Node(int id) {
            this.id = id;
            this.g = 0;
            this.h = 0;
            this.f = 0;
            this.parent = null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Node node = (Node) o;
            return this.id == node.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    public static List<Node> findPath(Node[] nodes, int[][] adjacencyMatrix, int startId, int endId) {
        Node startNode = nodes[startId];
        Node endNode = nodes[endId];

        // sorts by f value in ascending order
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        openList.add(startNode);

        Set<Node> closedList = new HashSet<>();

        while (!openList.isEmpty()) {
            Node currentNode = openList.poll(); // Get the node with lowest f value

            if (currentNode.equals(endNode)) {
                return reconstructPath(currentNode);
            }

            closedList.add(currentNode);
            for (int i = 0; i < adjacencyMatrix.length; i++) {
                // edge between current node and node i
                int edgeWeight = adjacencyMatrix[currentNode.id][i];
                if (edgeWeight > 0) { // if edge exists
                    Node neighbor = nodes[i];
                    // skip the iteration if neighbor is already evaluated
                    if (closedList.contains(neighbor))
                        continue;

                    int tentative_g = currentNode.g + edgeWeight;

                    // Check if already in open list with better path
                    boolean inOpenList = false;
                    for (Node openNode : openList) {
                        if (openNode.equals(neighbor) && openNode.g <= tentative_g) {
                            inOpenList = true;
                            break; // skip this node(neighbor) because it is already in open list with better path
                        }
                    }
                    if (!inOpenList) {
                        neighbor.g = tentative_g;
                        neighbor.h = Math.abs(neighbor.id - endNode.id); // Manhattan distance heuristic
                        neighbor.f = neighbor.g + neighbor.h;
                        neighbor.parent = currentNode;

                        // Remove and re-add to update priority if already in open list
                        openList.remove(neighbor);
                        openList.add(neighbor);
                    }
                }
            }
        }
        return new ArrayList<>(); // No path found
    }

    // Reconstruct path from end node to start node
    private static List<Node> reconstructPath(Node endNode) {
        List<Node> path = new ArrayList<>();
        Node current = endNode;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of vertices: ");
        int numVertices = sc.nextInt();

        // Create array of nodes
        Node[] nodes = new Node[numVertices];
        for (int i = 0; i < numVertices; i++) {
            nodes[i] = new Node(i);
        }

        int[][] adjacencyMatrix = new int[numVertices][numVertices];

        System.out.print("Enter number of edges: ");
        int numEdges = sc.nextInt();

        System.out.println("Enter edges as 'source destination weight':");
        for (int i = 0; i < numEdges; i++) {
            int source = sc.nextInt();
            int dest = sc.nextInt();
            int weight = sc.nextInt();

            adjacencyMatrix[source][dest] = weight;
            // If undirected graph, uncomment the following line:
            // adjacencyMatrix[dest][source] = weight;
        }

        System.out.print("Enter start vertex: ");
        int startId = sc.nextInt();

        System.out.print("Enter goal vertex: ");
        int goalId = sc.nextInt();

        List<Node> path = findPath(nodes, adjacencyMatrix, startId, goalId);

        if (path.isEmpty()) {
            System.out.println("No path found!");
        } else {
            System.out.println("Path found: ");
            for (Node node : path) {
                System.out.print(node.id + " ");
            }

            int totalCost = 0;
            for (int i = 0; i < path.size() - 1; i++) {
                totalCost += adjacencyMatrix[path.get(i).id][path.get(i + 1).id];
            }
            System.out.println("\nTotal path cost: " + totalCost);
        }
        sc.close();
    }
}