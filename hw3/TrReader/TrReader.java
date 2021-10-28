import java.io.Reader;
import java.io.IOException;

/** Translating Reader: a stream that is a translation of an
 *  existing reader.
 *  @author Rocky Lubbers
 */
public class TrReader extends Reader {
    /** A new TrReader that produces the stream of characters produced
     *  by STR, converting all characters that occur in FROM to the
     *  corresponding characters in TO.  That is, change occurrences of
     *  FROM.charAt(0) to TO.charAt(0), etc., leaving other characters
     *  unchanged.  FROM and TO must have the same length. */

    private  String new_from;
    private  String new_to;
    private Reader new_str;
    private int read_counter;

    public TrReader(Reader str, String from, String to) {
        new_from = from;
        new_to = to;
        new_str = str;
    }

    public void close() {    }

    public int read(char[] cbuf, int off, int len) throws IOException {
        read_counter = off;
        int new_read = new_str.read(cbuf, off, len);
        for (int i = 0; i < read_counter + new_read; i++) {
            for (int j = 0; j < new_from.length(); j++) {
                if (new_from.charAt(j) == cbuf[i]) {
                    cbuf[i] = new_to.charAt(j);
                    break;
                }
            }
        }
        return new_read;
    }

    // FILL IN
    // NOTE: Until you fill in the right methods, the compiler will
    //       reject this file, saying that you must declare TrReader
    //     abstract.  Don't do that; define the right methods instead!
}


