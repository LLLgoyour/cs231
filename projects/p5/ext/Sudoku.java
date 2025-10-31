/*
 * file name: Sudoku.java
 * author: Jack Dai
 * last modified: 10/28/2025
 * purpose of the class:
 * Sudoku solver that uses simple backtracking with an explicit stack.
 * The solver supports a small visualization hook (via LandscapeDisplay)
 * when delay > 0. It also provides a time-limited solve()
 * method for experiments.
 */

import java.util.*;

public class Sudoku {
    private Board board;
    private LandscapeDisplay ld;
    // whether the most recent call to solve(maxIters) ended due to hitting the
    // iteration limit
    private boolean lastTimedOut = false;
    private int delay; // milliseconds; 0 => no visualization
    /**
     * Selection strategy for choosing the next empty cell.
     * 0 = row-major (first empty), 1 = MRV (minimum remaining values),
     * 2 = MRV with degree tie-breaker, 3 = random empty cell with a valid value
     */
    private int strategy = 1; // default = MRV
    private final Random rand = new Random();

    /**
     * Create a Sudoku with a randomly generated board containing numLocked fixed
     * values.
     * 
     * @param numLocked number of cells to pre-fill and lock on the board (randomly
     *                  placed)
     * @param delay     visualization delay in milliseconds; set to 0 to disable
     *                  drawing
     */
    public Sudoku(int numLocked, int delay) {
        this.board = new Board(numLocked);
        this.delay = delay;
        this.ld = (delay > 0) ? new LandscapeDisplay(board) : null;
    }

    /**
     * Create a Sudoku by reading a board from a file.
     * 
     * @param filename path to a board file to read initial values from
     * @param delay    visualization delay in milliseconds; set to 0 to disable
     *                 drawing
     */
    public Sudoku(String filename, int delay) {
        this.board = new Board(filename);
        this.delay = delay;
        this.ld = (delay > 0) ? new LandscapeDisplay(board) : null;
    }

    /**
     * New constructors that allow setting selection strategy directly.
     * 
     * @param numLocked
     * @param delay
     * @param strategy
     */
    public Sudoku(int numLocked, int delay, int strategy) {
        this(numLocked, delay);
        this.strategy = strategy;
    }

    public Sudoku(String filename, int delay, int strategy) {
        this(filename, delay);
        this.strategy = strategy;
    }

    public void setStrategy(int strategy) {
        this.strategy = strategy;
    }

    /**
     * Find the next valid value for the provided cell.
     *
     * @param cell cell to find a next value for (must not be locked)
     * @return next valid value or 0 if none
     */
    public int findNextValue(Cell cell) {
        if (cell == null)
            return 0;
        if (cell.isLocked())
            return 0;

        int start = cell.getValue() + 1;
        for (int v = start; v <= board.getCols(); v++) {
            if (board.validValue(cell.getRow(), cell.getCol(), v)) {
                return v;
            }
        }
        return 0;
    }

    /**
     * Row-major (left-to-right, top-to-bottom) next cell selection.
     * Returns the next Cell to assign following a simple row-major scan. The
     * returned Cell will already have a valid value assigned via
     * findNextValue(Cell). If no empty cell remains, or if the first
     * encountered empty cell has no valid values, this method returns null.
     */
    private Cell findNextCellRowMajor() {
        int rows = board.getRows();
        int cols = board.getCols();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = board.get(r, c);
                if (cell.isLocked() || cell.getValue() != 0)
                    continue;
                int val = findNextValue(cell);
                if (val != 0) {
                    cell.setValue(val);
                    return cell;
                } else {
                    // this empty cell has no possible values => dead end
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * MRV (minimum remaining values) selection. Existing behavior.
     * 
     * @param degreeTieBreak
     *                       whether to break ties by selecting the cell with the
     *                       highest
     *                       degree (most empty neighbors).
     * @return the chosen Cell with a value assigned, or null if no assignment is
     *         possible (either no empty cells or a dead-end where an empty cell
     *         has zero possible values).
     */
    private Cell findNextCellMRV(boolean degreeTieBreak) {
        int bestR = -1, bestC = -1;
        int bestCount = Integer.MAX_VALUE;
        int bestDegree = -1;
        int rows = board.getRows();
        int cols = board.getCols();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = board.get(r, c);
                if (cell.isLocked() || cell.getValue() != 0)
                    continue;

                int count = 0;
                for (int v = 1; v <= board.getCols(); v++) {
                    if (board.validValue(r, c, v))
                        count++;
                }

                if (count == 0)
                    return null;

                int degree = 0;
                if (degreeTieBreak) {
                    // count empty neighbors in same row, column and block (unique)
                    for (int cc = 0; cc < cols; cc++) {
                        if (cc == c)
                            continue;
                        if (board.get(r, cc).getValue() == 0)
                            degree++;
                    }
                    for (int rr = 0; rr < rows; rr++) {
                        if (rr == r)
                            continue;
                        if (board.get(rr, c).getValue() == 0)
                            degree++;
                    }
                    int br = (r / 3) * 3;
                    int bc = (c / 3) * 3;
                    for (int rr = br; rr < br + 3; rr++) {
                        for (int cc = bc; cc < bc + 3; cc++) {
                            if (rr == r && cc == c)
                                continue;
                            if (board.get(rr, cc).getValue() == 0)
                                degree++;
                        }
                    }
                }

                if (count < bestCount || (count == bestCount && degreeTieBreak && degree > bestDegree)) {
                    bestCount = count;
                    bestR = r;
                    bestC = c;
                    bestDegree = degree;
                    if (bestCount == 1 && !degreeTieBreak)
                        break;
                }
            }
            if (bestCount == 1 && !degreeTieBreak)
                break;
        }

        if (bestR == -1)
            return null;

        Cell chosen = board.get(bestR, bestC);
        int val = findNextValue(chosen);
        if (val != 0) {
            chosen.setValue(val);
            return chosen;
        }
        return null;
    }

    /**
     * Random empty cell selection: pick a random empty cell that has at least one
     * valid value, assign first valid value encountered.
     *
     * @return a randomly selected empty Cell with a valid value assigned, or
     *         null if no empty cell with a valid assignment exists.
     */
    private Cell findNextCellRandom() {
        int rows = board.getRows();
        int cols = board.getCols();
        ArrayList<Cell> empties = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = board.get(r, c);
                if (!cell.isLocked() && cell.getValue() == 0)
                    empties.add(cell);
            }
        }
        if (empties.isEmpty())
            return null;

        Collections.shuffle(empties, rand);
        for (Cell cell : empties) {
            int val = findNextValue(cell);
            if (val != 0) {
                cell.setValue(val);
                return cell;
            } else {
                // if first randomly chosen empty has no values, skip to next
                continue;
            }
        }
        return null;
    }

    /**
     * Dispatch to the chosen selection strategy.
     *
     * @return a Cell chosen by the configured selection strategy with a value
     *         already assigned, or null if no valid assignment can be made.
     */
    public Cell findNextCell() {
        switch (strategy) {
            case 0:
                return findNextCellRowMajor();
            case 1:
                return findNextCellMRV(false);
            case 2:
                return findNextCellMRV(true);
            case 3:
                return findNextCellRandom();
            default:
                return findNextCellMRV(false);
        }
    }

    /**
     * Solve with a maximum number of iterations (main while-loop iterations).
     *
     * @param maxIters maximum number of loop iterations before timing out
     * @return true if a solution was found; false if failed or
     *         timed out
     */
    public boolean solve(int maxIters) {
        LinkedList<Cell> stack = new LinkedList<>();

        int totalToFill = board.getRows() * board.getCols() - board.numLocked();

        // mark not finished while solving
        board.setFinished(false);

        int iters = 0;
        lastTimedOut = false;

        while (stack.size() < totalToFill) {
            // iteration limit check
            if (++iters > maxIters) {
                // timeout
                lastTimedOut = true;
                board.setFinished(true);
                if (ld != null)
                    ld.repaint();
                return false;
            }

            if (delay > 0) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                if (ld != null) {
                    ld.repaint();
                }
            }

            Cell next = findNextCell();

            while (next == null && stack.size() > 0) {
                Cell back = stack.pop();
                int v = findNextValue(back);
                back.setValue(v);
                if (ld != null) {
                    ld.repaint();
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                if (v != 0) {
                    next = back;
                    break;
                } else {
                    // ensure it's reset to 0 and continue backtracking
                    back.setValue(0);
                }
            }

            if (next == null) {
                // stack empty and no next => no solution (not a timeout)
                lastTimedOut = false;
                board.setFinished(true); // indicate final state so draw() can show message
                if (ld != null) {
                    ld.repaint();
                }
                return false;
            } else {
                stack.push(next);
            }
        }

        // solved
        lastTimedOut = false;
        board.setFinished(true);
        if (ld != null) {
            ld.repaint();
        }
        return true;
    }

    /**
     * Return true if the most recent call to solve() ended because
     * the iteration limit was exceeded.
     *
     * @return true if the last timed run timed out
     */
    public boolean wasLastTimeout() {
        return lastTimedOut;
    }

    /**
     * Solve the board using an explicit stack for backtracking.
     *
     * @return true if a solution was found and the board is filled with
     *         a valid solution.
     */
    public boolean solve() {
        LinkedList<Cell> stack = new LinkedList<>();

        int totalToFill = board.getRows() * board.getCols() - board.numLocked();

        // mark not finished while solving
        board.setFinished(false);

        while (stack.size() < totalToFill) {
            if (delay > 0) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                if (ld != null) {
                    ld.repaint();
                }
            }
            Cell next = findNextCell();

            while (next == null && stack.size() > 0) {
                Cell back = stack.pop();
                int v = findNextValue(back);
                back.setValue(v);
                if (ld != null) {
                    ld.repaint();
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                if (v != 0) {
                    next = back;
                    break;
                } else {
                    // ensure it's reset to 0 and continue backtracking
                    back.setValue(0);
                }
            }

            if (next == null) {
                // stack empty and no next => no solution
                board.setFinished(true); // indicate final state so draw() can show message
                if (ld != null) {
                    ld.repaint();
                }
                return false;
            } else {
                stack.push(next);
            }
        }

        // solved
        board.setFinished(true);
        if (ld != null) {
            ld.repaint();
        }
        return true;
    }

    /**
     * constructs a Sudoku from a filename (or
     * random) and attempts to solve it, printing initial and final boards.
     */
    public static void main(String[] args) {
        Sudoku s;
        if (args.length == 0) {
            // default: random small board with few locked values and no delay
            s = new Sudoku(0, 0);
        } else if (args.length == 1) {
            s = new Sudoku(args[0], 0);
        } else if (args.length == 2) {
            int numLocked = Integer.parseInt(args[0]);
            int delay = Integer.parseInt(args[1]);
            s = new Sudoku(numLocked, delay);
        } else {
            s = new Sudoku(args[0], Integer.parseInt(args[1]));
        }

        System.out.println("Initial board:\n" + s.board.toString());
        boolean solved = s.solve();
        System.out.println("Solved? " + solved);
        System.out.println("Final board:\n" + s.board.toString());
    }
}