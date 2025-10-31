/*
 * file name: Board.java
 * author: Jack Dai
 * last modified: 10/26/2025
 * purpose of the class:
 * The Board class holds the array of Cells that make up the Sudoku board. 
 */

import java.io.*;
import java.util.*;
import java.awt.*;

public class Board {
    private boolean finished;
    /** Board size (rows and columns). */
    private static final int SIZE = 9;

    /** 9x9 grid of cells. Row-major: cells[row][col]. */
    private final Cell[][] cells;

    /**
     * Construct an empty 9x9 board. All cells are initialized with value 0 and not
     * locked. Rows and columns are 0-indexed: 0..8.
     */
    public Board() {
        this.cells = new Cell[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                this.cells[r][c] = new Cell(r, c, 0, false);
            }
        }
    }

    /**
     * Construct a board by first creating an empty 9x9 grid, then reading initial
     * values from the given file.
     *
     * @param filename path to the input file
     * @throws RuntimeException if the file cannot be read or the format is
     *                          invalid
     */
    public Board(String filename) {
        this();
        read(filename);
    }

    public Board(int numFixed) {
        // validate parameter
        if (numFixed < 0 || numFixed > SIZE * SIZE) {
            throw new IllegalArgumentException("numFixed must be between 0 and " + (SIZE * SIZE));
        }

        // create empty board first
        this.cells = new Cell[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                this.cells[r][c] = new Cell(r, c, 0, false);
            }
        }

        // randomly place `numFixed` locked cells with valid values
        Random rand = new Random();
        int placed = 0;
        // guard against pathological cases by attempting until placed or many tries
        int attempts = 0;
        while (placed < numFixed && attempts < numFixed * 1000) {
            attempts++;
            int r = rand.nextInt(SIZE);
            int c = rand.nextInt(SIZE);
            if (cells[r][c].isLocked())
                continue; // already filled

            // try values 1..9 in random order
            ArrayList<Integer> vals = new ArrayList<>();
            for (int v = 1; v <= SIZE; v++)
                vals.add(v);
            Collections.shuffle(vals, rand);

            boolean placedHere = false;
            for (int v : vals) {
                if (validValue(r, c, v)) {
                    cells[r][c].setValue(v);
                    cells[r][c].setLocked(true);
                    placed++;
                    placedHere = true;
                    break;
                }
            }

            // if no valid value found for this cell, try another cell
            if (!placedHere)
                continue;
        }

        if (placed < numFixed) {
            // couldn't place requested number (very unlikely unless impossible request)
            throw new RuntimeException(
                    "Unable to place " + numFixed + " locked cells after many attempts; placed " + placed);
        }
    }

    /**
     * Get the Cell at the specified row and column.
     *
     * @param row row index (0..8)
     * @param col column index (0..8)
     * @return the cell at (row, col)
     * @throws IndexOutOfBoundsException if row/col are outside 0..8
     */
    public Cell get(int row, int col) {
        checkIndices(row, col);
        return cells[row][col];
    }

    /**
     * Set the numeric value for the cell at (row, col).
     *
     * @param row   row index (0..8)
     * @param col   column index (0..8)
     * @param value new numeric value to set
     * @throws IndexOutOfBoundsException if row/col are outside 0..8
     */
    public void set(int row, int col, int value) {
        checkIndices(row, col);
        cells[row][col].setValue(value);
    }

    /** Set both value and locked state on the cell at (row,col). */
    public void set(int row, int col, int value, boolean locked) {
        checkIndices(row, col);
        cells[row][col].setValue(value);
        cells[row][col].setLocked(locked);
    }

    /**
     * Set the locked state for the cell at (row, col).
     *
     * @param row    row index (0..8)
     * @param col    column index (0..8)
     * @param locked whether the cell should be locked
     * @throws IndexOutOfBoundsException if row/col are outside 0..8
     */
    public void set(int row, int col, boolean locked) {
        checkIndices(row, col);
        cells[row][col].setLocked(locked);
    }

    /**
     * Read board values from a file and print helpful debug output as lines are
     * processed.
     *
     * @param filename path to the input file
     * @return true if the file was read successfully; false otherwise
     */
    public boolean read(String filename) {
        try {
            // assign to a variable of type FileReader a new FileReader object, passing
            // filename to the constructor
            FileReader fr = new FileReader(filename);
            // assign to a variable of type BufferedReader a new BufferedReader, passing the
            // FileReader variable to the constructor
            BufferedReader br = new BufferedReader(fr);

            // row counter for assigning into the board
            int r = 0;

            // assign to a variable of type String line the result of calling the readLine
            // method of your BufferedReader object.
            String line = br.readLine();
            // start a while loop that loops while line isn't null
            while (line != null && r < SIZE) {
                // print line
                System.out.println(line);
                // assign to an array of Strings the result of splitting the line up by spaces
                // (line.split("[ ]+"))
                String[] arr = line.trim().split("[ ]+");
                // let's see what this array holds:
                if (arr.length > 1) {
                    System.out.println("the first item in arr: " + arr[0] + ", the second item in arr: " + arr[1]);
                } else if (arr.length == 1) {
                    System.out.println("the first item in arr: " + arr[0] + ", the second item in arr: <none>");
                } else {
                    System.out.println("the first item in arr: <none>, the second item in arr: <none>");
                }
                // print the size of the String array (you can use .length)
                System.out.println(arr.length);
                // use the line to set various Cells of this Board accordingly
                // Parse up to 9 columns (ignore extras). If fewer than 9, remaining cells stay
                // default (0, unlocked)
                for (int c = 0; c < Math.min(SIZE, arr.length); c++) {
                    try {
                        int v = Integer.parseInt(arr[c]);
                        cells[r][c].setValue(v);
                        cells[r][c].setLocked(v != 0);
                    } catch (NumberFormatException nfe) {
                        // non-integer token: treat as 0 (empty)
                        cells[r][c].setValue(0);
                        cells[r][c].setLocked(false);
                    }
                }
                r++;
                // assign to line the result of calling the readLine method of your
                // BufferedReader object.
                line = br.readLine();
            }
            // call the close method of the BufferedReader
            br.close();
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("Board.read():: unable to open file " + filename);
        } catch (IOException ex) {
            System.out.println("Board.read():: error reading file " + filename);
        }

        return false;
    }

    /*
     * Board accessors and utilities
     */

    public int getCols() {
        return SIZE;
    }

    public int getRows() {
        return SIZE;
    }

    public boolean isLocked(int r, int c) {
        checkIndices(r, c);
        return cells[r][c].isLocked();
    }

    public int numLocked() {
        int cnt = 0;
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (cells[r][c].isLocked())
                    cnt++;
            }
        }
        return cnt;
    }

    public int value(int r, int c) {
        checkIndices(r, c);
        return cells[r][c].getValue();
    }

    /**
     * tests if the given value is a valid value at the given row and column of the
     * board
     * It should make sure the value is unique in its row, in its column, and in its
     * local 3x3 square.
     * 
     * @param row   the target row index (0-based)
     * @param col   the target column index (0-based)
     * @param value the value to test (must be in 1..SIZE)
     * @return true if placing value at (row,col) would not violate Sudoku
     *         constraints (row, column, and 3x3 block uniqueness); false
     *         otherwise (also false when value is out of range)
     */
    public boolean validValue(int row, int col, int value) {
        // value must be between 1 and SIZE (inclusive)
        if (value < 1 || value > SIZE)
            return false;

        checkIndices(row, col);

        // Check row uniqueness (ignore the cell at col)
        for (int c = 0; c < SIZE; c++) {
            if (c == col)
                continue;
            if (cells[row][c].getValue() == value)
                return false;
        }

        // Check column uniqueness (ignore the cell at row)
        for (int r = 0; r < SIZE; r++) {
            if (r == row)
                continue;
            if (cells[r][col].getValue() == value)
                return false;
        }

        // Check 3x3 block
        int br = (row / 3) * 3;
        int bc = (col / 3) * 3;
        for (int r = br; r < br + 3; r++) {
            for (int c = bc; c < bc + 3; c++) {
                if (r == row && c == col)
                    continue;
                if (cells[r][c].getValue() == value)
                    return false;
            }
        }

        return true;
    }

    /**
     * Check whether the current board contains a valid completed solution.
     *
     * @return true if every cell contains a value in 1..SIZE and each value
     *         satisfies Sudoku constraints in its row, column and 3x3 block;
     *         false otherwise.
     */
    public boolean validSolution() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                int v = cells[r][c].getValue();
                if (v < 1 || v > SIZE)
                    return false;
                if (!validValue(r, c, v))
                    return false;
            }
        }
        return true;
    }

    /**
     * Ensure row and col are within 0..8.
     *
     * @param row the row index to validate (0-based)
     * @param col the column index to validate (0-based)
     */
    private void checkIndices(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            throw new IndexOutOfBoundsException("row and col must be in 0..8: got (" + row + ", " + col + ")");
        }
    }

    /**
     * Set the finished flag which controls whether the draw method prints the
     * completion message. Sudoku should call this when it finishes attempting a
     * solve (success or failure).
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * Generate a multi-line string representation of the 9x9 board.
     * Each row is one line with values separated by a single space, matching the
     * input file format. This makes it easy to compare to the original file.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                sb.append(cells[r][c].getValue());
                if (c < SIZE - 1)
                    sb.append(' ');
            }
            if (r < SIZE - 1)
                sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Draw the board onto the provided Graphics context.
     *
     * @param g     the Graphics context to draw on
     * @param scale pixel scale to position and size cells (e.g. 30 or 40)
     */
    public void draw(Graphics g, int scale) {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                get(i, j).draw(g, j * scale + 5, i * scale + 10, scale);
            }
        }
        if (finished) {
            if (validSolution()) {
                g.setColor(new Color(0, 127, 0));
                g.drawChars("Hurray!".toCharArray(), 0, "Hurray!".length(), scale * 3 + 5, scale * 10 + 10);
            } else {
                g.setColor(new Color(127, 0, 0));
                g.drawChars("No solution!".toCharArray(), 0, "No Solution!".length(), scale * 3 + 5, scale * 10 + 10);
            }
        }
    }

    /**
     * Simple entry point that loads a board from a file (defaults to "board1.txt")
     * so you can observe the debug output from Board.read(String).
     * 
     * @param args
     */
    public static void main(String[] args) {
        String filename = (args != null && args.length > 0) ? args[0] : "board1.txt";
        Board b = new Board(filename); // constructor calls read(filename), which prints debug info
        // After reading, print the board in a format matching the input file
        System.out.println(b.toString());
        // tell whether the board read from file is solved
        System.out.println("Solved? " + b.validSolution());
    }
}