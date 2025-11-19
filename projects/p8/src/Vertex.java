/*
 * file name: Vertex.java
 * author: Jack Dai
 * last modified: 11/20/2025
 * purpose of the class:
 * This class defines Vertex objects.
 */

import java.util.ArrayList;

public class Vertex {
    private ArrayList<Edge> edges;

    /**
     * This constructor initialize a Vertex.
     */
    public Vertex() {
        this.edges = new ArrayList<Edge>();
    }

    /**
     * This method returns the Edge which connects this vertex and the given Vertex
     * vertex if
     * such an Edge exists, otherwise returns null.
     * 
     * @param vertex
     * @return the Edge which connects this vertex and the given Vertex vertex if
     *         such an Edge exists, otherwise returns null.
     */
    public Edge getEdgeTo(Vertex vertex) {
        if (vertex == null) {
            return null;
        }
        for (Edge e : this.edges) {
            try {
                Vertex other = e.other(this);
                if (other != null) {
                    if (other == vertex) {
                        return e;
                    } else {
                        continue;
                    }
                }
            } catch (Exception ex) {
                // ignore and fallback to vertices()
            }

            // Fallback: inspect vertices() array
            try {
                Vertex[] verts = e.vertices();
                if (verts != null && verts.length >= 2) {
                    if (verts[0] == vertex || verts[1] == vertex) {
                        return e;
                    }
                } else if (verts != null && verts.length == 1) {
                    if (verts[0] == vertex) {
                        return e;
                    }
                }
            } catch (Exception ex) {
                // ignore and continue
            }
        }
        return null;
    }

    /**
     * This method adds the specified Edge edge to the ArrayList of Edges incident
     * to this Vertex. Note: this should not do anything else. Any other
     * book-keeping will be handled in the Graph class.
     * 
     * @param edge
     */
    public void addEdge(Edge edge) {
        if (edge == null) {
            return;
        }
        if (!this.edges.contains(edge)) {
            this.edges.add(edge);
        }
    }

    /**
     * This method removes this Edge from the ArrayList of Edges incident to this
     * Vertex.
     * Returns true if this Edge was connected to this Vertex, otherwise returns
     * false. Note: this should not do anything else. Any other book-keeping will be
     * handled in the Graph class.
     * 
     * @param edge
     * @return true if this Edge was connected to this Vertex, otherwise returns
     *         false. Note: this should not do anything else. Any other book-keeping
     *         will be handled in the Graph class.
     */
    public boolean removeEdge(Edge edge) {
        if (edge == null) {
            return false;
        }
        return this.edges.remove(edge);
    }

    /**
     * This method returns an ArrayList of all the Vertices adjacent to this Vertex.
     * 
     * @return an ArrayList of all the Vertices adjacent to this Vertex.
     */
    public ArrayList<Vertex> adjacentVertices() {
        ArrayList<Vertex> adj = new ArrayList<Vertex>();
        for (Edge e : this.edges) {
            Vertex other = null;
            try {
                other = e.other(this);
            } catch (Exception ex) {
                other = null;
            }

            if (other == null) {
                try {
                    Vertex[] verts = e.vertices();
                    if (verts == null) {
                        continue;
                    }
                    if (verts.length == 2) {
                        Vertex candidate = (verts[0] == this) ? verts[1] : verts[0];
                        if (candidate != this && !adj.contains(candidate)) {
                            adj.add(candidate);
                        }
                    } else if (verts.length == 1) {
                        if (verts[0] != this && !adj.contains(verts[0])) {
                            adj.add(verts[0]);
                        }
                    }
                } catch (Exception ex) {
                    // ignore
                }
            } else {
                if (other != this && !adj.contains(other)) {
                    adj.add(other);
                }
            }
        }
        return adj;
    }

    /**
     * This method returns an ArrayList of all the Edges incident to this Vertex.
     * 
     * @return an ArrayList of all the Edges incident to this Vertex.
     */
    public ArrayList<Edge> incidentEdges() {
        return new ArrayList<Edge>(this.edges);
    }
}
