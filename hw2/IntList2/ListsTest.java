import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *
 *  @author Rocky Lubbers
 */


public class ListsTest {

    @Test
    public void testRuns() {

        int[] test = new int[] {1, 3, 7, 5, 4, 6, 9, 10, 10, 11};
        int[][] anotherone = new int[][] {{1, 3, 7}, {5}, {4,6,9,10}, {10, 11}};
        IntList list0 = IntList.list(test);
        IntList2 sample = IntList2.list(anotherone);
        IntList2 tested = Lists.naturalRuns(list0);
        assertEquals(tested, sample);
        int[] next_test = new int[] {};
        IntList next_tested1 = IntList.list(next_test);
        IntList2 next_tested = Lists.naturalRuns(next_tested1);
        assertEquals(next_tested, null);

        int[] nu = new int[] {0,1,2,3,4};
        IntList newww = IntList.list(nu);
        int[][] nutest = new int[][] {{0, 1, 2, 3, 4}};
        assertEquals(newww, Lists.naturalRuns(newww));

    }


    // It might initially seem daunting to try to set up
    // Intlist2 expected.
   	//
    // There is an easy way to get the IntList2 that you want in just
    // few lines of code! Make note of the IntList2.list method that
    // takes as input a 2D array.

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ListsTest.class));
    }
}

