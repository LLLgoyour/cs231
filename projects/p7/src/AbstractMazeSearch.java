/*
 * file name: AbstractMazeSearch.java
 * author: Jack Dai
 * last modified: 11/16/2025
 * purpose of the class:
 * To unite DFS, BFS, A* classes to search a maze (as they will all behave extremely similarly).
 */

import java.awt.Color;
import java.awt.Graphics;

public abstract class AbstractMazeSearch {

    private Maze maze;
    private Cell start;
    private Cell target;
    private Cell cur;

    public AbstractMazeSearch(Maze maze) {
        this.maze = maze;
        this.start = null;
        this.target = null;
        this.cur = null;
    }

    // Abstract methods for specific search strategies

    /**
     * Return and remove the next Cell to explore from the search
     * frontier maintained by the concrete search strategy.
     *
     * @return the next Cell to explore, or null if the frontier
     *         is empty
     */
    public abstract Cell findNextCell();

    /**
     * Add the given Cell to the search frontier maintained by the
     * concrete strategy (e.g. push to a stack for DFS, offer to a queue for
     * BFS, or insert into a priority queue for A*).
     *
     * @param next the Cell to add to the frontier
     */
    public abstract void addCell(Cell next);

    /**
     * Update the representation of next inside the frontier when its
     * priority or predecessor changes. This is only meaningful for strategies
     * that support priority updates (for example, A*). Implementations that do
     * not support reprioritization may provide a no-op implementation.
     *
     * @param next the Cell whose stored state should be updated
     */
    public abstract void updateCell(Cell next);

    /**
     * Return the number of cells currently stored in the search frontier
     * (the number of future cells to explore).
     *
     * @return the number of cells remaining in the frontier
     */
    public abstract int numRemainingCells();

    // Accessors / mutators

    /**
     * This method returns the underlying Maze.
     * 
     * @return the underlying Maze
     */
    public Maze getMaze() {
        return this.maze;
    }

    /**
     * This method returns the target of the search.
     * 
     * @return the target of the search
     */
    public Cell getTarget() {
        return this.target;
    }

    /**
     * This method sets the given cell to be the current location of the search.
     * 
     * @param cell
     */
    public void setCur(Cell cell) {
        this.cur = cell;
    }

    /**
     * This method returns the current Cell location of the search.
     * 
     * @return the current Cell location of the search.
     */
    public Cell getCur() {
        return this.cur;
    }

    /**
     * This method returns the start of the search.
     * 
     * @return the start of the search.
     */
    public Cell getStart() {
        return this.start;
    }

    /**
     * This method resets the current, start, and target Cells to be null.
     */
    public void reset() {
        this.start = null;
        this.target = null;
        this.cur = null;
    }

    /**
     * Draws the maze, all explored paths (via prev pointers), and the final path
     * from start to target if it exists.
     * 
     * @param g     the Graphics object to draw on.
     * @param scale the size of each cell in pixels.
     */
    public void draw(Graphics g, int scale) {
        // Draws the base version of the maze
        getMaze().draw(g, scale);
        // Draws the paths taken by the searcher
        getStart().drawAllPrevs(getMaze(), g, scale, Color.RED);
        // Draws the start cell
        getStart().draw(g, scale, Color.BLUE);
        // Draws the target cell
        getTarget().draw(g, scale, Color.RED);
        // Draws the current cell
        getCur().draw(g, scale, Color.MAGENTA);

        // If the target has been found, draws the path taken by the searcher to reach
        // the target sans backtracking.
        if (getTarget().getPrev() != null) {
            Cell traceBackCur = getTarget().getPrev();
            while (!traceBackCur.equals(getStart())) {
                traceBackCur.draw(g, scale, Color.GREEN);
                traceBackCur = traceBackCur.getPrev();
            }
            getTarget().drawPrevPath(g, scale, Color.BLUE);
        }
    }

    /**
     * Builds the path from start to the specified cell by
     * following prev pointers on each Cell. If the prev
     * chain does not lead back to the stored start cell (for example,
     * because prev is null or a cycle is detected), this method
     * returns null.
     *
     * @param cell the destination Cell to trace back from
     * @return a LinkedList containing the path from start to
     *         cell (inclusive), ordered from start to cell, or
     *         null if no valid path from start exists
     */
    public LinkedList<Cell> traceback(Cell cell) {
        if (cell == null || start == null) {
            return null;
        }

        LinkedList<Cell> path = new LinkedList<Cell>();
        Cell current = cell;
        java.util.HashSet<Cell> seen = new java.util.HashSet<>();

        // Follow prev pointers back to the start, but guard against cycles
        while (current != null) {
            // if we've already visited this cell while tracing back, there is a cycle
            if (seen.contains(current)) {
                break;
            }
            seen.add(current);
            path.addFirst(current);
            if (current == current.getPrev()) {
                break;
            }
            if (current.getPrev() == null) {
                break;
            }
            current = current.getPrev();
        }

        // Verify that path really starts at "start"
        if (path.isEmpty() || path.get(0) != start) {
            return null;
        }
        return path;
    }

    /**
     * Main search routine that finds a path from start to
     * target using the concrete search strategy implemented by the
     * subclass. The method manages the search frontier through the abstract
     * methods addCell(Cell), findNextCell(),
     * updateCell(Cell), and numRemainingCells().
     * 
     * If display is true, this method will create a
     * MazeSearchDisplay (using scale 20) and will call
     * Thread.sleep(delay) and repaint() between each step so
     * the search progress can be visualized. Pass display=false to
     * run the search without creating a GUI.
     *
     * @param start   the Cell at which the search begins
     * @param target  the Cell to find
     * @param display if true show the MazeSearchDisplay
     * @param delay   number of milliseconds to sleep between displayed steps
     * @return a LinkedList representing the found path from
     *         start to target (inclusive) ordered from start to
     *         target, or null if no path exists
     */
    public LinkedList<Cell> search(Cell start, Cell target, boolean display, int delay) {
        this.start = start;
        this.target = target;
        setCur(start);

        // For drawing correctness
        start.setPrev(start);

        // create display if requested
        MazeSearchDisplay displayWin = null;
        if (display) {
            displayWin = new MazeSearchDisplay(this, 20);
        }

        // initialize frontier with starting cell
        addCell(start);

        Maze maze = getMaze();

        while (numRemainingCells() > 0) {
            // visualization: sleep and repaint
            if (display && displayWin != null) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                }
                displayWin.repaint();
            }

            Cell current = findNextCell();
            setCur(current);

            // explore neighbors
            for (Cell neighbor : maze.getNeighbors(current)) {
                if (neighbor.getPrev() == null) {
                    neighbor.setPrev(current);
                    addCell(neighbor);
                } else {
                    // check if this is a faster path using path length comparison
                    LinkedList<Cell> oldPath = traceback(neighbor);
                    Cell oldPrev = neighbor.getPrev();
                    neighbor.setPrev(current);
                    LinkedList<Cell> newPath = traceback(neighbor);

                    boolean better = false;
                    if (oldPath == null && newPath != null) {
                        better = true;
                    } else if (oldPath != null && newPath != null && newPath.size() < oldPath.size()) {
                        better = true;
                    }

                    if (better) {
                        updateCell(neighbor);
                    } else {
                        // revert prev if not actually better
                        neighbor.setPrev(oldPrev);
                    }
                }

                if (neighbor == target) {
                    if (display && displayWin != null) {
                        displayWin.repaint();
                    }
                    return traceback(target);
                }
            }
        }

        if (display && displayWin != null) {
            displayWin.repaint();
        }

        return null;
    }

}
