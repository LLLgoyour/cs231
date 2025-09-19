package projects.p1.src;

import java.util.ArrayList;
import java.util.Random;

/*
 * file name: Deck.java
 * Author: Jack Dai
 * Last modified: 9/18/2025
 * 
 * Purpose of the class: TODO
 */
public class Deck {

    /**
     * Creates the underlying deck as an ArrayList of Card objects.
     * Calls build() as a subroutine to build the deck itself.
     */
    private ArrayList<Card> deck;
    private Random random = new Random();

    /**
     * Builds a deck of 52 cards, 4 each of cards with values 2-9 and 11,
     * and 16 cards with the value 10.
     */
    public Deck() {
        // initialize and build the standard 52-card deck
        build();
    }

    /**
     * Rebuilds a deck of 52 cards, 4 each of cards with values 2-9 and 11, and 16
     * cards with the value 10
     * Replaces any current deck stored.
     */
    public void build() {
        // Create a new ArrayList and fill it with 52 cards:
        // 4 copies of each value 2..9 and 11, and 16 copies of value 10.
        deck = new ArrayList<Card>(52);

        // values 2 through 9 for 4 copies each
        for (int val = 2; val <= 9; val++) {
            for (int copy = 0; copy < 4; copy++) {
                deck.add(new Card(val));
            }
        }

        // value 10 for 16 copies
        for (int copy = 0; copy < 16; copy++) {
            deck.add(new Card(10));
        }

        // value 11 (ace) for 4 copies
        for (int copy = 0; copy < 4; copy++) {
            deck.add(new Card(11));
        }
    }

    /**
     * Returns the number of cards left in the deck.
     * 
     * @return the number of cards left in the deck
     */
    public int size() {
        return deck.size();
    }

    /**
     * Returns and removes the first card of the deck.
     * 
     * @return the first card of the deck
     */
    public Card deal() {
        return deck.remove(0);
    }

    /**
     * Shuffles the cards currently in the deck.
     */
    public void shuffle() {
        // Shuffle by repeatedly removing a random card from the current
        // deck and appending it to a new list. Refer to the approach
        // used in lab 1 Shuffle class.
        ArrayList<Card> shuffled = new ArrayList<Card>(deck.size());
        while (deck.size() > 0) {
            int idx = random.nextInt(deck.size()); // random index < current size
            Card removed = deck.remove(idx);
            shuffled.add(removed);
        }

        // replace the old deck with the shuffled one
        deck = shuffled;
    }

    /**
     * Returns a string representation of the deck.
     * 
     * @return a string representation of the deck
     */
    public String toString() {
        return deck.toString();
    }
}