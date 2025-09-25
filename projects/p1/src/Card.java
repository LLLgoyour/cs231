package projects.p1.src;

/**
 * file name: Card.java
 * Author: Jack Dai
 * last modified: 9/24/2025
 * 
 * Purpose of the class:
 * This class hold all information unique to the card
 */

public class Card {
    private int value;

    /**
     * Constructs a card with the specified value.
     * 
     * @param val value of the card
     */
    public Card(int val) {
        if (2 <= val && val <= 11) {
            this.value = val;
        }
    }

    /**
     * Returns the value of the card.
     * 
     * @return the value of the card
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns a string representation of this card.
     * 
     * @return a string representation of this card
     */
    public String toString() {
        return String.valueOf(value);
    }
}
