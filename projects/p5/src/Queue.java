/*
 * file name: Stack.java
 * author: Jack Dai
 * last modified: 10/23/2025
 * purpose of the class:
 * Creates the Queue interface for the project.
 */

public interface Queue<T> {

    /**
     * Adds the given {@code item} to the end of this queue.
     * 
     * @param item the item to add to the queue.
     */
    public void offer(T item);

    /**
     * Returns the number of items in the queue.
     * 
     * @return the number of items in the queue.
     */
    public int size();

    /**
     * Returns the item at the front of the queue.
     * 
     * @return the item at the front of the queue.
     */
    public T peek();

    /**
     * Returns and removes the item at the front of the queue.
     * 
     * @return the item at the front of the queue.
     */
    public T poll();
}