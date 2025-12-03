package lecture.Graph.MinimumSpanningTree;

import java.util.LinkedList;

public class MSTDemo {
    /*
     * Prim's algorithm:
     * 1. select the edge with the smallest weight.
     * 2. select the edge with the smallest weight that is connected to the previous
     * selection
     * until all vertices have been visited
     * 
     * Kruskal's algorithm:
     * select the edge with the smallest weight, and add it to the selection
     * as long as it does not create a cycle.
     * 
     * 
     */

    // Finding a path
    public boolean hasPath(Vertex source, Vertex destination) {
        Queue<Vertex> queue = new LinkedList<>();
        Set<Vertex> seen = new HashSet<Vertex>();

        queue.add(source);

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();

            if (!seen.contains(current)) {
                seen.add(current);

                if (current == destination) {
                    return true;
                }
            }
            for (Vertex adjacent : current.getNeighbors()) {
                if (!seen.contains(adjacent)) {
                    queue.add(adjacent);
                }
            }
        }
    }
}
