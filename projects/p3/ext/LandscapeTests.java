/*
file name:      LandscapeTests.java
Authors:        Ike Lage & Jack Dai
last modified:  10/05/2025

How to run:     java -ea LandscapeTests
*/

public class LandscapeTests {

    public static void landscapeTests() {

        // case 1: testing Landscape(int, int)
        {
            // set up
            Landscape l1 = new Landscape(100, 100);
            Landscape l2 = new Landscape(50, 200);

            // verify
            System.out.println(l1);
            System.out.println(l2);

            // test
            assert l1 != null : "Error in Landscape::Landscape(int, int)";
            assert l2 != null : "Error in Landscape::Landscape(int, int)";
        }

        // case 2: testing getWidth() and getHeight()
        {
            // set up
            Landscape l1 = new Landscape(80, 60);
            Landscape l2 = new Landscape(25, 75);

            // verify
            System.out.println(l1.getWidth() + " == 80");
            System.out.println(l1.getHeight() + " == 60");
            System.out.println(l2.getWidth() + " == 25");
            System.out.println(l2.getHeight() + " == 75");

            // test
            assert l1.getWidth() == 80 : "Error in Landscape::getWidth()";
            assert l1.getHeight() == 60 : "Error in Landscape::getHeight()";
            assert l2.getWidth() == 25 : "Error in Landscape::getWidth()";
            assert l2.getHeight() == 75 : "Error in Landscape::getHeight()";
        }

        // case 3: testing addAgent() and toString()
        {
            // set up
            Landscape l1 = new Landscape(100, 100);
            SocialAgent a1 = new SocialAgent(10, 20, 15);
            AntiSocialAgent a2 = new AntiSocialAgent(30, 40, 20);

            // test
            l1.addAgent(a1);
            l1.addAgent(a2);

            // verify
            System.out.println(l1.toString());
            assert l1.toString().contains("2") : "Error in Landscape::addAgent() or toString()";
        }

        // case 4: testing getNeighbors()
        {
            // set up
            Landscape l1 = new Landscape(100, 100);
            SocialAgent a1 = new SocialAgent(10, 10, 20);
            SocialAgent a2 = new SocialAgent(12, 14, 20);
            AntiSocialAgent a3 = new AntiSocialAgent(40, 50, 20);
            l1.addAgent(a1);
            l1.addAgent(a2);
            l1.addAgent(a3);

            // verify
            LinkedList<Agent> n1 = l1.getNeighbors(10, 10, 10);
            System.out.println("Neighbors near (10,10) with r=10: " + n1.size());

            // test
            assert n1.size() == 1 : "Error in Landscape::getNeighbors() (expected 1)";
        }

        // case 5: testing draw()
        {
            // This test simply ensures draw() runs without crashing.
            Landscape l1 = new Landscape(50, 50);
            SocialAgent a1 = new SocialAgent(5, 5, 10);
            l1.addAgent(a1);

            try {
                l1.draw(null); // No crash means success
                System.out.println("Landscape::draw() executed successfully (no Graphics yet)");
            } catch (Exception e) {
                assert false : "Error in Landscape::draw() - threw exception";
            }
        }

        System.out.println("\nAll Landscape tests passed!");
    }

    public static void main(String[] args) {
        landscapeTests();
    }
}
