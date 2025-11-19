/*
 * file name: MazeDepthFirstSearch.java
 * author: Jack Dai
 * last modified: 11/17/2025
 * purpose of the class:
 * Extend the AbstractMazeSearch class and implement the DFS algorithm for maze pathfinding.
 */

public class MazeDepthFirstSearch extends AbstractMazeSearch {

    private Stack<Cell> stack;

    /**
     * Constructs a DFS searcher for the provided maze.
     *
     * @param maze the Maze instance to search
     */
    public MazeDepthFirstSearch(Maze maze) {
        super(maze);
        this.stack = new LinkedList<Cell>();
    }

    /**
     * {@inheritDoc}
     *
     * This implementation returns and removes the top element of the internal
     * LIFO stack. If the stack is empty, this method returns null.
     *
     * @return the next Cell to explore or null if none remain
     */
    @Override
    public Cell findNextCell() {
        return stack.pop();
    }

    /**
     * {@inheritDoc}
     *
     * This implementation pushes next onto the internal stack so it
     * will be explored before previously-added cells.
     *
     * @param next the Cell to add to the frontier
     */
    @Override
    public void addCell(Cell next) {
        stack.push(next);
    }

    /**
     * For DFS this method does nothing because the stack-based frontier has
     * no concept of updating an element's priority.
     *
     * @param next the Cell whose priority would be updated (ignored)
     */
    @Override
    public void updateCell(Cell next) {
        // no-op for DFS; structure does not support reprioritization
    }

    /**
     * {@inheritDoc}
     *
     * @return the number of cells currently stored in the internal frontier
     */
    @Override
    public int numRemainingCells() {
        return stack.size();
    }
}
