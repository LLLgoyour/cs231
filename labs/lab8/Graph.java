/*
 * file name: Graph.java
 * author: Jack Dai
 * last modified: 11/20/2025
 * purpose of the class:
 * This class builds the Graph structure that contains Vertex and Edge objects.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Graph {
    private ArrayList<Vertex> vertices;
    private ArrayList<Edge> edges;

    /**
     * this constructor is equivalent to Graph(0).
     */
    public Graph() {
        this(0);
    }

    /**
     * this constructor is equivalent to Graph(n, 0.0).
     * 
     * @param n
     */
    public Graph(int n) {
        this(n, 0.0);
    }

    /**
     * The default constructor creates a graph of n vertices where each pair of
     * vertices has an edge
     * between them of distance 1 with probability given by the supplied
     * probability.
     * 
     * @param n
     * @param probability
     */
    public Graph(int n, double probability) {
        // Initialize vertex and edge lists
        vertices = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();

        for (int i = 0; i < n; i++)
            vertices.add(new Vertex());

        // For each unordered pair, add an edge with given probability
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (Math.random() <= probability) {
                    Edge e = new Edge(vertices.get(i), vertices.get(j), 1.0);
                    edges.add(e);
                    vertices.get(i).addEdge(e);
                    vertices.get(j).addEdge(e);
                }
            }
        }
    }

    /**
     * A graph constructor that takes in a filename and builds
     * the graph with the number of vertices and specific edges
     * specified.
     * 
     * @param filename
     */
    public Graph(String filename) {

        try {
            // Setup for reading the file
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            // Get the number of vertices from the file and initialize that number of
            // vertices
            vertices = new ArrayList<Vertex>();
            Integer numVertices = Integer.valueOf(br.readLine().split(": ")[1]);
            for (int i = 0; i < numVertices; i++) {
                vertices.add(new Vertex());
            }

            // Read in the edges specified by the file and create them
            edges = new ArrayList<Edge>(); // If you used a different data structure to store Edges, you'll need to
                                           // update
                                           // this line
            String header = br.readLine(); // We don't use the header, but have to read it to skip to the next line
            // Read in all the lines corresponding to edges
            String line = br.readLine();
            while (line != null) {
                // Parse out the index of the start and end vertices of the edge
                String[] arr = line.split(",");
                Integer start = Integer.valueOf(arr[0]);
                Integer end = Integer.valueOf(arr[1]);

                // Make the edge that starts at start and ends at end with weight 1
                Edge edge = new Edge(vertices.get(start), vertices.get(end), 1.);
                // Add the edge to the set of edges for each of the vertices
                vertices.get(start).addEdge(edge);
                vertices.get(end).addEdge(edge);
                // Add the edge to the ArrayList of edges in the graph
                this.edges.add(edge);

                // Read the next line
                line = br.readLine();
            }
            // call the close method of the BufferedReader:
            br.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Graph constructor:: unable to open file " + filename + ": file not found");
        } catch (IOException ex) {
            System.out.println("Graph constructor:: error reading file " + filename);
        }
    }

    /**
     * This method returns the number of vertices
     * 
     * @return the number of vertices
     */
    public int size() {
        if (this.vertices == null) {
            return 0;
        }
        return this.vertices.size();
    }

    /**
     * This method returns an ArrayList object that can be used to iterate over the
     * vertices (don't re-invent the wheel here, this should be as simple as
     * returning the structure you're using to keep track of the vertices)
     * 
     * @return an ArrayList object that can be used to iterate over the vertices
     */
    public ArrayList<Vertex> getVertices() {
        return this.vertices;
    }

    /**
     * This method returns an ArrayList object that iterates over the edges.
     * 
     * @return an ArrayList object that iterates over the edges.
     */
    public ArrayList<Edge> getEdges() {
        return this.edges;
    }

    /**
     * This method creates a new Vertex, adds it to the Graph, and returns it.
     * 
     * @return the new added Vertex
     */
    public Vertex addVertex() {
        if (this.vertices == null) {
            this.vertices = new ArrayList<Vertex>();
        }
        Vertex v = new Vertex();
        this.vertices.add(v);
        return v;
    }

    /**
     * This method creates a new Edge, adds it to the Graph (make sure the endpoints
     * are aware of this new Edge), and returns it.
     * 
     * @param u
     * @param v
     * @param distance
     * @return the new added Edge
     */
    public Edge addEdge(Vertex u, Vertex v, double distance) {
        if (u == null || v == null) {
            return null;
        }
        if (this.edges == null) {
            this.edges = new ArrayList<Edge>();
        }
        Edge e = new Edge(u, v, distance);
        this.edges.add(e);
        u.addEdge(e);
        v.addEdge(e);
        return e;
    }

    /**
     * This method returns the Edge between u and v if such an Edge exists,
     * otherwise returns null.
     * 
     * @param u
     * @param v
     * @return the Edge between u and v if such an Edge exists, otherwise returns
     *         null.
     */
    public Edge getEdge(Vertex u, Vertex v) {
        if (u == null || v == null) {
            return null;
        }
        // search incident edges of u for an edge connecting to v
        for (Edge e : u.incidentEdges()) {
            Vertex other = e.other(u);
            if (other == v) {
                return e;
            }
        }
        return null;
    }

    /**
     * This method returns the Vertex at index
     * 
     * @param index
     * @return the Vertex at index
     */
    public Vertex getVertex(int index) {
        if (this.vertices == null) {
            return null;
        }
        return this.vertices.get(index);
    }

    /**
     * If the given Vertex vertex is in this Graph, removes it and returns true.
     * Otherwise, returns false.
     * 
     * @param vertex
     * @return true if the given Vertex vertex is in this Graph. Otherwise, returns
     *         false.
     */
    public boolean remove(Vertex vertex) {
        if (vertex == null || this.vertices == null) {
            return false;
        }
        if (!this.vertices.contains(vertex)) {
            return false;
        }

        // Remove all incident edges first
        ArrayList<Edge> incident = new ArrayList<Edge>(vertex.incidentEdges());
        for (Edge e : incident) {
            this.remove(e);
        }

        // Remove the vertex from the list
        return this.vertices.remove(vertex);
    }

    /**
     * If the given Edge is in the Graph, removes it and returns true. Otherwise,
     * returns false.
     * 
     * @param edge
     * @return true if the given Edge is in the Graph. Otherwise,
     *         returns false.
     */
    public boolean remove(Edge edge) {
        if (edge == null || this.edges == null) {
            return false;
        }
        boolean removed = this.edges.remove(edge);
        if (!removed) {
            return false;
        }

        // inform endpoints to remove this edge
        try {
            Vertex[] verts = edge.vertices();
            if (verts != null) {
                for (Vertex v : verts) {
                    if (v != null) {
                        v.removeEdge(edge);
                    }
                }
            }
        } catch (Exception ex) {
            // ignore
        }

        return true;
    }

    /**
     * This method uses Dijkstra's algorithm to compute the minimal distance in this
     * Graph from the given Vertex source to all other Vertices in the graph. The
     * HashMap returned maps each Vertex to its distance from the source.
     * 
     * @param source
     * @return maps each Vertex to its distance from the source.
     */
    public HashMap<Vertex, Double> distanceFrom(Vertex source) {
        HashMap<Vertex, Double> dist = new HashMap<Vertex, Double>();
        if (this.vertices == null) {
            return dist;
        }

        // Initialize distances
        for (Vertex v : this.vertices) {
            dist.put(v, Double.POSITIVE_INFINITY);
        }
        if (source == null || !dist.containsKey(source)) {
            return dist;
        }
        dist.put(source, 0.0);

        // Priority queue ordered by current distance in dist map
        PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>(new java.util.Comparator<Vertex>() {
            public int compare(Vertex a, Vertex b) {
                double da = dist.get(a);
                double db = dist.get(b);
                return Double.compare(da, db);
            }
        });

        // add all vertices
        for (Vertex v : this.vertices) {
            pq.add(v);
        }

        while (!pq.isEmpty()) {
            Vertex u = pq.poll();
            double du = dist.get(u);
            if (du == Double.POSITIVE_INFINITY) {
                break; // remaining vertices unreachable
            }

            for (Edge e : u.incidentEdges()) {
                Vertex v = e.other(u);
                if (v == null) {
                    continue;
                }
                double alt = du + e.distance();
                double dv = dist.get(v);
                if (alt < dv) {
                    // decrease priority: remove and re-add with new distance
                    dist.put(v, alt);
                    // Java PQ doesn't support decrease-key; remove and re-add
                    pq.remove(v);
                    pq.add(v);
                }
            }
        }

        return dist;
    }
}
