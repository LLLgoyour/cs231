package projects.p1.ext;

/*
file name:      BlackjackTests.java
Authors:        Max Bender & Naser Al Madi & Jack Dai
last modified:  9/18/2025

Use this program to debug Blackjack class

How to run:     java -ea BlackjackTests
*/

// Expected results:
// Draw percent: 8 +/- 1
// Dealer win percent: 49 +/- 1
// Player win percent: 41 +/1 1

public class BlackjackTests {

    public static void blackjackTests() {
        // testing outcome distribution
        {
            // set up
            Blackjack blackjack = new Blackjack();
            int trials = 100000;
            int dealerWins = 0, playerWins = 0, draws = 0;

            for (int i = 0; i < trials; i++) {
                int result = blackjack.game(false);
                if (result == -1) {
                    dealerWins++;
                } else if (result == 1) {
                    playerWins++;
                } else {
                    draws++;
                }
            }

            double dealerPct = (dealerWins * 100.0) / trials;
            double playerPct = (playerWins * 100.0) / trials;
            double drawPct = (draws * 100.0) / trials;

            System.out.printf("Dealer win percent: %.2f%%\n", dealerPct);
            System.out.printf("Player win percent: %.2f%%\n", playerPct);
            System.out.printf("Draw percent: %.2f%%\n", drawPct);

            assert Math.abs(dealerPct - 49) <= 1 : "Dealer win % out of expected range";
            assert Math.abs(playerPct - 41) <= 1 : "Player win % out of expected range";
            assert Math.abs(drawPct - 8) <= 1 : "Draw % out of expected range";
        }
    }

    public static void main(String[] args) {
        blackjackTests();
    }
}