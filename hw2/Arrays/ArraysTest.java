import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *  @author Rocky
 */

public class ArraysTest {

    @Test
    public void testcatenate() {
        int[] a = new int[] {1,2,3};
        int[] b = new int[] {4,5,6};
        int[] c = Arrays.catenate(a,b);
        int[] d = new int[] {1,2,3,4,5,6};
        int[] n = new int[] {};

        int[] r = new int[] {10,9,8};
        int[] s = new int[] {7,6,5};
        int[] t = Arrays.catenate(r, s);
        int[] u = new int[] {10,9,8,7,6,5};

        assertArrayEquals(c, d);
        assertArrayEquals(t, u);
        assertArrayEquals(a, Arrays.catenate(a, n));

    }

    @Test
    public void test_remove() {
        int[] x = new int[] {1,2,3,4,5,6,7,8,9,10};
        int[] test_x = Arrays.remove(x,2,3);
        int[] answer = new int[] {1,2,7,8,9,10};
        assertArrayEquals(test_x, answer);
    }


    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}
