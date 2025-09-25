package homework.hw3.q6;

public class q6 {
    public static boolean isDiagonal(int[][] grid) {
        // Method implementation
        int target = grid[0][0];
        for (int i = 1; i < grid.length; i++) {
            if (grid[i][i] != target) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // ...
    }
}
