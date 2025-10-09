/*
 * file name: Agent.java
 * author: Jack Dai
 * last modified: 10/05/2025
 * purpose of the class:
 * Defines a base class for all agents in the simulation, storing their
 * position, interaction radius, and movement state. Subclasses like
 * SocialAgent and AntiSocialAgent implement specific behaviors for
 * updating and drawing the agents.
 */

import java.awt.Graphics;

abstract class Agent {
    private double x0;
    private double y0;
    protected int radius;
    protected boolean moved;

    /**
     * Constructor that sets the position.
     * 
     * @param x0     the initial x coordinate
     * @param y0     the initial y coordinate
     * @param radius the interaction radius
     */
    public Agent(double x0, double y0, int radius) {
        this.x0 = x0;
        this.y0 = y0;
        this.radius = radius;
        this.moved = false;
    }

    /**
     * Gets the x coordinate of the agent.
     * 
     * @return the x position
     */
    public double getX() {
        return this.x0;
    }

    /**
     * Gets the y coordinate of the agent.
     * 
     * @return the y position
     */
    public double getY() {
        return this.y0;
    }

    /**
     * Gets the interaction radius of the agent.
     * 
     * @return the radius
     */
    public int getRadius() {
        return this.radius;
    }

    /**
     * Checks whether the agent moved in the last update.
     * 
     * @return whether the agent moved
     */
    public boolean getMoved() {
        return this.moved;
    }

    /**
     * Sets the x position of the agent.
     * 
     * @param newX the new x coordinate
     */
    public void setX(double newX) {
        this.x0 = newX;
    }

    /**
     * Sets the y position of the agent.
     * 
     * @param newY the new y coordinate
     */
    public void setY(double newY) {
        this.y0 = newY;
    }

    /**
     * Sets the interaction radius of the agent.
     * 
     * @param newRadius the new interaction radius
     */
    public void setRadius(int newRadius) {
        this.radius = newRadius;
    }

    /**
     * Returns a string representation of the agent's position.
     * 
     * @return a String containing the x and y positions, e.g. "(3.024, 4.245)"
     */
    public String toString() {
        return String.format("(%.3f, %.3f)", this.x0, this.y0);
    }

    /**
     * Updates the agent's state based on the landscape environment.
     * 
     * @param scape the landscape containing this agent
     */
    public abstract void updateState(Landscape scape);

    /**
     * Draws the agent on the graphics context.
     * 
     * @param g the graphics context for drawing
     */
    public abstract void draw(Graphics g);
}