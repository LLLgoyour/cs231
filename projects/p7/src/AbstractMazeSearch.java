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

    /** Abstract methods for specific search strategies **/
    public abstract Cell findNextCell();

    public abstract void addCell(Cell next);

    public abstract void updateCell(Cell next);

    public abstract int numRemainingCells();

    /** Accessors / mutators **/
    public Maze getMaze() {
        return this.maze;
    }

    public Cell getTarget() {
        return this.target;
    }

    public void setCur(Cell cell) {
        this.cur = cell;
    }

    public Cell getCur() {
        return this.cur;
    }

    public Cell getStart() {
        return this.start;
    }

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
     * Builds the path from start to the given cell by following prev pointers.
     * Returns null if no path from start exists (i.e. prev chain does not end at
     * start).
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
     * Main search routine. Optional visualization controls included to match
     * SearchTests.
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
