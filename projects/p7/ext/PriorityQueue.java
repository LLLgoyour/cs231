public interface PriorityQueue<T> {

    /**
     * Adds the given {@code item} into this queue.
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
     * Returns the item of greatest priority in the queue.
     * 
     * @return the item of greatest priority in the queue.
     */
    public T peek();

    /**
     * Returns and removes the item of greatest priority in the queue.
     * 
     * @return the item of greatest priority in the queue.
     */
    public T poll();

    /**
     * Updates the priority of the given item - that is, ensures that it is 'behind'
     * items with higher priority and 'ahead' of items with lower priority.
     * 
     * Assumes all other items' priorities in this Priority Queue have not changed.
     * 
     * @param item the item whose priority has been updated.
     */
    public void updatePriority(T item);
}