
/*
 * file name: Experiment.java
 * author: Jack Dai
 * last modified: 10/27/2025
 * purpose of the class:
 * Experiment runner to evaluate Sudoku solver performance with varying numbers
 * of initially locked cells. The runner generates random boards with a
 * specified number of locked cells, attempts to solve each board using the
 * Sudoku solver, and writes per-count statistics to a CSV file.
 *
 * Usage:
 * java Experiment [minFixed] [maxFixed] [step] [trials] [maxIters] [outFile]
 * Defaults: minFixed=0, maxFixed=40, step=1,
 * trials=50, @code maxIters=200000, outFile="experiment_results.txt"
 */
import java.io.*;

public class Experiment {
    /**
     * Run the experiment. Command-line arguments may override defaults.
     *
     * @param args optional arguments: minFixed maxFixed step trials maxIters
     *             outFile
     */
    public static void main(String[] args) {
        int minFixed = 0;
        int maxFixed = 40;
        int step = 1;
        int trials = 50;
        int maxIters = 200000;

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
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid argument; using defaults.");
        }

        String outFile = "experiment_results.txt";
        // allow optional output filename as final argument
        if (args.length > 5) {
            outFile = args[5];
        }

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outFile)))) {
            // CSV header: number of fixed cells, number of trials, successes, failures,
            // number of timeouts, average solve time (ms) for successful runs, success_rate
            writer.println("numFixed,trialCount,successes,failures,timeouts,avgSolveMs(success),success_rate");

            for (int num = minFixed; num <= maxFixed; num += step) {
                int successes = 0;
                int failures = 0;
                int timeouts = 0;
                long totalSolveTimeNs = 0L;

                for (int t = 0; t < trials; t++) {
                    Sudoku s = new Sudoku(num, 0); // no visualization

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
                String line = String.format("%d,%d,%d,%d,%d,%.3f,%.3f", num, trials, successes, failures, timeouts,
                        avgMs, successRate);
                writer.println(line);
                writer.flush();
            }

            System.out.println("Experiment complete. Results written to: " + outFile);
        } catch (IOException ioe) {
            System.out.println("Unable to write results to " + outFile + ": " + ioe.getMessage());
        }
    }
}
