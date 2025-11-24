
/**
 * A subclass of the Graph class for the Voronoi game on Graphs. 
 * 
 * Written by mbender for CS 231 at Colby College.
 */

import java.util.HashMap;
import java.util.Random;

public class VoronoiGraph extends Graph {

    private HashMap<Vertex, Integer> values;
    public HashMap<VertexPair, Double> distances;

    private HashMap<Vertex, Integer> tokens;
    private HashMap<Vertex, Integer> ownerP;
    private HashMap<Vertex, Vertex> ownerV;
    private HashMap<Integer, Integer> playerValues;

    public VoronoiGraph() {
        Random rand = new Random();
        values = new HashMap<>();
        reset();

        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                Vertex v = addVertex();
                if (r > 0)
                    addEdge(v, getVertex(r * 4 + c - 4), rand.nextDouble(1, 2));
                if (c > 0)
                    addEdge(v, getVertex(r * 4 + c - 1), rand.nextDouble(1, 2));
                values.put(v, rand.nextInt(100));
                ownerP.put(v, null);
                ownerV.put(v, null);
            }
        }
        distances = calculateDistances();
    }

    public VoronoiGraph(int n, double density) {
        Random rand = new Random();
        values = new HashMap<>();
        reset();

        for (int i = 0; i < n; i++) {
            Vertex v = addVertex();
            for (int j = 0; j < i; j++)
                if (rand.nextDouble() < 1 - Math.sqrt(density))
                    addEdge(getVertex(j), v, rand.nextDouble(1, 2));
            values.put(v, rand.nextInt(100));
            ownerP.put(v, null);
            ownerV.put(v, null);
        }
        distances = calculateDistances();
    }

    /**
     * Resets the game.
     * 
     * Removes tokens, etc.
     */
    public void reset() {
        tokens = new HashMap<>();
        ownerP = new HashMap<>();
        ownerV = new HashMap<>();
        playerValues = new HashMap<>();
    }

    /**
     * Returns the value of the given Vertex v.
     * 
     * @param v the Vertex to look up the value of.
     * @return the value of the given Vertex v.
     */
    public int getValue(Vertex v) {
        return values.get(v);
    }

    public Double getDistance(Vertex u, Vertex v) {
        return distances.get(new VertexPair(u, v));
    }

    /**
     * Adds a token to the Vertex v owned by the given player.
     * 
     * @param v      the Vertex chosen.
     * @param player the player placing the token.
     */
    public void setToken(Vertex v, int player) {
        if (v == null) {
            System.out.println("Someone tried to put a token at a null entry.");
            return;
        }
        if (tokens.get(v) != null) {
            System.out.println(
                    "You can't put a token at " + v + ": there is already a token there. This forfeits your turn.");
            return;
        }

        tokens.put(v, player);
        for (Vertex u : getVertices()) {
            Integer uOwner = ownerP.get(u);
            VertexPair uOwnerPair = new VertexPair(u, ownerV.get(u));
            VertexPair uv = new VertexPair(u, v);
            if ((uOwner == null && distances.get(uv) != Double.POSITIVE_INFINITY)
                    || (uOwner != null && distances.get(uv) < distances.get(uOwnerPair))) {
                ownerP.put(u, player);
                ownerV.put(u, v);
                playerValues.put(player, playerValues.getOrDefault(player, 0) + getValue(u));
                if (uOwner != null)
                    playerValues.put(uOwner, playerValues.get(uOwner) - getValue(u));
            }
        }
    }

    /**
     * Returns whether there is already a token at the given Vertex v.
     * 
     * @param v the Vertex to check.
     * @return whether there is already a token at the given Vertex v.
     */
    public boolean hasToken(Vertex v) {
        return tokens.get(v) != null;
    }

    /**
     * Returns the closest token to the given Vertex v.
     * 
     * @param v the Vertex to check.
     * @return the closest token to the given Vertex v.
     */
    public Vertex getClosestToken(Vertex v) {
        return ownerV.get(v);
    }

    /**
     * Returns the index of the player currently controlling the Vertex v.
     * 
     * @param v the Vertex to check.
     * @return the index of the player currently controlling the Vertex v.
     */
    public Integer getCurrentOwner(Vertex v) {
        return ownerP.get(v);
    }

    /**
     * A helper class for pairs of Vertices.
     */
    public static class VertexPair {
        Vertex a, b;

        public VertexPair(Vertex a, Vertex b) {
            this.a = a;
            this.b = b;
        }

        public boolean equals(Object o) {
            if (!(o instanceof VertexPair))
                return false;
            VertexPair vp = (VertexPair) o;
            if (a == vp.a && b == vp.b)
                return true;
            if (a == vp.b && b == vp.a)
                return true;
            return false;
        }

        public int hashCode() {
            return a.hashCode() + b.hashCode();
        }

        public String toString() {
            return "{" + a + ", " + b + "}";
        }
    }

    /**
     * Calculates using the Floyd-Warshall Algorithm the distance between each pair
     * of Vertices. Returns a HashMap mapping each VertexPair to the distance
     * between them.
     * 
     * @return a HashMap mapping each VertexPair to the distance
     *         between them.
     */
    public HashMap<VertexPair, Double> calculateDistances() {
        HashMap<VertexPair, Double> out = new HashMap<>();
        for (Vertex u : getVertices()) {
            for (Vertex v : getVertices()) 
                out.put(new VertexPair(u, v), Double.POSITIVE_INFINITY);
            
            out.put(new VertexPair(u, u), 0.0);

            for (Edge e : u.incidentEdges())
                out.put(new VertexPair(u, e.other(u)), e.distance());
        }

        for (Vertex k : getVertices())
            for (Vertex u : getVertices())
                for (Vertex v : getVertices()) {
                    VertexPair uv = new VertexPair(u, v);
                    VertexPair uk = new VertexPair(u, k);
                    VertexPair kv = new VertexPair(k, v);
                    if (out.get(uv) > out.get(uk) + out.get(kv))
                        out.put(uv, out.get(uk) + out.get(kv));
                }

        return out;
    }

    /**
     * Returns a HashMap mapping each player with any controlled Vertex to the
     * summed value of vertices they control.
     * 
     * @return a HashMap mapping each player with any controlled Vertex to the
     *         summed value of vertices they control.
     */
    public HashMap<Integer, Integer> playerValues() {
        return playerValues;
    }
}
