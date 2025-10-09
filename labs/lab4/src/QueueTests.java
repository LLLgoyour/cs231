/*
file name:      QueueTests.java
Authors:        Ike Lage
last modified:  03/12/2025

How to run:     java -ea QueueTests
*/

public class QueueTests {

    public static double linkedListTests() {
        double score = 0.;

        // case 1: testing LinkedList()
        {
            // setup
            LinkedList<Integer> ll = new LinkedList<Integer>();

            // test
            if (ll != null) {
                score += 0.25;
            }
        }

        // case 2: testing add(T item)
        {
            // setup
            LinkedList<Integer> ll = new LinkedList<Integer>();
            for (int i = 0; i < 5; i++) {
                ll.add(i);
            }

            // test
            if (ll.size() == 5) {
                score += 0.25;
            }
        }

        // case 3: testing add(int index, T item)
        {
            // setup
            LinkedList<Integer> ll = new LinkedList<Integer>();
            ll.add(0, 1);
            ll.add(1, 2);
            ll.add(1, 3);
            ll.add(0, 4);
            ll.add(4, 5);
            ll.add(3, 6);

            // test
            if (ll.size() == 6) {
                score += 0.5;
            }
        }

        // case 4: testing clear()
        {
            // setup
            LinkedList<Integer> ll = new LinkedList<Integer>();
            for (int i : new int[] { 1, 2, 3 }) {
                ll.add(i);
            }
            ll.clear();

            // test
            if (ll.size() == 0) {
                score += 0.5;
            }
        }

        // case 5: testing contains()
        {
            // setup
            LinkedList<Integer> ll = new LinkedList<Integer>();
            for (int i = 0; i < 3; i++) {
                ll.add(2 * i);
            }

            // test
            if ((ll.contains(0)) && (ll.contains(4)) && (!ll.contains(3))) {
                score += 0.5;
            }
        }

        // case 6: testing equals()
        {
            // setup
            LinkedList<Integer> list1 = new LinkedList<Integer>();
            LinkedList<Integer> list2 = new LinkedList<Integer>();
            LinkedList<Integer> list3 = new LinkedList<Integer>();
            LinkedList<Integer> list4 = new LinkedList<Integer>();
            for (int i = 0; i < 3; i++) {
                list1.add(i);
                list2.add(i);
                list3.add(i);
                list4.add(i);
            }
            list3.add(4);
            list4.add(5);

            // test
            if (list1.equals(list2) && !list2.equals(list3) && !list3.equals(list4) && !list4.equals("Hello")) {
                score += 0.5;
            }
        }

        // case 7: testing get()
        {
            // setup
            LinkedList<Integer> ll = new LinkedList<Integer>();
            for (int i = 0; i < 5; i++) {
                ll.add(4 - i);
            }

            // test
            if (ll.get(0) == 0 && ll.get(3) == 3 && ll.get(4) == 4) {
                score += 0.5;
            }
        }

        // case 8: testing isEmpty()
        {
            // setup
            LinkedList<Integer> list1 = new LinkedList<Integer>();
            LinkedList<Integer> list2 = new LinkedList<Integer>();
            list2.add(5);

            // test
            if (list1.isEmpty() && !list2.isEmpty()) {
                score += 0.5;
            }
        }

        // case 9: testing remove()
        {
            // setup
            LinkedList<Integer> ll = new LinkedList<Integer>();
            for (int i = 0; i < 5; i++) {
                ll.add(4 - i);
            }

            int remove0 = ll.remove();
            int remove1 = ll.remove();

            // test
            if (remove0 == 0 && remove1 == 1) {
                score += 0.5;
            }
        }

        // case 10: testing remove(int index)
        {
            // setup
            LinkedList<Integer> ll = new LinkedList<Integer>();
            for (int i = 0; i < 8; i++) {
                ll.add(7 - i);
            }
            int remove0 = ll.remove(0);
            int remove3 = ll.remove(3);
            int remove5 = ll.remove(5);

            // test
            if (remove0 == 0 && remove3 == 4 && remove5 == 7) {
                score += 0.5;
            }
        }

        // case 11: testing add(int index, T item) and iterator()
        {
            // setup
            LinkedList<Integer> ll = new LinkedList<Integer>();
            ll.add(0, 1);
            ll.add(1, 4);
            ll.add(1, 2);
            ll.add(0, 0);
            ll.add(4, 5);
            ll.add(3, 3);

            // test
            int counter = 0;
            int total = 0;
            for (int val : ll) {
                if (val == counter) {
                    total += 1;
                }
                counter++;
            }
            if (total == 6) {
                score += 0.5;
            }

        }
        return score;
    }

    public static double queueTests() {

        double testScore = linkedListTests() / 5.0; // Your linked list should still pass the LL tests -- 1 point

        // case 12: testing offer, peek and poll
        {
            // setup
            LinkedList<Integer> ll = new LinkedList<Integer>();
            ll.offer(0);
            ll.offer(1);
            ll.offer(2);
            ll.offer(3);

            // test
            int counter = 0;
            while (ll.peek() != null) {
                int val = ll.poll();
                if (val == counter) {
                    testScore += 0.25;
                }
                ;
                counter++;
            }
        }

        // case 13: testing for constant offer and poll operations
        {
            // setup
            LinkedList<Integer> ll = new LinkedList<Integer>();
            int bigNum = 1000000;
            for (int i = 0; i < bigNum; i++) {
                ll.offer(i);
            }

            // test
            int counter = 0;
            while (ll.peek() != null) {
                int val = ll.poll();
                assert val == counter : "Error in Queue implementation!";
                counter++;
            }
            if (counter == bigNum) {
                testScore += 2;
            }
            ;
        }

        return testScore;
    }

    public static void main(String[] args) {
        System.out.println(queueTests());
    }

}