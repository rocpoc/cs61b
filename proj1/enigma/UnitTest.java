package enigma;

import org.junit.Assert;
import org.junit.Test;
import ucb.junit.textui;

/** The suite of all JUnit tests for the enigma package.
 *  @author
 */
public class UnitTest {

    /** Run the JUnit tests in this package. Add xxxTest.class entries to
     *  the arguments of runClasses to run other JUnit tests. */
    public static void main(String[] ignored) {
        textui.runClasses(PermutationTest.class, MovingRotorTest.class,
                UnitTest.class);
    }


    @Test
    public void testinput() {
        String conf = "testing/correct/default.conf";
        String inp = "testing/correct/trivial.inp";
        String out = "testing/correct/trivial.out";
        String[] test = {conf, inp, out};
        Main.main(test);

    }
    @Test
    public void testinput1() {
        String conf = "testing/correct/default.conf";
        String inp = "testing/correct/trivial1.inp";
        String out = "testing/correct/trivial1.out";
        String[] test = {conf, inp, out};
        Main.main(test);
    }


    @Test
    public void testerr() {
        String conf = "testing/error/default.conf";
        String inp = "testing/error/trivialerr.inp";
        String[] test = {conf, inp};
        try {
            Main.main(test);
        } catch (EnigmaException e) {
            Assert.fail("Test failed");
        }
    }



}


