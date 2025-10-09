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
     * A constructor that sets the width and height fields, and initializes the
     * agent list.
     * 
     * @param w the width of the landscape
     * @param h the height of the landscape
     */
    public Landscape(int w, int h) {
        this.w = w;
        this.h = h;
        this.agents = new LinkedList<Agent>();
    }

    /**
     * Gets the height of the landscape.
     * 
     * @return the height
     */
    public int getHeight() {
        return this.h;
    }

    /**
     * Gets the width of the landscape.
     * 
     * @return the width
     */
    public int getWidth() {
        return this.w;
    }

    /**
     * Inserts an agent at the beginning of its list of agents.
     * 
     * @param a the agent to add to the landscape
     */
    public void addAgent(Agent a) {
        this.agents.addFirst(a);

    }

    /**
     * Returns a string representation of the Landscape indicating the number of
     * agents.
     * 
     * @return a String representing the Landscape. It can be as simple as
     *         indicating the number of Agents on the Landscape.
     */
    public String toString() {
        return "Landscape with " + this.agents.size() + " agents.";
    }

    /**
     * Finds all agents within a specified radius of a given location.
     * 
     * @param x0     the x coordinate center of search area
     * @param y0     the y coordinate center of search area
     * @param radius the search radius
     * @return a list of the Agents within radius distance of the location x0, y0.
     */
    public LinkedList<Agent> getNeighbors(double x0, double y0, double radius) {
        // Create list to store neighboring agents
        LinkedList<Agent> neighbors = new LinkedList<Agent>();
        LinkedList.Node<Agent> current = this.agents.getHead();

        // Traverse through all agents to find neighbors
        while (current != null) {
            Agent a = current.getData();
            // Calculate distance from target location
            double dx = a.getX() - x0;
            double dy = a.getY() - y0;
            double dist = Math.sqrt(dx * dx + dy * dy);

            // Add agent if within radius but not at exact same location
            if (dist <= radius && dist > 0) {
                neighbors.addFirst(a);
            }
            current = current.getNext();
        }
        return neighbors;
    }

    /**
     * Updates all agents in the landscape by replacing one random agent with an
     * AntiSocialAgent
     * and then updating all agent states.
     * 
     * @return the number of agents that moved during this update
     */
    public int updateAgents() {
        // Check if there are no agents, nothing to do
        if (this.agents.getHead() == null) {
            return 0;
        }

        Random r = new Random();

        // Find a random index within the list
        int numAgents = this.agents.size();
        int randIdx = r.nextInt(numAgents);

        // Traverse to that index
        LinkedList.Node<Agent> current = this.agents.getHead();
        int i = 0;
        while (current != null && i < randIdx) {
            current = current.getNext();
            i++;
        }

        // Store values of x, y, and radius before deleting
        Agent removedAgent = current.getData();
        double x = removedAgent.getX();
        double y = removedAgent.getY();
        int rad = removedAgent.getRadius();

        // Remove the chosen agent from the list
        this.agents.remove(removedAgent);

        // Create replacement AntiSocialAgent
        AntiSocialAgent newAgent = new AntiSocialAgent(x, y, rad);
        this.agents.addFirst(newAgent);

        // Update all agents and count how many moved
        int movedCount = 0;
        LinkedList.Node<Agent> node = this.agents.getHead();
        while (node != null) {
            node.getData().updateState(this);
            if (node.getData().getMoved()) {
                movedCount++;
            }
            node = node.getNext();
        }
        return movedCount;
    }

    /**
     * Calls the draw method of all the agents on the Landscape.
     * 
     * @param g the graphics context for drawing
     */
    public void draw(Graphics g) {
        // Handle null graphics context (like during testing)
        if (g == null) {
            return;
        }

        // Draw all agents in the landscape
        LinkedList.Node<Agent> current = this.agents.getHead();
        while (current != null) {
            current.getData().draw(g);
            current = current.getNext();
        }
    }

    /**
     * Gets the agents list for extension experimental analysis purposes.
     * 
     * @return the LinkedList of agents
     */
    public LinkedList<Agent> getAgents() {
        return this.agents;
    }
}