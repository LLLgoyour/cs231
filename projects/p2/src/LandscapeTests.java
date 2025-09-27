/*
file name:      LandscapeTests.java
Authors:        Max Bender & Naser Al Madi & Ike Lage & Jack Dai
last modified:  09/26/2025

How to run:     java -ea LandscapeTests
*/

//Note: to get this test code to run, fill in the TODOs with the correct values!
//Other than that, it checks for the same things as the autograder code.

import java.util.ArrayList;

public class LandscapeTests {

    public static void landscapeTests() {

        // case 1: testing Landscape(int, int)
        {
            // set up
            Landscape l1 = new Landscape(2, 4, 0.9);
            Landscape l2 = new Landscape(10, 10, 0.9);

            // verify
            System.out.println(l1);
            System.out.println("\n");
            System.out.println(l2);

            // test
            assert l1 != null : "Error in Landscape::Landscape(int, int)";
            assert l2 != null : "Error in Landscape::Landscape(int, int)";
        }

        // case 2: testing getRows()
        {
            // set up
            Landscape l1 = new Landscape(2, 4);
            Landscape l2 = new Landscape(10, 10);

            // verify
            System.out.println(l1.getRows() + " == 2");
            System.out.println(l2.getRows() + " == 10");

            // test
            assert l1.getRows() == 2 : "Error in Landscape::getRows()";
            assert l2.getRows() == 10 : "Error in Landscape::getRows()";

        }

        // case 3: testing getCols()
        {
            // set up
            Landscape l1 = new Landscape(2, 4);
            Landscape l2 = new Landscape(10, 10);

            // verify
            System.out.println(l1.getCols() + " == 4");
            System.out.println(l2.getCols() + " == 10");

            // test
            assert l1.getCols() == 4 : "Error in Landscape::getCols()";
            assert l2.getRows() == 10 : "Error in Landscape::getCols()";
        }

        // case 4: testing getCell(int, int)
        {
            // set up
            Landscape l1 = new Landscape(2, 4, 1);

            // verify
            System.out.println(l1.getCell(0, 0) + " == TODO");
            System.out.println(l1.getCell(0, 3) + " == TODO");

            // test
            assert l1.getCell(0, 0) != null : "Error in Landscape::getCell()";
            assert l1.getCell(0, 3) != null : "Error in Landscape::getCell()";

        }

        // case 5: testing reset()
        {
            // set up
            Landscape l1 = new Landscape(2, 2, 0.);
            l1.getCell(0, 0).setAlive(true);
            l1.getCell(1, 1).setAlive(true);

            // verify

            // There are exactly 2 alive cells right now
            int total = 0;
            for (int i = 0; i < l1.getRows(); i++) {
                for (int j = 0; j < l1.getCols(); j++) {
                    if (l1.getCell(i, j).getAlive()) {
                        total += 1;
                    }
                }
            }
            System.out.println(total + " == TODO");

            // test
            l1.reset();

            int total2 = 0;
            for (int i = 0; i < l1.getRows(); i++) {
                for (int j = 0; j < l1.getCols(); j++) {
                    if (l1.getCell(i, j).getAlive()) {
                        total2 += 1;
                    }
                }
            }
            System.out.println(total2 + " == TODO");
            assert total2 == TODO : "Error in Landscape::reset()";

        }

        // case 6: testing getNeighbors()
        {

            // set up
            Landscape l1 = new Landscape(3, 3);

            // verify
            ArrayList<Cell> n1 = l1.getNeighbors(0, 0);
            ArrayList<Cell> n2 = l1.getNeighbors(1, 1);

            System.out.println(n1.size() + " == TODO");
            System.out.println(n2.size() + " == TODO");

            // test
            assert n1.size() == TODO : "Error in Landscape::getNeighbors(int, int)";
            assert n2.size() == TODO : "Error in Landscape::getNeighbors(int, int)";

        }

        // case 7: testing advance()
        {
            // set up
            Landscape l1 = new Landscape(3, 3, 0.);

            l1.getCell(0, 0).setAlive(true);
            l1.getCell(0, 2).setAlive(true);
            l1.getCell(1, 1).setAlive(true);
            l1.getCell(2, 0).setAlive(true);
            l1.getCell(2, 1).setAlive(true);

            // verify
            System.out.println(l1);

            // test
            l1.advance();
            System.out.println(l1);

            System.out.println(l1.getCell(0, 0).getAlive() + " == TODO");
            System.out.println(l1.getCell(0, 1).getAlive() + " == TODO");
            System.out.println(l1.getCell(0, 2).getAlive() + " == TODO");
            System.out.println(l1.getCell(1, 0).getAlive() + " == TODO");
            System.out.println(l1.getCell(1, 1).getAlive() + " == TODO");
            System.out.println(l1.getCell(1, 2).getAlive() + " == TODO");
            System.out.println(l1.getCell(2, 0).getAlive() + " == TODO");
            System.out.println(l1.getCell(2, 1).getAlive() + " == TODO");
            System.out.println(l1.getCell(2, 2).getAlive() + " == TODO");

            assert l1.getCell(0, 0).getAlive() == TODO;
            assert l1.getCell(0, 1).getAlive() == TODO;
            assert l1.getCell(0, 2).getAlive() == TODO;
            assert l1.getCell(1, 0).getAlive() == TODO;
            assert l1.getCell(1, 1).getAlive() == TODO;
            assert l1.getCell(1, 2).getAlive() == TODO;
            assert l1.getCell(2, 0).getAlive() == TODO;
            assert l1.getCell(2, 1).getAlive() == TODO;
            assert l1.getCell(2, 2).getAlive() == TODO;

        }
    }

    public static void main(String[] args) {

        landscapeTests();
    }
}