import java.util.Observable;
/** 
 *  @author Josh Hug
 *  CITE -- recieved help in lab from Cyan Bastiaans on Cycles.
 */

public class MazeCycles extends MazeExplorer {
    /* Inherits public fields: 
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private int[] E = new int[edgeTo.length];
    int dub;
    private boolean b;


    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        s = maze.xyTo1D(1,1);
        t = maze.xyTo1D(m.N(), m.N());
        distTo[s] = 0;
        edgeTo[s] = s;
    }


    private void cfs(int v) {
        marked[v] = true;
        announce();
        if (v == t) {
            targetFound = true;
        }
        if (targetFound) {
            return;
        }

        for (int w : maze.adj(v)) {
            if (marked[w] && E[v] != w) {
                edgeTo[w] = v;
                dub = w;
                announce();
                targetFound = true;
                return;
            }
            else if (!marked[w]) {
                E[w] = v;
                announce();
                distTo[w] = distTo[v] + 1;
                cfs(w);
                if (targetFound) {
                    if (!b) {
                        edgeTo[w] = v;
                    }
                    if (v == dub) {
                        b = true;
                    }
                    announce();
                    return;
                }
            }
        }
    }

    // For every visited vertex v, if there is an adjacent u
    // such that u is already visited and u is not parent of v,
    // then there is a cycle in graph.



    @Override
    public void solve() {
        cfs(s);

    }
} 

