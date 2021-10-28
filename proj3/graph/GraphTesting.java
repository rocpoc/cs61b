package graph;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/** Unit tests for the Graph class.
 *  @author ROCKY LUBBERS
 */
public class GraphTesting {

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

    @Test
    public void testvertecies() {
        Graph G = new UndirectedGraph();
        int vert0 = G.add();
        int vert1 = G.add();
        int vert2 = G.add();
        int vert3 = G.add();
        assertEquals(G.contains(vert0), true);
        assertEquals(G.contains(vert1), true);
        assertEquals(G.contains(vert2), true);
        assertEquals(G.contains(vert3), true);
    }

    @Test
    public void testedges() {
        Graph G = new UndirectedGraph();
        int vert0 = G.add();
        int vert1 = G.add();
        int vert2 = G.add();
        int vert3 = G.add();
        G.add(1, 2);
        G.add(2, 4);
        G.add(2, 3);
        G.add(1, 4);
        assertEquals(G.contains(vert0, vert1), true);
        assertEquals(G.contains(vert0, vert3), true);
        assertEquals(G.contains(vert1, vert2), true);
        assertEquals(G.contains(vert0, vert3), true);
        assertEquals(G.contains(vert2, vert3), false);
    }

    @Test
    public void testremove() {
        Graph G = new UndirectedGraph();
        int vert0 = G.add();
        int vert1 = G.add();
        int vert2 = G.add();
        int vert3 = G.add();
        assertEquals(G.contains(vert0), true);
        assertEquals(G.contains(vert1), true);
        assertEquals(G.contains(vert2), true);
        assertEquals(G.contains(vert3), true);
        G.remove(vert0);
        G.remove(vert1);
        G.remove(vert2);
        G.remove(vert3);
        assertEquals(G.contains(vert0), false);
        assertEquals(G.contains(vert1), false);
        assertEquals(G.contains(vert2), false);
        assertEquals(G.contains(vert3), false);
    }

    @Test
    public void testsuccessor() {
        Graph G = new UndirectedGraph();
        int vert0 = G.add();
        int vert1 = G.add();
        int vert2 = G.add();
        int vert3 = G.add();
        G.add(1, 1);
        G.add(1, 2);
        G.add(1, 3);
        G.add(1, 4);
        int zeropred0 = G.successor(vert0, 0);
        int zeropred1 = G.successor(vert0, 1);
        int zeropred2 = G.successor(vert0, 2);
        int zeropred3 = G.successor(vert0, 3);
        int zero = G.predecessor(vert0, 0);
        assertEquals(zeropred0, 1);
        assertEquals(zeropred1, 2);
        assertEquals(zeropred2, 3);
        assertEquals(zeropred3, 4);
        assertEquals(zero, 1);
    }

    @Test
    public void testpredecessor() {
        Graph G = new UndirectedGraph();
        int vert0 = G.add();
        int vert1 = G.add();
        int vert2 = G.add();
        int vert3 = G.add();
        G.add(2, 1);
        G.add(3, 1);
        G.add(4, 1);
        int zeropred0 = G.predecessor(vert0, 0);
        int zeropred1 = G.predecessor(vert0, 1);
        int zeropred2 = G.predecessor(vert0, 2);
        int zeropred3 = G.predecessor(vert0, 4);
        assertEquals(zeropred0, 2);
        assertEquals(zeropred1, 3);
        assertEquals(zeropred2, 4);
        assertEquals(zeropred3, 0);
    }

    @Test
    public void testpredecessorsITER() {
        Graph G = new UndirectedGraph();
        int vert0 = G.add();
        int vert1 = G.add();
        int vert2 = G.add();
        int vert3 = G.add();
        G.add(2, 1);
        G.add(3, 1);
        G.add(4, 1);
        ArrayList<Integer> L = new ArrayList<>();
        graph.Iteration x = G.predecessors(1);
        while (x.hasNext()) {
            L.add((int) x.next());
        }
        assertEquals((long) L.get(0), 2);
        assertEquals((long) L.get(1), 3);
        assertEquals((long) L.get(2), 4);
    }

    @Test
    public void testsuccessorsITER() {
        Graph G = new UndirectedGraph();
        int vert0 = G.add();
        int vert1 = G.add();
        int vert2 = G.add();
        int vert3 = G.add();
        G.add(1, 1);
        G.add(1, 2);
        G.add(1, 3);
        ArrayList<Integer> L = new ArrayList<>();
        graph.Iteration x = G.successors(1);
        while (x.hasNext()) {
            L.add((int) x.next());
        }
        assertEquals((long) L.get(0), 1);
        assertEquals((long) L.get(1), 2);
        assertEquals((long) L.get(2), 3);
    }

    @Test
    public void intoutdegree() {
        Graph G = new UndirectedGraph();
        int vert0 = G.add();
        int vert1 = G.add();
        int vert2 = G.add();
        int vert3 = G.add();
        G.add(1, 2);
        G.add(2, 1);
        int k = G.inDegree(1);
        int x = G.outDegree(1);
        assertEquals(k, 1);
        assertEquals(x, 1);
        G.remove(1, 2);
        assertEquals(G.inDegree(1), 0);
        assertEquals(G.outDegree(1), 0);
    }


    @Test
    public void directedtestvertecies() {
        Graph G = new DirectedGraph();
        int vert0 = G.add();
        int vert1 = G.add();
        int vert2 = G.add();
        int vert3 = G.add();
        assertEquals(G.contains(vert0), true);
        assertEquals(G.contains(vert1), true);
        assertEquals(G.contains(vert2), true);
        assertEquals(G.contains(vert3), true);
    }

    @Test
    public void directedtestedges() {
        Graph G = new DirectedGraph();
        int vert0 = G.add();
        int vert1 = G.add();
        int vert2 = G.add();
        int vert3 = G.add();
        G.add(1, 2);
        G.add(2, 4);
        G.add(2, 3);
        G.add(1, 4);
        assertEquals(G.contains(vert0, vert1), true);
        assertEquals(G.contains(vert0, vert3), true);
        assertEquals(G.contains(vert1, vert2), true);
        assertEquals(G.contains(vert0, vert3), true);
        assertEquals(G.contains(vert2, vert3), false);
    }

    @Test
    public void directedtestremove() {
        Graph G = new DirectedGraph();
        int vert0 = G.add();
        int vert1 = G.add();
        int vert2 = G.add();
        int vert3 = G.add();
        assertEquals(G.contains(vert0), true);
        assertEquals(G.contains(vert1), true);
        assertEquals(G.contains(vert2), true);
        assertEquals(G.contains(vert3), true);
        G.remove(vert0);
        G.remove(vert1);
        G.remove(vert2);
        G.remove(vert3);
        assertEquals(G.contains(vert0), false);
        assertEquals(G.contains(vert1), false);
        assertEquals(G.contains(vert2), false);
        assertEquals(G.contains(vert3), false);
    }

    @Test
    public void directedtestsuccessor() {
        Graph G = new DirectedGraph();
        int vert0 = G.add();
        int vert1 = G.add();
        int vert2 = G.add();
        int vert3 = G.add();
        G.add(1, 1);
        G.add(1, 2);
        G.add(1, 3);
        G.add(1, 4);
        int zeropred0 = G.successor(vert0, 0);
        int zeropred1 = G.successor(vert0, 1);
        int zeropred2 = G.successor(vert0, 2);
        int zeropred3 = G.successor(vert0, 3);
        int zero = G.predecessor(vert0, 1);
        assertEquals(zeropred0, 1);
        assertEquals(zeropred1, 2);
        assertEquals(zeropred2, 3);
        assertEquals(zeropred3, 4);
        assertEquals(zero, 0);
    }

    @Test
    public void directedtestpredecessor() {
        Graph G = new DirectedGraph();
        int vert0 = G.add();
        int vert1 = G.add();
        int vert2 = G.add();
        int vert3 = G.add();
        G.add(2, 1);
        G.add(3, 1);
        G.add(4, 1);
        int zeropred0 = G.predecessor(vert0, 0);
        int zeropred1 = G.predecessor(vert0, 1);
        int zeropred2 = G.predecessor(vert0, 2);
        int zeropred3 = G.predecessor(vert0, 3);

        assertEquals(zeropred0, 2);
        assertEquals(zeropred1, 3);
        assertEquals(zeropred2, 4);
        assertEquals(zeropred3, 0);
    }

    @Test
    public void directedtestpredecessorsITER() {
        Graph G = new DirectedGraph();
        int vert0 = G.add();
        int vert1 = G.add();
        int vert2 = G.add();
        int vert3 = G.add();
        G.add(2, 1);
        G.add(3, 1);
        G.add(4, 1);
        ArrayList<Integer> L = new ArrayList<>();
        graph.Iteration x = G.predecessors(1);
        while (x.hasNext()) {
            L.add((int) x.next());
        }
        assertEquals((long) L.get(0), 2);
        assertEquals((long) L.get(1), 3);
        assertEquals((long) L.get(2), 4);
    }

    @Test
    public void directedtestsuccessorsITER() {
        Graph G = new DirectedGraph();
        int vert0 = G.add();
        int vert1 = G.add();
        int vert2 = G.add();
        int vert3 = G.add();
        G.add(1, 2);
        G.add(1, 3);
        G.add(1, 4);
        ArrayList<Integer> L = new ArrayList<>();
        graph.Iteration x = G.successors(1);
        while (x.hasNext()) {
            L.add((int) x.next());
        }
        assertEquals((long) L.get(0), 2);
        assertEquals((long) L.get(1), 3);
        assertEquals((long) L.get(2), 4);
    }

    @Test
    public void directedinoutdegree() {
        Graph G = new DirectedGraph();
        int vert0 = G.add();
        int vert1 = G.add();
        int vert2 = G.add();
        int vert3 = G.add();
        G.add(1, 2);
        G.add(2, 1);
        int k = G.inDegree(1);
        int x = G.outDegree(1);
        assertEquals(k, 1);
        assertEquals(x, 1);
        G.remove(1, 2);
        G.remove(2, 1);
        assertEquals(G.inDegree(1), 0);
        assertEquals(G.outDegree(1), 0);
    }

    @Test
    public void directedtestedgeITER() {
        Graph G = new DirectedGraph();
        int vert0 = G.add();
        int vert1 = G.add();
        int vert2 = G.add();
        int vert3 = G.add();
        G.add(1, 2);
        int[] one = {1, 2};
        G.add(2, 1);
        int[] two = {2, 1};
        G.add(3, 1);
        int[] three = {3, 1};
        ArrayList<int[]> L = new ArrayList<>();
        graph.Iteration x = G.edges();
        while (x.hasNext()) {
            int[] me = (int[]) x.next();
            L.add(me);
        }
        assertArrayEquals(L.get(0), one);
        assertArrayEquals(L.get(1), two);
        assertArrayEquals(L.get(2), three);
    }

    @Test
    public void testmaxvert() {
        Graph G = new DirectedGraph();
        int vert0 = G.add();
        int vert1 = G.add();
        int vert2 = G.add();
        int vert3 = G.add();
        G.add(1, 2);
        G.add(1, 3);
        assertEquals(G.vertexSize(), 4);
        assertEquals(G.edgeSize(), 2);
        G.remove(1, 2);
        G.remove(1, 3);
        assertEquals(G.edgeSize(), 0);
        assertEquals(G.vertexSize(), 4);
        G.remove(vert0);
        G.remove(vert3);
        assertEquals(G.vertexSize(), 2);
        G.remove(vert1);
        G.remove(vert2);
        G.remove(4);
        assertEquals(G.vertexSize(), 0);
    }

    @Test
    public void stackoverflowtest() {
        Graph G = new UndirectedGraph();
        G.add();
        G.add();
        G.add(1, 2);
        G.add(1, 1);
        assertEquals(G.edgeSize(), 2);
        G.remove(1);
        assertEquals(G.edgeSize(), 0);
        G.add();
        G.add(1, 2);
        G.add(2, 1);
        G.add(1, 2);
        G.add(2, 1);
        assertEquals(G.edgeSize(), 1);
    }


}
