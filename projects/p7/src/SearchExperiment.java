/*
 * file name: SearchExperiment.java
 * author: Jack Dai
 * last modified: 11/19/2025
 * purpose of the class:
 * Run experiments to collect data for the Exploration.
*/

import java.io.FileWriter;
import java.io.IOException;

public class SearchExperiment {

    public static void main(String[] args) {
        int rows = 20;
        int cols = 20;
        int trials = 100; // per density
        int densitySteps = 10; // will produce densities 0.0,0.1,...,1.0
        String outFile = "search_experiment_results.txt";

        // Allow overriding via command-line args: trials and densitySteps
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

        System.out.println("Writing results to: " + outFile);

        try (FileWriter fw = new FileWriter(outFile)) {
            fw.write("density,algo,reached_prob,avg_path_len,avg_explored_cells\n");
            for (int i = 0; i <= densitySteps; i++) {
                double d = ((double) i) / densitySteps;
                Stats dfs = new Stats();
                Stats bfs = new Stats();
                Stats astar = new Stats();

                for (int t = 0; t < trials; t++) {
                    Maze maze = new Maze(rows, cols, d);
                    Cell start = maze.get(1, 1);
                    Cell target = maze.get(rows - 2, cols - 2);
                    start.setType(CellType.FREE);
                    target.setType(CellType.FREE);

                    // DFS
                    MazeDepthFirstSearch dfssearch = new MazeDepthFirstSearch(maze);
                    LinkedList<Cell> dfsPath = dfssearch.search(start, target, false, 0);
                    int dfsExplored = maze.countVisitedCells();
                    if (dfsPath != null) {
                        dfs.reached++;
                        dfs.totalPathLen += dfsPath.size();
                    }
                    dfs.totalExplored += dfsExplored;

                    maze.reset();

                    // BFS
                    MazeBreadthFirstSearch bfssearch = new MazeBreadthFirstSearch(maze);
                    LinkedList<Cell> bfsPath = bfssearch.search(start, target, false, 0);
                    int bfsExplored = maze.countVisitedCells();
                    if (bfsPath != null) {
                        bfs.reached++;
                        bfs.totalPathLen += bfsPath.size();
                    }
                    bfs.totalExplored += bfsExplored;

                    maze.reset();

                    // A*
                    MazeAStarSearch asearch = new MazeAStarSearch(maze);
                    LinkedList<Cell> aPath = asearch.search(start, target, false, 0);
                    int aExplored = maze.countVisitedCells();
                    if (aPath != null) {
                        astar.reached++;
                        astar.totalPathLen += aPath.size();
                    }
                    astar.totalExplored += aExplored;

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

    private static String formatStats(double density, String algo, Stats s, int trials) {
        double reachedProb = ((double) s.reached) / trials;
        double avgPathLen = s.reached > 0 ? ((double) s.totalPathLen) / s.reached : 0.0;
        double avgExplored = ((double) s.totalExplored) / trials;
        return String.format("%.2f,%s,%.4f,%.4f,%.4f", density, algo, reachedProb, avgPathLen, avgExplored);
    }

    static class Stats {
        int reached = 0;
        long totalPathLen = 0;
        long totalExplored = 0;
    }
}
