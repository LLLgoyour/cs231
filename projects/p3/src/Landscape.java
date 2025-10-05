/*
* file name: Landscape.java
* author: Jack Dai
* last modified: 10/02/2025
* purpose of this class:
* TODO
*/

import java.awt.Graphics;

public class Landscape {
    private int w;
    private int h;
    private LinkedList<Agent> agents;

    /**
     * a constructor that sets the width and height fields, and initializes the
     * agent list.
     * 
     * @param w the width fields
     * @param h the height fields
     */
    public Landscape(int w, int h) {
        this.w = w;
        this.h = h;
        this.agents = new LinkedList<Agent>();
    }

    /**
     * 
     * @return the height.
     */
    public int getHeight() {
        return this.h;
    }

    /**
     * 
     * @return the width.
     */
    public int getWidth() {
        return this.w;
    }

    /**
     * inserts an agent at the beginning of its list of agents.
     * 
     * @param a
     */
    public void addAgent(Agent a) {
        this.agents.addFirst(a);

    }

    /**
     * @return a String representing the Landscape. It can be as simple as
     *         indicating the number of Agents on the Landscape.
     */
    public String toString() {
        return "Landscape with " + this.agents.size() + " agents.";
    }

    /**
     * 
     * @param x0
     * @param y0
     * @param radius
     * @return a list of the Agents within radius distance of the location x0, y0.
     */
    public LinkedList<Agent> getNeighbors(double x0, double y0, double radius) {
        LinkedList<Agent> neighbors = new LinkedList<Agent>();
        LinkedList<Agent>.Node current = this.agents.getHead();

        while (current != null) {
            Agent a = current.data;
            double dx = a.getX() - x0;
            double dy = a.getY() - y0;
            double dist = Math.sqrt(dx * dx + dy * dy);

            if (dist <= radius && dist > 0) {
                neighbors.addFirst(a);
            }
            current = current.next;
        }
        return neighbors;
    }

    /**
     * Calls the draw method of all the agents on the Landscape.
     * 
     * @param g
     */
    public void draw(Graphics g) {
        LinkedList<Agent>.Node current = this.agents.getHead();
        while (current != null) {
            current.data.draw(g);
            current = current.next;
        }
    }
}