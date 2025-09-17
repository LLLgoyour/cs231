/**
 * Homework 2 Simulations
 * Question 2
 */

package hw2.qOneTwo;

public class Simulation {
    public static double play(int target) {
        Coin coinOne = new Coin();
        Coin coinTwo = new Coin();
        int count = 0;
        for (int i = 0; i < target; i++) {
            coinOne.flip();
            coinTwo.flip();
            if (coinOne.getValue() == coinTwo.getValue()) {
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
