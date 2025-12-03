package lecture.Graph.Dijkstra;

import java.util.*;

public class DijkstraDemo {
    private int vertices;
    private Edge edge;

    public DijkstraDemo(int vertices) {
        this.vertices = vertices;
    }

    void dijkstra(int start) {
        int[] dist = new int[vertices];
        Arrays.fill(dist, Integer.MAX_VALUE); // initialize the V array and fill with infinity numbers
        dist[start] = 0;

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(start, 0));

        while (!pq.isEmpty()) {

        }
    }
}
