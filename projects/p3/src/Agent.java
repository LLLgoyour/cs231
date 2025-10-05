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
     * @param x0
     * @param y0
     * @param radius
     */
    public Agent(double x0, double y0, int radius) {
        this.x0 = x0;
        this.y0 = y0;
        this.radius = radius;
        this.moved = false;
    }

    /**
     * 
     * @return the x position.
     */
    public double getX() {
        return this.x0;
    }

    /**
     * 
     * @return the y position.
     */
    public double getY() {
        return this.y0;
    }

    /**
     * 
     * @return the radius.
     */
    public int getRadius() {
        return this.radius;
    }

    /**
     * 
     * @return whether the agent moved.
     */
    public boolean getMoved() {
        return this.moved;
    }

    /**
     * sets the x position.
     * 
     * @param newX
     */
    public void setX(double newX) {
        this.x0 = newX;
    }

    /**
     * sets the y position.
     * 
     * @param newY
     */
    public void setY(double newY) {
        this.y0 = newY;
    }

    /**
     * sets the radius.
     * 
     * @param newRadius
     */
    public void setRadius(int newRadius) {
        this.radius = newRadius;
    }

    /**
     * @return a String containing the x and y positions, e.g. "(3.024, 4.245)".
     */
    public String toString() {
        return String.format("(%.3f, %.3f)", this.x0, this.y0);
    }

    /**
     * does nothing, for now.
     * 
     * @param scape
     */
    public abstract void updateState(Landscape scape);

    /**
     * does nothing, for now.
     * 
     * @param g
     */
    public abstract void draw(Graphics g);
}