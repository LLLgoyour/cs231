/**
 * Homework 2 Simulations
 * Question 3
 */

package hw2.qThreeFour;

import java.util.Random;

public class NewCoin {

    private int value;
    private String side;
    private static final Random random = new Random();

    public NewCoin() {
        if (random.nextDouble() < 0.3) {
            side = "Heads";
            value = 1;
        } else {
            side = "Tails";
            value = 0;
        }
    }

    public int getValue() {
        return value;
    }

    public void flip() {
        if (random.nextDouble() < 0.3) {
            side = "Heads";
            value = 1;
        } else {
            side = "Tails";
            value = 0;
        }
    }

    public String toString() {
        return side;
    }

    public static void main(String[] args) {
        NewCoin coin = new NewCoin();
        coin.flip();
        System.out.println(coin);
    }
}
