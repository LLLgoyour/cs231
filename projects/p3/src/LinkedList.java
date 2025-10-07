/*
 * file name: LinkedList.java
 * author: Jack Dai
 * last modified: 10/06/2025
 * purpose of this class:
 * A linked list implementation that supports any data type (T)
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements Iterable<T> {

    /**
     * Node inner class
     */
    public class Node<T> {
        public T data;
        public Node<T> next;

        public Node() {
            this.data = null;
            this.next = null;
        }

        public Node(T newData) {
            this.data = newData;
            this.next = null;
        }

        public Node(T newData, Node<T> newNext) {
            this.data = newData;
            this.next = newNext;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * constructor
     */
    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Add an item to the end of the list
     * 
     * @param item
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
     * @param item
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
     * 
     * @return the head node
     */
    public Node<T> getHead() {
        return this.head;
    }

    /**
     * Convenience add method that appends to the end of the list.
     */
    public void add(T item) {
        addLast(item);
    }

    /**
     * Insert an item at the specified index (0-based). If index == size, appends
     * to end.
     * Throws IndexOutOfBoundsException for invalid indices.
     * 
     * @param index
     * @param item
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
     * 
     * @return the size of the list
     */
    public int size() {
        return this.size;
    }

    /**
     * 
     * @param item
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
     * Remove the first occurrence of an item from the list
     * 
     * @param item
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
     * Remove and return the element at the given index. Throws
     * IndexOutOfBoundsException for invalid index.
     * 
     * @param index
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
     * Remove and return the last item in the list, or null if empty.
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
     * Return the last item in the list or null if empty.
     */
    public T getLast() {
        if (this.tail == null)
            return null;
        return this.tail.data;
    }

    /**
     * Return the element at the given index (0-based). Throws
     * IndexOutOfBoundsException
     * for invalid index.
     * 
     * @param index
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
     * Clear the list.
     */
    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * @return true if list is empty.
     */
    public boolean isEmpty() {
        return this.head == null;
    }

    /**
     * Reverse the list
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
     * Insert an item roughly in the middle (or at given position). If position <=0
     * inserts at head.
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
     * Queue-like convenience methods
     */
    public void offer(T item) {
        addLast(item);
    }

    /**
     * 
     * @return the head value
     */
    public T peek() {
        if (this.head == null)
            return null;
        return this.head.data;
    }

    /**
     * 
     * @return this will remove the first node
     */
    public T poll() {
        return removeFirst();
    }

    /**
     * Iterator support to allow for-each iteration.
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
     * Convert the list to a String
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        Node<T> temp = this.head;

        while (temp != null) {
            result.append(temp.data);
            if (temp.next != null)
                result.append(", ");
            temp = temp.next;
        }

        result.append("]");
        return result.toString();
    }
}
