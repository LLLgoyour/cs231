/*
 * file name: MazeDepthFirstSearch.java
 * author: Jack Dai
 * last modified: 11/16/2025
 * purpose of the class:
 * Extend the AbstractMazeSearch class and implement the DFS algorithm.
 */

public class MazeDepthFirstSearch extends AbstractMazeSearch {

    private Stack<Cell> stack;

    public MazeDepthFirstSearch(Maze maze) {
        super(maze);
        this.stack = new LinkedList<Cell>();
    }

    @Override
    public Cell findNextCell() {
        return stack.pop();
    }

    @Override
    public void addCell(Cell next) {
        stack.push(next);
    }

    @Override
    public void updateCell(Cell next) {
        // no-op for DFS; structure does not support reprioritization
    }

    @Override
    public int numRemainingCells() {
        return stack.size();
    }
}
