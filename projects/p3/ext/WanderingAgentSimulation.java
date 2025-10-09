/*
 * file name: WanderingAgentSimulation.java
 * author: Jack Dai
 * last modified: 10/08/2025
 * purpose of this class:
 * Demonstrates the WanderingAgent alongside other agent types
 * in a mixed simulation environment to showcase different behaviors.
 */

import java.util.Random;

public class WanderingAgentSimulation {

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
     * Runs a mixed simulation with different agent types.
     * 
     * @param numSocial     number of social agents
     * @param numAntiSocial number of anti-social agents
     * @param numWandering  number of wandering agents
     * @return number of iterations the simulation ran
     */
    public static int runMixedExpt(int numSocial, int numAntiSocial, int numWandering) {
        int width = 600;
        int height = 600;
        Landscape scape = new Landscape(width, height);
        Random rand = new Random();

        // Add Social Agents (blue)
        for (int i = 0; i < numSocial; i++) {
            double x = rand.nextDouble() * width;
            double y = rand.nextDouble() * height;
            int radius = 30;
            SocialAgent a = new SocialAgent(x, y, radius);
            scape.addAgent(a);
        }

        // Add Anti-Social Agents (red)
        for (int i = 0; i < numAntiSocial; i++) {
            double x = rand.nextDouble() * width;
            double y = rand.nextDouble() * height;
            int radius = 25;
            AntiSocialAgent a = new AntiSocialAgent(x, y, radius);
            scape.addAgent(a);
        }

        // Add Wandering Agents (green)
        for (int i = 0; i < numWandering; i++) {
            double x = rand.nextDouble() * width;
            double y = rand.nextDouble() * height;
            int radius = 20;
            WanderingAgent a = new WanderingAgent(x, y, radius);
            scape.addAgent(a);
        }

        // Create the display window for visualization
        LandscapeDisplay display = new LandscapeDisplay(scape);

        // Initialize counters
        int moved = 1; // Start with non-zero to enter loop
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
     * Main method to run the demonstration.
     */
    public static void main(String[] args) {
        System.out.println("Starting WanderingAgent demonstration...");
        System.out.println("Blue circles: Social Agents (seek company)");
        System.out.println("Red circles: Anti-Social Agents (avoid crowds)");
        System.out.println("Green circles: Wandering Agents (explore territory)");
        System.out.println("Close the window to end the simulation.\n");

        // Run simulation with mixed agent types
        int iterations = runMixedExpt(15, 10, 12);

        System.out.println("Simulation completed after " + iterations + " iterations.");

        /*
         * System.out.println("Starting WanderingAgent impact analysis...");
         * System.out.
         * println("Running multiple simulations to compare different agent compositions.\n"
         * );
         * 
         * // Different agent compositions to test
         * int[][] compositions = {
         * { 30, 0, 0 }, // Only social agents
         * { 25, 0, 5 }, // Social + few wandering
         * { 20, 0, 10 }, // Social + more wandering
         * { 15, 0, 15 }, // Equal social and wandering
         * { 0, 30, 0 }, // Only anti-social agents
         * { 0, 25, 5 }, // Anti-social + few wandering
         * { 15, 15, 0 }, // Social + anti-social
         * { 10, 10, 10 } // Mixed population
         * };
         * 
         * StringBuilder resultTable = new StringBuilder();
         * resultTable.append("WanderingAgent Simulation\n");
         * resultTable.append("Social\tAntiSocial\tWandering\tIterations\tNotes\n");
         * resultTable.append("------\t----------\t---------\t----------\t-----\n");
         * 
         * for (int[] composition : compositions) {
         * int numSocial = composition[0];
         * int numAntiSocial = composition[1];
         * int numWandering = composition[2];
         * 
         * System.out.
         * printf("Running simulation: Social=%d, AntiSocial=%d, Wandering=%d\n",
         * numSocial, numAntiSocial, numWandering);
         * 
         * int result = runMixedExpt(numSocial, numAntiSocial, numWandering);
         * 
         * // Add analysis notes based on composition
         * String notes = "";
         * if (numWandering == 0 && numSocial > 0) {
         * notes = "Pure social clustering";
         * } else if (numWandering == 0 && numAntiSocial > 0) {
         * notes = "Pure anti-social spreading";
         * } else if (numWandering > 0) {
         * notes = "Mixed with exploration";
         * }
         * 
         * resultTable.append(String.format("%d\t%d\t\t%d\t\t%d\t\t%s\n",
         * numSocial, numAntiSocial, numWandering, result, notes));
         * 
         * System.out.printf("Completed after %d iterations.\n\n", iterations);
         * }
         * // Save results to file
         * writeResults("wandering_agent_analysis.txt", resultTable.toString());
         * 
         * System.out.
         * println("Analysis completed! Results saved to wandering_agent_analysis.txt");
         */
    }
}