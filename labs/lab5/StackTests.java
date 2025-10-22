/*
file name:      StackTests.java
Authors:        Max Bender and Ike Lage
last modified:  04/01/2025

How to run:     java -ea StackTests
*/

public class StackTests {

    /**
     * Stack Tests
     */
    public static double stackTests() {

        double testScore = 0.;
        {
            /**
             * Standard Test
             */
            LinkedList<Integer> stack = new LinkedList<>();
            for (int i : new int[] { 0, 1, 2 })
                stack.push(i);

            // Check for error in size-tracking in LinkedList::push
            if (stack.size() == 3)
                testScore += 1;

            for (int i : new int[] { 2, 1, 0 }) {
                // Check for error in LinkedList::peek
                if (stack.peek().equals(i))
                    testScore += 1 / 3.;

                Integer popped = stack.pop();
                // Check for an error in LinkedList::pop/push
                if (popped != null && popped.equals(i))
                    testScore += 1 / 3.;

                // Check for error in size-tracking in LinkedList::pop/push
                if (stack.size() == i)
                    testScore += 1 / 3.;
            }

            /**
             * Test to confirm runtime
             */
            LinkedList<Integer> bigStack = new LinkedList<>();
            for (int i = 0; i < 1000000; i++)
                bigStack.push(i);
            for (int i = 0; i < 1000000; i++)
                bigStack.pop();

            testScore += 2;

        }
        return testScore;
    }

    public static void main(String[] args) {
        System.out.println(stackTests() + "/6");
    }

}