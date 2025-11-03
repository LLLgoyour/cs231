/*
 * file name: AVLMapTester.java
 * author: Jack Dai
 * last modified: 11/02/2025
 * purpose of the class: tester for AVLMap.java
 */

public class AVLMapTester {

    public static void testInsertBalance() {
        System.out.println("-".repeat(30) + "\nAVL Insert/Balance Test:");
        AVLMap<Integer, String> m = new AVLMap<>();
        for (int i = 1; i <= 7; i++) {
            m.put(i, "" + i);
        }
        System.out.println("Tree structure (should be balanced):\n" + m);
        System.out.println("size: " + m.size() + " expected: 7");
        System.out.println("maxDepth (height): " + m.maxDepth() + " expected: <= 4");
        System.out.println("Entry set: " + m.entrySet());
        System.out.println("-".repeat(30));
    }

    public static void testRemove() {
        System.out.println("-".repeat(30) + "\nAVL Remove Test:");
        AVLMap<Integer, String> m = new AVLMap<>();
        int[] vals = { 4, 2, 6, 1, 3, 5, 7 };
        for (int v : vals)
            m.put(v, "" + v);
        System.out.println("Before remove:\n" + m);
        m.remove(4);
        System.out.println("After removing 4:\n" + m);
        System.out.println("size: " + m.size() + " expected: 6");
        System.out.println("maxDepth (height): " + m.maxDepth());
        System.out.println("-".repeat(30));
    }

    public static void main(String[] args) {
        testInsertBalance();
        testRemove();
    }
}
