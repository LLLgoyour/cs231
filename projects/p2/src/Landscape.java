/*
file name:      Landscape.java
Authors:        Jack Dai
last modified:  09/26/2025
purpose of this class:  
*/

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Landscape {

    /**
     * The underlying grid of Cells for Conway's Game
     */
    private Cell[][] landscape;

    /**
     * The original probability each individual Cell is alive
     */
    private double initialChance;

    /**
     * Constructs a Landscape of the specified number of rows and columns.
     * All Cells are initially dead.
     * 
     * @param rows    the number of rows in the Landscape
     * @param columns the number of columns in the Landscape
     */
    public Landscape(int rows, int columns) {
        landscape = new Cell[rows][columns];
        reset();
    }

    /**
     * Constructs a Landscape of the specified number of rows and columns.
     * Each Cell is initially alive with probability specified by chance.
     * 
     * @param rows    the number of rows in the Landscape
     * @param columns the number of columns in the Landscape
     * @param chance  the probability each individual Cell is initially alive
     */
    public Landscape(int rows, int columns, double chance) {
        Random r = new Random();
        landscape = new Cell[rows][columns];
        reset();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (r.nextDouble() < chance) {
                    // if a random number is below the probability
                    // then set the cell alive
                    landscape[i][j].setAlive(true);
                } else {
                    // set the cell other
                    landscape[i][j].setAlive(false);
                }
            }
        }
    }

    /**
     * Recreates the Landscape according to the specifications given
     * in its initial construction.
     */
    public void reset() {
        // initialize each cell as dead
        for (int i = 0; i < landscape.length; i++) {
            for (int j = 0; j < landscape[0].length; j++) {
                landscape[i][j] = new Cell(false);
            }
        }
    }

    /**
     * Returns the number of rows in the Landscape.
     * 
     * @return the number of rows in the Landscape
     */
    public int getRows() {
        return landscape.length;
    }

    /**
     * Returns the number of columns in the Landscape.
     * 
     * @return the number of columns in the Landscape
     */
    public int getCols() {
        return landscape[0].length;
    }

    /**
     * Returns the Cell specified the given row and column.
     * 
     * @param row the row of the desired Cell
     * @param col the column of the desired Cell
     * @return the Cell specified the given row and column
     */
    public Cell getCell(int row, int col) {
        return landscape[row][col];
    }

    /**
     * Returns a String representation of the Landscape.
     */
    public String toString() {
        String s = "";
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                s = s + landscape[i][j].toString();
                if (j < getCols() - 1) {
                    s = s + " ";
                }
            }
            if (i < getRows() - 1) {
                s = s + "\n";
            }
        }
        return s;
    }

    /**
     * Returns an ArrayList of the neighboring Cells to the specified location.
     * 
     * @param row the row of the specified Cell
     * @param col the column of the specified Cell
     * @return an ArrayList of the neighboring Cells to the specified location
     */
    public ArrayList<Cell> getNeighbors(int row, int col) {
        ArrayList<Cell> neighbors = new ArrayList<Cell>();
        // iterate from -1 to 1
        // and skip landscape[row][col]
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue; // skip the cell itself
                }
                int r = row + i;
                int c = col + j;
                // make sure the neighboring cells are not out of bound
                if (r >= 0 && r < getRows() && c >= 0 && c < getCols()) {
                    neighbors.add(landscape[r][c]);
                }
            }
        }
        return neighbors;
    }

    /**
     * Advances the current Landscape by one step.
     */
    public void advance() {
    }

    /**
     * Draws the Cell to the given Graphics object at the specified scale.
     * An alive Cell is drawn with a black color; a dead Cell is drawn gray.
     * 
     * @param g     the Graphics object on which to draw
     * @param scale the scale of the representation of this Cell
     */
    public void draw(Graphics g, int scale) {
        for (int x = 0; x < getRows(); x++) {
            for (int y = 0; y < getCols(); y++) {
                g.setColor(getCell(x, y).getAlive() ? Color.BLACK : Color.gray);
                g.fillOval(x * scale, y * scale, scale, scale);
            }
        }
    }

    public static void main(String[] args) {
    }
}