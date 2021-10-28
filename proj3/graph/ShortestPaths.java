package graph;

/* See restrictions in Graph.java. */
import java.util.List;
import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.Queue;
import java.util.Iterator;

/** The shortest paths through an edge-weighted graph.
 *  By overrriding methods getWeight, setWeight, getPredecessor, and
 *  setPredecessor, the client can determine how to represent the weighting
 *  and the search results.  By overriding estimatedDistance, clients
 *  can search for paths to specific destinations using A* search.
 *  @author ROCKY LUBBERS
 */
public abstract class ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public ShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        _source = source;
        _dest = dest;
    }


    /** Initialize the shortest paths.  Must be called before using
     *  getWeight, getPredecessor, and pathTo. */
    public void setPaths() {
        for (int i = 1; i < _G.vertexSize() + 1; i++) {
            setWeight(i, (double) Integer.MAX_VALUE);
        }
        setWeight(_source, 0.0);
        Traversal t = new Updatetraversal(_G);
        t.traverse(_source);
    }

    /** Returns the starting vertex. */
    public int getsource() {
        return _source;
    }

    /** Returns the target vertex, or 0 if there is none. */
    public int getDest() {
        return _dest;
    }

    /** Returns the current weight of vertex V in the graph.  If V is
     *  not in the graph, returns positive infinity. */
    public abstract double getWeight(int v);

    /** Set getWeight(V) to W. Assumes V is in the graph. */
    protected abstract void setWeight(int v, double w);

    /** Returns the current predecessor vertex of vertex V in the graph, or 0 if
     *  V is not in the graph or has no predecessor. */
    public abstract int getPredecessor(int v);

    /** Set getPredecessor(V) to U. */
    protected abstract void setPredecessor(int v, int u);

    /** Returns an estimated heuristic weight of the shortest path from vertex
     *  V to the destination vertex (if any).  This is assumed to be less
     *  than the actual weight, and is 0 by default. */
    protected double estimatedDistance(int v) {
        return 0.0;
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    protected abstract double getWeight(int u, int v);

    /** Returns a list of vertices starting at _source and ending
     *  at V that represents a shortest path to V.  Invalid if there is a
     *  destination vertex other than V. */
    public List<Integer> pathTo(int v) {
        List<Integer> L = new ArrayList<>();
        L.add(v);
        while (v != _source) {
            L.add(getPredecessor(v));
            v = getPredecessor(v);
        }
        Collections.reverse(L);
        return L;
    }

    /** Returns a list of vertices starting at the source and ending at the
     *  destination vertex. Invalid if the destination is not specified. */
    public List<Integer> pathTo() {
        return pathTo(getDest());
    }

    /** A* Traversal. */
    private class Updatetraversal extends Traversal {

        /** Creates new Traversal from Graph G. */
        Updatetraversal(Graph G) {
            super(G, new TreeQueue());
        }

        @Override
        protected boolean visit(int v) {
            if (v == getDest()) {
                return false;
            }
            return true;
        }
        @Override
        protected boolean processSuccessor(int u, int v) {
            if (getWeight(v)  > getWeight(u, v) + getWeight(u)) {
                setWeight(v, getWeight(u, v) + getWeight(u));
                setPredecessor(v, u);
                _fringe.remove(v);
                return true;
            }
            return false;
        }
    }

    /** The graph being searched. */
    protected final Graph _G;
    /** The starting vertex. */
    private final int _source;
    /** The target vertex. */
    private final int _dest;

    /** TreeSet wrapped in Priority Queue. */
    private class TreeQueue implements Queue {

        /** TreeSet for Queue. */
        private TreeSet<Integer> ts;

        /** Return TreeSet for Queue. */
        protected TreeSet<Integer> getts() {
            return ts;
        }

        /** Create Priority Queue + TreeSet. */
        TreeQueue() {
            ts = new TreeSet(new Updatecomp());
        }

        @Override
        public int size() {
            return ts.size();
        }

        @Override
        public boolean isEmpty() {
            return ts.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return ts.contains(o);
        }

        @Override
        public Iterator iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public Object[] toArray(Object[] a) {
            return new Object[0];
        }

        @Override
        public boolean add(Object o) {
            return ts.add((Integer) o);
        }

        @Override
        public boolean remove(Object o) {
            return ts.remove(o);
        }

        @Override
        public boolean addAll(Collection c) {
            return ts.addAll(c);
        }

        @Override
        public void clear() {
            ts.clear();
        }

        @Override
        public boolean retainAll(Collection c) {
            return ts.retainAll(c);
        }

        @Override
        public boolean removeAll(Collection c) {
            return ts.removeAll(c);
        }

        @Override
        public boolean containsAll(Collection c) {
            return ts.containsAll(c);
        }

        @Override
        public boolean offer(Object o) {
            return false;
        }

        @Override
        public Object remove() {
            return null;
        }

        @Override
        public Object poll() {
            return ts.pollFirst();
        }

        @Override
        public Object element() {
            return null;
        }

        @Override
        public Object peek() {
            return null;
        }

    }

    /** Updated Comparator for A* Traversal. */
    public class Updatecomp implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            Integer v = (Integer) o1;
            Integer u = (Integer) o2;
            double w1 = getWeight(Math.abs((Integer) o1))
                    + estimatedDistance((Integer) o1);
            double w2 = getWeight(Math.abs((Integer) o2))
                    + estimatedDistance((Integer) o2);
            int c =  Double.compare(w1, w2);
            if (c > 0) {
                return 1;
            }
            if (c < 0) {
                return -1;
            }
            if (c == 0) {
                return v.compareTo(u);
            } else {
                return c;
            }

        }
    }
}
