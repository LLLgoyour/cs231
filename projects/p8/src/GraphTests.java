import java.util.HashMap;

public class GraphTests {

    public static double vertexTests() {

        double testScore = 0.;

        // Vertex is not null
        Vertex v1 = new Vertex();
        if (v1 != null)
            testScore += 0.5;

        // Make a 2 vertices
        Vertex v2 = new Vertex();
        // add an edge between them
        Edge e = new Edge(v1, v2, 10.);

        // Distance works
        if (e.distance() == 10.)
            testScore += 0.5;

        // Other works
        if (e.other(v1) == v2)
            testScore += 0.5;

        if (e.other(v2) == v1)
            testScore += 0.5;

        // Vertices works
        if ((e.vertices()[0] == v1 || e.vertices()[1] == v1) &&
                (e.vertices()[0] == v2 || e.vertices()[1] == v2) &&
                (e.vertices().length == 2))
            testScore += 0.5;

        return testScore;
    }

    public static double edgeTests() {
        double testScore = 0.;

        // Make 2 vertices
        Vertex v1 = new Vertex();
        Vertex v2 = new Vertex();

        // add an edge between them
        Edge e = new Edge(v1, v2, 10.);

        // Add the edge to both vertices
        v1.addEdge(e);
        v2.addEdge(e);

        // getEdgeTo works
        if (v1.getEdgeTo(v2) == e && v2.getEdgeTo(v1) == e)
            testScore += 0.5;

        // adjacentVertices works
        if (v1.adjacentVertices().get(0) == v2 && v2.adjacentVertices().get(0) == v1)
            testScore += 0.5;

        // incidentEdges works
        if (v1.incidentEdges().get(0) == e && v2.incidentEdges().get(0) == e)
            testScore += 0.5;

        // addEdge works
        Vertex v3 = new Vertex();
        Edge e2 = new Edge(v1, v3, 5.);
        v3.addEdge(e2);
        v1.addEdge(e2);
        if ((v1.getEdgeTo(v3) == e2) &&
                (v1.adjacentVertices().contains(v3)) &&
                (v1.incidentEdges().contains(e2)))
            testScore += 0.5;

        // removeEdge works
        v1.removeEdge(e2);
        v3.removeEdge(e2);
        if ((v1.getEdgeTo(v3) == null && v3.getEdgeTo(v1) == null) &&
                (v1.adjacentVertices().get(0) == v2 && v1.adjacentVertices().size() == 1) &&
                (v1.incidentEdges().get(0) == e && v1.incidentEdges().size() == 1) &&
                (v3.adjacentVertices().size() == 0 && v3.incidentEdges().size() == 0))
            testScore += 0.5;

        return testScore;
    }

    public static double graphConstructorTests() {

        double testScore = 0.;

        Graph g1 = new Graph("graph1.txt");
        if (g1.size() == 4)
            testScore++;

        // Graph's random construtor makes approximately the right number of edges
        Graph g2 = new Graph(100, 0.5);
        if (g2.size() == 100)
            testScore += 0.5;
        if (g2.getEdges().size() > 2200 && g2.getEdges().size() < 2700)
            testScore += 0.5;

        return testScore;
    }

    public static double graphTests() {

        double testScore = 0.;

        // Makes a graph from a file
        Graph g1 = new Graph("graph1.txt");

        Vertex v1 = g1.getVertices().get(0);
        Vertex v2 = g1.getVertices().get(1);
        Vertex v3 = g1.getVertices().get(2);
        HashMap<Vertex, Double> distances = g1.distanceFrom(v1);
        if (distances.get(v1) == 0. && distances.get(v2) == 1. && distances.get(v3) == 2.)
            testScore += 0.5;

        // Graph's add edge updates vertices
        g1.addEdge(v1, v3, 0.5);
        Edge e = g1.getEdge(v1, v3);
        if ((v1.getEdgeTo(v3) == e) &&
                (v1.adjacentVertices().contains(v3)) &&
                (v1.incidentEdges().contains(e)))
            testScore += 0.5;

        // Distances still works with new edge
        HashMap<Vertex, Double> distances2 = g1.distanceFrom(v1);
        if (distances2.get(v1) == 0. && distances2.get(v3) == .5)
            testScore += 0.5;

        // Graph's remove edge updates vertices
        g1.remove(e);
        if ((v1.getEdgeTo(v3) == null && v3.getEdgeTo(v1) == null) &&
                (!v1.adjacentVertices().contains(v3) && !v3.adjacentVertices().contains(v1)))
            testScore += 0.5;

        // Graph's add vertex updates edges/vertices
        Vertex v4 = g1.addVertex();
        if (g1.size() == 5)
            testScore += 0.5;

        // Graph's remove vertex updates edges/vertices
        g1.remove(v4);
        if (g1.size() == 4)
            testScore += 0.5;

        return testScore;
    }

    public static void main(String[] args) {
        System.out.println(vertexTests() + "/2.5");
        System.out.println(edgeTests() + "/2.5");
        System.out.println(graphConstructorTests() + "/2");
        System.out.println(graphTests() + "/3");
    }
}