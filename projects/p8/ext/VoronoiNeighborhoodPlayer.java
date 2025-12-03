/*
 * file name: VoronoiNeighborhoodPlayer.java
 * author: Jack Dai
 * last modified: 12/3/2025
 * purpose of the class:
 * A simple heuristic player that places tokens to maximize the total value in
 * a local neighborhood around the candidate vertex. For each candidate vertex
 * it sums the values of all vertices whose shortest-path distance to the
 * candidate is at most a fixed radius (e.g., 2.0). It picks the candidate
 * with the highest neighborhood total, breaking ties by intrinsic vertex
 * value. This heuristic favors placing tokens near dense clusters of value.
 */
public class VoronoiNeighborhoodPlayer extends VoronoiPlayerAlgorithm {

    // radius (in terms of shortest-path distance) to consider as "neighborhood"
    private final double radius;

    public VoronoiNeighborhoodPlayer(VoronoiGraph g) {
        this(g, 2.0);
    }

    public VoronoiNeighborhoodPlayer(VoronoiGraph g, double radius) {
        super(g);
        this.radius = radius;
    }

    @Override
    public Vertex chooseVertex(int playerIndex, int numRemainingTurns) {
        Vertex best = null;
        int bestScore = Integer.MIN_VALUE;

        // Evaluate every candidate vertex that is not already tokened
        for (Vertex candidate : graph.getVertices()) {
            if (graph.hasToken(candidate))
                continue; // skip positions that are already taken

            int score = 0; // sum of values in the candidate's neighborhood

            // Sum values of all vertices within `radius` of the candidate
            for (Vertex v : graph.getVertices()) {
                Double d = graph.getDistance(candidate, v);
                // distance may be POSITIVE_INFINITY for disconnected pairs; check null/limit
                if (d != null && d <= radius) {
                    score += graph.getValue(v);
                }
            }

            // Prefer higher neighborhood score; break ties using intrinsic vertex value
            if (score > bestScore || (score == bestScore && (best == null
                    || graph.getValue(candidate) > graph.getValue(best)))) {
                best = candidate;
                bestScore = score;
            }
        }

        if (best == null) {
            for (Vertex v : graph.getVertices())
                if (!graph.hasToken(v)) {
                    best = v;
                    break;
                }
        }

        return best;
    }
}
