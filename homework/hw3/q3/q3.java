package homework.hw3.q3;

public class q3 {
    public static void main(String[] args) {
        int[][] grid = new int[3][3];
        int tmp = 1;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = tmp++;
            }
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
        }
    }
}
