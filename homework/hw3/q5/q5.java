package homework.hw3.q5;

public class q5 {
    public static int[][] transpose(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        // New transposed matrix with swapped dimensions
        int[][] transposedGrid = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposedGrid[j][i] = grid[i][j];
            }
        }
        return transposedGrid;
    }

    public static void main(String[] args) {
        int[][] grid = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 9 }
        };
        System.out.println(transpose(null));
    }
}
