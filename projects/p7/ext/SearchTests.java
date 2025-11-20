//Ike Lage
//CS231
//Project 7 Tester for Maze Searches

public abstract class SearchTests {

    public static double searchTests() {
        int restarts = 100;

        int bfsPaths = 0;
        int dfsPaths = 0;
        int astarPaths = 0;

        int bfsSearched = 0;
        int dfsSearched = 0;
        int astarSearched = 0;

        int r = 0;
        while (r < restarts) {
            Maze maze = new Maze(10, 10, .05);
            Cell start = maze.get(1, 1);
            Cell target = maze.get(8, 8);

            MazeDepthFirstSearch dfssearcher = new MazeDepthFirstSearch(maze);
            LinkedList<Cell> dfssearched = dfssearcher.search(start, target, false, 0);
            if (dfssearched != null) {
                dfsPaths += dfssearched.size();
                dfsSearched += maze.countVisitedCells();

                maze.reset();
                MazeBreadthFirstSearch bfssearcher = new MazeBreadthFirstSearch(maze);
                LinkedList<Cell> bfssearched = bfssearcher.search(start, target, false, 0);
                bfsPaths += bfssearched.size();
                bfsSearched += maze.countVisitedCells();

                maze.reset();
                MazeAStarSearch astarsearcher = new MazeAStarSearch(maze);
                LinkedList<Cell> astarsearched = astarsearcher.search(start, target, false, 0);
                astarPaths += astarsearched.size();
                astarSearched += maze.countVisitedCells();
                r++;
            }
        }

        int score = 0;
        if ((dfsSearched / restarts > 60) && (dfsSearched / restarts < 90)) {
            System.out.println("Test 1");
            score++;
        }
        if (dfsPaths / restarts < 50) {
            System.out.println("Test 2");
            score++;
        }

        if ((bfsSearched / restarts > 80) && (bfsSearched / restarts < 110)) {
            System.out.println("Test 3");
            score++;
        }
        if (bfsPaths / restarts < 25) {
            System.out.println("Test 4");
            score++;
        }

        if (astarSearched / restarts < 80) {
            System.out.println("Test 5");
            score++;
        }
        if (astarPaths / restarts < 25) {
            System.out.println("Test 6");
            score++;
        }

        return score;
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println(searchTests() + "/6");

    }

}