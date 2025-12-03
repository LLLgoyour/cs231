import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Runs batch comparisons between Voronoi player algorithms (Influence, Random,
 * Greedy) without visualization and exports summary statistics to a text file.
 */
public class ComparePlayers {

    private static class Stats {
        public int games = 0;
        public int aWins = 0;
        public int bWins = 0;
        public int ties = 0;
        public long aTotal = 0;
        public long bTotal = 0;
        public long marginTotal = 0; // a - b
        public int aFirstWins = 0;
        public int aSecondWins = 0;
    }

    public static void main(String[] args) throws Exception {
        int games = 100; // number of graphs per ordering
        int numVertices = 100;
        double density = 0.1;
        int numTurns = 10;
        long timeLimitMs = 500; // per move timeout

        Stats vsRandom = compare(VoronoiInfluencePlayer.class, VoronoiRandomPlayer.class, games, numVertices,
                density, numTurns, timeLimitMs);

        Stats vsGreedy = compare(VoronoiInfluencePlayer.class, VoronoiGreedyPlayer.class, games, numVertices,
                density, numTurns, timeLimitMs);

        // write results
        try (PrintWriter out = new PrintWriter(new FileWriter("compare_results.txt", false))) {
            out.println("ComparePlayers results");
            out.println();

            writeSection(out, "Influence vs Random", vsRandom);
            out.println();
            writeSection(out, "Influence vs Greedy", vsGreedy);

            out.println();
            out.println("Notes:");
            out.println("- Each 'game' uses a new random VoronoiGraph(n=" + numVertices + ", density=" + density
                    + ") and plays " + numTurns + " turns per player.");
            out.println("- A move that times out is skipped (no token placed).");
            out.println("- Results include overall wins, ties, average scores, and margins.");
        }

        System.out.println("Done. Results written to compare_results.txt");
    }

    private static void writeSection(PrintWriter out, String title, Stats s) {
        out.println(title);
        out.println("Games: " + s.games);
        out.println("A wins: " + s.aWins + " (A first wins: " + s.aFirstWins + ", A second wins: " + s.aSecondWins
                + ")");
        out.println("B wins: " + s.bWins);
        out.println("Ties: " + s.ties);
        double aAvg = s.games > 0 ? (double) s.aTotal / s.games : 0;
        double bAvg = s.games > 0 ? (double) s.bTotal / s.games : 0;
        double avgMargin = s.games > 0 ? (double) s.marginTotal / s.games : 0;
        out.printf("Avg score A: %.3f\n", aAvg);
        out.printf("Avg score B: %.3f\n", bAvg);
        out.printf("Avg margin (A - B): %.3f\n", avgMargin);
        out.println("A win rate: " + String.format("%.3f", s.aWins / (double) s.games));
        out.println("B win rate: " + String.format("%.3f", s.bWins / (double) s.games));
        out.println("Tie rate: " + String.format("%.3f", s.ties / (double) s.games));
    }

    private static Stats compare(Class<? extends VoronoiPlayerAlgorithm> aClass,
            Class<? extends VoronoiPlayerAlgorithm> bClass, int games, int n, double density, int numTurns,
            long timeLimitMs) throws Exception {

        Stats stats = new Stats();
        stats.games = games * 2; // we will play games with both orderings

        for (int game = 0; game < games; game++) {
            // create a fresh graph for this trial
            final VoronoiGraph vg = new VoronoiGraph(n, density);

            // two orderings: 0 -> (A,B) and 1 -> (B,A)
            for (int ordering = 0; ordering < 2; ordering++) {
                vg.reset();

                final VoronoiPlayerAlgorithm[] players = new VoronoiPlayerAlgorithm[2];

                // instantiate players with a small timeout to avoid hang
                final int ord = ordering; // make effectively final for lambdas
                Thread t0 = new Thread(() -> {
                    try {
                        if (ord == 0)
                            players[0] = aClass.getConstructor(VoronoiGraph.class).newInstance(vg);
                        else
                            players[0] = bClass.getConstructor(VoronoiGraph.class).newInstance(vg);
                    } catch (Exception e) {
                        players[0] = null;
                    }
                });

                Thread t1 = new Thread(() -> {
                    try {
                        if (ord == 0)
                            players[1] = bClass.getConstructor(VoronoiGraph.class).newInstance(vg);
                        else
                            players[1] = aClass.getConstructor(VoronoiGraph.class).newInstance(vg);
                    } catch (Exception e) {
                        players[1] = null;
                    }
                });

                t0.start();
                t1.start();
                t0.join(500);
                t1.join(500);

                // if none instantiated, declare tie for this ordering
                if (players[0] == null && players[1] == null) {
                    stats.ties++;
                    continue;
                }

                // simulate turns
                for (int turn = 0; turn < numTurns; turn++) {
                    for (int playerIdx = 0; playerIdx < 2; playerIdx++) {
                        final int pi = playerIdx;
                        final int turnLocal = turn; // capture loop var for lambda
                        Thread chooser = new Thread(() -> {
                            try {
                                if (players[pi] != null)
                                    // chooseVertex may be somewhat long-running
                                    box.v = players[pi].chooseVertex(pi, numTurns - turnLocal - 1);
                                else
                                    box.v = null;
                            } catch (Exception e) {
                                box.v = null;
                            }
                        });

                        chooser.start();
                        try {
                            chooser.join(timeLimitMs);
                        } catch (InterruptedException e) {
                            chooser.interrupt();
                        }
                        if (chooser.getState() == Thread.State.TERMINATED && box.v != null) {
                            vg.setToken(box.v, playerIdx);
                        } else {
                            chooser.interrupt();
                            // skip placing a token if timeout
                        }
                        box.v = null;
                    }
                }

                HashMap<Integer, Integer> results = vg.playerValues();
                int val0 = results.getOrDefault(0, 0);
                int val1 = results.getOrDefault(1, 0);

                // map A and B to player index depending on ordering
                int aIdx = ordering == 0 ? 0 : 1;
                int bIdx = ordering == 0 ? 1 : 0;
                int aScore = (aIdx == 0) ? val0 : val1;
                int bScore = (bIdx == 0) ? val0 : val1;

                stats.aTotal += aScore;
                stats.bTotal += bScore;
                stats.marginTotal += (aScore - bScore);

                if (aScore > bScore) {
                    stats.aWins++;
                    if (aIdx == 0)
                        stats.aFirstWins++;
                    else
                        stats.aSecondWins++;
                } else if (bScore > aScore) {
                    stats.bWins++;
                } else {
                    stats.ties++;
                }
            }
        }

        return stats;
    }

    // small shared box used to pass vertex from chooser thread
    private static class Box {
        Vertex v;
    }

    private static Box box = new Box();
}
