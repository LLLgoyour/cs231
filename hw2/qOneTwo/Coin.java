/**
 * Homework 2 Simulations
 * Question 1
 */

package hw2.qOneTwo;

import java.util.Random;

public class Coin {

    private int value;
    private String side;
    Random random = new Random();

    public Coin() {
        if (random.nextBoolean()) {
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
        if (random.nextBoolean()) {
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
        Coin coin = new Coin();
        coin.flip();
        System.out.println(coin);
    }
}
