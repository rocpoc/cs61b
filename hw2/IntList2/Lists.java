/* NOTE: The file Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2, Problem #1. */

/** List problem.
 *  @author Rocky Lubbers
 */
class Lists {
    /** Return the list of lists formed by breaking up L into "natural runs":
     *  that is, maximal strictly ascending sublists, in the same order as
     *  the original.  For example, if L is (1, 3, 7, 5, 4, 6, 9, 10, 10, 11),
     *  then result is the four-item list ((1, 3, 7), (5), (4, 6, 9, 10), (10, 11)).
     *  Destructive: creates no new IntList items, and may modify the
     *  original list pointed to by L. */
    static IntList2 naturalRuns(IntList L) {

        if (L == null) {
            return null;
        }
        if (L == new IntList () { }) {
            return new IntList2();
        }

        IntList2 secondlist = new IntList2();
        IntList start = L;
        IntList pointer = L;
        IntList saved = start;
        IntList2 secondpoint = secondlist;

        while (pointer.tail != null) {
            if (pointer.head >= pointer.tail.head) {
                saved = pointer.tail;
                pointer.tail = null;
                secondpoint.head = start;
                secondpoint.tail = new IntList2();
                secondpoint = secondpoint.tail;
                secondpoint.head = saved;
                pointer = saved;
                start = saved;
            }
            if (pointer.head < pointer.tail.head) {
                pointer = pointer.tail;
                saved = saved.tail;
            }
        }

        if (secondlist.head == null) {
            secondlist.head = L;
        }

        return secondlist;
    }
}
