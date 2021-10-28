package graph;

/* See restrictions in Graph.java. */

import java.util.ArrayList;
import java.util.LinkedList;

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *  Cite - nested class implementation from piazza and slack discussions.
 *  @author Rocky Lubbers
 */
abstract class GraphObj extends Graph {

    /** Array to hold Vertecies. */
    protected ArrayList<Vertex> vertextlist;

    /** Array to hold Edges. */
    protected ArrayList<Edge> edgelist;

    /** A new, empty Graph. */
    GraphObj() {
        vertextlist = new ArrayList<>();
        edgelist = new ArrayList<>();
    }

    @Override
    public int vertexSize() {
        int count = 0;
        for (Vertex v : vertextlist) {
            if (v != null && vertextlist.indexOf(v) != 0) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int maxVertex() {
        if (vertextlist.isEmpty()) {
            return 0;
        } else {
            for (int i = vertextlist.size() - 1; i > 0; i--) {
                if (vertextlist.get(i) != null) {
                    return i;
                }
            }
        }
        return 0;
    }

    @Override
    public int edgeSize() {
        int count = 0;
        for (Edge e : edgelist) {
            if (e != null) {
                count++;
            }
        }
        return count;
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        if (contains(v)) {
            for (Vertex vert : vertextlist) {
                if (vert != null && vert.id() == v) {
                    return vert.successors.size();
                }
            }
        }
        return 0;
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        for (Vertex v : vertextlist) {
            if (v != null && v.id() == u) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(int u, int v) {
        if (contains(u) && contains(v)) {
            for (Edge e: edgelist) {
                if ((e.from() == u && e.to() == v)) {
                    return true;
                } else if (!isDirected() && (e.from() == v && e.to() == u)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int add() {
        if (vertextlist.isEmpty()) {
            Vertex v0 = new Vertex();
            Vertex v1 = new Vertex();
            v1.set(1);
            vertextlist.add(v0);
            vertextlist.add(v1);
            return 1;
        } else {
            if (vertextlist.contains(null)) {
                int index = vertextlist.indexOf(null);
                Vertex v = new Vertex();
                vertextlist.set(index, v);
                v.set(vertextlist.indexOf(v));
                return v.id();
            } else {
                Vertex v = new Vertex();
                vertextlist.add(v);
                v.set(vertextlist.indexOf(v));
                return v.id();
            }
        }
    }

    /** Add complex edges U and V to graph. */
    private void edgeset(int u, int v) {
        if (u != v) {
            vertextlist.get(u).addsuc(v);
            vertextlist.get(u).addpred(v);
            vertextlist.get(v).addpred(u);
            vertextlist.get(v).addsuc(u);
        } else {
            vertextlist.get(u).addsuc(v);
            vertextlist.get(v).addpred(u);
        }
    }

    /** Add simple edges U and V to graph. */
    private void simpleedgeset(int u, int v) {
        vertextlist.get(u).addsuc(v);
        vertextlist.get(v).addpred(u);
    }

    @Override
    public int add(int u, int v) {
        Edge e = new Edge(u, v);
        int edgeID = edgeId(u, v);
        if (!isDirected() && contains(v, u) || contains(u, v)) {
            return edgeId(u, v);
        }
        if (isDirected()) {
            if (edgelist.isEmpty()) {
                e.set(edgeID);
                edgelist.add(e);
                simpleedgeset(u, v);
                return edgeId(u, v);
            }
            if (edgelist.contains(null)) {
                int index = edgelist.indexOf(null);
                edgelist.set(index, e);
                e.set(edgeID);
                simpleedgeset(u, v);
                return edgeID;
            } else {
                edgelist.add(e);
                e.set(edgeID);
                simpleedgeset(u, v);
                return edgeID;
            }
        } else if (!isDirected()) {
            if (edgelist.isEmpty()) {
                e.set(edgeID);
                edgelist.add(e);
                edgeset(u, v);
                return edgeID;
            }
            if (edgelist.contains(null)) {
                int index = edgelist.indexOf(null);
                edgelist.set(index, e);
                e.set(edgeID);
                edgeset(u, v);
                return edgeID;
            } else {
                edgelist.add(e);
                e.set(edgeID);
                edgeset(u, v);
                return edgeID;
            }
        }
        return edgeId(u, v);
    }

    @Override
    public void remove(int v) {
        for (Vertex vert : vertextlist) {
            while (vert != null && vert.id() == v) {
                while (!vert.predecessors.isEmpty()) {
                    remove(v, vert.predecessors.getFirst());
                }
                while (!vert.successors.isEmpty()) {
                    remove(v, vert.successors.getFirst());
                }
                if (vert.successors.isEmpty() && vert.predecessors.isEmpty()) {
                    vertextlist.set(v, null);
                    break;
                }
            }
        }
        vertextlist.set(v, null);
    }

    @Override
    public void remove(int u, int v) {
        ArrayList<Edge> remove = new ArrayList<>();
        for (Edge e : edgelist) {
            if (e != null && isDirected() && edgeId(u, v) == e.id()) {
                Vertex vertu = vertextlist.get(u);
                Vertex vertv = vertextlist.get(v);
                vertu.successors.remove((Integer) v);
                vertv.predecessors.remove((Integer) u);
                remove.add(e);
                break;
            } else if (e != null && (!isDirected() && edgeId(u, v) == e.id())
                    || edgeId(v, u) == e.id()) {
                Vertex vertu = vertextlist.get(u);
                Vertex vertv = vertextlist.get(v);
                vertu.successors.remove((Integer) v);
                vertu.predecessors.remove((Integer) v);
                vertv.successors.remove((Integer) u);
                vertv.predecessors.remove((Integer) u);
                remove.add(e);
                break;
            }
        }
        edgelist.removeAll(remove);
    }

    @Override
    public Iteration<Integer> vertices() {
        ArrayList<Integer> L = new ArrayList<>();
        for (Vertex v : vertextlist) {
            if (v != null && vertextlist.indexOf(v) != 0) {
                L.add(v.id());
            }
        }
        return Iteration.iteration(L);
    }

    @Override
    public int successor(int v, int k) {
        for (Vertex vert : vertextlist) {
            if (vert != null && vert.id() == v) {
                if (k < vert.successors().size()) {
                    return (int) vert.successors().get(k);
                }
            }
        }
        return 0;
    }

    @Override
    public abstract int predecessor(int v, int k);

    @Override
    public Iteration<Integer> successors(int v) {
        ArrayList<Integer> L = new ArrayList<>();
        for (Vertex vert : vertextlist) {
            if (vert != null && vert.id() == v) {
                for (int i = 0; i < vert.successors.size(); i++) {
                    L.add(vert.successors.get(i));
                }
            }
        }
        return Iteration.iteration(L);
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        ArrayList<int[]> L = new ArrayList<>();
        for (Edge E : edgelist) {
            int[] i = new int[]{E.from(), E.to()};
            L.add(i);
        }
        return Iteration.iteration(L);
    }

    @Override
    protected void checkMyVertex(int v) {
        if (!contains(v)) {
            throw new IllegalArgumentException("vertex not from Graph");
        }
    }

    /** Cite -- pairing function by Matthew Szudzik @ Wolfram Research. */
    @Override
    protected int edgeId(int u, int v) {
        if (!isDirected() && u != v) {
            int temp = u;
            u = Math.min(u, v);
            v = Math.max(temp, v);
        }
        if (u >= v) {
            return (u * u + u + v);
        } else {
            return (v * v + u);
        }
    }

    /** Vertex class. */
    class Vertex {
        /** Vertex ID. */
        private int vertex;

        /** List of Predecessors. */
        private LinkedList<Integer> predecessors;

        /** List of Successors. */
        private LinkedList<Integer> successors;

        /** Creates new Vertex. */
        Vertex() {
            predecessors = new LinkedList<>();
            successors = new LinkedList<>();
        }

        /** Sets vertex to V. */
        final void set(int v) {
            vertex = v;
        }

        /** Returns ID of the Vertex. */
        final int id() {
            return vertex;
        }

        /** Return the predecessors. */
        LinkedList predecessors() {
            return predecessors;
        }

        /** Return the successors. */
        LinkedList successors() {
            return successors;
        }

        /** Add predecessor X to me. */
        void addpred(int x) {
            this.predecessors.add(x);
        }

        /** Add successor X to me. */
        void addsuc(int x) {
            this.successors.add(x);
        }
    }

    /** Edge class. */
    class Edge {
        /** Edge TO. */
        private int to;

        /** Edge FROM. */
        private int from;

        /** Number of the edge. */
        private int edge;

        /** Construct a new Edge from vertices U and V. */
        Edge(int u, int v) {
            from = u;
            to = v;
        }

        /** Return edge FROM. */
        protected int from() {
            return from;
        }

        /** Return edge TO. */
        protected int to() {
            return to;
        }

        /** Set edge to V. */
        final void set(int v) {
            edge = v;
        }

        /** Return edge ID. */
        protected int id() {
            return edge;
        }
    }
}
