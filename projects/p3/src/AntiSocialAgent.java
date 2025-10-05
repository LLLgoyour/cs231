/*
* file name: AntiSocialAgent.java
* author: Jack Dai
* last modified: 10/02/2025
* purpose of this class:
* TODO
*/

import java.awt.Graphics;
import java.awt.Color;

public class AntiSocialAgent extends Agent {
    /**
     * calls the super class constructor and sets the radius field.
     * 
     * @param x0     the x position
     * @param y0     the y position
     * @param radius the radius of interaction
     */
    public AntiSocialAgent(double x0, double y0, int radius) {
        super(x0, y0, radius);
        this.moved = false;
    }

    /**
     * draws a circle of radius 5 (i.e. it fits in a 10x10 box) at the Agent's
     * location. If the agent moved during the last updateState, it is drawn with a
     * lighter shade of red, otherwise a darker shade of red.
     * 
     * @param g
     */
    public void draw(Graphics g) {
        if (!moved)
            g.setColor(new Color(255, 0, 0));
        else
            g.setColor(new Color(255, 125, 125));

        g.fillOval((int) getX(), (int) getY(), 5, 5);
    }

    /**
     * for now, leave it blank.
     * 
     * @param scape
     */
    public void updateState(Landscape scape) {

    }
}