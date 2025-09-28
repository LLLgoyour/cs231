/**
 * file name: LifeSimulation.java
 * author: Jack Dai
 * last modified: 09/28/2025
 * 
 * This class drives the simulation of the Conway's Game of Life
 * by advancing a Landscape object and display it
 * through LandscapeDisplay
 */

public class LifeSimulation {
    public static void main(String[] args) throws InterruptedException {
        // defaults
        int rows = 100;
        int cols = 100;
        double chance = 0.25;
        int step = 1000; // how many time steps to run
        int delay = 250; // delay in ms between steps
        int scale = 6; // cell's pixel size

        // command line argument override
        if (args.length >= 2) {
            rows = Integer.parseInt(args[0]);
            cols = Integer.parseInt(args[1]);
        }
        if (args.length >= 3) {
            step = Integer.parseInt(args[3]);
        }

        // create a new Landscape
        Landscape scape = new Landscape(rows, cols, chance);

        // display the Landscape
        LandscapeDisplay display = new LandscapeDisplay(scape, scale);

        // simulation loop
        for (int t = 0; t < step; t++) {
            Thread.sleep(delay);
            scape.advance();
            display.repaint();
        }
    }
}
