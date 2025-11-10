import java.lang.Math;
import java.util.ArrayList;
import java.util.Objects;

public class HashMap<K, V> {

    // Inner class representing a node in the hash table's linked list
    protected class HashNode {
        K key; // The key for the hash node
        V value; // The value associated with the key
        HashNode next; // Reference to the next node in the list (for collision handling)

        // Constructor to initialize the key-value pair
        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        // Overridden toString() to print key-value pairs
        public String toString() {
            return (String) this.key + ":" + (String) this.value;
        }
    }

    private Object[] hashTable; // Array to store hash table buckets
    private int tableSize; // Size of hash table
    private int filled; // Number of filled slots in the hash table

    // Constructor to initialize the hash table with the default size
    public HashMap() {
        this.tableSize = 16;
        this.filled = 0;
        this.hashTable = new Object[tableSize];
    }

    // Method to insert or update a key-value pair in the hash table
    public void put(K key, V value) {

        // Resize the table if load factor exceeds a threshold (1/3 of table size)
        if (filled >= tableSize / 3) {
            // Collect all entries

            // Double the table size
            // Create a new table

            // Reinsert all entries into the new larger table
        }

        int position = hash(key); // Find the position based on the key's hash code

        HashNode current = (HashNode) hashTable[position];
        if (current == null) {
            // If no entry exists at this position, create a new node
            hashTable[position] = new HashNode(key, value);
            filled++;
        } else {
            // Handle collisions by traversing the linked list
            while (current.next != null && !current.key.equals(key)) {
                current = current.next;
            }
            if (current.key.equals(key)) {
                // Update the value if the key already exists
                current.value = value;
            } else {
                // Append a new node if the key is unique
                current.next = new HashNode(key, value);
                filled++;
            }
        }
    }

    // Hash function to determine the position based on the key's hash code
    private int hash(K key) {
        return Math.abs(key.hashCode()) % this.tableSize;
    }

    // Overridden toString() to print all key-value pairs in the hash table
    public String toString() {
        String result = "";

        for (int i = 0; i < tableSize; i++) {
            HashNode node = (HashNode) hashTable[i];
            while (node != null) {
                result += (String) node.key + "=" + (String) node.value + ", ";
                node = node.next;
            }
        }
        return result;
    }

    // Method to check if a key exists in the hash table
    public boolean contains(K key) {
        return find(key) != null;
    }

    // Method to retrieve a value based on its key
    public V get(K key) {
        HashNode node = find(key);
        return node == null ? null : node.value;
    }

    // Method to find and return the node for a given key
    public HashNode find(K key) {
        int position = hash(key);
        // access the bucket at the computed position
        HashNode current = (HashNode) hashTable[position];
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    // Method to clear the hash table
    public void clear() {
        this.hashTable = new Object[tableSize];
    }

    // Method to remove a key-value pair by key
    public boolean remove(K key) {
        int position = hash(key);
        // Traverse the linked list to find the node
        HashNode current = (HashNode) hashTable[position];
        HashNode prev = null;

        while (current != null && !Objects.equals(current.key, key)) {
            prev = current;
            current = current.next;
        }

        // Key not found
        if (current == null)
            return false;

        // Remove the head node
        if (prev == null) {
            hashTable[position] = current.next;
        } else {
            // Remove node in the middle or end
            prev.next = current.next;
        }
        filled--;
        return true;
    }

    // Method to get all keys in the hash table
    public ArrayList<K> keySet() {
        ArrayList<K> results = new ArrayList<>();
        for (int i = 0; i < tableSize; i++) {
            HashNode node = (HashNode) hashTable[i];
            while (node != null) {
                results.add(node.key);
                node = node.next;
            }
        }
        return results;
    }

    // Method to get all values in the hash table
    public ArrayList<V> values() {
        ArrayList<V> results = new ArrayList<>();
        for (int i = 0; i < tableSize; i++) {
            HashNode node = (HashNode) hashTable[i];
            while (node != null) {
                results.add(node.value);
                node = node.next;
            }
        }
        return results;
    }

    // Method to get all key-value pairs in the hash table
    public ArrayList<HashNode> entrySet() {
        ArrayList<HashNode> results = new ArrayList<>();
        for (int i = 0; i < tableSize; i++) {
            HashNode node = (HashNode) hashTable[i];
            while (node != null) {
                results.add(node);
                node = node.next;
            }
        }
        return results;
    }

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("one", "ONE");
        map.put("two", "TWO");
        map.put("three", "THREE");
        map.put("four", "FOUR");
        map.put("five", "FIVE");

        System.out.println(map);
    }
}