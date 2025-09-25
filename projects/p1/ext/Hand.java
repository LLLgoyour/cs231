package projects.p1.ext;

/*
 * file name: Hand.java
 * Author: Jack Dai
 * Last modified: 9/24/2025
 * 
 * Purpose of the class: 
 * This class hold a set of cards
 * and is updated with extension methods
 */

public class Hand {

    /**
     * Creates an empty hand as an ArrayList of Cards.
     */
    private ArrayList<Card> hand;

    /**
     * Constructs an empty Hand
     */
    public Hand() {
        hand = new ArrayList<Card>();
    }

    /**
     * Removes any cards currently in the hand.
     */
    public void reset() {
        // Remove all elements by repeatedly removing the last element
        // until the list is empty. This avoids skipping elements as
        // removing by increasing index would.
        while (hand.size() > 0) {
            hand.remove(hand.size() - 1);
        }
    }

    /**
     * Adds the specified card to the hand.
     * 
     * @param card the card to be added to the hand
     */
    public void add(Card card) {
        hand.add(card);
    }

    /**
     * Returns the number of cards in the hand.
     * 
     * @return the number of cards in the hand
     */
    public int size() {
        return hand.size();
    }

    /**
     * Returns the card in the hand specified by the given index.
     * 
     * @param index the index of the card in the hand.
     * @return the card in the hand at the specified index.
     */
    public Card getCard(int index) {
        return hand.get(index);
    }

    /**
     * Returns the summed value over all cards in the hand.
     * 
     * @return the summed value over all cards in the hand
     */
    public int getTotalValue() {
        int sum = 0;
        int aces = 0; // count of aces (value 11)

        for (int i = 0; i < hand.size(); i++) {
            int v = hand.get(i).getValue();
            sum += v;
            if (v == 11) {
                aces++;
            }
        }

        // Convert some Aces from 11 to 1 (subtract 10) while beneficial
        while (sum > 21 && aces > 0) {
            sum -= 10; // treat one ace as 1 instead of 11
            aces--;
        }

        return sum;
    }

    /**
     * Returns true if this hand is a "Blackjack": exactly two cards totaling 21.
     * Typically this is an Ace (11) + a 10-value card.
     *
     * @return true if the hand is a two-card 21, false otherwise
     */
    public boolean isBlackjack() {
        return hand.size() == 2 && getTotalValue() == 21;
    }

    /**
     * Returns a string representation of the hand.
     * 
     * @return a string representation of the hand
     */
    public String toString() {
        // Match expected format: "[5, 2, 3] : 10"
        return hand.toString() + " : " + getTotalValue();
    }
}
