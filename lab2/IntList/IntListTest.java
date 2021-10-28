import static org.junit.Assert.*;
import org.junit.Test;

public class IntListTest {

    /** Example test that verifies correctness of the IntList.list static
     *  method. The main point of this is to convince you that
     *  assertEquals knows how to handle IntLists just fine.
     */

    @Test
    public void testList() {
        IntList one = new IntList(1, null);
        IntList twoOne = new IntList(2, one);
        IntList threeTwoOne = new IntList(3, twoOne);

        IntList x = IntList.list(3, 2, 1);
        assertEquals(threeTwoOne, x);
    }

    @Test
    public void testdSquareList() {
        IntList L = IntList.list(1, 2, 3);
        IntList.dSquareList(L);
        assertEquals(IntList.list(1, 4, 9), L);
    }

    /*  Do not use the "new" keyword in your tests. You can create
     *  lists using the handy IntList.list method.
     *
     *  Make sure to include test cases involving lists of various sizes
     *  on both sides of the operation. That includes the empty list, which
     *  can be instantiated, for example, with
     *  IntList empty = IntList.list().
     */

    @Test
    public void testSquareListRecursive() {
        IntList start = new IntList(1, null);
        IntList two_start = new IntList(2, start);
        IntList threetwo_start = new IntList(3, two_start);
        IntList t = IntList.squareListRecursive(threetwo_start);
        IntList empty = new IntList();
        IntList r_empty = IntList.squareListRecursive(empty);
        assertNotEquals(threetwo_start, t);
        assertEquals(empty, r_empty);
    }

    @Test
    public void testequals() {
        IntList A = IntList.list(10, 9, 8, 7, 6, 5),
                B = IntList.list(5, 6, 7, 8);
        IntList C = IntList.list(1, 2, 3, 4),
                D = IntList.list(1, 2, 3, 4);
        
        assertEquals(A.equals(B), false);
        assertEquals(C.equals(D), true);
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(IntListTest.class));
    }
}
