import java.io.*;

public class Board {
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
     * <p>
     * Assumed file format (simple and common for Sudoku): 9 lines, each line with
     * 9 integers separated by whitespace. 0 means empty; non-zero values are
     * treated as locked (given) cells.
     * </p>
     *
     * @param filename path to the input file
     * @throws RuntimeException if the file cannot be read or the format is
     *                          invalid
     */
    public Board(String filename) {
        this();
        read(filename);
    }

    /**
     * Get the {@link Cell} at the specified row and column.
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
     * <p>
     * Note: This implementation does not enforce locking rules. If your
     * assignment requires preventing updates to locked cells, add a check like
     * {@code if (cells[row][col].isLocked()) throw new IllegalStateException(...);}.
     * </p>
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
     * <p>
     * Expected format: 9 lines, each with 9 integers separated by spaces. 0 means
     * empty and will be unlocked; non-zero will be set and locked (treating them
     * as givens).
     * </p>
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

    /**
     * Simple entry point that loads a board from a file (defaults to "board1.txt")
     * so you can observe the debug output from {@link #read(String)}.
     */
    public static void main(String[] args) {
        String filename = (args != null && args.length > 0) ? args[0] : "board1.txt";
        Board b = new Board(filename); // constructor calls read(filename), which prints debug info
        // After reading, print the board in a format matching the input file
        System.out.println(b.toString());
    }

    /** Ensure row and col are within 0..8. */
    private void checkIndices(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            throw new IndexOutOfBoundsException("row and col must be in 0..8: got (" + row + ", " + col + ")");
        }
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
}
