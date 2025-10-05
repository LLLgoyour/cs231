/*
 * file name: LinkedList.java
 * author: Jack Dai
 * last modified: 10/02/2025
 * purpose of this class:
 * A generic singly linked list implementation that supports any data type (T)
 */

public class LinkedList<T> {

    /**
     * Node inner class
     */
    public class Node {
        public T data;
        public Node next;

        public Node(T newData) {
            this.data = newData;
            this.next = null;
        }

        public Node(T newData, Node newNext) {
            this.data = newData;
            this.next = newNext;
        }
    }

    private Node head;
    private Node tail;

    /**
     * constructor
     */
    public LinkedList() {
        this.head = null;
        this.tail = null;
    }

    /**
     * Add an item to the end of the list
     * 
     * @param item
     */
    public void addLast(T item) {
        Node newNode = new Node(item);
        if (this.head == null) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            this.tail.next = newNode;
            this.tail = newNode;
        }
    }

    /**
     * Add an item to the beginning of the list
     * 
     * @param item
     */
    public void addFirst(T item) {
        Node newNode = new Node(item);
        if (this.head == null) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            newNode.next = this.head;
            this.head = newNode;
        }
    }

    /**
     * 
     * @return the head node
     */
    public Node getHead() {
        return this.head;
    }

    /**
     * 
     * @return the size of the list
     */
    public int size() {
        int count = 0;
        Node current = this.head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    /**
     * 
     * @param item
     * @return true if the list contains the given item
     */
    public boolean contains(T item) {
        Node current = this.head;
        while (current != null) {
            if (current.data.equals(item)) { // use equals(), not ==
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Remove the first occurrence of an item from the list
     * 
     * @param item
     */
    public void remove(T item) {
        if (this.head == null)
            return;

        if (this.head.data.equals(item)) {
            this.head = this.head.next;
            if (this.head == null)
                this.tail = null;
            return;
        }

        Node current = this.head;
        while (current.next != null) {
            if (current.next.data.equals(item)) {
                current.next = current.next.next;
                if (current.next == null)
                    this.tail = current;
                return;
            }
            current = current.next;
        }
    }

    /**
     * Reverse the list
     */
    public void reverse() {
        Node prev = null;
        Node current = this.head;
        this.tail = this.head;

        while (current != null) {
            Node next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        this.head = prev;
    }

    /**
     * Convert the list to a String for debugging
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Node temp = this.head;

        while (temp != null) {
            result.append(temp.data);
            if (temp.next != null)
                result.append(", ");
            temp = temp.next;
        }

        return result.toString();
    }
}