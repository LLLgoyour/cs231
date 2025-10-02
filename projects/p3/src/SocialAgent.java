/*
 * file name: SocialAgent.java
 * author: Jack Dai
 * last modified: 10/02/2025
 * purpose of the class:
 * TODO
 */

public class SocialAgent {
    /**
     * calls the super class constructor and sets the radius field.
     * 
     * @param x0
     * @param y0
     * @param radius
     */
    public SocialAgent(double x0, double y0, int radius) {
        super(x0, y0, radius);
        // remainder of constructor code
    }

    /**
     * draws a circle of radius 5 (i.e. it fits in a 10x10 box) at the Agent's
     * location. If the agent moved during the last updateState, it is drawn with a
     * lighter shade of blue, otherwise a darker shade of blue.
     * 
     * @param g
     */
    public void draw(Graphics g) {
        if (!moved)
            g.setColor(new Color(0, 0, 255));
        else
            g.setColor(new Color(125, 125, 255));

        g.fillOval((int) getX(), (int) getY(), 5, 5);
    }

    /**
     * for now, leave this blank.
     * 
     * @param scape
     */
    public void updateState(Landscape scape) {

    }
}