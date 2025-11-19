/*
 * file name: Edge.java
 * author: Jack Dai
 * last modified: 11/20/2025
 * purpose of the class:
 * This class defines Edge objects.
 */

public class Edge {
    private Vertex u;
    private Vertex v;
    private double dist;

    /**
     * This constructor constructs an Edge consisting of the two vertices with a
     * distance of the given distance.
     * 
     * @param u
     * @param v
     * @param instance
     */
    public Edge(Vertex u, Vertex v, double instance) {
        this.u = u;
        this.v = v;
        this.dist = instance;
    }

    /**
     * This method returns the distance of this edge.
     * 
     * @return the distance of this edge.
     */
    public double distance() {
        return this.dist;
    }

    /**
     * If vertex is one of the endpoints of this edge, returns the other end point.
     * Otherwise returns null.
     * 
     * @param vertex
     * @return the other end point if vertex is one of the endpoints of this edge.
     *         Otherwise returns null.
     */
    public Vertex other(Vertex vertex) {
        if (vertex == null) {
            return null;
        }
        if (vertex == this.u) {
            return this.v;
        }
        if (vertex == this.v) {
            return this.u;
        }
        return null;
    }

    /**
     * This method returns an array of the two Vertices comprising this Edge. Order
     * is
     * arbitrary.
     * 
     * @return an array of the two Vertices comprising this Edge. Order is
     *         arbitrary.
     */
    public Vertex[] vertices() {
        return new Vertex[] {
                this.u, this.v
        };
    }
}
