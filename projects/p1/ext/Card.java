package projects.p1.ext;

/**
 * Author: Jack Dai
 * 
 * Purpose of the class: TODO
 * 
 */
public class Card {

    /**
     * The value of the card.
     */
    private int value;

    /**
     * Constructs a card with the specified value.
     * 
     * @param val
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
