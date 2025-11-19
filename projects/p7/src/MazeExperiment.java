public class MazeExperiment {

    public static void main(String[] args) {
        int rows = 20;
        int cols = 20;
        int trials = 100; // trials per density

        // densities: 0.0, 0.1, ..., 1.0 (11 settings)
        int densitySteps = 10;

        String outFile = "maze_experiment_results.txt";
        System.out.println("Writing results to: " + outFile);

        try (java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter(outFile))) {
            pw.println("density,algo,reached_prob,avg_path_len,avg_explored_cells");
            System.out.println("density,algo,reached_prob,avg_path_len,avg_explored_cells");

            for (int i = 0; i <= densitySteps; i++) {
                double d = ((double) i) / densitySteps; // 0.0 .. 1.0
                // accumulate stats for each algorithm separately
                Stats dfs = new Stats();
                Stats bfs = new Stats();
                Stats astar = new Stats();

                for (int t = 0; t < trials; t++) {
                    Maze maze = new Maze(rows, cols, d);
                    // choose fixed start and target but ensure they are FREE
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

                    // reset maze
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

                // Print CSV lines for each algorithm (both to file and stdout)
                String dfsLine = formatStats(d, "DFS", dfs, trials);
                String bfsLine = formatStats(d, "BFS", bfs, trials);
                String astarLine = formatStats(d, "AStar", astar, trials);

                pw.println(dfsLine);
                pw.println(bfsLine);
                pw.println(astarLine);

                System.out.println(dfsLine);
                System.out.println(bfsLine);
                System.out.println(astarLine);
            }
        } catch (java.io.IOException ioe) {
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
