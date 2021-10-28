import java.util.Iterator;
import utils.Filter;

/** A kind of Filter that lets through every other VALUE element of
 *  its input sequence, starting with the first.
 *  @author You
 */
class AlternatingFilter<Value> extends Filter<Value> {
    int count = 0;
    /** A filter of values from INPUT that lets through every other
     *  value. */
    AlternatingFilter(Iterator<Value> input) {
        super(input);      
    }

    @Override
    protected boolean keep() {
        count = 1 - count;
        if (count == 1) {
            return true;
        } else {
            return false;
        }
    }
}
