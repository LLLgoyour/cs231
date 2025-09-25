package projects.p1.src;

/*
 * file name: Blackjack.java
 * Author: Jack Dai
 * Last modified: 9/18/2025
 * 
 * Purpose of this class: TODO
 */
public class Blackjack {

    /**
     * 
     */
    Deck deck;
    Hand dealerHand;
    Hand playerHand;
    private int reshuffleCutoff;

    /**
     * 
     */
    public Blackjack() {
        this(26);
    }

    /**
     * 
     * @param reshuffleCutoff
     */
    public Blackjack(int reshuffleCutoff) {
        this.reshuffleCutoff = reshuffleCutoff;
        reset();
    }

    /**
     * 
     */
    public void reset() {
        //
        if (deck == null || deck.size() < reshuffleCutoff) {
            deck = new Deck();
            deck.shuffle();
        }

        playerHand = new Hand();
        dealerHand = new Hand();
    }

    /**
     * 
     */
    public void deal() {
        //
        playerHand.add(deck.deal());
        dealerHand.add(deck.deal());
    }

    /**
     * 
     * @return
     */
    public boolean playerTurn() {
        while (playerHand.getTotalValue() < 16) {
            //
            playerHand.add(deck.deal());
            if (playerHand.getTotalValue() > 21) {
                //
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * @return
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

    public String toString() {
        return "THe player hand: " + playerHand.toString() + " (Total: " + playerHand.getTotalValue() + ")" +
                "\nThe dealer hand: " + dealerHand.toString() + " (Total: " + dealerHand.getTotalValue() + ")";
    }

    public int game(boolean verbose) {
        // Reset the game and reshuffle the deck if needed
        reset();
        // Deal cards to the player and dealer
        deal();

        if (verbose) {
            System.out.println("Initial hands: ");
            System.out.println(this.toString());
        }

        // The player's turn
        if (!playerTurn()) {
            // If the player busts,
            // then show player's and dealer's hand
            if (verbose) {
                System.out.println("The player busts. The dealer wins."
                        + "\nFinal player's hand: " + playerHand + " (Total: " + playerHand.getTotalValue() + ")."
                        + "\nFinal dealer's hand: " + dealerHand + " (Total: " + dealerHand.getTotalValue() + ").");
            }
            return -1; // If the dealer wins
        }

        // The dealer's turn
        if (!dealerTurn()) {
            // If the dealer busts,
            // then show player's and dealer's hand
            if (verbose) {
                System.out.println("The dealer busts. The player wins."
                        + "\nFinal player's hand: " + playerHand + " (Total: " + playerHand.getTotalValue() + ")."
                        + "\nFinal dealer's hand: " + dealerHand + " (Total: " + dealerHand.getTotalValue() + ").");
            }
            return 1; // If the player wins
        }

        if (verbose) {
            // Print final hands
            // if both player and dealer don't bust
            System.out.println("Final player's hand: " + playerHand + " (Total: " + playerHand.getTotalValue() + ")."
                    + "\nFinal dealer's hand: " + dealerHand + " (Total: " + dealerHand.getTotalValue() + ").");
        }

        // Compare total values
        // and decide the winner
        int playerTotal = playerHand.getTotalValue();
        int dealerTotal = dealerHand.getTotalValue();

        if (playerTotal > dealerTotal) {
            if (verbose) {
                System.out.println("The player wins.");
            }
            return 1; // If the player wins
        } else if (playerTotal < dealerTotal) {
            if (verbose) {
                System.out.println("The dealer wins.");
            }
            return -1; // If the dealer wins
        } else {
            if (verbose) {
                System.out.println("It's a tie.");
            }
        }
    }

    public static void main(String[] args) {

    }
}
