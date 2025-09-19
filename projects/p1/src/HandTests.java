package projects.p1.src;

/*
file name:      HandTests.java
Authors:        Max Bender & Naser Al Madi
last modified:  8/28/2022

How to run:     java -ea HandTests
*/

public class HandTests {

    public static void handTests() {

        // case 1: testing Hand()
        {
            // set up
            Hand h1 = new Hand();

            // verify
            System.out.println(h1 + " == [] : 0");

            // test
            assert h1 != null : "Error in Hand::Hand()";
            assert h1.size() == 0 : "Error in Hand::size()";
        }

        // case 2: testing getTotalValue() and size()
        {
            // set up
            Hand h1 = new Hand();

            // verify
            System.out.println(h1.getTotalValue() + " == 0");

            // test
            assert h1.getTotalValue() == 0 : "Error in Hand::getTotalValue()";
            assert h1.size() == 0 : "Error in Hand::size()";
        }

        // case 3: testing getTotalValue() and size()
        {
            // set up
            Hand h1 = new Hand();
            h1.add(new Card(5));

            // verify
            System.out.println(h1.getTotalValue() + " == 5");

            // test
            assert h1.getTotalValue() == 5 : "Error in Hand::getTotalValue() or Hand::add()";
            assert h1.size() == 1 : "Error in Hand::size()";
        }

        // case 4: testing getTotalValue(), size(), and getCard()
        {
            // set up
            Hand h1 = new Hand();
            h1.add(new Card(5));
            h1.add(new Card(2));
            h1.add(new Card(3));

            // verify
            System.out.println(h1.getTotalValue() + " == 10");
            System.out.println(h1 + " == [5, 2, 3] : 10");

            // test
            assert h1.getTotalValue() == 10 : "Error in Hand::getTotalValue() or Hand::add()";
            assert h1.size() == 3 : "Error in Hand::size()";
            assert h1.getCard(0).getValue() == 5 : "Error in Hand::getCard()";
            assert h1.getCard(1).getValue() == 2 : "Error in Hand::getCard()";
            assert h1.getCard(2).getValue() == 3 : "Error in Hand::getCard()";
        }

        // case 5: testing reset()
        {
            // set up
            Hand h1 = new Hand();
            h1.add(new Card(5));
            h1.add(new Card(2));
            h1.add(new Card(3));
            h1.reset();

            // verify
            System.out.println(h1.getTotalValue() + " == 0");
            System.out.println(h1 + " == [] : 0");

            // test

            assert h1.getTotalValue() == 0 : "Error in Hand::getTotalValue() or Hand::add()";
            assert h1.size() == 0 : "Error in Hand::size()";
        }

        // case 6: testing toString()
        {
            // set up
            Hand h1 = new Hand();
            h1.add(new Card(5));
            h1.add(new Card(2));
            h1.add(new Card(3));

            // verify
            System.out.println(h1.getTotalValue() + " == 10");
            System.out.println(h1 + " == [5, 2, 3] : 10");

            // test
            assert h1.toString().equals("[5, 2, 3] : 10") : "Error in Hand::toString()";
        }

        System.out.println("*** Done testing Hand! ***\n");
    }

    public static void main(String[] args) {

        handTests();
    }
}