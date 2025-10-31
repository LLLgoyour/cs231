/*
 * file name: Cell.java
 * author: Jack Dai
 * last modified: 10/26/2025
 * purpose of the class:
 * The Cell will represent one location in a Sudoku board.
 */

import java.awt.*;

public class Cell {
    private boolean locked;
    private int row;
    private int col;
    private int value;

    /**
     * initialize the row, column, and value fields to the given parameter values.
     * Set the locked field to false in default;
     * 
     * @param row   the row index of this cell (0-based)
     * @param col   the column index of this cell (0-based)
     * @param value the initial numeric value for the cell (0 means empty)
     */
    public Cell(int row, int col, int value) {
        locked = false;
        this.row = row;
        this.col = col;
        this.value = value;
    }

    /**
     * initialize the row, column, value, and locked fields to the given parameter
     * values.
     * 
     * @param row    the row index of this cell (0-based)
     * @param col    the column index of this cell (0-based)
     * @param value  the initial numeric value for the cell (0 means empty)
     * @param locked whether the cell should be marked locked (immutable)
     */
    public Cell(int row, int col, int value, boolean locked) {
        this.row = row;
        this.col = col;
        this.value = value;
        this.locked = locked;
    }

    /**
     * 
     * @return the Cell's row index
     */
    public int getRow() {
        return this.row;
    }

    /**
     * 
     * @return this Cell's column index
     */
    public int getCol() {
        return this.col;
    }

    /**
     * @return the Cell's value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Set the numeric value for this cell.
     *
     * @param newval the new numeric value (0 means empty)
     */
    public void setValue(int newval) {
        this.value = newval;
    }

    /**
     * 
     * @return the value of the locked field
     */
    public boolean isLocked() {
        return this.locked;
    }

    /**
     * Set the cell's locked state. Locked cells are treated as fixed clues and
     * should not be changed by a solver.
     */
    public void setLocked(boolean lock) {
        this.locked = lock;
    }

    /**
     * The toString function should generate and return a representating String. In
     * this case, have it return a string with the Cell's numeric value.
     */
    @Override
    public String toString() {
        return Integer.toString(this.value);
    }

    /**
     * Draw this cell on a graphics context at the given pixel coordinates.
     *
     * @param g     the Graphics context to draw on
     * @param x     the x-coordinate (pixels) of the top-left corner of the cell
     * @param y     the y-coordinate (pixels) of the top-left corner of the cell
     * @param scale the width/height in pixels of the cell square
     */
    public void draw(Graphics g, int x, int y, int scale) {
        char toDraw = (char) ((int) '0' + getValue());
        g.setColor(isLocked() ? Color.BLUE : Color.RED);
        g.drawChars(new char[] { toDraw }, 0, 1, x, y);
    }
}
