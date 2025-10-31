
/*
 * file name: ExperimentAlgorithms.java
 * author: Jack Dai
 * last modified: 10/29/2025
 * purpose of the class: Compare different next-cell selection heuristics for the Sudoku solver.
 * Writes CSV with per-(numFixed,strategy) statistics.
 *
 * Usage:
 * java ExperimentAlgorithms [minFixed] [maxFixed] [step] [trials] [maxIters] [outFile]
 * Defaults: minFixed=0, maxFixed=40, step=1, trials=50, maxIters=200000,
 * outFile=experiment_algorithms_results.txt
 */
import java.io.*;

public class ExperimentAlgorithms {
    private static final int[] DEFAULT_STRATEGIES = new int[] { 0, 1, 2, 3 };
    private static final String[] STRATEGY_NAMES = new String[] { "row-major", "mrv", "mrv+degree", "random" };

    public static void main(String[] args) {
        int minFixed = 0;
        int maxFixed = 40;
        int step = 1;
        int trials = 50;
        int maxIters = 200000;
        String outFile = "experiment_algorithms_results.txt";

        try {
            if (args.length > 0)
                minFixed = Integer.parseInt(args[0]);
            if (args.length > 1)
                maxFixed = Integer.parseInt(args[1]);
            if (args.length > 2)
                step = Integer.parseInt(args[2]);
            if (args.length > 3)
                trials = Integer.parseInt(args[3]);
            if (args.length > 4)
                maxIters = Integer.parseInt(args[4]);
            if (args.length > 5)
                outFile = args[5];
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid argument; using defaults.");
        }

        int[] strategies = DEFAULT_STRATEGIES;

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outFile)))) {
            writer.println("numFixed,strategy,trialCount,successes,failures,timeouts,avgSolveMs(success),success_rate");

            for (int num = minFixed; num <= maxFixed; num += step) {
                for (int sIdx = 0; sIdx < strategies.length; sIdx++) {
                    int strat = strategies[sIdx];
                    String sName = STRATEGY_NAMES[sIdx];

                    int successes = 0;
                    int failures = 0;
                    int timeouts = 0;
                    long totalSolveTimeNs = 0L;

                    for (int t = 0; t < trials; t++) {
                        Sudoku s = new Sudoku(num, 0, strat); // no visualization, chosen strategy

                        long start = System.nanoTime();
                        boolean solved = s.solve(maxIters);
                        long elapsed = System.nanoTime() - start;

                        if (solved) {
                            successes++;
                            totalSolveTimeNs += elapsed;
                        } else {
                            if (s.wasLastTimeout()) {
                                timeouts++;
                            } else {
                                failures++;
                            }
                        }
                    }

                    double avgMs = successes > 0 ? (totalSolveTimeNs / (double) successes) / 1_000_000.0 : 0.0;
                    double successRate = trials > 0 ? (successes / (double) trials) : 0.0;
                    String line = String.format("%d,%s,%d,%d,%d,%d,%.3f,%.3f", num, sName, trials, successes,
                            failures, timeouts, avgMs, successRate);
                    writer.println(line);
                    writer.flush();

                    System.out.printf("num=%d strategy=%s successes=%d failures=%d timeouts=%d avgMs=%.3f\n", num,
                            sName, successes, failures, timeouts, avgMs);
                }
            }

            System.out.println("Experiment complete. Results written to: " + outFile);
        } catch (IOException ioe) {
            System.out.println("Unable to write results to " + outFile + ": " + ioe.getMessage());
        }
    }
}
