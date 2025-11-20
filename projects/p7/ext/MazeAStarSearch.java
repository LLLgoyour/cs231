/*
 * file name: MazeAStarSearch.java
 * author: Jack Dai
 * last modified: 11/16/2025
 * purpose of the class:
 * Extend the AbstractMazeSearch class and implement the A* algorithm.
 */

import java.util.Comparator;

public class MazeAStarSearch extends AbstractMazeSearch {

    private PriorityQueue<Cell> priorityQueue;

    /**
     * Constructs an A* searcher for the provided maze.
     *
     * @param maze the Maze instance to search
     */
    public MazeAStarSearch(Maze maze) {
        super(maze);
        // A* comparator: smaller f-value means higher priority
        Comparator<Cell> comp = new Comparator<Cell>() {
            @Override
            public int compare(Cell c1, Cell c2) {
                int f1 = costEstimate(c1);
                int f2 = costEstimate(c2);
                return f1 - f2;
            }
        };
        this.priorityQueue = new Heap<Cell>(comp);
    }

    /**
     * Estimate the total cost f(n) = g(n) + h(n) for A* prioritization.
     * g(n): number of steps from the start to cell,
     * computed by traceback(Cell) (defensive default used if
     * traceback is unavailable).
     * h(n)}: Manhattan distance from cell to the target.
     *
     * @param cell the cell to estimate cost for
     * @return the estimated total cost (smaller is higher priority)
     */
    private int costEstimate(Cell cell) {
        // f(n) = g(n) + h(n)
        // g(n): length of the path from the start to this cell (using traceback)
        // h(n): Manhattan distance from this cell to the target
        LinkedList<Cell> path = traceback(cell);
        int g;
        if (path == null) {
            // In theory, any cell in the queue should already be reachable;
            // this is a defensive default in case something goes wrong
            g = Integer.MAX_VALUE / 4;
        } else {
            g = path.size();
        }

        Cell target = getTarget();
        int h = 0;
        if (target != null && cell != null) {
            h = Math.abs(cell.getRow() - target.getRow()) +
                    Math.abs(cell.getCol() - target.getCol());
        }
        return g + h;
    }

    /**
     * {@inheritDoc}
     *
     * @return the highest-priority Cell (smallest estimated cost) or
     *         null if the frontier is empty
     */
    @Override
    public Cell findNextCell() {
        return priorityQueue.poll();
    }

    /**
     * {@inheritDoc}
     *
     * @param next the Cell to add to the priority frontier
     */
    @Override
    public void addCell(Cell next) {
        priorityQueue.offer(next);
    }

    /**
     * {@inheritDoc}.
     *
     * When a shorter path to a previously-discovered cell is found, the
     * priority of that cell must be updated in the heap; this implementation
     * delegates to PriorityQueue.updatePriority(Object)}.
     *
     * @param next the {@link Cell} whose priority should be updated
     */
    @Override
    public void updateCell(Cell next) {
        // When we find a shorter path to a cell, we need to
        // update its priority inside the heap.
        priorityQueue.updatePriority(next);
    }

    /**
     * {@inheritDoc}
     *
     * @return the number of cells currently stored in the priority frontier
     */
    @Override
    public int numRemainingCells() {
        return priorityQueue.size();
    }
}