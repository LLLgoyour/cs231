/*
 * file name: LifeSimulation.java
 * author: Jack Dai
 * last modified: 10/01/2025
 * 
 * This class drives the simulation of the Conway's Game of Life
 * by advancing a Landscape object and display it
 * through LandscapeDisplay
 */

import java.util.Random;

public class LifeSimulation {
    /**
     * initializes the simulation parameters,
     * creates a Landscape and its display, and advances the simulation for
     * the specified number of steps
     * 
     * @param args command line arguments:
     *             args[0] = number of rows,
     *             args[1] = number of columns,
     *             args[2] = number of steps (optional)
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Random r = new Random();
        // defaults
        int rows = 100;
        int cols = 100;
        // double chance = 0.25; // default, and change chance
        // to 0.1, 0.2, 0.3, ... , 0.9
        // and explore the
        double chance = r.nextDouble(); // exploration data
        int steps = 1000; // how many time steps to run
        int delay = 250; // delay in ms between steps
        int scale = 6; // cell's pixel size

        // command line argument
        if (args.length >= 2) {
            rows = Integer.parseInt(args[0]);
            cols = Integer.parseInt(args[1]);
        }
        if (args.length >= 3) {
            steps = Integer.parseInt(args[2]);
        }

        // initialize the landscape with the given rows, cols, and alive-cell
        // probability
        Landscape scape = new Landscape(rows, cols, chance);

        // create a display window that renders the landscape at the specified scale
        LandscapeDisplay display = new LandscapeDisplay(scape, scale);

        // simulation loop
        for (int t = 0; t < steps; t++) {
            Thread.sleep(delay); // for exploration convenience, skipped sleep(delay)
            scape.advance();
            display.repaint();
        }
    }
}