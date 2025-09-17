/**
 * Project: Java Bootcamp
 * Author: Jack Dai
 * Purpose: Tester class for Project 1.
 *          Verifies constructors, getters/setters, computeDiagonal,
 *          and toString functionality.
 */

// Write a file header with your name and class purpose

// Once you have completed both the Rectangle class and this Tester class,
// You should be able to run this file, Tester.java and get the following
// Output to your terminal. When evaluating your code, we will run our own
// version of this tester file and confirm that this is the output we get.
/***
 * 4.0
 * 5.0
 * 2.2
 * 2.2
 * Height: 2.2, Width: 2.2, Diagonal: 3.111269837220809
 * Height: 1.0, Width: 1.0, Diagonal: 1.4142135623730951
 * Height: 2.0, Width: 2.0, Diagonal: 2.8284271247461903
 * Height: 3.0, Width: 3.0, Diagonal: 4.242640687119285
 * Height: 4.0, Width: 4.0, Diagonal: 5.656854249492381
 * Height: 1.0, Width: 1.0, Diagonal: 1.4142135623730951
 * Height: 2.0, Width: 2.0, Diagonal: 2.8284271247461903
 * Height: 3.0, Width: 3.0, Diagonal: 4.242640687119285
 * Height: 5.0, Width: 5.0, Diagonal: 7.0710678118654755
 */

// Define a Tester class

public class Tester {

    // Define a static testRectangle method
    /**
     * Tests Rectangle class functionality:
     * verifies setters/getters, diagonal computation,
     * and toString output
     */
    public static void testRectangle() {
        // Define and initialize 1 rectangle with width 3 and height 7
        Rectangle rectangle = new Rectangle(3.0, 7.0);
        // Set the height to be 4
        rectangle.setHeight(4.0);
        // Get the height and confirm that it is 4
        // by printing it
        System.out.println(rectangle.getHeight());

        // Call the computeDiagonal method and print the output
        // Confirm that it is 5
        // Compute diagonal using the Pythagorean theorem
        System.out.println(rectangle.computeDiagonal());

        // Initialize 1 square using the second constructor
        // with width and height of 2.2
        // Create a 2.2 x 2.2 square to test the second constructor
        Rectangle square = new Rectangle(2.2, 2.2);

        // Get the height and width of the square
        // Confirm they are both 2.2 by printing them
        System.out.println(square.getWidth());
        System.out.println(square.getHeight());

        // Print the string representation of the square. Don't call
        // the toString() method directly, instead use the following
        // line (if your object is named square). Confirm it calls your toString method
        // System.out.println( square );
        // It should print: Height: 2.2, Width: 2.2, Diagonal: 3.111269837220809
        System.out.println(square);

    }

    // define a static main method
    /**
     * Calls testRectangle
     * and runs additional array tests
     */
    public static void main(String[] args) {
        // Call the testRectangle method to run your tests
        testRectangle();
        // Define an array of Rectangle objects of size 4
        Rectangle[] array = new Rectangle[4];
        // Use an indexed for loop to generate 4 squares of
        // size 1x1, 2x2, 3x3 and 4x4 and put them in the array
        for (int i = 1; i <= 4; i++) {
            Rectangle tempRectangle = new Rectangle(i, i);
            array[i - 1] = tempRectangle;
        }
        // Use another indexed for loop to print each square in order
        for (int i = 1; i <= 4; i++) {
            System.out.println(array[i - 1]);
        }
        // Change the last rectangle in the list to be of size 5x5 instead of 4x4
        array[3] = new Rectangle(5, 5);
        // Use another indexed for loop to print each square in order
        for (int i = 1; i <= 4; i++) {
            System.out.println(array[i - 1]);
        }
    }
}
