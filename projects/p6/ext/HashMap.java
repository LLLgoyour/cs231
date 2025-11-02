/*
 * file name: HashMap.java
 * author: Jack Dai
 * last modified: 10/31/2025
 * purpose of the class: Implements a Map using a hash table with separate chaining
 */

import java.util.ArrayList;

public class HashMap<K, V> implements MapSet<K, V> {
    /**
     * Node stored in each bucket. Acts as a singly-linked list node
     * and extends KeyValuePair so it stores a key and a value.
     */
    private static class Node<K, V> extends KeyValuePair<K, V> {
        private Node<K, V> next;

        public Node(K key, V value) {
            super(key, value);
        }
    }

    private static final int DEFAULT_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    private Node<K, V>[] root;
    private int size;

    private double maxLoadFactor;

    public HashMap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Construct a HashMap with the default initial capacity and load factor.
     */

    public HashMap(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Construct a HashMap with a specific initial capacity and default load factor.
     *
     * @param capacity initial number of buckets
     */

    @SuppressWarnings("unchecked")
    public HashMap(int capacity, double maxLoadFactor) {
        this.maxLoadFactor = maxLoadFactor;
        this.size = 0;
        // create generic array
        this.root = (Node<K, V>[]) new Node[capacity];
    }

    /**
     * Construct a HashMap with a specific initial capacity and load factor.
     *
     * @param capacity      initial number of buckets
     * @param maxLoadFactor maximal load factor before resizing
     */

    /**
     * Returns the number of buckets (capacity) of the internal array.
     *
     * @return the length of the bucket array, or 0 if uninitialized
     */
    public int capacity() {
        return (root == null) ? 0 : root.length;
    }

    /**
     * Compute the bucket index for the given key using its hashCode.
     *
     * @param key the key to hash (must be non-null)
     * @return index in the bucket array
     */
    private int hash(K key) {
        return Math.abs(key.hashCode() % capacity());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * {@inheritDoc}
     *
     * This implementation resets the table to the default capacity and
     * default load factor.
     */
    @Override
    public void clear() {
        this.size = 0;
        this.maxLoadFactor = DEFAULT_LOAD_FACTOR;
        @SuppressWarnings("unchecked")
        Node<K, V>[] newRoot = (Node<K, V>[]) new Node[DEFAULT_CAPACITY];
        this.root = newRoot;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced and returned. If {@code value} is {@code null},
     * this implementation does nothing and returns {@code null}.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the value to associate with the key
     * @return the previous value associated with {@code key}, or {@code null}
     *         if there was no mapping for {@code key}
     * @throws NullPointerException if {@code key} is {@code null}
     */
    @Override
    public V put(K key, V value) {
        if (value == null)
            return null;
        if (key == null)
            throw new NullPointerException("HashMap does not support null keys");

        if (root == null) {
            // initialize with default capacity
            @SuppressWarnings("unchecked")
            Node<K, V>[] newRoot = (Node<K, V>[]) new Node[DEFAULT_CAPACITY];
            root = newRoot;
        }

        int idx = hash(key);
        Node<K, V> cur = root[idx];
        while (cur != null) {
            if (cur.getKey().equals(key)) {
                V old = cur.getValue();
                cur.setValue(value);
                return old;
            }
            cur = cur.next;
        }

        // insert at head
        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = root[idx];
        root[idx] = newNode;
        size++;

        // resize if needed
        if (size > maxLoadFactor * capacity()) {
            resize(capacity() * 2);
        }

        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @throws NullPointerException if {@code key} is {@code null}
     */
    @Override
    public V get(K key) {
        if (key == null)
            throw new NullPointerException("HashMap does not support null keys");
        if (root == null)
            return null;
        int idx = hash(key);
        Node<K, V> cur = root[idx];
        while (cur != null) {
            if (cur.getKey().equals(key))
                return cur.getValue();
            cur = cur.next;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @return an ArrayList containing every key in the table (no particular order)
     */
    @Override
    public ArrayList<K> keySet() {
        ArrayList<K> out = new ArrayList<>();
        if (root == null)
            return out;
        for (int i = 0; i < capacity(); i++) {
            Node<K, V> cur = root[i];
            while (cur != null) {
                out.add(cur.getKey());
                cur = cur.next;
            }
        }
        return out;
    }

    /**
     * {@inheritDoc}
     *
     * The order corresponds to the order of {@link #keySet()}.
     */
    @Override
    public ArrayList<V> values() {
        ArrayList<V> out = new ArrayList<>();
        if (root == null)
            return out;
        for (int i = 0; i < capacity(); i++) {
            Node<K, V> cur = root[i];
            while (cur != null) {
                out.add(cur.getValue());
                cur = cur.next;
            }
        }
        return out;
    }

    /**
     * {@inheritDoc}
     *
     * The order corresponds to the order of {@link #keySet()}.
     */
    @Override
    public ArrayList<MapSet.KeyValuePair<K, V>> entrySet() {
        ArrayList<MapSet.KeyValuePair<K, V>> out = new ArrayList<>();
        if (root == null)
            return out;
        for (int i = 0; i < capacity(); i++) {
            Node<K, V> cur = root[i];
            while (cur != null) {
                out.add(cur);
                cur = cur.next;
            }
        }
        return out;
    }

    /**
     * {@inheritDoc}
     *
     * For a hash table this returns the size of the largest bucket
     * (i.e. the maximum chain length).
     */
    @Override
    public int maxDepth() {
        if (root == null)
            return 0;
        int max = 0;
        for (int i = 0; i < capacity(); i++) {
            int count = 0;
            Node<K, V> cur = root[i];
            while (cur != null) {
                count++;
                cur = cur.next;
            }
            if (count > max)
                max = count;
        }
        return max;
    }

    /**
     * {@inheritDoc}
     *
     * This implementation delegates to {@link #get(Object)} and therefore
     * treats a mapping to {@code null} as absent (this implementation does
     * not allow {@code null} values).
     */
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * {@inheritDoc}
     *
     * @throws NullPointerException if {@code key} is {@code null}
     */
    @Override
    public V remove(K key) {
        if (key == null)
            throw new NullPointerException("HashMap does not support null keys");
        if (root == null)
            return null;
        int idx = hash(key);
        Node<K, V> cur = root[idx];
        Node<K, V> prev = null;
        while (cur != null) {
            if (cur.getKey().equals(key)) {
                V old = cur.getValue();
                if (prev == null) {
                    root[idx] = cur.next;
                } else {
                    prev.next = cur.next;
                }
                size--;
                // shrink if too sparse
                if (capacity() > DEFAULT_CAPACITY && size < (maxLoadFactor * capacity()) / 4) {
                    resize(Math.max(DEFAULT_CAPACITY, capacity() / 2));
                }
                return old;
            }
            prev = cur;
            cur = cur.next;
        }
        return null;
    }

    /**
     * Returns a multi-line string representation of the hash table; each
     * line shows the contents of a bucket. Useful for debugging.
     *
     * @return a string representation of this HashMap
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < this.capacity(); i++) {
            Node<K, V> node = this.root[i];
            output.append("bin ").append(i).append(": ");
            while (node != null) {
                output.append(node.toString()).append(" | ");
                node = node.next;
            }
            output.append("\n");
        }
        return output.toString();
    }

    @SuppressWarnings("unchecked")
    /**
     * Resize the internal bucket array and rehash all entries into the
     * new table. This is called automatically when growing or shrinking.
     *
     * @param newCapacity the new bucket array size
     */
    private void resize(int newCapacity) {
        Node<K, V>[] old = root;
        root = (Node<K, V>[]) new Node[newCapacity];
        size = 0;
        if (old == null)
            return;
        for (int i = 0; i < old.length; i++) {
            Node<K, V> cur = old[i];
            while (cur != null) {
                put(cur.getKey(), cur.getValue());
                cur = cur.next;
            }
        }
    }

}