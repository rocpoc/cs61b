import java.util.TreeSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.io.PrintStream;
import java.util.Collections;

/** Program to read a sequences of words and print all words in it that
 *  appear more than once.
 *  @author P. N. Hilfinger
 */
public class Dups4 {

    /** Read the sequence of words on INPUT, and return as a List.  If LINKED,
     *  use a linked representation.  Otherwise, use an array representation.
     */
    static List<String> readList(Scanner input, boolean linked) {
        List<String> L;
        if (linked) {
            L = new LinkedList<String>();
        } else {
            L = new ArrayList<String>();
        }
        while (input.hasNext()) {
            L.add(input.next());
        }
        return L;
    }

    /** Return a list of all items in L that appear more than once.
     *  Each item appears once in the result.
     */
    static List<String> duplicates(List<String> L) {
        TreeSet<String> result = new TreeSet<>();
        for (ListIterator<String> p1 = L.listIterator(); p1.hasNext();
             p1.nextIndex()) {
            String x = p1.next();
            if (result.contains(x)) {
                result.add(x);
            }


            for (ListIterator<String> p2 = L.listIterator(L.size());
                 p1.nextIndex() < p2.previousIndex() - 1 ; p2.previousIndex()) {
                if (x.equals(p2.previous())) {
                    result.add(x);
                    break;
                }
            }
        }
        ArrayList<String> newresult = new ArrayList<>();
        for (String x: result) {
            newresult.add(x);
        }
        return newresult;
    }


    /** Print the items in L on OUTPUT on a line, separated by blanks. */
    static void writeList(List<String> L, PrintStream output) {
        /* The following loop is short for
         *     for (Iterator<String> _i_ = L.iterator(); L.hasNext(); ) {
         *         String x = _i_.next();
         *         output.printf("%s ", x);
         *     }
         */
        for (String x : L) {
            output.printf("%s ", x);
        }
        output.println();
    }

    /** Read words from the standard input and print the list of duplicated
     *  words.  Internally use a linked representation for the input list
     *  iff ARGS[0] is "linked". */
    public static void main(String... args) {
        boolean useLinked = args.length == 0 || args[0].equals("linked");
        writeList(duplicates(readList(new Scanner(System.in), useLinked)),
                System.out);
    }

}

