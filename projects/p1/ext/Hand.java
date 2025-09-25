package projects.p1.ext;

/*
 * Author: Jack Dai
 * 
 * Purpose of the class: TODO
 */

public class Hand {

    /**
     * Creates an empty hand as an ArrayList of Cards.
     */
    private ArrayList<Card> hand;

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
        for (int i = 0; i < hand.size(); i++) {
            sum += hand.get(i).getValue();
        }
        return sum;
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
