import java.util.Iterator; // defines the Iterator interface

public class LinkedList<T> {
    private class Node<T> {
        T data;
        Node<T> next;

        /**
         * a constructor that initializes next to null and the container field to item.
         * 
         * @param item
         */
        public Node(T item) {
            this.data = item;
            this.next = null;
        }

        /**
         * 
         * @return the value of the container field.
         */
        public T getData() {
            return this.data;
        }

        /**
         * sets next to the given node.
         * 
         * @param n
         */
        public void setNext(Node<T> n) {
            this.next = n;
        }

        /**
         * 
         * @return the next field.
         */
        public Node<T> getNext() {
            return this.next;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * constructor that initializes the fields so it is an empty list.
     */
    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * 
     * @return the size of the list.
     */
    public int size() {
        return this.size;
    }

    /**
     * empties the list (resets the fields so it is an empty list).
     */
    public void clear() {
        this.tail = null;
        this.head = null;
        this.size = 0;
    }

    /**
     * 
     * @return true if the list is empty, otherwise this method returns false.
     * 
     */
    public boolean isEmpty() {
        return this.head == null;
    }

    /**
     * inserts the item at the beginning of the list.
     * 
     * @param item
     */
    public void add(T item) {
        Node<T> newNode = new Node<T>(item);
        if (isEmpty()) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            newNode.next = this.head;
            this.head = newNode;
        }
        this.size++;
    }

    /**
     * 
     * @param index given index
     * @return the item specified by the given index.
     */
    public T get(int index) {
        Node<T> current = this.head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.getData();
    }

    /**
     * inserts the item at the specified position in the list.
     *
     * @param index
     * @param item
     */
    public void add(int index, T item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> newNode = new Node<>(item);
        if (index == 0) {
            newNode.next = this.head;
            this.head = newNode;
            if (this.tail == null) {
                this.tail = newNode;
            }
        } else {
            Node<T> current = this.head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
            if (newNode.next == null) {
                this.tail = newNode;
            }
        }
        size++;
    }

    /**
     * 
     * @param o
     * @return true if o is present in this list, otherwise this method returns
     *         false. More specifically, if there is an item item in this list such
     *         that item.equals(o), then this method should return true.
     * 
     */
    public boolean contains(Object o) {
        Node<T> current = this.head;
        while (current != null) {
            if (current.getData().equals(o)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * removes the first item of the list and returns it.
     * 
     * @return list with the removed first item
     */
    public T remove() {
        if (this.isEmpty()) {
            this.tail = null;
        }
        T removedData = this.head.getData();
        this.head = this.head.next;
        size--;
        if (this.head == null) {
            this.tail = null;
        }
        return removedData;
    }

    /**
     * @return a String representation of the list.
     */
    public String toString() {
        Node<T> current = this.head;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        while (current != null) {
            sb.append(current.getData());
            if (current.getNext() != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 
     * @return true if o is a LinkedList that also contains the same items in the
     *         same
     *         order.
     *         Notice that for this method the parameter is of type Object instead
     *         of being
     *         a LinkedList itself.
     */
    // public boolean equals(Object o) {
    // if (this == o) {
    // return true;
    // }
    // if (!(o instanceof LinkedList)) {
    // return false;
    // }
    // LinkedList<?> other = (LinkedList<?>) o;
    // if (this.size != other.size) {
    // return false;
    // }

    // Node<T> current1 = this.head;
    // Node<?> current2 = other.head;

    // while (current1 != null) {
    // if (!current1.getData().equals(current2.getData())) {
    // return false;
    // }
    // current1 = current1.next;
    // current2 = current2.next;
    // }
    // return true;
    // }
}