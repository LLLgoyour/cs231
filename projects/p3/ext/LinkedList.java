/*
 * file name: LinkedList.java
 * author: Jack Dai
 * last modified: 10/08/2025
 * purpose of this class:
 * A linked list implementation that supports any data type (T)
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements Iterable<T> {

    /**
     * Node inner class
     */
    public static class Node<T> {
        private T data;
        private Node<T> next;

        /**
         * Default constructor that creates a node with null data and null next
         * reference.
         */
        public Node() {
            this.data = null;
            this.next = null;
        }

        /**
         * Constructor that creates a node with the specified data and null next
         * reference.
         * 
         * @param newData the data to store in this node
         */
        public Node(T newData) {
            this.data = newData;
            this.next = null;
        }

        /**
         * Constructor that creates a node with the specified data and next reference.
         * 
         * @param newData the data to store in this node
         * @param newNext the next node to link to
         */
        public Node(T newData, Node<T> newNext) {
            this.data = newData;
            this.next = newNext;
        }

        /**
         * Sets next to the given node.
         * 
         * @param n the new node to set to the next
         */
        public void setNext(Node<T> n) {
            this.next = n;
        }

        /**
         * Returns the next field.
         * 
         * @return the next field
         */
        public Node<T> getNext() {
            return this.next;
        }

        /**
         * Returns the value of the data field.
         * 
         * @return the value of the data field.
         */
        public T getData() {

            return data;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * Default constructor that creates an empty linked list.
     */
    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Add an item to the end of the list
     * 
     * @param item the item to add to the end of the list
     */
    public void addLast(T item) {
        Node<T> newNode = new Node<>(item);
        if (this.head == null) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            this.tail.next = newNode;
            this.tail = newNode;
        }
        this.size++;
    }

    /**
     * Add an item to the beginning of the list
     * 
     * @param item the item to add to the beginning of the list
     */
    public void addFirst(T item) {
        Node<T> newNode = new Node<>(item);
        if (this.head == null) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            newNode.next = this.head;
            this.head = newNode;
        }
        this.size++;
    }

    /**
     * Removes and returns the first element of the list, or null if empty.
     * 
     * @return the first element of the list, or null if empty.
     */
    public T removeFirst() {
        if (this.head == null) {
            return null;
        }
        T data = this.head.data;
        this.head = this.head.next;
        if (this.head == null) {
            this.tail = null;
        }
        return data;
    }

    /**
     * Gets the head node of the linked list.
     * 
     * @return the head node
     */
    public Node<T> getHead() {
        return this.head;
    }

    /**
     * Convenience add method that appends to the beginning of the list.
     * 
     * @param item the item to add to the beginning of the list
     */
    public void add(T item) {
        Node<T> newNode = new Node<T>(item);
        newNode.setNext(head);
        head = newNode;
        size++;
    }

    /**
     * Insert an item at the specified index (0-based). If index == size, appends
     * to end.
     * Throws IndexOutOfBoundsException for invalid indices.
     * 
     * @param index the index at which to insert the item
     * @param item  the item to insert
     */
    public void add(int index, T item) {
        int n = size();
        if (index < 0 || index > n) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        if (index == 0) {
            addFirst(item);
            return;
        }
        if (index == n) {
            addLast(item);
            return;
        }
        Node<T> prev = this.head;
        for (int i = 0; i < index - 1; i++) {
            prev = prev.next;
        }
        Node<T> newNode = new Node<>(item, prev.next);
        prev.next = newNode;
        this.size++;
    }

    /**
     * Gets the size of the list.
     * 
     * @return the size of the list
     */
    public int size() {
        return this.size;
    }

    /**
     * Checks if the list contains the specified item.
     * 
     * @param item the item to search for in the list
     * @return true if the list contains the given item
     */
    public boolean contains(T item) {
        Node<T> current = this.head;
        while (current != null) {
            if ((current.data == null && item == null) ||
                    (current.data != null && current.data.equals(item))) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Compares this LinkedList with another object for equality.
     * 
     * @param o the object to compare with
     * @return true if o is a LinkedList and also contains the same items
     *         in the same order.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LinkedList)) {

            return false;
        }
        // Use an unbounded wildcard to avoid unchecked cast warnings
        LinkedList<?> transformed = (LinkedList<?>) o;
        // Now I have a reference to something Java knows is a LinkedList!

        if (transformed.size() != size) {
            return false;// check the length of size
        }

        Node<T> current = head;
        Node<?> otherCurrent = transformed.head;

        while (current != null) {

            Object a = current.getData();
            Object b = otherCurrent.getData();
            if (a == null ? b != null : !a.equals(b)) {

                return false;
            }
            current = current.next;
            otherCurrent = otherCurrent.getNext();
        }
        return true;
    }

    /**
     * Removes the first occurrence of an item from the list.
     * 
     * @param item the item to remove from the list
     */
    public void remove(T item) {
        if (this.head == null)
            return;

        if ((this.head.data == null && item == null) ||
                (this.head.data != null && this.head.data.equals(item))) {
            this.head = this.head.next;
            if (this.head == null)
                this.tail = null;
            this.size--;
            return;
        }

        Node<T> current = this.head;
        while (current.next != null) {
            if ((current.next.data == null && item == null) ||
                    (current.next.data != null && current.next.data.equals(item))) {
                current.next = current.next.next;
                if (current.next == null)
                    this.tail = current;
                this.size--;
                return;
            }
            current = current.next;
        }
    }

    /**
     * Remove and return the first element of the list, or null if empty.
     * 
     * @return the first element of the list, or null if empty
     */
    public T remove() {
        return removeFirst();
    }

    /**
     * Removes and returns the element at the given index. Throws
     * IndexOutOfBoundsException for invalid index.
     * 
     * @param index the index of the element to remove
     * @return the element at the given index
     */
    public T remove(int index) {
        int n = size();
        if (index < 0 || index >= n) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        if (index == 0) {
            return removeFirst();
        }
        Node<T> prev = this.head;
        for (int i = 0; i < index - 1; i++) {
            prev = prev.next;
        }
        Node<T> toRemove = prev.next;
        T data = toRemove.data;
        prev.next = toRemove.next;
        if (prev.next == null) {
            this.tail = prev;
        }
        this.size--;
        return data;
    }

    /**
     * Removes and returns the last item in the list, or null if empty.
     * 
     * @return the last item in the list, or null if empty
     */
    public T removeLast() {
        if (this.head == null) {
            return null;
        }
        if (this.head == this.tail) {
            T data = this.head.data;
            this.head = null;
            this.tail = null;
            this.size--;
            return data;
        }
        Node<T> current = this.head;
        while (current.next != this.tail) {
            current = current.next;
        }
        T data = this.tail.data;
        this.tail = current;
        this.tail.next = null;
        this.size--;
        return data;
    }

    /**
     * Returns the last item in the list or null if empty.
     * 
     * @return the last item in the list or null if empty
     */
    public T getLast() {
        if (this.tail == null)
            return null;
        return this.tail.data;
    }

    /**
     * Returns the element at the given index (0-based). Throws
     * IndexOutOfBoundsException for invalid index.
     * 
     * @param index the index of the element to retrieve
     * @return the element at the given index (0-based)
     */
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        Node<T> current = this.head;
        int i = 0;
        while (current != null && i < index) {
            current = current.next;
            i++;
        }
        if (current == null) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        return current.data;
    }

    /**
     * Clears the list, removing all elements.
     */
    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Checks if the list is empty.
     * 
     * @return true if list is empty.
     */
    public boolean isEmpty() {
        return this.head == null;
    }

    /**
     * Reverses the order of elements in the list.
     */
    public void reverse() {
        Node<T> prev = null;
        Node<T> current = this.head;
        this.tail = this.head;

        while (current != null) {
            Node<T> next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        this.head = prev;
    }

    /**
     * Inserts an item roughly in the middle (or at given position). If position <=0
     * inserts at head.
     * 
     * @param item     the item to insert
     * @param position the desired position to insert at
     */
    public void addMiddle(T item, int position) {
        if (position <= 0 || this.head == null) {
            addFirst(item);
            return;
        }
        Node<T> current = this.head;
        int index = 0;
        while (current.next != null && index < position - 1) {
            current = current.next;
            index++;
        }
        Node<T> newNode = new Node<>(item, current.next);
        current.next = newNode;
        if (newNode.next == null)
            this.tail = newNode;
        this.size++;
    }

    /**
     * Queue-like convenience method that adds an item to the end of the list.
     * 
     * @param item the item to add to the end of the list
     */
    public void offer(T item) {
        addLast(item);
    }

    /**
     * Returns the head value without removing it.
     * 
     * @return the head value
     */
    public T peek() {
        if (this.head == null)
            return null;
        return this.head.data;
    }

    /**
     * Removes and returns the first element of the list.
     * 
     * @return this will remove the first node
     */
    public T poll() {
        return removeFirst();
    }

    /**
     * Provides iterator support to allow for-each iteration.
     * 
     * @return an iterator over the elements in this list
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> curr = head;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T data = curr.data;
                curr = curr.next;
                return data;
            }
        };
    }

    /**
     * Converts the list to a String representation.
     * 
     * @return a string representation of the list
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.size);
        sb.append(this.head);
        sb.append(this.isEmpty());
        return sb.toString();
    }
}
