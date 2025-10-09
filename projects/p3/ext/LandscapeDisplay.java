/*
  Originally written by Bruce A. Maxwell a long time ago.
  Updated by Brian Eastwood and Stephanie Taylor more recently
  Author: Jack Dai
  last modified: 10/05/2025
  
  Creates a window using the JFrame class.

  Creates a drawable area in the window using the JPanel class.

  The JPanel calls the Landscape's draw method to fill in content, so the
  Landscape class needs a draw method.
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Displays a Landscape graphically using Swing. In this version, we do not
 * assume
 * the Landscape is a grid.
 */
public class LandscapeDisplay {
    protected JFrame win;
    protected Landscape scape;
    private LandscapePanel canvas;

    /**
     * Initializes a display window for a Landscape.
     * 
     * @param scape the Landscape to display
     */
    public LandscapeDisplay(Landscape scape) {
        // Setup the main window
        this.win = new JFrame("Agents");
        this.win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Store reference to the landscape
        this.scape = scape;

        // Create a panel in which to display the Landscape
        this.canvas = new LandscapePanel(this.scape.getWidth(),
                this.scape.getHeight());

        // Add the panel to the window, layout, and display
        this.win.add(this.canvas, BorderLayout.CENTER);
        this.win.pack();
        this.win.setVisible(true);
    }

    /**
     * Saves an image of the display contents to a file. The supplied
     * filename should have an extension supported by javax.imageio, e.g.
     * "png" or "jpg".
     *
     * @param filename the name of the file to save
     */
    public void saveImage(String filename) {
        // Extract the file extension from the filename
        String ext = filename.substring(filename.lastIndexOf('.') + 1, filename.length());

        // Create an image buffer to save this component
        Component tosave = this.win.getRootPane();
        BufferedImage image = new BufferedImage(tosave.getWidth(), tosave.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        // Paint the component to the image buffer
        Graphics g = image.createGraphics();
        tosave.paint(g);
        g.dispose();

        // Save the image to file
        try {
            ImageIO.write(image, ext, new File(filename));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    /**
     * This inner class provides the panel on which Landscape elements
     * are drawn.
     */
    private class LandscapePanel extends JPanel {
        /**
         * Creates the panel.
         * 
         * @param width  the width of the panel in pixels
         * @param height the height of the panel in pixels
         */
        public LandscapePanel(int width, int height) {
            super();
            // Set panel dimensions and background color
            this.setPreferredSize(new Dimension(width, height));
            this.setBackground(Color.white);
        }

        /**
         * Method overridden from JComponent that is responsible for
         * drawing components on the screen. The supplied Graphics
         * object is used to draw.
         * 
         * @param g the Graphics object used for drawing
         */
        public void paintComponent(Graphics g) {
            // Call parent's paint method and then draw the landscape
            super.paintComponent(g);
            scape.draw(g);
        } // end paintComponent

    } // end LandscapePanel

    /**
     * Repaints the display window to show updated landscape state.
     */
    public void repaint() {
        this.win.repaint();
    }

    /**
     * Test function that creates a new LandscapeDisplay and populates it with 200
     * agents, then runs an infinite animation loop.
     * 
     * @param args command line arguments (not used)
     * @throws InterruptedException if thread sleep is interrupted
     */
    public static void main(String[] args) throws InterruptedException {
        // Create landscape with specified dimensions
        Landscape scape = new Landscape(500, 500);
        Random gen = new Random();

        // Create 100 SocialAgents and 100 AntiSocialAgents with random positions
        for (int i = 0; i < 100; i++) {
            scape.addAgent(new SocialAgent(gen.nextDouble() * scape.getWidth(),
                    gen.nextDouble() * scape.getHeight(),
                    25));
            scape.addAgent(new AntiSocialAgent(gen.nextDouble() * scape.getWidth(),
                    gen.nextDouble() * scape.getHeight(),
                    50));
        }

        // Create display window
        LandscapeDisplay display = new LandscapeDisplay(scape);

        // Run animation loop
        while (true) {
            Thread.sleep(10);
            scape.updateAgents();
            display.repaint();
        }
    }
}