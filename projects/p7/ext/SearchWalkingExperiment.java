/*
 * file name: SearchWalkingExperiment.java
 * author: Jack Dai
 * last modified: 11/19/2025
 * purpose of the class: 
 * Run experiments to measure the "walking steps" required to physically
 * walk between consecutively visited cells in the order each search explores
 * them. Exports results to `search_walking_results.txt`.
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class SearchWalkingExperiment {

    /**
     * runs experiments across densities and writes CSV results to
     * `search_walking_results.txt`.
     * Command-line args: [trials] [densitySteps].
     */
    public static void main(String[] args) {
        int rows = 20;
        int cols = 20;
        int trials = 100; // per density
        int densitySteps = 10; // will produce densities 0.0,0.1,...,1.0
        String outFile = "search_walking_results.txt";

        if (args.length >= 1) {
            try {
                trials = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
            }
        }
        if (args.length >= 2) {
            try {
                densitySteps = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
            }
        }

        // announce output file
        System.out.println("Writing results to: " + outFile);

        try (FileWriter fw = new FileWriter(outFile)) {
            // write CSV header
            fw.write("density,algo,reached_prob,avg_path_len,avg_explored_cells,avg_walking_steps\n");

            for (int i = 0; i <= densitySteps; i++) {
                double d = ((double) i) / densitySteps;
                Stats dfs = new Stats();
                Stats bfs = new Stats();
                Stats astar = new Stats();

                for (int t = 0; t < trials; t++) {
                    // build a fresh random maze for this trial
                    Maze maze = new Maze(rows, cols, d);
                    Cell start = maze.get(1, 1);
                    Cell target = maze.get(rows - 2, cols - 2);
                    start.setType(CellType.FREE);
                    target.setType(CellType.FREE);

                    // DFS
                    MazeDepthFirstSearch dfssearch = new MazeDepthFirstSearch(maze);
                    // collect the visitation order returned by the searcher
                    List<Cell> dfsVisited = null;
                    LinkedList<Cell> dfsPath = dfssearch.search(start, target, false, 0);
                    dfsVisited = dfssearch.getVisitedOrder();
                    int dfsExplored = maze.countVisitedCells();
                    if (dfsPath != null) {
                        dfs.reached++;
                        dfs.totalPathLen += dfsPath.size();
                    }
                    dfs.totalExplored += dfsExplored;
                    dfs.totalWalkingSteps += computeWalkingSteps(maze, dfsVisited);

                    maze.reset();

                    // BFS
                    MazeBreadthFirstSearch bfssearch = new MazeBreadthFirstSearch(maze);
                    LinkedList<Cell> bfsPath = bfssearch.search(start, target, false, 0);
                    List<Cell> bfsVisited = bfssearch.getVisitedOrder();
                    int bfsExplored = maze.countVisitedCells();
                    if (bfsPath != null) {
                        bfs.reached++;
                        bfs.totalPathLen += bfsPath.size();
                    }
                    bfs.totalExplored += bfsExplored;
                    bfs.totalWalkingSteps += computeWalkingSteps(maze, bfsVisited);

                    maze.reset();

                    // A*
                    MazeAStarSearch asearch = new MazeAStarSearch(maze);
                    LinkedList<Cell> aPath = asearch.search(start, target, false, 0);
                    List<Cell> aVisited = asearch.getVisitedOrder();
                    int aExplored = maze.countVisitedCells();
                    if (aPath != null) {
                        astar.reached++;
                        astar.totalPathLen += aPath.size();
                    }
                    astar.totalExplored += aExplored;
                    astar.totalWalkingSteps += computeWalkingSteps(maze, aVisited);

                    maze.reset();
                }

                fw.write(formatStats(d, "DFS", dfs, trials));
                fw.write("\n");
                fw.write(formatStats(d, "BFS", bfs, trials));
                fw.write("\n");
                fw.write(formatStats(d, "AStar", astar, trials));
                fw.write("\n");
                fw.flush();

                System.out.println(String.format("d=%.2f done", d));
            }

        } catch (IOException ioe) {
            System.err.println("Error writing results file: " + ioe.getMessage());
        }
    }

    /**
     * Formats aggregated statistics as a CSV line.
     *
     * @param density the maze obstacle density
     * @param algo    algorithm name
     * @param s       collected statistics
     * @param trials  number of trials used for normalization
     * @return a CSV formatted string summarizing the stats
     */
    private static String formatStats(double density, String algo, Stats s, int trials) {
        double reachedProb = ((double) s.reached) / trials;
        double avgPathLen = s.reached > 0 ? ((double) s.totalPathLen) / s.reached : 0.0;
        double avgExplored = ((double) s.totalExplored) / trials;
        double avgWalking = ((double) s.totalWalkingSteps) / trials;
        return String.format("%.2f,%s,%.4f,%.4f,%.4f,%.4f", density, algo, reachedProb, avgPathLen, avgExplored,
                avgWalking);
    }

    // Simple accumulator for aggregated experiment statistics.
    static class Stats {
        int reached = 0;
        long totalPathLen = 0;
        long totalExplored = 0;
        long totalWalkingSteps = 0;
    }

    /**
     * Compute the total walking-steps required to physically walk between each
     * consecutive pair in the visitation order, using BFS distances on the
     * maze grid.
     *
     * @param maze         the maze used to compute valid shortest paths
     * @param visitedOrder ordered list of visited cells to sum distances for
     * @return total walking steps (0 if list is empty or has one element)
     */
    private static int computeWalkingSteps(Maze maze, List<Cell> visitedOrder) {
        if (visitedOrder == null || visitedOrder.size() < 2)
            return 0;
        int total = 0;
        for (int i = 0; i < visitedOrder.size() - 1; i++) {
            Cell a = visitedOrder.get(i);
            Cell b = visitedOrder.get(i + 1);
            // compute shortest grid distance between successive visited cells
            int d = shortestPathLength(maze, a, b);
            // if no path exists between them (shouldn't happen often), count as large
            if (d < 0)
                d = Integer.MAX_VALUE / 4;
            total += d;
        }
        return total;
    }

    /**
     * Performs a BFS on the maze grid to find the shortest number of moves
     * between two cells, returning -1 when unreachable.
     *
     * @param maze the maze in which to search
     * @param a    source cell
     * @param b    destination cell
     * @return shortest number of steps from a to b, or -1 if unreachable
     */
    private static int shortestPathLength(Maze maze, Cell a, Cell b) {
        if (a.getRow() == b.getRow() && a.getCol() == b.getCol())
            return 0;
        int rows = maze.getRows();
        int cols = maze.getCols();
        boolean[][] seen = new boolean[rows][cols];
        Deque<int[]> q = new ArrayDeque<>();
        // enqueue starting cell with distance 0
        q.add(new int[] { a.getRow(), a.getCol(), 0 });
        seen[a.getRow()][a.getCol()] = true;
        int[][] steps = new int[][] { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0], c = cur[1], dist = cur[2];
            for (int[] s : steps) {
                int nr = r + s[0];
                int nc = c + s[1];
                if (nr < 0 || nr >= rows || nc < 0 || nc >= cols)
                    continue;
                if (seen[nr][nc])
                    continue;
                Cell cell = maze.get(nr, nc);
                // skip obstacles
                if (cell.getType() == CellType.OBSTACLE)
                    continue;
                if (nr == b.getRow() && nc == b.getCol())
                    return dist + 1;
                seen[nr][nc] = true;
                q.add(new int[] { nr, nc, dist + 1 });
            }
        }
        return -1;
    }
}
