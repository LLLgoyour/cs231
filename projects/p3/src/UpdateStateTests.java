/*
file name:      UpdateStateTests.java
Authors:        Ike Lage
last modified:  03/04/2025

How to run:     java -ea UpdateStateTests
*/

public class UpdateStateTests {

    public static double updateStateTests() {

        double score = 0.;

        // case 1: testing updateState() doesn't move the agent for a social agent when
        // it has many neighbors
        {
            // set up
            Landscape landscape = new Landscape(100, 100);
            SocialAgent a1 = new SocialAgent(10, 10, 5);
            landscape.addAgent(a1);
            // Make 4 Social Agents within a radius of 5 of the agent
            landscape.addAgent(new SocialAgent(12, 10, 5));
            landscape.addAgent(new SocialAgent(8, 10, 5));
            landscape.addAgent(new SocialAgent(10, 12, 5));
            landscape.addAgent(new SocialAgent(10, 8, 5));
            // verify
            a1.updateState(landscape);
            // test - assert will throw if the condition is false
            assert (a1.getX() == 10) && (a1.getY() == 10) && (a1.getMoved() == false)
                    : "case 1 failed: social agent moved despite many neighbors";
            System.out.println("1");
            score += 0.75;
        }

        // case 2: testing updateState() moves the agent for an antisocial agent when it
        // has many neighbors
        {
            Landscape landscape = new Landscape(100, 100);
            AntiSocialAgent a1 = new AntiSocialAgent(10, 10, 5);
            landscape.addAgent(a1);
            landscape.addAgent(new SocialAgent(12, 10, 5));
            landscape.addAgent(new SocialAgent(8, 10, 5));
            landscape.addAgent(new SocialAgent(10, 12, 5));
            landscape.addAgent(new SocialAgent(10, 8, 5));
            a1.updateState(landscape);
            assert (a1.getX() != 10) && (a1.getY() != 10) && (a1.getY() != a1.getX()) && (a1.getMoved() == true)
                    : "case 2 failed: antisocial agent did not move despite many neighbors";
            System.out.println("2");
            score += 0.75;
        }

        // case 3: testing updateState() moves the agent for a social agent when it has
        // few neighbors
        {
            Landscape landscape = new Landscape(100, 100);
            SocialAgent a1 = new SocialAgent(10, 10, 5);
            landscape.addAgent(a1);
            // Only 1 neighbor
            landscape.addAgent(new SocialAgent(20, 20, 5));
            a1.updateState(landscape);
            assert (a1.getX() != 10) && (a1.getY() != 10) && (a1.getY() != a1.getX()) && (a1.getMoved() == true)
                    : "case 3 failed: social agent did not move despite few neighbors";
            System.out.println("3");
            score += 0.75;
        }

        // case 4: testing updateState() doesn't move the agent for an antisocial agent
        // when it has few neighbors
        {
            Landscape landscape = new Landscape(100, 100);
            AntiSocialAgent a1 = new AntiSocialAgent(10, 10, 5);
            landscape.addAgent(a1);
            // Only 1 neighbor
            landscape.addAgent(new SocialAgent(20, 20, 5));
            a1.updateState(landscape);
            assert (a1.getX() == 10) && (a1.getY() == 10) && (a1.getMoved() == false)
                    : "case 4 failed: antisocial agent moved despite few neighbors";
            System.out.println("4");
            score += 0.75;
        }

        return score;
    }

    public static void main(String[] args) {

        System.out.println(updateStateTests());
    }
}