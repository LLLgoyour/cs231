/*
 * project: Java Bootcamp
 * Jack Dai
 * Simple rectangle class for project 1
 */

public class Rectangle {
    // Write a file header with your name and class (file) purpose

    // define but don't initialize a width field of type double
    // make sure it has the correct access modifier (private)
    private double width;

    // define but don't initialize a height field of type double
    // make sure it has the correct access modifier
    private double height;

    // Make a constructor that takes in values for the width and height
    // Make sure both of your class fields are initialized by the end of
    // the constructor!
    /**
     * This is a constructor that takes in the width and height values.
     * 
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     */
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    // Write a JavaDoc comment for your constructor

    // Make a second constructor that takes in just the width
    // and calls the first constructor with the width and the
    // height equal (i.e. it makes a square)
    // Write a JavaDoc comment for your constructor

    /**
     * This is the constructor that takes in the width value and calls the first
     * constructor with the width and the height equal.
     * 
     * @param width
     */
    public Rectangle(int width) {
        this(width, width);
    }

    // Write a getter method for the width
    // Write a JavaDoc comment for your method

    /**
     * return width
     * 
     * @return (double) width
     */
    public double getWidth() {
        return this.width;
    }

    // Write a getter method for the height
    // Write a JavaDoc comment for your method

    /**
     * return height
     * 
     * @return (double) height
     */
    public double getHeight() {
        return this.height;
    }

    // Write a setter method for the width
    // Write a JavaDoc comment for your method
    /**
     * set width
     * 
     * @param width new width
     */
    public void setWidth(double width) {
        this.width = width;
    }

    // Write a setter method for the height
    // Write a JavaDoc comment for your method
    /**
     * set height
     * 
     * @param height new height
     */
    public void setHeight(double height) {
        this.height = height;
    }

    // Write a method that called computeDiagonal that
    // computes the length of the diagonal and returns it as a double
    // Your method should use the Math.sqrt and the Math.pow methods
    // Write a JavaDoc comment for your method
    // Write a single line comment for at least one
    // line in your method
    /**
     * This method computes the length of the diagonal and returns it as a double.
     * Use Math.sqrt and Math.pow methods.
     * 
     * @return the length of the diagonal (double)
     */
    public double computeDiagonal() {
        return Math.sqrt(Math.pow(this.width, 2) + Math.pow(this.height, 2));
    }

    // Write a toString method that has the following format:
    // "Height: [[height]], Width: [[width]], Diagonal: [[length of diagonal]]"
    // eg: "Height: 3.0, Width: 4.0, Diagonal: 5.0"
    public String toString() {
        return String.format("Height: %.1f, Width: %.1f, Diagonal: %.1f",
                this.height, this.width, computeDiagonal());
    }
}