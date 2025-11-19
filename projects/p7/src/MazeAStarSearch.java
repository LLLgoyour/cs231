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

    // f(n) = g(n) + h(n)
    // g(n): length of the path from the start to this cell (using traceback)
    // h(n): Manhattan distance from this cell to the target
    private int costEstimate(Cell cell) {
        LinkedList<Cell> path = traceback(cell);
        int g;
        if (path == null) {
            // In theory, any cell in the queue should already be reachable;
            // this is a defensive default in case something goes wrong.
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

    @Override
    public Cell findNextCell() {
        return priorityQueue.poll();
    }

    @Override
    public void addCell(Cell next) {
        priorityQueue.offer(next);
    }

    @Override
    public void updateCell(Cell next) {
        // When we find a shorter path to a cell, we need to
        // update its priority inside the heap.
        priorityQueue.updatePriority(next);
    }

    @Override
    public int numRemainingCells() {
        return priorityQueue.size();
    }
}