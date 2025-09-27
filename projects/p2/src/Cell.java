/*
file name:      Cell.java
Authors:        Jack Dai
last modified:  09/26/2025
purpose:        This class creates Cell object and the Cell object
                will represent a location on the Landscape
*/

import java.util.ArrayList;

public class Cell {

    /**
     * The status of the Cell.
     */
    private boolean alive;

    /**
     * Constructs a dead cell.
     */
    public Cell() {
        alive = false;
    }

    /**
     * Constructs a cell with the specified status.
     * 
     * @param status a boolean to specify if the Cell is initially alive
     */
    public Cell(boolean status) {
        // if status == true then alive == true
        // false otherwise
        alive = status;
    }

    /**
     * Returns whether the cell is currently alive.
     * 
     * @return whether the cell is currently alive
     */
    public boolean getAlive() {
        return alive;
    }

    /**
     * Sets the current status of the cell to the specified status.
     * 
     * @param status a boolean to specify if the Cell is alive or dead
     */
    public void setAlive(boolean status) {
        alive = status;
    }

    /**
     * Updates the state of the Cell.
     * 
     * If this Cell is alive and if there are 2 or 3 alive neighbors,
     * this Cell stays alive. Otherwise, it dies.
     * 
     * If this Cell is dead and there are 3 alive neighbors,
     * this Cell comes back to life. Otherwise, it stays dead.
     * 
     * @param neighbors An ArrayList of Cells
     */
    public void updateState(ArrayList<Cell> neighbors) {
        int aliveCount = 0;
        // first check neighboring cells near c
        for (Cell c : neighbors) {
            if (c != null && c.getAlive()) {
                aliveCount++;
            }
        }
        // then check if the cell is alive
        boolean nextAlive;
        if (alive) {
            nextAlive = (aliveCount == 2 || aliveCount == 3);
        } else {
            nextAlive = (aliveCount == 3);
        }
        alive = nextAlive;
    }

    /**
     * Returns a String representation of this Cell.
     * 
     * @return 1 if this Cell is alive, otherwise 0.
     */
    public String toString() {
        return alive ? "1" : "0";
    }
}