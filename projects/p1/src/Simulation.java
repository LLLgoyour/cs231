package projects.p1.src;

/*
 * file name: Simulation.java
 * Author: Jack Dai
 * Last modified: 9/24/2025
 * 
 * Purpose of the class:
 * This simulated the game Blackjack for 1000 times 
 * and print out the game result
 */

public class Simulation {
    public static void main(String[] args) {
        // simulate multiple games and track results
        {
            // set up
            int playerCount = 0, dealerCount = 0, tieCount = 0;
            int N = 10000; // number of games to simulate
            Blackjack blackjack = new Blackjack(); // Create an instance of Blackjack

            for (int i = 0; i < N; i++) {
                int res = blackjack.game(false); // Run the game in non-verbose mode

                if (res == 0) {// It's a tie, tie count++
                    tieCount++;
                }
                if (res == 1) { // player wins, player count++
                    playerCount++;
                }
                if (res == -1) { // dealer wins, dealer count++
                    dealerCount++;
                }
            }
            // Final summary of results
            System.out.println("Run " + N + " times. \nThe result is --->  \nplayer wins: " + playerCount
                    + " \ndealer wins: " + dealerCount + " \nties: " + tieCount);

            System.out.println("\nDealer's win rate: " + ((double) dealerCount / N) * 100 + "%"
                    + "\nPlayer's win rate: " + ((double) playerCount / N) * 100 + "%"
                    + "\nTie rate: " + ((double) tieCount / N) * 100 + "%");
        }
    }
}
