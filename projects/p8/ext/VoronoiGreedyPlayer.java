/**
 * This PlayerAlgorithm chooses the Vertex of maximal value that does not yet
 * have a token on it.
 */
public class VoronoiGreedyPlayer extends VoronoiPlayerAlgorithm {
    public VoronoiGreedyPlayer(VoronoiGraph g) {
        super(g);
    }

    public Vertex chooseVertex(int playerIndex, int numRemainingTurns) {
        Vertex out = null;
        for (Vertex v : graph.getVertices())
            if (!graph.hasToken(v) && (out == null || graph.getValue(v) > graph.getValue(out)))
                out = v;
        return out;
    }
}