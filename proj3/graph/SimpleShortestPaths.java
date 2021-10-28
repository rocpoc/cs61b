package graph;

/* See restrictions in Graph.java. */

import java.util.ArrayList;

/** A partial implementation of ShortestPaths that contains the weights of
 *  the vertices and the predecessor edges.   The client needs to
 *  supply only the two-argument getWeight method.
 *  @author ROCKY LUBBERS
 */
public abstract class SimpleShortestPaths extends ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public SimpleShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public SimpleShortestPaths(Graph G, int source, int dest) {
        super(G, source, dest);
        weights = new ArrayList<>(G.vertexSize() + 1);
        predecessors = new ArrayList<>(G.vertexSize() + 1);

        for (int i = 0; i < G.vertexSize() + 1; i++) {
            predecessors.add(0);
        }
        for (int i = 0; i < G.vertexSize() + 1; i++) {
            weights.add(0.0);
        }
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    @Override
    protected abstract double getWeight(int u, int v);

    @Override
    public double getWeight(int v) {
        return weights.get(v - 1);
    }

    @Override
    protected void setWeight(int v, double w) {
        weights.set(v - 1, w);
    }

    @Override
    public int getPredecessor(int v) {
        return predecessors.get(v - 1);
    }

    @Override
    protected void setPredecessor(int v, int u) {
        predecessors.set(v - 1, u);
    }

    /** List of weights for vertices. */
    private ArrayList<Double> weights;

    /** List of predecessors. */
    private ArrayList<Integer> predecessors;
}
