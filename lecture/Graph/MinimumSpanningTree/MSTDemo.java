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
     * MST Example:
     * a->c->b->d
     * 
     * find a cycle in a graph:
     * algorithm:
     * 1. traverse the graph (BFS, DFS)
     * 2. mark vertex as visited
     * if encounter a marked vertex, that isn't the parent of the current vertex,
     * then we have a cycle
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

    // methods: find a cycle in the graph
    public boolean hasCycle() {
        this.unVisitAll();

        for (Vertex adjacent : this.vertices) {
            if (!adjacent.checkMarked()) {
                if (checkCycle(adjacent, null)) {
                    return true;
                }
            }
        }
    }

    public boolean checkCycle(Vertex current, Vertex parent) {
        current.mark();
        for (Vertex adjacent : current.getNeighbors()) {
            if (!adjacent.checkMarked()) {
                if (checkCycle(adjacent, current)) {
                    return true;
                }
            } else if (adjacent != parent) {
                return true;
            }
        }
        return true;
    }
}
