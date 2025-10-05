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
     * populates it with N randomly positioned SocialAgents,
     * runs 100 update cycles, and returns the total number of agent moves.
     * 
     * @param N number of agents
     * @return total number of agents that moved during the run
     */
    public static int runExpt(int N) {
        int width = 500;
        int height = 500;
        Landscape scape = new Landscape(width, height);
        Random rand = new Random();

        // add N agents at random positions
        for (int i = 0; i < N; i++) {
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

    public static double agentSimulationTests() {

        double score = 0.;

        {
            int[] nums = { 50, 100 };
            int[] correctAnswers = { 234, 541 };
            for (int i = 0; i < nums.length; i++) {
                int output = 0;
                for (int j = 0; j < 20; j++) {
                    output += runExpt(nums[i]);
                }
                output /= 20;
                System.out.println(output);
                if ((output < correctAnswers[i] + 100) && ((output > correctAnswers[i] - 100))) {
                    score += 1.;
                }
            }
        }

        {
            int output = runExpt(250);
            if (output == 5000) {
                score += 1.;
            }

        }
        return score;
    }

    public static void main(String[] args) {
        System.out.println(agentSimulationTests());
    }
}