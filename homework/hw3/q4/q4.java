package homework.hw3.q4;

public class q4 {
    public static int sumRow(int[][] grid, int rowIndex) {
        // Method implementation
        int sum = 0;
        for (int i = 0; i < grid[rowIndex].length; i++) {
            sum += grid[rowIndex][i];
        }
        return sum;
    }

    public static void main(String[] args) {
        int[][] grid = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 9 }
        };
        System.out.println(sumRow(grid, 2));
    }
}
