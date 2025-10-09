import java.util.Objects;

public class Queue<E> {
    private final Object[] elements;
    private final int capacity;
    private int first;
    private int end;
    private int size;

    public Queue(int capacity) {
        this.capacity = capacity;
        this.elements = new Object[capacity];
    }

    /**
     * element offer in the tail
     *
     * @param element added element
     * @return true if offer, false if can't offer
     */
    public boolean offer(E element) {
        // if capacity is already full return false
        if (size == capacity) {
            return false;
        }
        if (size == 0) {
            elements[end] = element;
        } else if (end + 1 == capacity) {
            end = 0;
            elements[end] = element;
        } else {
            elements[++end] = element;
        }
        size++;
        return true;
    }

    /**
     * remove the head element
     *
     * @return removed element or null
     */
    public E poll() {
        if (size == 0) {
            return null;
        }
        Object o = elements[first];
        elements[first] = null;

        if (++first == capacity) {
            first = 0;
        }
        if (--size == 0) {
            first = 0;
            end = 0;
        }
        return (E) o;
    }

    /**
     * return size of the queue
     *
     * @return size of the queue (number of elements)
     */
    public int size() {
        return size;
    }

    /**
     * clear the queue
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
        first = 0;
        end = 0;
    }

    public void rotateQueue(Queue<E> queue) {
        if (queue.size() <= 1) {
            return;
        }

        // poll the first element
        E firstElement = queue.poll();

        // add the element to the end of the queue
        queue.offer(firstElement);
    }

    public static Queue<Integer> mergeQueues(Queue<Integer> queueOne, Queue<Integer> queueTwo) {
        Queue<Integer> merged = new LinkedList<>();

        // Alternate while both have elements.
        while (!queueOne.isEmpty() && !queueTwo.isEmpty()) {
            merged.offer(queueOne.poll());
            merged.offer(queueTwo.poll());
        }

        // At this point at least one is empty.
        // Append the rest of whichever is non-empty.
        while (!queueOne.isEmpty()) {
            merged.offer(queueOne.poll());
        }
        while (!queueTwo.isEmpty()) {
            merged.offer(queueTwo.poll());
        }

        return merged;
    }

    /**
     * rewrite the toString
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (size == 0) {
            stringBuilder.append("[ ]");
            return stringBuilder.toString();
        }
        stringBuilder.append("[ ");
        if (first < end) {
            for (int i = first; i <= end; i++) {
                stringBuilder.append(elements[i]);
                if (i != end) {
                    stringBuilder.append(", ");
                }
            }
        } else if (size == 1) {
            stringBuilder.append(elements[first]);
        } else {
            for (int i = first; i < capacity; i++) {
                stringBuilder.append(elements[i]);
                stringBuilder.append(", ");
            }
            for (int i = 0; i <= end; i++) {
                stringBuilder.append(elements[i]);
                if (i != end) {
                    stringBuilder.append(", ");
                }
            }
        }
        stringBuilder.append(" ]");
        return stringBuilder.toString();
    }
}