/*
 * file name: BSTMap.java
 * author: Jack Dai
 * last modified: 10/31/2025
 * purpose of the class: Implements a Map using a binary search tree (BST)
 *                     to store key-value pairs.
 */

import java.util.ArrayList;
import java.util.Comparator;

public class BSTMap<K, V> implements MapSet<K, V> {
    /**
     * Internal node used by the BST. Extends {@link MapSet.KeyValuePair}
     * so it already stores a key and value. Each node keeps references
     * to its left and right children (may be null).
     */
    private static class Node<K, V> extends KeyValuePair<K, V> {
        private Node<K, V> left;
        private Node<K, V> right;

        public Node(K key, V value) {
            super(key, value);
            // left and right default to null
        }
    }

    private Node<K, V> root;
    private int size; // number of entries in the mapping

    private Comparator<K> comparator;

    /**
     * Construct a BSTMap using the given comparator to order keys. If
     * {@code comparator} is {@code null} a default comparator that casts
     * keys to {@link Comparable} will be used.
     *
     * @param comparator comparator used to compare keys; may be {@code null}
     */
    public BSTMap(Comparator<K> comparator) {
        // If a comparator is provided, use it; otherwise create a default
        // comparator that assumes K implements Comparable<K>.
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
     * Construct a BSTMap that uses the natural ordering of keys
     * (i.e. keys must implement {@code Comparable}).
     */
    public BSTMap() {
        // calls the default constructor
        this(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<K> keySet() {
        ArrayList<K> keys = new ArrayList<>();
        keySet(root, keys);
        return keys;
    }

    /**
     * Helper for {@link #keySet()}. Performs an in-order traversal to
     * collect keys in sorted order (least to greatest).
     *
     * @param cur    current subtree root (may be null)
     * @param output list to append keys to
     */
    private void keySet(Node<K, V> cur, ArrayList<K> output) {
        if (cur == null)
            return;
        keySet(cur.left, output);
        output.add(cur.getKey());
        keySet(cur.right, output);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * {@inheritDoc}
     *
     * Returns an ArrayList of each {@code KeyValuePair} in the map in the
     * same order as {@link #keySet()} (i.e. in-order traversal of the BST).
     */
    @Override
    public ArrayList<MapSet.KeyValuePair<K, V>> entrySet() {
        ArrayList<MapSet.KeyValuePair<K, V>> out = new ArrayList<>();
        entrySet(root, out);
        return out;
    }

    /**
     * Helper for {@link #entrySet()}. Performs an in-order traversal and
     * appends each {@link MapSet.KeyValuePair} to {@code out} in the same
     * order as {@link #keySet()}.
     *
     * @param cur current subtree root (may be null)
     * @param out list to append entries to
     */
    private void entrySet(Node<K, V> cur, ArrayList<MapSet.KeyValuePair<K, V>> out) {
        if (cur == null)
            return;
        entrySet(cur.left, out);
        out.add(cur);
        entrySet(cur.right, out);
    }

    /**
     * {@inheritDoc}
     *
     * Returns an ArrayList of the values in the same order as {@link #keySet()}.
     */
    @Override
    public ArrayList<V> values() {
        ArrayList<V> out = new ArrayList<>();
        values(root, out);
        return out;
    }

    /**
     * Helper for {@link #values()}. Traverses the tree in-order and appends
     * each node's value to {@code out} following the same order as
     * {@link #keySet()}.
     *
     * @param cur current subtree root (may be null)
     * @param out list to append values to
     */
    private void values(Node<K, V> cur, ArrayList<V> out) {
        if (cur == null)
            return;
        values(cur.left, out);
        out.add(cur.getValue());
        values(cur.right, out);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(K key) {
        return get(key, root);
    }

    /**
     * Helper method for get(V key)
     * 
     * @param key
     * @param cur
     * @return
     */
    /**
     * Recursive helper for {@link #get(Object)}.
     *
     * @param key the key to search for (must be non-null)
     * @param cur the current node being inspected (may be null)
     * @return the associated value, or {@code null} if not found
     * @throws NullPointerException if {@code key} is {@code null}
     */
    private V get(K key, Node<K, V> cur) {
        if (key == null) {
            throw new NullPointerException("BSTMap does not support null keys");
        }
        if (cur == null) {
            return null;
        }

        int cmp = comparator.compare(key, cur.getKey());
        if (cmp < 0) {
            return get(key, cur.left);
        } else if (cmp > 0) {
            return get(key, cur.right);
        } else {
            // found
            return cur.getValue();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            // can also just return false
            throw new NullPointerException("BSTMap does not support null keys");
        }
        return containsKey(key, root);
    }

    /**
     * Helper method for containsKey(K key) method
     * 
     * @param key
     * @param cur
     * @return
     */
    /**
     * Recursive helper for {@link #containsKey(Object)}.
     *
     * @param key the key to search for (non-null)
     * @param cur current node being inspected (may be null)
     * @return {@code true} if the key exists in the subtree rooted at {@code cur}
     */
    private boolean containsKey(K key, Node<K, V> cur) {
        if (cur == null) {
            return false;
        }
        int cmp = comparator.compare(key, cur.getKey());
        if (cmp < 0) {
            return containsKey(key, cur.left);
        } else if (cmp > 0) {
            return containsKey(key, cur.right);
        } else {
            return true;
        }
    }

    /**
     * {@inheritDoc}
     *
     * Returns the length of the longest path from the root to a leaf.
     */
    @Override
    public int maxDepth() {
        return maxDepth(root);
    }

    /**
     * Recursive helper for {@link #maxDepth()}. Computes the maximum depth
     * of the subtree rooted at {@code cur}.
     *
     * @param cur current node (may be null)
     * @return maximal root-to-leaf path length in this subtree
     */
    private int maxDepth(Node<K, V> cur) {
        if (cur == null)
            return 0;
        int l = maxDepth(cur.left);
        int r = maxDepth(cur.right);
        return 1 + Math.max(l, r);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V put(K key, V value) {
        if (value == null) {
            return null;
        }

        if (root == null) {
            root = new Node<K, V>(key, value);
            size = 1;
            return null;
        }

        return put(key, value, root);
    }

    /**
     * Helper method for put(K key, V value)
     * 
     * @param key
     * @param value
     * @param cur
     * @return
     */
    /**
     * Recursive helper for {@link #put(Object, Object)}. Inserts the key
     * into the subtree rooted at {@code cur} (or replaces the existing
     * value) and returns the previous value if replaced, otherwise {@code null}.
     *
     * @param key   key to insert (non-null)
     * @param value value to associate with the key (non-null)
     * @param cur   current node (must be non-null when called)
     * @return previous value if key existed, otherwise {@code null}
     */
    private V put(K key, V value, Node<K, V> cur) {
        int cmp = comparator.compare(key, cur.getKey());
        if (cmp < 0) {
            if (cur.left != null) {
                // return the recursive call's result to the left
                return put(key, value, cur.left);
            } else {
                // insert a new Node with the given KeyValuePair to the left of cur
                cur.left = new Node<>(key, value);
                size++;
                return null;
            }
        } else if (cmp > 0) {
            if (cur.right != null) {
                // return the recursive call's result to the right
                return put(key, value, cur.right);
            } else {
                // insert a new Node with the given KeyValuePair to the right of cur
                cur.right = new Node<>(key, value);
                size++;
                return null;
            }
        } else { // in this case, cur.getKey() == key
            // Set the value of cur's KeyValuePair to be the given value and return the old
            // one
            V old = cur.getValue();
            cur.setValue(value);
            return old;
        }
    }

    /**
     * {@inheritDoc}
     *
     * This implementation removes the node with the given key if present
     * and returns its associated value. If the node has two children, the
     * in-order successor is used to replace it.
     *
     * @throws NullPointerException if {@code key} is {@code null}
     */
    @Override
    public V remove(K key) {
        if (key == null)
            throw new NullPointerException("BSTMap does not support null keys");
        if (root == null)
            return null;

        Node<K, V> parent = null;
        Node<K, V> cur = root;
        int cmp = 0;
        while (cur != null) {
            cmp = comparator.compare(key, cur.getKey());
            if (cmp < 0) {
                parent = cur;
                cur = cur.left;
            } else if (cmp > 0) {
                parent = cur;
                cur = cur.right;
            } else {
                break;
            }
        }

        if (cur == null)
            return null; // not found

        V oldValue = cur.getValue();

        // Case: at most one child
        if (cur.left == null || cur.right == null) {
            Node<K, V> replacement = (cur.left != null) ? cur.left : cur.right;
            if (parent == null) {
                root = replacement;
            } else if (parent.left == cur) {
                parent.left = replacement;
            } else {
                parent.right = replacement;
            }
        } else {
            // Two children: find successor (smallest in right subtree)
            Node<K, V> succParent = cur;
            Node<K, V> succ = cur.right;
            while (succ.left != null) {
                succParent = succ;
                succ = succ.left;
            }

            // Detach successor from its parent
            if (succParent.left == succ) {
                succParent.left = succ.right;
            } else {
                succParent.right = succ.right; // succParent == cur case
            }

            // Replace cur with succ
            succ.left = cur.left;
            succ.right = cur.right == succ ? succ.right : cur.right;

            if (parent == null) {
                root = succ;
            } else if (parent.left == cur) {
                parent.left = succ;
            } else {
                parent.right = succ;
            }
        }

        size--;
        return oldValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Helper for {@link #toString()}. Performs a reverse in-order traversal
     * (right, node, left) and appends each node's string representation
     * prefixed with tabs equal to its depth.
     *
     * @param cur      current node (may be null)
     * @param curDepth current depth of {@code cur}
     * @param sb       StringBuilder to append output to
     */
    private void toString(Node<K, V> cur, int curDepth, StringBuilder sb) {
        if (cur == null)
            return;

        toString(cur.right, curDepth + 1, sb);
        sb.append("\t".repeat(curDepth) + cur + "\n");
        toString(cur.left, curDepth + 1, sb);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if (size() == 0)
            return "(empty BST)";
        StringBuilder sb = new StringBuilder();
        toString(this.root, 0, sb);
        return sb.toString();
    }
}