/*
 * file name: AVLMap.java
 * author: Jack Dai
 * last modified: 11/02/2025
 * purpose: Implements a Map using an AVL self-balancing binary search tree
 */

import java.util.ArrayList;
import java.util.Comparator;

/**
 * AVLMap is a MapSet implementation backed by an AVL (self-balancing)
 * binary search tree. It keeps the tree approximately balanced after every
 * insertion and removal by maintaining node heights and performing
 * rotations when necessary. Keys are ordered either by a provided
 * {@link Comparator} or by their natural ordering (they must implement
 * {@link Comparable} in that case).
 *
 * @param <K> key type
 * @param <V> value type
 */
public class AVLMap<K, V> implements MapSet<K, V> {
    private static class Node<K, V> extends KeyValuePair<K, V> {
        private Node<K, V> left;
        private Node<K, V> right;
        private int height;

        public Node(K key, V value) {
            super(key, value);
            this.height = 1; // leaf
        }
    }

    private Node<K, V> root;
    private int size;
    private Comparator<K> comparator;

    /**
     * Construct an AVLMap that uses the provided comparator to order keys.
     * If {@code comparator} is {@code null} this implementation will assume
     * keys use their natural ordering (i.e., implement {@code Comparable}).
     *
     * @param comparator comparator used to compare keys, or {@code null}
     */
    public AVLMap(Comparator<K> comparator) {
        if (comparator != null) {
            this.comparator = comparator;
        } else {
            this.comparator = new Comparator<K>() {
                @Override
                public int compare(K o1, K o2) {
                    return ((Comparable<K>) o1).compareTo(o2);
                }
            };
        }
    }

    /**
     * Construct an AVLMap that orders keys by their natural ordering.
     * Keys must implement {@code Comparable<K>}.
     */
    public AVLMap() {
        this(null);
    }

    // Utility helpers for maintaining node heights and balances

    /**
     * Return the height of the given node (0 for {@code null}).
     *
     * @param n node to inspect (may be {@code null})
     * @return height of {@code n}, or 0 when {@code n} is {@code null}
     */
    private int height(Node<K, V> n) {
        return (n == null) ? 0 : n.height;
    }

    /**
     * Update {@code n.height} based on the heights of its children.
     * If {@code n} is {@code null} this method does nothing.
     *
     * @param n node whose height should be recomputed
     */
    private void updateHeight(Node<K, V> n) {
        if (n != null) {
            n.height = 1 + Math.max(height(n.left), height(n.right));
        }
    }

    /**
     * Compute the balance factor of {@code n} defined as
     * {@code height(n.left) - height(n.right)}. A balance factor outside
     * the range [-1, 1] indicates the subtree needs rebalancing.
     *
     * @param n node whose balance factor to compute
     * @return balance factor (left height minus right height); 0 for {@code null}
     */
    private int balanceFactor(Node<K, V> n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    /**
     * Perform a right rotation on the subtree rooted at {@code y} and
     * return the new subtree root. Used to restore AVL balance.
     *
     * @param y subtree root to rotate
     * @return new root of the rotated subtree
     */
    private Node<K, V> rotateRight(Node<K, V> y) {
        Node<K, V> x = y.left;
        Node<K, V> T2 = x.right;

        // rotation
        x.right = y;
        y.left = T2;

        // update heights
        updateHeight(y);
        updateHeight(x);

        return x;
    }

    /**
     * Perform a left rotation on the subtree rooted at {@code x} and
     * return the new subtree root. Used to restore AVL balance.
     *
     * @param x subtree root to rotate
     * @return new root of the rotated subtree
     */
    private Node<K, V> rotateLeft(Node<K, V> x) {
        Node<K, V> y = x.right;
        Node<K, V> T2 = y.left;

        // rotation
        y.left = x;
        x.right = T2;

        // update heights
        updateHeight(x);
        updateHeight(y);

        return y;
    }

    /**
     * Rebalance the subtree rooted at {@code node} if its balance factor is
     * outside the acceptable range. Handles the four AVL rotation cases
     * (LL, LR, RR, RL) and returns the new root of the subtree.
     *
     * @param node subtree root to rebalance
     * @return new root of the (possibly rotated) subtree
     */
    private Node<K, V> rebalance(Node<K, V> node) {
        if (node == null)
            return null;
        updateHeight(node);
        int bf = balanceFactor(node);
        // Left heavy
        if (bf > 1) {
            if (balanceFactor(node.left) < 0) {
                // LR
                node.left = rotateLeft(node.left);
            }
            // LL
            return rotateRight(node);
        }
        // Right heavy
        if (bf < -1) {
            if (balanceFactor(node.right) > 0) {
                // RL
                node.right = rotateRight(node.right);
            }
            // RR
            return rotateLeft(node);
        }
        return node;
    }

    /**
     * Associate the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced and returned. If {@code value} is {@code null}
     * this implementation does nothing and returns {@code null}.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to associate with the key
     * @return the previous value associated with {@code key}, or {@code null}
     *         if there was no mapping for {@code key}
     */
    @Override
    public V put(K key, V value) {
        if (value == null)
            return null;
        Holder<V> holder = new Holder<>();
        root = put(root, key, value, holder);
        if (holder.isNew)
            size++;
        return holder.old;
    }

    /**
     * Recursive helper for {@link #put(Object, Object)}. Inserts the key
     * into the subtree rooted at {@code node} (or replaces the existing
     * value) and returns the (possibly new) subtree root.
     *
     * @param node   current subtree root (may be {@code null})
     * @param key    key to insert
     * @param value  value to associate with the key
     * @param holder holder used to communicate whether insertion created a
     *               new node and to return a replaced value
     * @return root of the subtree after insertion and rebalancing
     */
    private Node<K, V> put(Node<K, V> node, K key, V value, Holder<V> holder) {
        if (node == null) {
            holder.isNew = true;
            return new Node<>(key, value);
        }
        int cmp = comparator.compare(key, node.getKey());
        if (cmp < 0) {
            node.left = put(node.left, key, value, holder);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value, holder);
        } else {
            // replace
            holder.old = node.getValue();
            node.setValue(value);
            holder.isNew = false;
            return node;
        }

        return rebalance(node);
    }

    /**
     * Small helper used to pass information out of recursive insert/remove
     * helpers: whether a node was newly created and the old value when a
     * replacement happened.
     */
    private static class Holder<V> {
        V old = null;
        boolean isNew = false;
    }

    /**
     * Return {@code true} if this map contains a mapping for the given key.
     *
     * @param key key whose presence is to be tested
     * @return {@code true} if the key exists in the map
     * @throws NullPointerException if {@code key} is {@code null}
     */
    @Override
    public boolean containsKey(K key) {
        if (key == null)
            throw new NullPointerException("AVLMap does not support null keys");
        return get(key) != null;
    }

    /**
     * Return the value associated with {@code key}, or {@code null} if no
     * mapping exists.
     *
     * @param key key whose associated value is to be returned
     * @return the value associated with {@code key}, or {@code null}
     * @throws NullPointerException if {@code key} is {@code null}
     */
    @Override
    public V get(K key) {
        if (key == null)
            throw new NullPointerException("AVLMap does not support null keys");
        Node<K, V> cur = root;
        while (cur != null) {
            int cmp = comparator.compare(key, cur.getKey());
            if (cmp < 0)
                cur = cur.left;
            else if (cmp > 0)
                cur = cur.right;
            else
                return cur.getValue();
        }
        return null;
    }

    /**
     * Remove the mapping for the specified key if it is present.
     *
     * @param key key whose mapping is to be removed
     * @return the previous value associated with {@code key}, or {@code null}
     *         if there was no mapping for {@code key}
     * @throws NullPointerException if {@code key} is {@code null}
     */
    @Override
    public V remove(K key) {
        if (key == null)
            throw new NullPointerException("AVLMap does not support null keys");
        Holder<V> holder = new Holder<>();
        root = remove(root, key, holder);
        if (holder.old != null)
            size--;
        return holder.old;
    }

    /**
     * Recursive helper for {@link #remove(Object)}. Removes the mapping for
     * {@code key} from the subtree rooted at {@code node} and returns the
     * new subtree root. The removed value (if any) is stored in {@code holder.old}.
     *
     * @param node   current subtree root (may be {@code null})
     * @param key    key to remove
     * @param holder holder used to return the removed value
     * @return root of the subtree after removal and rebalancing
     */
    private Node<K, V> remove(Node<K, V> node, K key, Holder<V> holder) {
        if (node == null)
            return null;
        int cmp = comparator.compare(key, node.getKey());
        if (cmp < 0) {
            node.left = remove(node.left, key, holder);
        } else if (cmp > 0) {
            node.right = remove(node.right, key, holder);
        } else {
            // found
            holder.old = node.getValue();
            // node with only one child or no child
            if (node.left == null)
                return node.right;
            else if (node.right == null)
                return node.left;

            // node with two children: get inorder successor (smallest in right)
            Node<K, V> succ = node.right;
            while (succ.left != null)
                succ = succ.left;
            // copy succ's data
            node.setValue(succ.getValue());
            // also need to replace key: since KeyValuePair.key is private with no setter,
            // easiest is to create replacement node
            // but to keep structure, we'll create new node with succ's key/value and assign
            // children appropriately below.
            K succKey = succ.getKey();
            // remove successor by key
            node.right = remove(node.right, succKey, new Holder<V>());
            // replace key by succKey
            // We must replace the key stored in node. KeyValuePair.key is private with no
            // setter; but existing BSTMap relied on keys immutable.
            // Workaround: create a new node to replace current node using successor's
            // key/value and current left/right children.
            Node<K, V> replacement = new Node<>(succKey, node.getValue());
            replacement.left = node.left;
            replacement.right = node.right;
            replacement.height = node.height; // will be updated by rebalance
            node = replacement;
        }

        return rebalance(node);
    }

    /**
     * Return an ArrayList containing every key in the map in ascending
     * (in-order) order.
     *
     * @return list of keys in sorted order
     */
    @Override
    public ArrayList<K> keySet() {
        ArrayList<K> out = new ArrayList<>();
        inorderKeys(root, out);
        return out;
    }

    /**
     * In-order traversal helper that appends each key to {@code out}.
     *
     * @param cur current node (may be {@code null})
     * @param out output list to append keys to
     */
    private void inorderKeys(Node<K, V> cur, ArrayList<K> out) {
        if (cur == null)
            return;
        inorderKeys(cur.left, out);
        out.add(cur.getKey());
        inorderKeys(cur.right, out);
    }

    /**
     * Return an ArrayList containing every value in the map. The order of
     * values corresponds to the order of {@link #keySet()} (in-order of keys).
     *
     * @return list of values in key-sorted order
     */
    @Override
    public ArrayList<V> values() {
        ArrayList<V> out = new ArrayList<>();
        inorderValues(root, out);
        return out;
    }

    /**
     * In-order traversal helper that appends each node's value to {@code out}.
     *
     * @param cur current node (may be {@code null})
     * @param out output list to append values to
     */
    private void inorderValues(Node<K, V> cur, ArrayList<V> out) {
        if (cur == null)
            return;
        inorderValues(cur.left, out);
        out.add(cur.getValue());
        inorderValues(cur.right, out);
    }

    /**
     * Return an ArrayList of KeyValuePair objects representing the key/value
     * mappings in this map. The order corresponds to {@link #keySet()}.
     *
     * @return list of map entries in key-sorted order
     */
    @Override
    public ArrayList<MapSet.KeyValuePair<K, V>> entrySet() {
        ArrayList<MapSet.KeyValuePair<K, V>> out = new ArrayList<>();
        inorderEntries(root, out);
        return out;
    }

    /**
     * In-order traversal helper that appends each node (as a KeyValuePair)
     * to {@code out}.
     *
     * @param cur current node (may be {@code null})
     * @param out output list to append entries to
     */
    private void inorderEntries(Node<K, V> cur, ArrayList<MapSet.KeyValuePair<K, V>> out) {
        if (cur == null)
            return;
        inorderEntries(cur.left, out);
        out.add(cur);
        inorderEntries(cur.right, out);
    }

    /**
     * Return the number of key/value mappings currently stored in the map.
     *
     * @return number of entries in the map
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Remove all mappings from the map. After this call {@link #size()}
     * will return 0 and the tree will be empty.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Return the height (maximum number of nodes on a root-to-leaf path)
     * of the AVL tree. For an empty tree this returns 0.
     *
     * @return height of the tree
     */
    @Override
    public int maxDepth() {
        return height(root);
    }

    /**
     * Helper for {@link #toString()}. Produces a rotated (right-first)
     * visual representation of the tree. Each node is printed on its own
     * line with tabs indicating depth.
     *
     * @param cur   current node
     * @param depth current depth used for indentation
     * @param sb    StringBuilder to append textual output to
     */
    private void toString(Node<K, V> cur, int depth, StringBuilder sb) {
        if (cur == null)
            return;
        toString(cur.right, depth + 1, sb);
        sb.append("\t".repeat(depth) + cur + "\n");
        toString(cur.left, depth + 1, sb);
    }

    /**
     * Return a multi-line string representation of the tree. Useful for
     * debugging and visual inspection.
     *
     * @return textual representation of the AVL tree
     */
    @Override
    public String toString() {
        if (size() == 0)
            return "(empty AVL)";
        StringBuilder sb = new StringBuilder();
        toString(root, 0, sb);
        return sb.toString();
    }

}
