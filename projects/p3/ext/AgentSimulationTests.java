/*
file name:      AgentSimulationTests.java
Authors:        Ike Lage & Jack Dai
last modified:  10/05/2025

How to run:     java -ea AgentSimulationTests
*/

import java.util.Random;

public class AgentSimulationTests {

    /**
     * This method creates a Landscape with width = 500 and height = 500,
     * populates it with the specified number of randomly positioned SocialAgents,
     * runs 100 update cycles, and returns the total number of agent moves.
     * 
     * @param numAgents number of agents
     * @return total number of agents that moved during the run
     */
    public static int runExpt(int numAgents) {
        int width = 500;
        int height = 500;
        Landscape scape = new Landscape(width, height);
        Random rand = new Random();

        // add numAgents agents at random positions
        for (int i = 0; i < numAgents; i++) {
            double x = rand.nextDouble() * width;
            double y = rand.nextDouble() * height;
            int radius = 20;
            SocialAgent a = new SocialAgent(x, y, radius);
            scape.addAgent(a);
        }

        // run 100 updates and track total moves
        int totalMoved = 0;
        for (int i = 0; i < 100; i++) {
            totalMoved += scape.updateAgents();
        }

        return totalMoved;
    }

    /**
     * Tests the agent simulation functionality by running experiments
     * with different numbers of agents and checking expected outcomes.
     * 
     * @return score based on test results
     */
    public static double agentSimulationTests() {

        double score = 0.;

        // Test with different numbers of agents and check average results
        {
            int[] nums = { 50, 100 };
            int[] correctAnswers = { 234, 541 };
            for (int i = 0; i < nums.length; i++) {
                int output = 0;
                // Run experiment 20 times and calculate average
                for (int j = 0; j < 20; j++) {
                    output += runExpt(nums[i]);
                }
                output /= 20;
                System.out.println(output);
                // Check if output is within acceptable range
                if ((output < correctAnswers[i] + 100) && ((output > correctAnswers[i] - 100))) {
                    score += 1.;
                }
            }
        }

        // Test with 250 agents expecting maximum movement
        {
            int output = runExpt(250);
            if (output == 5000) {
                score += 1.;
            }

        }
        return score;
    }

    /**
     * Main method that runs the agent simulation tests.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println(agentSimulationTests());
    }
}