package projects.p1.ext;

import java.util.Scanner;

/*
 * File name: Blackjack.java
 * Author: Jack Dai
 * Last modified: 9/24/2025
 *
 * Purpose of this class:
 * This class implements the Blackjack card game
 * It manages the deck, player hand, dealer hand, and the game rules for
 * dealing, taking turns, checking for busts, and determining the winner
 * This class includes extension methods
 */

public class Blackjack {

    Deck deck; // The deck of cards used for the game
    Hand dealerHand; // The dealer's hand
    Hand playerHand; // The player's hand
    private int reshuffleCutoff; // Threshold at which the deck is rebuilt and shuffled

    /**
     * Default constructor for Blackjack
     * Uses a reshuffle cutoff of 26 cards
     */
    public Blackjack() {
        this(26);
    }

    /**
     * Constructor for Blackjack with a custom reshuffle cutoff
     * 
     * @param reshuffleCutoff the number of cards below which the deck is rebuilt
     *                        and shuffled
     */
    public Blackjack(int reshuffleCutoff) {
        this.reshuffleCutoff = reshuffleCutoff;
        reset();
    }

    /**
     * Resets the game state
     */
    public void reset() {
        if (deck == null || deck.size() < reshuffleCutoff) {
            deck = new Deck();
            deck.shuffle();
        }

        playerHand = new Hand();
        dealerHand = new Hand();
    }

    /**
     * Deals one card each to the player and the dealer
     */
    public void deal() {
        playerHand.add(deck.deal());
        dealerHand.add(deck.deal());
    }

    /**
     * Simulates the player's turn
     * The player draws until their hand has a value of 16 or more
     * If the hand value exceeds 21, the player busts
     * 
     * @return false if the player busts, true otherwise
     */
    public boolean playerTurn() {
        while (playerHand.getTotalValue() < 16) {
            playerHand.add(deck.deal());
            if (playerHand.getTotalValue() > 21) {
                return false;
            }
        }
        return true;
    }

    /**
     * Simulates the dealer's turn
     * The dealer draws until their hand has a value of 17 or more
     * If the hand value exceeds 21, the dealer busts
     * 
     * @return false if the dealer busts, true otherwise
     */
    public boolean dealerTurn() {
        while (dealerHand.getTotalValue() < 17) {
            dealerHand.add(deck.deal());
            if (dealerHand.getTotalValue() > 21) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method is part of the interactive player turn extension
     * Allows a human player to choose to hit or stay using terminal input
     *
     * @param scanner a Scanner connected to System.in for reading player input
     * @return false if the player busts, true otherwise
     */
    public boolean playerTurnInteractive(Scanner scanner) {
        // Print current player hand and dealer's hand
        System.out.println("Your hand: " + playerHand.toString() + " (Total: " + playerHand.getTotalValue() + ")");
        if (dealerHand.size() > 0) {
            System.out.println("Dealer's visible card: " + dealerHand.getCard(0));
        }

        while (true) {
            // Ask player for action
            System.out.print("Do you want to 'hit' or 'stay'? ");
            String line = scanner.nextLine();
            if (line == null) {
                // treat EOF as stay
                break;
            }
            line = line.trim().toLowerCase();
            if (line.equals("hit") || line.equals("h")) {
                playerHand.add(deck.deal());
                System.out.println("You drew: " + playerHand.getCard(playerHand.size() - 1));
                System.out.println(
                        "Your hand: " + playerHand.toString() + " (Total: " + playerHand.getTotalValue() + ")");
                if (playerHand.getTotalValue() > 21) {
                    System.out.println("You busted!");
                    return false;
                } else if (playerHand.getTotalValue() == 21) {
                    System.out.println("You have 21.");
                    return true;
                }
            } else if (line.equals("stay") || line.equals("s")) {
                // player stays
                System.out.println(
                        "You stay with: " + playerHand.toString() + " (Total: " + playerHand.getTotalValue() + ")");
                break;
            } else {
                System.out.println("Please type 'hit' (or 'h') to draw a card, or 'stay' (or 's') to end your turn.");
            }
        }
        return true;
    }

    /**
     * Sets the reshuffle cutoff value
     *
     * @param cutoff the reshuffle cutoff value
     */
    public void setReshuffleCutoff(int cutoff) {
        this.reshuffleCutoff = cutoff;
    }

    /**
     * Returns the reshuffle cutoff value
     *
     * @return the reshuffle cutoff value
     */
    public int getReshuffleCutoff() {
        return reshuffleCutoff;
    }

    /**
     * Returns a string representation of the current game state
     */
    public String toString() {
        return "The player hand: " + playerHand.toString() + " (Total: " + playerHand.getTotalValue() + ")" +
                "\nThe dealer hand: " + dealerHand.toString() + " (Total: " + dealerHand.getTotalValue() + ")";
    }

    /**
     * Plays one complete game of Blackjack
     * 
     * @param verbose if true, prints detailed information about the game flow and
     *                results
     * @return 1 if the player wins
     *         -1 if the dealer wins
     *         0 if the game is a tie (push)
     */
    public int game(boolean verbose) {
        reset(); // Reset the game and shuffle the deck if necessary
        deal(); // Deal the initial cards

        if (verbose) {
            System.out.println("Initial hands: ");
            System.out.println(this.toString());
        }

        // Player's turn
        if (!playerTurn()) {
            if (verbose) {
                System.out.println("The player busts. The dealer wins."
                        + "\nFinal player's hand: " + playerHand + " (Total: " + playerHand.getTotalValue() + ")."
                        + "\nFinal dealer's hand: " + dealerHand + " (Total: " + dealerHand.getTotalValue() + ").");
            }
            return -1;
        }

        // Dealer's turn
        if (!dealerTurn()) {
            if (verbose) {
                System.out.println("The dealer busts. The player wins."
                        + "\nFinal player's hand: " + playerHand + " (Total: " + playerHand.getTotalValue() + ")."
                        + "\nFinal dealer's hand: " + dealerHand + " (Total: " + dealerHand.getTotalValue() + ").");
            }
            return 1;
        }

        if (verbose) {
            System.out.println("Final player's hand: " + playerHand + " (Total: " + playerHand.getTotalValue() + ")."
                    + "\nFinal dealer's hand: " + dealerHand + " (Total: " + dealerHand.getTotalValue() + ").");
        }

        // Compare totals
        int playerTotal = playerHand.getTotalValue();
        int dealerTotal = dealerHand.getTotalValue();

        if (playerTotal > dealerTotal) {
            if (verbose) {
                System.out.println("The player wins.");
            }
            return 1;
        } else if (playerTotal < dealerTotal) {
            if (verbose) {
                System.out.println("The dealer wins.");
            }
            return -1;
        } else {
            if (verbose) {
                System.out.println("It's a tie.");
            }
            return 0;
        }
    }

    /**
     * Getter for the player's hand
     * 
     * @return the player's hand
     */
    public Hand getPlayerHand() {
        return playerHand;
    }

    /**
     * Getter for the dealer's hand
     * 
     * @return the dealer's hand
     */
    public Hand getDealerHand() {
        return dealerHand;
    }

    /**
     * Getter for the deck
     * 
     * @return the current deck
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Main method to run a single Blackjack game with verbose output
     */
    public static void main(String[] args) {
        Blackjack blackjack = new Blackjack();
        blackjack.game(true);
    }
}