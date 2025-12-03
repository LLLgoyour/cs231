import java.util.HashMap;
import java.util.Map;

/**
 * A one-step adversarial lookahead player.
 *
 * For each candidate vertex it can place, it simulates placing that token,
 * then assumes the opponent will place the token that minimizes the current
 * player's final total (i.e., the opponent plays adversarially). It chooses
 * the candidate which maximizes the player's guaranteed final total under
 * that single-step lookahead. This is essentially a maximin over one
 * opponent response and typically outperforms pure random and often beats
 * greedy strategies in practice on Voronoi graphs.
 */
public class VoronoiOneStepAheadPlayer extends VoronoiPlayerAlgorithm {

    public VoronoiOneStepAheadPlayer(VoronoiGraph g) {
        super(g);
    }

    public Vertex chooseVertex(int playerIndex, int numRemainingTurns) {
        Vertex best = null;
        int bestGuaranteed = Integer.MIN_VALUE;

        // snapshot of existing tokens
        HashMap<Vertex, Integer> existingTokens = graph.getTokens();

        for (Vertex candidate : graph.getVertices()) {
            if (graph.hasToken(candidate))
                continue;

            // simulate placing candidate for playerIndex as first move
            int guaranteedAfterOpponent = Integer.MAX_VALUE;

            // opponent will choose any free vertex (not already tokened and not candidate)
            for (Vertex opp : graph.getVertices()) {
                if (graph.hasToken(opp) || opp.equals(candidate))
                    continue;

                // compute player's total after candidate then opponent's placement
                int playerTotal = simulateSequentialPlacement(existingTokens, candidate, playerIndex, opp,
                        1 - playerIndex, playerIndex);

                // opponent is adversarial: pick the opp that minimizes player's final total
                if (playerTotal < guaranteedAfterOpponent)
                    guaranteedAfterOpponent = playerTotal;
            }

            // If there were no available opponent moves (rare), compute after only our
            // placement
            if (guaranteedAfterOpponent == Integer.MAX_VALUE) {
                guaranteedAfterOpponent = simulateSequentialPlacement(existingTokens, candidate, playerIndex, null,
                        -1, playerIndex);
            }

            // pick candidate that maximizes the guaranteed final total
            if (guaranteedAfterOpponent > bestGuaranteed
                    || (guaranteedAfterOpponent == bestGuaranteed && (best == null
                            || graph.getValue(candidate) > graph.getValue(best)))) {
                bestGuaranteed = guaranteedAfterOpponent;
                best = candidate;
            }
        }

        // fallback
        if (best == null) {
            for (Vertex v : graph.getVertices())
                if (!graph.hasToken(v)) {
                    best = v;
                    break;
                }
        }

        return best;
    }

    /**
     * Simulates sequential token placement (firstPlaced then secondPlaced) on top
     * of existingTokens without mutating the graph. It follows the same strict
     * "closer-than" rule used by VoronoiGraph.setToken: a newly placed token
     * becomes the owner of a vertex only if it is strictly closer than the
     * previous closest token.
     *
     * @param existingTokens current tokens (token vertex -> player index)
     * @param firstPlaced    vertex placed first (may be null)
     * @param firstPlayer    owner index of firstPlaced
     * @param secondPlaced   vertex placed second (may be null)
     * @param secondPlayer   owner index of secondPlaced
     * @param queryPlayer    which player's total to compute
     * @return total value controlled by queryPlayer after the placements
     */
    private int simulateSequentialPlacement(HashMap<Vertex, Integer> existingTokens, Vertex firstPlaced,
            int firstPlayer, Vertex secondPlaced, int secondPlayer, int queryPlayer) {

        // For each vertex, track the current minimum distance to any placed token
        // and the current owner player index.
        Map<Vertex, Double> minDist = new HashMap<>();
        Map<Vertex, Integer> owner = new HashMap<>();

        for (Vertex v : graph.getVertices()) {
            minDist.put(v, Double.POSITIVE_INFINITY);
            owner.put(v, null);
        }

        // initialize with existing tokens
        for (Map.Entry<Vertex, Integer> e : existingTokens.entrySet()) {
            Vertex tok = e.getKey();
            int p = e.getValue();
            for (Vertex v : graph.getVertices()) {
                double d = graph.getDistance(v, tok);
                if (d < minDist.get(v)) {
                    minDist.put(v, d);
                    owner.put(v, p);
                }
            }
        }

        // apply first placement
        if (firstPlaced != null && firstPlayer >= 0) {
            for (Vertex v : graph.getVertices()) {
                double d = graph.getDistance(v, firstPlaced);
                if (d < minDist.get(v)) {
                    minDist.put(v, d);
                    owner.put(v, firstPlayer);
                }
            }
        }

        // apply second placement
        if (secondPlaced != null && secondPlayer >= 0) {
            for (Vertex v : graph.getVertices()) {
                double d = graph.getDistance(v, secondPlaced);
                if (d < minDist.get(v)) {
                    minDist.put(v, d);
                    owner.put(v, secondPlayer);
                }
            }
        }

        // sum values for queryPlayer
        int sum = 0;
        for (Vertex v : graph.getVertices()) {
            Integer o = owner.get(v);
            if (o != null && o == queryPlayer)
                sum += graph.getValue(v);
        }
        return sum;
    }
}
