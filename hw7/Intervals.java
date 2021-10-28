import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.Math;
import java.util.*;

/** HW #8, Problem 3.
 *  @author Rocky Lubbers
  */
public class Intervals {
    /** Assuming that INTERVALS contains two-element arrays of integers,
     *  <x,y> with x <= y, representing intervals of ints, this returns the
     *  total length covered by the union of the intervals.
     *  CITE - used stackoverflow for Comparator method of sorting.*/
    public int coveredLength(List<int[]> intervals) {
        java.util.Collections.sort(intervals, new newComparator());
        List<int[]> L = new ArrayList<>();
        int sum = 0;
        int head = intervals.get(0)[0];
        int tail = intervals.get(0)[1];
        for (int i = 1; i < intervals.size(); i++) {
            int[] current = intervals.get(i);
            if (current[0] <= tail) {
                tail = Math.max(current[1], tail);
            } else {
                L.add(new int[]{head,tail});
                head = current[0];
                tail = current[1];
                L.add(new int[]{head,tail});
            }
        }
        for (int[] i : L) {
            sum += i[1] -i[0];
        }
        return sum;
    }

    /** Overiding the comparator class to compare
     * intervals and sort them efficiently */
    class newComparator implements Comparator {
        public int compare(Object o1, Object o2){
            int[] i1 = (int[]) o1;
            int[] i2 = (int[]) o2;
            return i1[0] - i2[0];
        }
    }

    /** Test intervals. */
    static final int[][] INTERVALS = {
        {19, 30},  {8, 15}, {3, 10}, {6, 12}, {4, 5},
    };
    /** Covered length of INTERVALS. */
    static final int CORRECT = 23;

    /** Performs a basic functionality test on the coveredLength method. */
    @Test
    public void basicTest() {
        assertEquals(CORRECT, coveredLength(Arrays.asList(INTERVALS)));
    }

    /** Runs provided JUnit test. ARGS is ignored. */
    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(Intervals.class));
    }

}
