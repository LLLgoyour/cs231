package projects.p1.ext;

import java.util.Scanner;

/*
 * file name: Interactive.java
 * Author: Jack Dai
 * Last modified: 9/24/2025
 * Purpose of this class: 
 * This extension lets a human play a single hand against the dealer using rules
 * implemented in Blackjack class.
 */

public class Interactive {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Blackjack game = new Blackjack();

        System.out.println("Welcome to Interactive Blackjack (extension)");
        System.out.print("Enter reshuffle cutoff (or press Enter for default 26): ");
        String line = scanner.nextLine().trim();
        if (!line.isEmpty()) {
            try {
                int cutoff = Integer.parseInt(line);
                game.setReshuffleCutoff(cutoff);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, using default cutoff.");
            }
        }

        // Play one game interactively
        game.reset();
        game.deal();

        System.out.println("Initial hands:");
        System.out.println(game.toString());

        boolean playerAlive = game.playerTurnInteractive(scanner);
        if (!playerAlive) {
            System.out.println("Dealer wins.");
            scanner.close();
            return;
        }

        boolean dealerAlive = game.dealerTurn();
        if (!dealerAlive) {
            System.out.println("Dealer busted. You win!");
            scanner.close();
            return;
        }

        int playerTotal = game.getPlayerHand().getTotalValue();
        int dealerTotal = game.getDealerHand().getTotalValue();

        System.out.println("Final player's hand: " + game.getPlayerHand() + " (Total: " + playerTotal + ")");
        System.out.println("Final dealer's hand: " + game.getDealerHand() + " (Total: " + dealerTotal + ")");

        if (playerTotal > dealerTotal) {
            System.out.println("You win!");
        } else if (playerTotal < dealerTotal) {
            System.out.println("Dealer wins.");
        } else {
            System.out.println("It's a tie (push).");
        }

        scanner.close();
    }
}
