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
     * Runs a simulation experiment with N SocialAgents.
     * 
     * @param N initial number of agents
     * @return number of iterations the simulation ran
     */
    public static int runExpt(int N) {
        int width = 500;
        int height = 500;
        Landscape scape = new Landscape(width, height);
        Random rand = new Random();

        // Create N SocialAgents with random positions
        for (int i = 0; i < N; i++) {
            double x = rand.nextDouble() * width;
            double y = rand.nextDouble() * height;
            int radius = 25;
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
     */
    public static void main(String[] args) {
        int N = 50; // default
        if (args.length > 0) {
            N = Integer.parseInt(args[0]);
        }

        int result = runExpt(N);
        System.out.println("Simulation finished after " + result + " iterations.");
    }
}
