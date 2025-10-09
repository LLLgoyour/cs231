/*
file name:      AgentTests.java
Authors:        Ike Lage & Jack Dai
last modified:  10/06/2025

How to run:     java -ea AgentTests
*/

public class AgentTests {

    public static double agentTests() {

        double score = 0.;

        // case 1: testing SocialAgent( double , double , int ) and AntiSocialAgent(
        // double , double , int )
        {
            SocialAgent sa = new SocialAgent(1.0, 2.0, 5);
            AntiSocialAgent aa = new AntiSocialAgent(3.0, 4.0, 7);
            assert sa != null : "case 1 failed: SocialAgent constructor returned null";
            assert aa != null : "case 1 failed: AntiSocialAgent constructor returned null";
            // initial moved should be false
            assert sa.getMoved() == false : "case 1 failed: SocialAgent moved should be false after construction";
            assert aa.getMoved() == false : "case 1 failed: AntiSocialAgent moved should be false after construction";
            System.out.println("1");
            score += 0.75;
        }

        // case 2: testing getX() for both SocialAgent and AntiSocialAgent
        {
            SocialAgent sa = new SocialAgent(10.5, 0.0, 5);
            AntiSocialAgent aa = new AntiSocialAgent(20.25, 1.0, 5);
            assert sa.getX() == 10.5 : "case 2 failed: SocialAgent getX returned wrong value";
            assert aa.getX() == 20.25 : "case 2 failed: AntiSocialAgent getX returned wrong value";
            System.out.println("2");
            score += 0.75;
        }

        // case 3: testing getY() for both SocialAgent and AntiSocialAgent
        {
            SocialAgent sa = new SocialAgent(0.0, 5.5, 5);
            AntiSocialAgent aa = new AntiSocialAgent(0.0, 7.75, 5);
            assert sa.getY() == 5.5 : "case 3 failed: SocialAgent getY returned wrong value";
            assert aa.getY() == 7.75 : "case 3 failed: AntiSocialAgent getY returned wrong value";
            System.out.println("3");
            score += 0.75;

        }

        // case 4: testing getRadius() for both SocialAgent and AntiSocialAgent
        {
            SocialAgent sa = new SocialAgent(0.0, 0.0, 2);
            AntiSocialAgent aa = new AntiSocialAgent(0.0, 0.0, 9);
            assert sa.getRadius() == 2 : "case 4 failed: SocialAgent getRadius returned wrong value";
            assert aa.getRadius() == 9 : "case 4 failed: AntiSocialAgent getRadius returned wrong value";
            System.out.println("4");
            score += 0.75;
        }

        // case 5: testing setX() for both SocialAgent and AntiSocialAgent
        {
            SocialAgent sa = new SocialAgent(0.0, 0.0, 5);
            AntiSocialAgent aa = new AntiSocialAgent(0.0, 0.0, 5);
            sa.setX(42.42);
            aa.setX(99.99);
            assert sa.getX() == 42.42 : "case 5 failed: SocialAgent setX/getX mismatch";
            assert aa.getX() == 99.99 : "case 5 failed: AntiSocialAgent setX/getX mismatch";
            System.out.println("5");
            score += 0.75;
        }

        // case 6: testing setY() for both SocialAgent and AntiSocialAgent
        {
            SocialAgent sa = new SocialAgent(0.0, 0.0, 5);
            AntiSocialAgent aa = new AntiSocialAgent(0.0, 0.0, 5);
            sa.setY(11.11);
            aa.setY(22.22);
            assert sa.getY() == 11.11 : "case 6 failed: SocialAgent setY/getY mismatch";
            assert aa.getY() == 22.22 : "case 6 failed: AntiSocialAgent setY/getY mismatch";
            System.out.println("6");
            score += 0.75;
        }

        // case 7: testing setRadius() for both SocialAgent and AntiSocialAgent
        {
            SocialAgent sa = new SocialAgent(0.0, 0.0, 5);
            AntiSocialAgent aa = new AntiSocialAgent(0.0, 0.0, 5);
            sa.setRadius(15);
            aa.setRadius(25);
            assert sa.getRadius() == 15 : "case 7 failed: SocialAgent setRadius/getRadius mismatch";
            assert aa.getRadius() == 25 : "case 7 failed: AntiSocialAgent setRadius/getRadius mismatch";
            System.out.println("7");
            score += 0.75;
        }

        // case 8: testing getMoved() for both SocialAgent and AntiSocialAgent
        {
            SocialAgent sa = new SocialAgent(0.0, 0.0, 5);
            AntiSocialAgent aa = new AntiSocialAgent(0.0, 0.0, 5);
            // initially, moved should be false
            assert sa.getMoved() == false : "case 8 failed: SocialAgent initial moved should be false";
            assert aa.getMoved() == false : "case 8 failed: AntiSocialAgent initial moved should be false";
            System.out.println("8");
            score += 0.75;
        }

        return score;
    }

    public static void main(String[] args) {

        System.out.println(agentTests());
    }
}
