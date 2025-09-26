import java.util.Arrays;
import java.util.Random;

public class Grid {
    /**
     * Checks whether two 2D int arrays are the exact same object.
     *
     * @param arr1 the first 2D int array to compare;
     * @param arr2 the second 2D int array to compare;
     * @return {@code true} if {@code arr1} and {@code arr2} are the same reference
     *         (or both {@code null}), {@code false} otherwise
     */
    public static boolean gridEquals(int[][] arr1, int[][] arr2) {
        return arr1 == arr2;
    }

    public static void main(String[] args) {
        // require at least 2 arguments
        if (args.length < 2) {
            System.out.println("Usage: java Grid <size> <maxVal> <positiveFlag:true|false> [values]");
            System.out.println("  <size> = number of rows/columns in the rectangular grid");
            System.out.println("  <maxVal> = maximum random value (exclusive)");
            System.out.println("  <positiveFlag> = true for positive numbers, false for negative");
            System.out.println("  [values] = optional comma-separated list of integers to fill the grid");
            return;
        }

        // Arg1 = number of rows
        // Arg2 = maximum random value
        // Arg3 = boolean
        // Arg4 = manual value for grid[]

        // defaults
        int size = 3; // rows/cols of rectangular grid
        int maxVal = 100; // range for random numbers
        boolean positiveOnly = true;
        String[] manualValues = null;

        if (args.length >= 3) {
            try {
                size = Integer.parseInt(args[0]);
                maxVal = Integer.parseInt(args[1]);
                positiveOnly = Boolean.parseBoolean(args[2]);
                if (args.length == 4) {
                    manualValues = args[3].split(",");
                }
            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid arguments. Usage: java Grid <size> <maxVal> <positiveFlag:true|false> [values]");
                return;
            }
        }

        // initialize a Random object
        Random r = new Random();

        // define and initialize a rectangular grid
        int[][] grid = new int[size][size];
        System.out.println("Rectangular grid:");

        if (manualValues != null) {
            // fill with provided values
            int idx = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int val = Integer.parseInt(manualValues[idx % manualValues.length]);
                    grid[i][j] = positiveOnly ? Math.abs(val) : -Math.abs(val);
                    idx++;
                    System.out.print(grid[i][j] + " ");
                }
                System.out.println();
            }
        } else {
            // fill with random numbers
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int val = r.nextInt(maxVal);
                    grid[i][j] = positiveOnly ? val : -val;
                    System.out.print(grid[i][j] + " ");
                }
                System.out.println();
            }
        }
        System.out.println();

        // define and initialize a non-rectangular grid
        int[][] grid2 = new int[size][];
        for (int i = 0; i < size; i++) {
            grid2[i] = new int[i + 1];
            for (int j = 0; j < grid2[i].length; j++) {
                int val = r.nextInt(maxVal);
                grid2[i][j] = positiveOnly ? val : -val;
            }
        }
        System.out.println("Non-rectangular grid:");
        for (int i = 0; i < grid2.length; i++) {
            System.out.println(Arrays.toString(grid2[i]));
        }
        System.out.println();
        // method 1 of initialize a 2D array
        // int[][] grid;
        // grid = new int[3][5];

        // method 2 of initialize a 2D array
        // int[][] grid2 = new int[3][];
        // for (int i = 0; i < 3; i++) {
        // grid2[i] = new int[i + 1];
        // }

        // go through the arrays and give values
        // and output each element in every rows
        // print all rows
        // for (int i = 0; i < grid.length; i++) {
        // for (int j = 0; j < grid[i].length; j++) {
        // grid[i][j] = r.nextInt();
        // System.out.println(grid[i][j]);
        // }
        // System.out.println();
        // }

        // for (int i = 0; i < grid2.length; i++) {
        // for (int j = 0; j < grid2[i].length; j++) {
        // grid2[i][j] = r.nextInt();
        // }
        // }

        // Check if two matrices are equal
        // int[][] arr1 = new int[2][2];
        // int[][] arr2 = new int[2][2];
        // int[][] arr3;
        // for (int i = 0; i < 2; i++) {
        // for (int j = 0; j < 2; j++) {
        // arr1[i][j] = i + j;
        // arr2[i][j] = i + j;
        // }
        // }
        // arr3 = arr1;
        // System.out.println(arr1 == arr2);
        // System.out.println(arr1 == arr3);
        // System.out.println(gridEquals(arr1, arr3)); // should return true
        // System.out.println(gridEquals(arr1, grid)); // return false

        // check if two matrices are equal
        int[][] arr1 = new int[2][2];
        int[][] arr2 = new int[2][2];
        int[][] arr3;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                arr1[i][j] = i + j;
                arr2[i][j] = i + j;
            }
        }
        arr3 = arr1;
        System.out.println("arr1 vs arr2 (equal values): " + gridEquals(arr1, arr2));
        System.out.println("arr1 vs arr3 (same reference): " + gridEquals(arr1, arr3));
        System.out.println("arr1 vs grid (different shape): " + gridEquals(arr1, grid));
    }
}