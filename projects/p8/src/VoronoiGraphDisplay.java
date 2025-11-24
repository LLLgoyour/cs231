import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.HashMap;
import java.util.Random;

public class VoronoiGraphDisplay {

    final class Coord {
        double x, y;

        Coord(double a, double b) {
            x = a;
            y = b;
        }

        double norm() {
            return Math.sqrt(x * x + y * y);
        }

        Coord diff(Coord c) {
            return new Coord(x - c.x, y - c.y);
        }

        Coord sum(Coord c) {
            return new Coord(x + c.x, y + c.y);
        }

        void addBy(Coord c) {
            x += c.x;
            y += c.y;
        }

        Coord scale(double d) {
            return new Coord(x * d, y * d);
        }

        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    JFrame win;
    protected VoronoiGraph graph;
    private LandscapePanel canvas;
    private int gridScale; // width (and height) of each square in the grid
    HashMap<Vertex, Coord> coords;
    private static Color[] colors = new Color[] { Color.RED, Color.BLUE };

    /**
     * Initializes a display window for a Landscape.
     * 
     * @param scape the Landscape to display
     * @param scale controls the relative size of the display
     * @throws InterruptedException
     */
    public VoronoiGraphDisplay(VoronoiGraph g, int scale) throws InterruptedException {

        // setup the window
        this.win = new JFrame("Voronoi Game");
        this.win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.graph = g;
        this.gridScale = scale;

        // create a panel in which to display the Landscape
        // put a buffer of two rows around the display grid
        this.canvas = new LandscapePanel((int) (this.graph.size()) * this.gridScale,
                (int) (this.graph.size()) * this.gridScale);

        // add the panel to the window, layout, and display
        this.win.add(this.canvas, BorderLayout.CENTER);
        this.win.pack();
        createCoordinateSystem();
        this.win.setVisible(true);
        repaint();
    }

    public void setGraph(VoronoiGraph graph) throws InterruptedException {
        this.graph = graph;
        createCoordinateSystem();
    }

    /**
     * Saves an image of the display contents to a file. The supplied
     * filename should have an extension supported by javax.ImageIO, e.g.
     * "png" or "jpg".
     *
     * @param filename the name of the file to save
     */
    public void saveImage(String filename) {
        // get the file extension from the filename
        String ext = filename.substring(filename.lastIndexOf('.') + 1, filename.length());

        // create an image buffer to save this component
        Component toSave = this.win.getRootPane();
        BufferedImage image = new BufferedImage(toSave.getWidth(), toSave.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        // paint the component to the image buffer
        Graphics g = image.createGraphics();
        toSave.paint(g);
        g.dispose();

        // save the image
        try {
            ImageIO.write(image, ext, new File(filename));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public void createCoordinateSystem() throws InterruptedException {

        // draw the graph
        // see http://yifanhu.net/PUB/graph_draw_small.pdf for more details
        Random rand = new Random();
        HashMap<Vertex, HashMap<Vertex, Double>> distances = new HashMap<>();
        for (Vertex v : graph.getVertices())
            distances.put(v, graph.distanceFrom(v));

        coords = new HashMap<>();
        for (Vertex v : graph.getVertices())
            coords.put(v, new Coord(rand.nextInt(canvas.getWidth() / 2) - canvas.getWidth() / 2,
                    rand.nextInt(canvas.getHeight() / 2) - canvas.getHeight() / 2));

        double step = 1000;
        for (int i = 0; i < 100; i++) {
            HashMap<Vertex, Coord> newCoords = new HashMap<>();
            for (Vertex v : graph.getVertices()) {
                Coord f = new Coord(0, 0);
                boolean pickRandom = false;
                for (Vertex u : graph.getVertices()) {
                    if (u == v)
                        continue;
                    Coord xv = coords.get(v);
                    Coord xu = coords.get(u);
                    if ((Math.abs(xv.x - xu.x) > .1 / i) && (Math.abs(xv.y - xu.y) > .1 / i))
                        f.addBy(xu.diff(xv).scale((xu.diff(xv).norm()
                                - (distances.get(u).get(v) == Double.POSITIVE_INFINITY ? 1000
                                        : distances.get(u).get(v) * 100))
                                / (xu.diff(xv).norm())));
                    else
                        pickRandom = true;
                }
                if (!pickRandom)
                    newCoords.put(v,
                            f.x == 0 && f.y == 0 ? coords.get(v) : coords.get(v).sum(f.scale(step / f.norm())));
                else
                    newCoords.put(v, new Coord(rand.nextInt(canvas.getWidth() / 2) - canvas.getWidth() / 2,
                            rand.nextInt(canvas.getHeight() / 2) - canvas.getHeight() / 2));

            }
            step *= .9;
            Coord average = new Coord(0, 0);
            for (Vertex v : graph.getVertices())
                average.addBy(coords.get(v));
            average = average.scale(1.0 / graph.size());
            for (Coord c : newCoords.values()) {
                c.x -= average.x;
                c.x = Math.min(Math.max(c.x, -canvas.getWidth() / 2), canvas.getWidth() / 2);
                c.y -= average.y;
                c.y = Math.min(Math.max(c.y, -canvas.getHeight() / 2), canvas.getHeight() / 2);
            }
            coords = newCoords;
            // Uncomment below to see how the coordinates are formed!
            // repaint();
            // Thread.sleep(50);
        }
        Coord average = new Coord(0, 0);
        for (Vertex v : graph.getVertices())
            average.addBy(coords.get(v));
        average = average.scale(1.0 / graph.size());
        double maxNorm = 0;
        for (Vertex v : graph.getVertices()) {
            Coord newCoord = (new Coord(coords.get(v).x - average.x, coords.get(v).y - average.y));
            coords.put(v, newCoord);
            maxNorm = Math.max(maxNorm, newCoord.norm());
        }
        for (Vertex v : graph.getVertices())
            coords.put(v, coords.get(v)
                    .scale((Math.min(canvas.getWidth() / 2, canvas.getHeight() / 2) - gridScale / 2) / maxNorm));

        int singletonCount = 0;
        for (Vertex v : graph.getVertices())
            if (!v.adjacentVertices().iterator().hasNext())
                coords.put(v, new Coord(-canvas.getWidth() / 2 + gridScale * ++singletonCount,
                        -canvas.getHeight() / 2 + gridScale));

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
            this.setPreferredSize(new Dimension(width, height));
            this.setBackground(Color.lightGray);
        }

        /**
         * Method overridden from JComponent that is responsible for
         * drawing components on the screen. The supplied Graphics
         * object is used to draw.
         * 
         * @param g the Graphics object used for drawing
         */
        public void paintComponent(Graphics g) {
            // take care of housekeeping by calling parent paintComponent
            super.paintComponent(g);
            g.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
            for (Edge e : graph.getEdges()) {
                g.setColor(Color.BLACK);
                g.drawLine((int) coords.get(e.vertices()[0]).x + gridScale / 4,
                        (int) coords.get(e.vertices()[0]).y + gridScale / 4,
                        (int) coords.get(e.vertices()[1]).x + gridScale / 4,
                        (int) coords.get(e.vertices()[1]).y + gridScale / 4);
            }
            for (Vertex v : graph.getVertices()) {
                if (graph.getCurrentOwner(v) == null)
                    g.setColor(Color.WHITE);
                else if (graph.hasToken(v))
                    g.setColor(colors[graph.getCurrentOwner(v)].darker().darker());
                else
                    g.setColor(colors[graph.getCurrentOwner(v)].brighter());
                g.fillOval((int) coords.get(v).x, (int) coords.get(v).y, gridScale / 2, gridScale / 2);
                g.setColor(Color.YELLOW.darker());
                g.setFont(new Font("Dialog", Font.BOLD, 12));
                String text = "" + /** v + " | " + */
                        (int) graph.getValue(v);
                g.drawChars(text.toCharArray(), 0, text.length(), (int) coords.get(v).x + gridScale / 8,
                        (int) coords.get(v).y + gridScale / 3);
            }

            String redText = "Red: " + graph.playerValues().getOrDefault(0, 0);
            String blueText = "Blue: " + graph.playerValues().getOrDefault(1, 0);
            g.setColor(Color.RED);
            g.drawChars(redText.toCharArray(), 0, redText.length(), gridScale * 2 - win.getWidth() / 2,
                    gridScale * 2 - win.getHeight() / 2);
            g.setColor(Color.BLUE);
            g.drawChars(blueText.toCharArray(), 0, blueText.length(), gridScale * 2 - win.getWidth() / 2,
                    gridScale * 3 - win.getHeight() / 2);

        } // end paintComponent

    } // end LandscapePanel

    public void repaint() {
        this.win.repaint();
    }
}