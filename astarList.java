import java.util.*;

public class astarList {
    // Node class for representing vertices in the graph
    static class Node {
        int id, g, h, f;
        Node parent;
        Map<Node, Integer> neighbors; // adjacent nodes with edge weights

        Node(int id) {
            this.id = id;
            this.g = 0;
            this.h = 0;
            this.f = 0;
            this.parent = null;
            this.neighbors = new HashMap<>();
        }

        void addNeighbor(Node neighbor, int weight) {
            this.neighbors.put(neighbor, weight);
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

    public static List<Node> findPath(Map<Integer, Node> graph, int startId, int endId) {
        Node startNode = graph.get(startId);
        Node endNode = graph.get(endId);

        if (startNode == null || endNode == null) {
            return new ArrayList<>();
        }

        // Initialize open and closed lists
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        Set<Node> closedList = new HashSet<>();

        // Add start node to open list
        openList.add(startNode);

        // Loop until open list is empty
        while (!openList.isEmpty()) {
            // Get the node with lowest f value
            Node currentNode = openList.poll();

            // If found the end node
            if (currentNode.equals(endNode)) {
                return reconstructPath(currentNode);
            }

            // Add to closed list
            closedList.add(currentNode);

            // Check all adjacent nodes
            for (Map.Entry<Node, Integer> entry : currentNode.neighbors.entrySet()) {
                Node neighbor = entry.getKey();
                int weight = entry.getValue();

                // Skip if in closed list
                if (closedList.contains(neighbor)) {
                    continue;
                }

                // Calculate g, h, and f values
                int tentative_g = currentNode.g + weight;

                // Check if already in open list with better path
                boolean inOpenList = false;
                for (Node openNode : openList) {
                    if (openNode.equals(neighbor) && openNode.g <= tentative_g) {
                        inOpenList = true;
                        break;
                    }
                }

                if (inOpenList) {
                    continue;
                }

                // Add neighbor to open list or update if better path found
                neighbor.g = tentative_g;
                neighbor.h = calculateHeuristic(neighbor, endNode);
                neighbor.f = neighbor.g + neighbor.h;
                neighbor.parent = currentNode;

                // Remove and re-add to update priority if already in open list
                openList.remove(neighbor);
                openList.add(neighbor);
            }
        }

        // No path found
        return new ArrayList<>();
    }

    private static int calculateHeuristic(Node a, Node b) {
        return Math.abs(a.id - b.id);
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

        Map<Integer, Node> graph = new HashMap<>();
        for (int i = 0; i < numVertices; i++) {
            graph.put(i, new Node(i));
        }

        System.out.print("Enter number of edges: ");
        int numEdges = sc.nextInt();

        System.out.println("Enter edges as 'source destination weight':");
        for (int i = 0; i < numEdges; i++) {
            int source = sc.nextInt();
            int dest = sc.nextInt();
            int weight = sc.nextInt();

            Node sourceNode = graph.get(source);
            Node destNode = graph.get(dest);

            if (sourceNode != null && destNode != null) {
                sourceNode.addNeighbor(destNode, weight);
                // If undirected graph, uncomment the following line:
                // destNode.addNeighbor(sourceNode, weight);
            }
        }

        System.out.print("Enter start vertex: ");
        int startId = sc.nextInt();

        System.out.print("Enter goal vertex: ");
        int goalId = sc.nextInt();

        List<Node> path = findPath(graph, startId, goalId);

        if (path.isEmpty()) {
            System.out.println("No path found!");
        } else {
            System.out.println("Path found: ");
            for (Node node : path) {
                System.out.print(node.id + " ");
            }

            int totalCost = 0;
            for (int i = 0; i < path.size() - 1; i++) {
                totalCost += path.get(i).neighbors.get(path.get(i + 1));
            }
            System.out.println("\nTotal path cost: " + totalCost);
        }
        sc.close();
    }
}