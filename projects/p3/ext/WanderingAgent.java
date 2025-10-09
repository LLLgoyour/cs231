/*
 * file name: WanderingAgent.java
 * author: Jack Dai
 * last modified: 10/08/2025
 * purpose of the class:
 * Represents agents that wander the landscape in a semi-random but
 * persistent manner. These agents maintain a direction of movement
 * and change direction periodically or when hitting boundaries.
 * They are drawn as green circles with different shades based on movement.
 */

import java.awt.Graphics;
import java.awt.Color;

public class WanderingAgent extends Agent {
    private double directionX;
    private double directionY;
    private int stepsSinceDirectionChange;
    private int stepsBeforeDirectionChange;
    private static final double MOVEMENT_SPEED = 2.0;

    /**
     * Constructor that initializes the wandering agent with a random direction.
     * 
     * @param x0     the initial x position
     * @param y0     the initial y position
     * @param radius the interaction radius
     */
    public WanderingAgent(double x0, double y0, int radius) {
        super(x0, y0, radius);
        this.moved = false;
        this.stepsSinceDirectionChange = 0;
        this.stepsBeforeDirectionChange = (int) (Math.random() * 30) + 10; // 10-40 steps
        setRandomDirection();
    }

    /**
     * Sets a new random direction for the agent to move in.
     */
    private void setRandomDirection() {
        double angle = Math.random() * 2 * Math.PI;
        this.directionX = Math.cos(angle);
        this.directionY = Math.sin(angle);
        this.stepsSinceDirectionChange = 0;
        this.stepsBeforeDirectionChange = (int) (Math.random() * 30) + 10;
    }

    /**
     * Draws the agent as a green circle. Lighter green if it moved recently,
     * darker green if it stayed still.
     * 
     * @param g the graphics context for drawing
     */
    public void draw(Graphics g) {
        // Set color based on movement state
        if (!moved) {
            g.setColor(new Color(0, 150, 0)); // Dark green
        } else {
            g.setColor(new Color(100, 255, 100)); // Light green
        }
        // Draw the agent as a small oval
        g.fillOval((int) getX(), (int) getY(), 5, 5);
    }

    /**
     * Updates the agent's state by moving in its current direction.
     * Changes direction periodically or when hitting landscape boundaries.
     * Unlike social agents, wandering agents ignore other agents and focus
     * on exploration.
     * 
     * @param scape the landscape containing this agent
     */
    public void updateState(Landscape scape) {
        // Handle null landscape case
        if (scape == null) {
            this.moved = false;
            return;
        }

        // Check if it's time to change direction
        this.stepsSinceDirectionChange++;
        if (this.stepsSinceDirectionChange >= this.stepsBeforeDirectionChange) {
            setRandomDirection();
        }

        // Calculate new position based on current direction
        double newX = getX() + (directionX * MOVEMENT_SPEED);
        double newY = getY() + (directionY * MOVEMENT_SPEED);

        // Check boundaries and bounce if necessary
        boolean hitBoundary = false;
        if (newX <= 0 || newX >= scape.getWidth()) {
            this.directionX = -this.directionX; // Reverse X direction
            newX = Math.max(0.0, Math.min(newX, scape.getWidth()));
            hitBoundary = true;
        }
        if (newY <= 0 || newY >= scape.getHeight()) {
            this.directionY = -this.directionY; // Reverse Y direction
            newY = Math.max(0.0, Math.min(newY, scape.getHeight()));
            hitBoundary = true;
        }

        // If we hit a boundary, reset the direction change counter
        if (hitBoundary) {
            this.stepsSinceDirectionChange = 0;
            this.stepsBeforeDirectionChange = (int) (Math.random() * 20) + 5;
        }

        // Check if agent actually moved
        this.moved = (Math.abs(newX - getX()) > 1e-9) || (Math.abs(newY - getY()) > 1e-9);

        // Update position
        setX(newX);
        setY(newY);
    }
}