/*
* file name: Landscape.java
* author: Jack Dai
* last modified: 10/05/2025
* purpose of this class:
* Represents the 2D environment that holds and manages all agents.
* It stores agents in a linked list, finds nearby neighbors, updates
* agent states each step, and draws them on the screen.
*/

import java.awt.Graphics;
import java.util.Random;

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

    public int updateAgents() {
        // if there are no agents, nothing to do
        if (this.agents.getHead() == null) {
            return 0;
        }

        Random r = new Random();

        // find a random index within the list
        int numAgents = this.agents.size();
        int randIdx = r.nextInt(numAgents);

        // traverse to that index
        LinkedList<Agent>.Node current = this.agents.getHead();
        int i = 0;
        while (current != null && i < randIdx) {
            current = current.next;
            i++;
        }

        // store values of x, y, and radius before deleting
        Agent removedAgent = current.data;
        double x = removedAgent.getX();
        double y = removedAgent.getY();
        int rad = removedAgent.getRadius();

        // remove the chosen agent from the list
        this.agents.remove(removedAgent);

        // create replacement AntiSocialAgent
        AntiSocialAgent newAgent = new AntiSocialAgent(x, y, rad);
        this.agents.addFirst(newAgent);

        // update all agents and count how many moved
        int movedCount = 0;
        LinkedList<Agent>.Node node = this.agents.getHead();
        while (node != null) {
            node.data.updateState(this);
            if (node.data.getMoved()) {
                movedCount++;
            }
            node = node.next;
        }
        return movedCount;
    }

    /**
     * Calls the draw method of all the agents on the Landscape.
     * 
     * @param g
     */
    public void draw(Graphics g) {
        // if g is null (like during testing), skip drawing
        if (g == null) {
            return;
        }

        LinkedList<Agent>.Node current = this.agents.getHead();
        while (current != null) {
            current.data.draw(g);
            current = current.next;
        }
    }
}