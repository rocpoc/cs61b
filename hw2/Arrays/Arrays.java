/* NOTE: The file ArrayUtil.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2, Problem #2. */

import com.sun.deploy.util.ArrayUtil;
import org.apache.commons.lang3.ArrayUtils;

/** Array utilities.
 *  @author
 */
class Arrays {
    /* 2a. */
    /** Returns a new array consisting of the elements of A followed by the
     *  the elements of B. */
    static int[] catenate(int[] A, int[] B) {
        if (A == null){
            return B;
        }
        if (B == null){
            return A;
        }
        int A_len = A.length;
        int B_len = B.length;
        int[] tobereturned = new int[(A_len + B_len)];
        System.arraycopy(A, 0, tobereturned, 0, A_len);
        System.arraycopy(B, 0, tobereturned, A_len, B_len);
        return tobereturned;
    }

    /* 2b. */
    /** Returns the array formed by removing LEN items from A,
     *  beginning with item #START. */
    static int[] remove(int[] A, int start, int len) {
        if (A == null){
            return null;
        }
        int[] k = Utils.subarray(A, 0, start);
        int[] j = Utils.subarray(A, A[start + len], A.length - (k.length + len) -1 );
        int[] returner = Arrays.catenate(k, j);
        return returner;
    }


    /** Returns the array of arrays formed by breaking up A into
     *  maximal ascending lists, without reordering.
     *  For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     *  returns the three-element array
     *  {{1, 3, 7}, {5}, {4, 6, 9, 10}}. */
    static int[][] naturalRuns(int[] A) {

        if (A == null) {
            return null;
        }


//
//
//        A == new IntList () {}
//            return new IntList2();



        return null;


    }
}
