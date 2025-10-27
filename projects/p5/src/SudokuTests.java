/*
file name:      SudokuTests.java
Authors:        Max Bender and Ike Lage
last modified:  04/01/2025

How to run:     java -ea SudokuTests
*/

public class SudokuTests {

    /**
     * Stack Tests
     */
    public static double sudokuTests() {

        double testScore = 0.;
        /**
         * Solve Blank Board
         */
        Sudoku game = new Sudoku(0, 0);
        if (game.solve()) {
            testScore += 2;
        } else {
            System.out.println("Blank board not solving");
        }

        /**
         * Solve a Board with a small number of elements
         */
        Sudoku game1 = new Sudoku(5, 0);
        if (game1.solve()) {
            testScore += 2;
        } else {
            System.out.println("5-element board not solving");
        }

        /**
         * Solve a Board with a more elements
         */
        Sudoku game2 = new Sudoku(40, 0);
        System.out.println("If your program hangs here, you have an error");
        game2.solve();
        System.out.println("Your program isn't hanging, disregard the note above");

        testScore += 2;

        return testScore;
    }

    public static void main(String[] args) {
        System.out.println(sudokuTests() + "/6");
    }

}