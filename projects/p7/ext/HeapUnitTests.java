/*
 * file name: HeapUnitTests.java
 * author: Jack Dai
 * last modified: 11/19/2025
 * purpose of the class:
 * Additional Heap Tests that help debug the Heap class.
 */

import java.util.Comparator;

public class HeapUnitTests {

    static class Node {
        int key;
        String id;

        Node(int key, String id) {
            this.key = key;
            this.id = id;
        }

        public String toString() {
            return id + ":" + key;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Node)) {
                return false;
            }
            Node n = (Node) o;
            return this.id.equals(n.id) && this.key == n.key;
        }
    }

    public static void main(String[] args) {
        int passed = 0;
        int total = 0;

        // Test 1: peek/poll on empty
        total++;
        Heap<Integer> h1 = new Heap<>((a, b) -> a - b);
        boolean t1 = h1.peek() == null && h1.poll() == null && h1.size() == 0;
        System.out.println("Test 1 (empty peek/poll): " + (t1 ? "PASSED" : "FAILED"));
        if (t1) {
            passed++;
        }
        // Test 2: offer/poll ordering
        total++;
        Heap<Integer> h2 = new Heap<>((a, b) -> a - b);
        for (int i = 5; i >= 0; i--) {
            h2.offer(i);
        }
        boolean t2 = true;
        for (int i = 0; i <= 5; i++) {
            Integer p = h2.poll();
            if (p == null || p != i) {
                t2 = false;
                break;
            }
        }
        System.out.println("Test 2 (ordering): " + (t2 ? "PASSED" : "FAILED"));
        if (t2) {
            passed++;
        }

        // Test 3: duplicates
        total++;
        Heap<Integer> h3 = new Heap<>((a, b) -> a - b);
        h3.offer(3);
        h3.offer(1);
        h3.offer(3);
        h3.offer(2);
        boolean t3 = h3.size() == 4 && h3.poll() == 1 && h3.poll() == 2 && h3.poll() == 3 && h3.poll() == 3;
        System.out.println("Test 3 (duplicates): " + (t3 ? "PASSED" : "FAILED"));
        if (t3) {
            passed++;
        }

        // Test 4: max-heap flag
        total++;
        Heap<Integer> h4 = new Heap<>((a, b) -> a - b, true); // max-heap
        h4.offer(1);
        h4.offer(5);
        h4.offer(3);
        boolean t4 = (h4.poll() == 5 && h4.poll() == 3 && h4.poll() == 1);
        System.out.println("Test 4 (max-heap): " + (t4 ? "PASSED" : "FAILED"));
        if (t4) {
            passed++;
        }

        // Test 5: updatePriority decreases key (should bubble up)
        total++;
        Comparator<Node> nodeComp = (a, b) -> Integer.compare(a.key, b.key);
        Heap<Node> h5 = new Heap<>(nodeComp);
        Node na = new Node(10, "a");
        Node nb = new Node(20, "b");
        Node nc = new Node(30, "c");
        h5.offer(nc);
        h5.offer(nb);
        h5.offer(na); // ordering by key: a(10), b(20), c(30)
        // decrease c to 5
        nc.key = 5;
        h5.updatePriority(nc);
        boolean t5 = h5.poll() == nc && h5.poll() == na && h5.poll() == nb;
        System.out.println("Test 5 (updatePriority bubbleUp): " + (t5 ? "PASSED" : "FAILED"));
        if (t5) {
            passed++;
        }

        // Test 6: updatePriority increases key (should bubble down)
        total++;
        Heap<Node> h6 = new Heap<>(nodeComp);
        na = new Node(10, "a");
        nb = new Node(20, "b");
        nc = new Node(30, "c");
        h6.offer(na);
        h6.offer(nb);
        h6.offer(nc);
        // increase a to 100
        na.key = 100;
        h6.updatePriority(na);
        boolean t6 = h6.poll() == nb && h6.poll() == nc && h6.poll() == na; // b(20), c(30), a(100)
        System.out.println("Test 6 (updatePriority bubbleDown): " + (t6 ? "PASSED" : "FAILED"));
        if (t6) {
            passed++;
        }

        // Test 7: updatePriority on non-existent item does nothing
        total++;
        Heap<Integer> h7 = new Heap<>((a, b) -> a - b);
        h7.offer(1);
        h7.offer(2);
        h7.updatePriority(999); // not present
        boolean t7 = (h7.poll() == 1 && h7.poll() == 2);
        System.out.println("Test 7 (updatePriority missing item): " + (t7 ? "PASSED" : "FAILED"));
        if (t7) {
            passed++;
        }

        System.out.println();
        System.out.println("HeapUnitTests: passed " + passed + " / " + total);
    }
}
