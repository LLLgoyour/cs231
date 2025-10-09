/*
 * file name: AgentSimulation.java
 * author: Jack Dai
 * last modified: 10/05/2025
 * purpose of the class:
 * Runs an agent-based simulation where SocialAgents and AntiSocialAgents
 * interact on a Landscape.
 */

import java.util.Random;

public class AgentSimulation {

    /**
     * Writes experiment results to a text file.
     *
     * @param filename The name of the output file (e.g., "results.txt").
     * @param data     The string content to write (such as table of results).
     */
    public static void writeResults(String filename, String data) {
        try (java.io.FileWriter writer = new java.io.FileWriter(filename, true)) {
            writer.write(data + System.lineSeparator());
            System.out.println("Data saved to " + filename);
        } catch (java.io.IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Runs a simulation experiment with N SocialAgents.
     * 
     * @param numAgents initial number of agents
     * @return number of iterations the simulation ran
     */
    public static int runExpt(int numAgents /* , int radius */) {
        int width = 500;
        int height = 500;
        Landscape scape = new Landscape(width, height);
        Random rand = new Random(); // Create numAgents SocialAgents with random positions

        for (int i = 0; i < numAgents; i++) {
            double x = rand.nextDouble() * width;
            double y = rand.nextDouble() * height;
            int radius = 25; // default, can adjust in main method
            SocialAgent a = new SocialAgent(x, y, radius);
            scape.addAgent(a);
        }

        // Create the display window for visualization
        LandscapeDisplay display = new LandscapeDisplay(scape);

        // Initialize counters
        int moved = 1; // non-zero so loop starts
        int iterations = 0;

        // Run while agents are still moving and limit to 5000 iterations
        while (moved > 0 && iterations < 5000) {
            moved = scape.updateAgents();
            display.repaint();

            try {
                Thread.sleep(20); // slow down simulation so we can see updates
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            iterations++;
        }

        return iterations;
    }

    /**
     * Runs simulation with N from command line.
     * 
     * @param args command line arguments, first argument is number of agents
     */
    public static void main(String[] args) {
        int numAgents = 50; // default
        if (args.length > 0) {
            numAgents = Integer.parseInt(args[0]);
        }

        int result = runExpt(numAgents);
        System.out.println("Simulation finished after " + result + " iterations.");

        // -----Uncomment the code below for Exploration 1:
        // int[] agentCounts = { 50, 100, 150, 200, 250 };
        // StringBuilder resultTable = new StringBuilder();
        // resultTable.append("Number of Agents\tIterations\n");
        // resultTable.append("----------------\t-----------\n");

        // for (int numAgents : agentCounts) {
        // System.out.println("\nRunning simulation with " + numAgents + " agents...");
        // int result = runExpt(numAgents);
        // resultTable.append(numAgents + "\t\t\t" + result + "\n");
        // System.out.println("Finished after " + result + " iterations.");
        // }

        // // Save all results to a single file
        // writeResults("simulation_results.txt", resultTable.toString());

        // -----Uncomment the code below for Exploration 2:
        // int[] radii = { 10, 20, 30, 40, 50 };
        // StringBuilder resultTable = new StringBuilder();
        // resultTable.append("Radius\tIterations\n");
        // resultTable.append("------\t-----------\n");

        // for (int r : radii) {
        // int result = runExpt(150, r);
        // resultTable.append(r + "\t" + result + "\n");
        // System.out.println("Radius " + r + " finished after " + result + "
        // iterations.");
        // }

        // writeResults("radius_results.txt", resultTable.toString());
    }
}
