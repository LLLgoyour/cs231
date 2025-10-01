
/*
 * file name: TestProbability.java
 * author: Jack Dai
 * last modified: 10/01/2025
 * purpose of the class:
 * This class is designed to test the effect of different 
 * initial alive-cell probabilities on the average number of living cells 
 * in Conway's Game of Life. It creates a 100x100 Landscape and runs the 
 * simulation for a specified number of steps (default 1000, 1500, or 2000). 
 * For each probability from 0.1 to 0.9, the simulation is repeated multiple 
 * times (default 5 trials) to calculate the average number of living cells. 
 * The results are written to a text file for further analysis or plotting.
 *
 */
import java.io.FileWriter;
import java.io.IOException;

public class TestProbability {
    public static void main(String[] args) throws IOException {
        // defaults
        int rows = 100;
        int cols = 100;
        // double chance = 0.25; // default, and change chance
        // to 0.1, 0.2, 0.3, ... , 0.9
        // and explore the
        int steps = 1000; // define the value to experiment with, i.e. 1000, 1500, 2000

        // command line argument
        if (args.length >= 2) {
            rows = Integer.parseInt(args[0]);
            cols = Integer.parseInt(args[1]);
        }
        if (args.length >= 3) {
            steps = Integer.parseInt(args[2]);
        }
        // Uncomment the lines below if you want to see the live graph
        // Open a file to save results
        FileWriter writer = new FileWriter("alive_prob_results2000.txt");
        writer.write("InitialProbability\tAverageLivingCells\n");

        // Test initial probabilities from 0.1 to 0.9 in steps of 0.1
        for (double prob = 0.1; prob <= 0.9; prob += 0.1) {
            int totalAlive = 0;
            int trials = 5; // number of repeated runs for averaging

            for (int t = 0; t < trials; t++) {
                // initialize landscape with probability `prob`
                Landscape scape = new Landscape(rows, cols, prob);

                // run simulation for given steps
                for (int step = 0; step < steps; step++) {
                    scape.advance();
                }

                // count living cells after simulation
                int livingCount = 0;
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        if (scape.getCell(i, j).getAlive()) {
                            livingCount++;
                        }
                    }
                }
                totalAlive += livingCount;
            }

            // compute average living cells for this probability
            double avgAlive = (double) totalAlive / trials;
            // write to file
            writer.write(String.format("%.1f\t%.2f\n", prob, avgAlive));

            // print to console for quick checking
            System.out.printf("Initial probability %.1f -> average living cells: %.2f%n",
                    prob, avgAlive);
        }

        writer.close();
        System.out.println("Results saved to alive_prob_results.txt");
    }
}