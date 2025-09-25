package homework.hw3.q1;

public class q1 {
    public static int printValueAt(int i, int j) {
        return grid[i][j];
    }

    private static int[][] grid = {
            { 1, 2, 3 },
            { 4, 5, 6 },
            { 7, 8, 9 }
    };

    public static void main(String[] args) {
        System.out.println(printValueAt(1, 2));
    }
}
