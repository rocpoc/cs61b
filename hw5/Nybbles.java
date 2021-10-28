/** Represents an array of integers each in the range -8..7.
 *  Such integers may be represented in 4 bits (called nybbles).
 *  @author Rocky Lubbers
 */
public class Nybbles {

    /** Maximum positive value of a Nybble. */
    public static final int MAX_VALUE = 7;

    /** Return an array of size N. */
    public Nybbles(int N) {
        // DON'T CHANGE THIS.
        _data = new int[(N + 7) / 8];
        _n = N;
    }

    /** Return the size of THIS. */
    public int size() {
        return _n;
    }

    /** Return the Kth integer in THIS array, numbering from 0.
     *  Assumes 0 <= K < N. */
    public int get(int k) {
        if (k < 0 || k >= _n) {
            throw new IndexOutOfBoundsException();
        } else {
            if (k <= 7) {
                int shifted = _data[0] >> (32 - (4 * (k+1)));
                shifted = shifted << 28;
                shifted = shifted >> 28;
                return shifted;
            }
            if (k > 7) {
                k -=8;
                int shifted = _data[1] >> (32 - (4 * (k+1)));
                shifted = shifted << 28;
                shifted = shifted >> 28;
                return shifted;
            }
            return 69;
        }
    }

    /** Set the Kth integer in THIS array to VAL.  Assumes
     *  0 <= K < N and -8 <= VAL < 8. */
    public void set(int k, int val) {
        if (k < 0 || k >= _n) {
            throw new IndexOutOfBoundsException();
        } else if (val < (-MAX_VALUE - 1) || val > MAX_VALUE) {
            throw new IllegalArgumentException();
        } else {
            if (k <= 7) {
                int new_val = val << 28;
                new_val = new_val >>> 4*(k);
                _data[0] |= new_val;
            }
            if (k > 7) {
                k -= 8;
                int new_val = val << 28;
                new_val = new_val >>> 4*(k);
                _data[1] |= new_val;
            }
        }
    }

    // DON'T CHANGE OR ADD TO THESE.
    /** Size of current array (in nybbles). */
    private int _n;
    /** The array data, packed 8 nybbles to an int. */
    private int[] _data;
}
