/**
 * file name: VoronoiInfluencePlayer.java
 * author: Jack Dai
 * last modified: 12/2/2025
 * Algorithm description:
 * A Voronoi-based player that, on each turn, evaluates every free vertex as a
 * potential store location and chooses the one that maximizes *new* captured
 * value.
 *
 * For each candidate vertex, the algorithm:
 * - Looks at every vertex in the graph.
 * - Checks whether placing a token at the candidate would make it the
 * strictly closest token to that vertex (using precomputed shortest-path
 * distances).
 * - If so, and if that vertex is currently either unowned or owned by the
 * *other* player, it adds that vertex's value to the candidate's score.
 *
 * In other words, the score for a candidate vertex is the total value of
 * vertices that would newly switch to this player (or be claimed from
 * unclaimed) if a token were placed there. The algorithm then selects the
 * candidate with the highest score, breaking ties by preferring higher
 * intrinsic vertex value.
 *
 * This yields an O(V^2) heuristic per move (after all-pairs distances are
 * precomputed) and typically outperforms both the random and simple
 * value-greedy strategies on Voronoi-style graphs.
 */
public class VoronoiInfluencePlayer extends VoronoiPlayerAlgorithm {

    public VoronoiInfluencePlayer(VoronoiGraph g) {
        super(g);
    }

    public Vertex chooseVertex(int playerIndex, int numRemainingTurns) {
        Vertex best = null;
        int bestScore = Integer.MIN_VALUE;

        for (Vertex candidate : graph.getVertices()) {
            if (graph.hasToken(candidate))
                continue;

            int score = 0;

            for (Vertex v : graph.getVertices()) {
                double dToCandidate = graph.getDistance(v, candidate);

                Vertex closest = graph.getClosestToken(v);
                double dCurrent = Double.POSITIVE_INFINITY;
                Integer currentOwner = null;

                if (closest != null) {
                    dCurrent = graph.getDistance(v, closest);
                    currentOwner = graph.getCurrentOwner(v);
                }

                // Would this new token become the closest?
                if (dToCandidate < dCurrent) {
                    // Only count it if it's not already ours
                    if (currentOwner == null || currentOwner != playerIndex) {
                        score += graph.getValue(v);
                    }
                }
            }

            // tie-breaker: prefer higher intrinsic vertex value
            if (score > bestScore || (score == bestScore && (best == null
                    || graph.getValue(candidate) > graph.getValue(best)))) {
                best = candidate;
                bestScore = score;
            }
        }

        // Fallback: if no candidate found (shouldn't happen), choose first free
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
