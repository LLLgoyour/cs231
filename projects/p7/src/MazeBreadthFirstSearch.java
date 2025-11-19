/*
 * file name: MazeBreadthFirstSearch.java
 * author: Jack Dai
 * last modified: 11/16/2025
 * purpose of the class:
 * Extend the AbstractMazeSearch class and implement the BFS algorithm for maze pathfinding.
 */

public class MazeBreadthFirstSearch extends AbstractMazeSearch {

    private Queue<Cell> queue;

    /**
     * Constructs a BFS searcher for the provided maze.
     *
     * @param maze the Maze instance to search
     */
    public MazeBreadthFirstSearch(Maze maze) {
        super(maze);
        this.queue = new LinkedList<Cell>();
    }

    /**
     * {@inheritDoc}
     *
     * This implementation removes and returns the head of the internal
     * FIFO queue, or null if the queue is empty.
     *
     * @return the next Cell to explore or null if none remain
     */
    @Override
    public Cell findNextCell() {
        return queue.poll();
    }

    /**
     * {@inheritDoc}
     * 
     * This implementation adds next to the tail of the internal
     * FIFO queue so it will be explored after previously-enqueued cells.
     *
     * @param next the Cell to add to the frontier
     */
    @Override
    public void addCell(Cell next) {
        queue.offer(next);
    }

    /**
     * For BFS this method is a no-op because the queue does not support
     * reprioritization of already-enqueued elements.
     *
     * @param next the Cell whose priority would be updated (ignored)
     */
    @Override
    public void updateCell(Cell next) {
        // no-op for BFS; queue order is FIFO
    }

    /**
     * {@inheritDoc}
     *
     * @return the number of cells currently stored in the internal frontier
     */
    @Override
    public int numRemainingCells() {
        return queue.size();
    }
}
