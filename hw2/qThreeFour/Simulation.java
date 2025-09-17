/**
 * Homework 2 Simulations
 * Question 4
 */

package hw2.qThreeFour;

public class Simulation {
    public static double play(int target) {
        NewCoin coinOne = new NewCoin();
        NewCoin coinTwo = new NewCoin();
        NewCoin coinThree = new NewCoin();
        int count = 0;
        for (int i = 0; i < target; i++) {
            coinOne.flip();
            coinTwo.flip();
            coinThree.flip();
            // cache values to avoid repeated method calls
            int v1 = coinOne.getValue();
            int v2 = coinTwo.getValue();
            int v3 = coinThree.getValue();
            if (v1 == v2 && v2 == v3) {
                count++;
            }
        }
        double probability = (double) count / (double) target;
        return probability;
    }

    public static void main(String[] args) {
        int i = 10000;
        double probability = play(i);
        System.out.println("Probability of " + i + " times: " + probability);
    }
}
