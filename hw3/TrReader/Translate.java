import java.io.IOException;
import java.io.StringReader;

/** String translation.
 *  @author
 */
public class Translate {
    /** Return the String S, but with all characters that occur in FROM
     *  changed to the corresponding characters in TO. FROM and TO must
     *  have the same length. */



    static String translate(String S, String from, String to) {
        /* NOTE: The try {...} catch is a technicality to keep Java happy. */
        char[] buffer = new char[S.length()];
        StringReader read_string = new StringReader(S);
        TrReader translater = new TrReader(read_string, from, to);
        try {
            translater.read(buffer);
        } catch (IOException e) {
            return null;
        }
        String resultant = "";
        for (int i = 0; i < S.length(); i++) {
            resultant += buffer[i];
        }
        return resultant;
    }
    /*
       REMINDER: translate must
      a. Be non-recursive
      b. Contain only 'new' operations, and ONE other method call, and no
         other kinds of statement (other than return).
      c. Use only the library classes String, and anything containing
         "Reader" in its name (browse the on-line documentation).
    */
}
