package graph;

/* See restrictions in Graph.java. */

import java.util.ArrayList;

/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *
 *  @author Rocky Lubbers
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public int inDegree(int v) {
        if (contains(v)) {
            for (Vertex vert : vertextlist) {
                if (vert != null && vert.id() == v) {
                    return vert.predecessors().size();
                }
            }
        }
        return 0;
    }

    @Override
    public int predecessor(int v, int k) {
        for (Vertex vert : vertextlist) {
            if (vert != null && vert.id() == v) {
                if (k < vert.predecessors().size()) {
                    return (int) vert.predecessors().get(k);
                }
            }
        }
        return 0;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        ArrayList<Integer> L = new ArrayList<>();
        for (Vertex vert : vertextlist) {
            if (vert != null && vert.id() == v) {
                for (int i = 0; i < vert.predecessors().size(); i++) {
                    L.add((int) vert.predecessors().get(i));
                }
                break;
            }
        }
        return Iteration.iteration(L);
    }
}
