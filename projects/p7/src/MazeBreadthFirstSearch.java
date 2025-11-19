/*
 * file name: MazeBreadthFirstSearch.java
 * author: Jack Dai
 * last modified: 11/16/2025
 * purpose of the class:
 * Extend the AbstractMazeSearch class and implement the BFS algorithm.
 */
public class MazeBreadthFirstSearch extends AbstractMazeSearch {

    private Queue<Cell> queue;

    public MazeBreadthFirstSearch(Maze maze) {
        super(maze);
        this.queue = new LinkedList<Cell>();
    }

    @Override
    public Cell findNextCell() {
        return queue.poll();
    }

    @Override
    public void addCell(Cell next) {
        queue.offer(next);
    }

    @Override
    public void updateCell(Cell next) {
        // no-op for BFS; queue order is FIFO
    }

    @Override
    public int numRemainingCells() {
        return queue.size();
    }
}
