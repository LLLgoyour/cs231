/*
 * file name: SocialAgent.java
 * author: Jack Dai
 * last modified: 10/05/2025
 * purpose of the class:
 * Represents agents that prefer company and tend to move when few
 * neighbors are nearby. Draws each agent as a blue circle whose
 * shade indicates whether it moved during the last update.
 */

import java.awt.Graphics;
import java.awt.Color;

public class SocialAgent extends Agent {
    /**
     * Calls the super class constructor and sets the radius field.
     * 
     * @param x0     the x position
     * @param y0     the y position
     * @param radius the radius of interaction
     */
    public SocialAgent(double x0, double y0, int radius) {
        super(x0, y0, radius); // sets x, y, and radius in Agent
        this.moved = false;
    }

    /**
     * Draws a circle of radius 5 (i.e. it fits in a 10x10 box) at the Agent's
     * location. If the agent moved during the last updateState, it is drawn with a
     * lighter shade of blue, otherwise a darker shade of blue.
     * 
     * @param g the graphics context for drawing
     */
    public void draw(Graphics g) {
        // Set color based on movement state
        if (!moved) {
            g.setColor(new Color(0, 0, 255));
        } else {
            g.setColor(new Color(125, 125, 255));
        }
        // Draw the agent as a small oval
        g.fillOval((int) getX(), (int) getY(), 5, 5);
    }

    /**
     * Updates the agent's position based on nearby neighbors.
     * Moves randomly when there are fewer than four neighbors within the
     * interaction radius. The agent stays within the bounds of the Landscape.
     *
     * @param scape the landscape that contains the agent
     */
    public void updateState(Landscape scape) {
        // Handle null landscape case
        if (scape == null) {
            this.moved = false;
            return;
        }

        // Get neighbors within interaction radius
        LinkedList<Agent> neighbors = scape.getNeighbors(getX(), getY(), getRadius());

        // Move if there are too few neighbors (social behavior - seeks company)
        if (neighbors.size() < 4) {
            // Generate random movement delta
            double deltaX = (Math.random() * 20.0) - 10.0;
            double deltaY = (Math.random() * 20.0) - 10.0;

            // Calculate new position
            double newX = getX() + deltaX;
            double newY = getY() + deltaY;

            // Keep agent within landscape bounds
            newX = Math.max(0.0, Math.min(newX, scape.getWidth()));
            newY = Math.max(0.0, Math.min(newY, scape.getHeight()));

            // Check if agent actually moved
            this.moved = (Math.abs(newX - getX()) > 1e-9) || (Math.abs(newY - getY()) > 1e-9);

            // Update position
            setX(newX);
            setY(newY);
        } else {
            // Enough neighbors, stay put
            this.moved = false;
        }
    }
}