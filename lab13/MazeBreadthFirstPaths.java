import java.util.Observable;
import java.util.PriorityQueue;
import java.util.Queue;
/**
 *  @author Josh Hug
 */

public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze m;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at vertex x. */
    private void bfs(int s) {
        Queue<Integer> fringe = new PriorityQueue<>();
        marked[s] = true;
        fringe.add(s);
        while (!fringe.isEmpty()) {
            int v = fringe.poll();
            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    announce();
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    fringe.add(w);
                }
                if (v == t) {
                    targetFound = true;
                    announce();
                    return;
                }
            }
        }
    }


    @Override
    public void solve() {
         bfs(s);
    }
}

