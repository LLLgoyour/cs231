package labs.lab1.src;

/*
file name:      ArrayListTests.java
Authors:        Ike Lage
last modified:  02/18/25

How to run:     java -ea ArrayListTests
*/

public class ArrayListTests {

    public static void arrayListTests() {

        // case 1: testing ArrayList()
        {
            // setup
            ArrayList<Integer> al = new ArrayList<Integer>();

            // verify
            System.out.println(al + " != null");

            // test
            assert al != null : "Error in ArrayList::ArrayList()";
        }

        // case 2: testing add(T item)
        {
            // setup
            ArrayList<Integer> al = new ArrayList<Integer>();
            for (int i = 0; i < 5; i++) {
                al.add(i);
            }

            // verify
            System.out.println(al.size() + " == 5");

            // test
            assert al.size() == 5 : "Error in ArrayList::add(T item) or ArrayList::size()";
        }

        // case 3: testing get()
        {
            // setup
            ArrayList<Integer> al = new ArrayList<Integer>();
            for (int i = 0; i < 5; i++) {
                al.add(i);
            }

            // verify
            System.out.println(al.get(0) + " == 0");
            System.out.println(al.get(3) + " == 3");
            System.out.println(al.get(4) + " == 4");

            // test
            assert al.get(0) == 0 : "Error in ArrayList::get(int index)";
            assert al.get(3) == 3 : "Error in ArrayList::get(int index)";
            assert al.get(4) == 4 : "Error in ArrayList::get(int index)";
        }

        // case 4: testing set(int index, T item)
        {
            // setup
            ArrayList<Integer> al = new ArrayList<Integer>();
            for (int i = 0; i < 5; i++) {
                al.add(i);
            }

            // verify
            System.out.println(al.get(0) + " == 0");
            System.out.println(al.get(3) + " == 3");
            System.out.println(al.get(4) + " == 4");

            // test
            al.set(0, 9);
            al.set(3, 8);
            al.set(4, 7);

            assert al.get(0) == 9 : "Error in ArrayList::set(int index, T item) or ArrayList::get(int index)";
            assert al.get(1) == 1 : "Error in ArrayList::set(int index, T item) or ArrayList::get(int index)";
            assert al.get(2) == 2 : "Error in ArrayList::set(int index, T item) or ArrayList::get(int index)";
            assert al.get(3) == 8 : "Error in ArrayList::set(int index, T item) or ArrayList::get(int index)";
            assert al.get(4) == 7 : "Error in ArrayList::set(int index, T item) or ArrayList::get(int index)";
        }

    }

    public static void main(String[] args) {

        arrayListTests();
    }
}
