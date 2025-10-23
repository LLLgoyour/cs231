public class Cell {
    private boolean locked;
    private int row;
    private int col;
    private int value;

    /**
     * initialize the row, column, and value fields to the given parameter values.
     * Set the locked field to false in default;
     * 
     * @param row
     * @param col
     * @param value
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
     * @param row
     * @param col
     * @param value
     * @param locked
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
     * 
     * @param newval
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
     * set the Cell's locked field to the new
     * value.
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
}
