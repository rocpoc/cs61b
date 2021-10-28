import static org.junit.Assert.*;
import org.junit.Test;

public class IntListTest {

    /** Sample test that verifies correctness of the IntList.list static
     *  method. The main point of this is to convince you that
     *  assertEquals knows how to handle IntLists just fine.
     */

    @Test
    public void testList() {
        IntList cheker = IntList.list(3,2,1);
        IntList x = IntList.list(3, 2, 1);
        assertEquals(cheker, x);
    }

    /** Do not use the new keyword in your tests. You can create
     *  lists using the handy IntList.list method.
     *
     *  Make sure to include test cases involving lists of various sizes
     *  on both sides of the operation. That includes the empty list, which
     *  can be instantiated, for example, with
     *  IntList empty = IntList.list().
     *
     *  Keep in mind that dcatenate(A, B) is NOT required to leave A untouched.
     *  Anything can happen to A.
     */

    @Test
    public void testDcatenate() {

        IntList tresdosuno = IntList.list(3,2,1);
        IntList seiscincoquatro = IntList.list(6,5,4);
    
        IntList call = IntList.dcatenate(tresdosuno, seiscincoquatro);
        IntList check = IntList.list(3, 2, 1, 6, 5, 4);
        
        assertEquals(call, check);
    }

    /** Tests that subtail works properly. Again, don't use new.
     *
     *  Make sure to test that subtail does not modify the list.
     */

    @Test
    public void testSubtail() {
        IntList threetwooone = IntList.list(3,2,1);
        IntList sixfivefour = IntList.list(6,5,4);

        IntList subtailcall1 = IntList.subTail(threetwooone, 2);
        IntList subtailcheck1 = IntList.list(1);

        IntList subtailcall2 = IntList.subTail(sixfivefour, 1);
        IntList subtailcheck2 = IntList.list(5,4);

        assertEquals(subtailcall1, subtailcheck1);
        assertEquals(subtailcall2, subtailcheck2);
    }

    /** Tests that sublist works properly. Again, don't use new.
     *
     *  Make sure to test that sublist does not modify the list.
     */

    @Test
    public void testSublist() {
        IntList fourthreetwoone = IntList.list(4,3,2,1);
        IntList sublistcall1 = IntList.sublist(fourthreetwoone, 1, 3);
        IntList sublistcheck1 = IntList.list(3,2,1);
        IntList sublistcall2 = IntList.sublist(fourthreetwoone, 2, 2);
        IntList sublistcheck2 = IntList.list(2,1);

        assertEquals(sublistcall1, sublistcheck1);
        assertEquals(sublistcall2, sublistcheck2);

    }

    /** Tests that dSublist works properly. Again, don't use new.
     *
     *  As with testDcatenate, it is not safe to assume that list passed
     *  to dSublist is the same after any call to dSublist
     */

    @Test
    public void testDsublist() { 
        IntList L = IntList.list(1,2,3,4,5);
        IntList callL = IntList.dsublist(L, 1, 3);
        assertNotEquals(L, callL); 
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(IntListTest.class));
    }
}
