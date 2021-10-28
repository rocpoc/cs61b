package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

/** The suite of all JUnit tests for the Machine class.
 *  @author
 */
public class RotorTest {

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTS ***** */
    Alphabet alphabet = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");

    Rotor r1 = new Reflector("B", new Permutation("(AE) (BN) (CK) (DQ) (FU) (GY) (HW) (IJ) (LO) (MP) (RX) (SZ) (TV)", alphabet));
    Rotor r2 = new FixedRotor("BETA", new Permutation("(ALBEVFCYODJWUGNMQTZSKPR) (HIX)", alphabet));
    Rotor r3 = new MovingRotor("III", new Permutation("(ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)", alphabet), "V");
    Rotor r4 = new MovingRotor("IV", new Permutation("(AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)", alphabet), "J");
    Rotor r5 = new MovingRotor("I", new Permutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", alphabet), "Q");

    @Test
       public void testConvertForward() {
           r5.set(5);
           assertEquals(8, r5.convertForward(5));
           r4.set(11);
           assertEquals(21, r4.convertForward(8));
           r3.set(23);
           assertEquals(9, r3.convertForward(21));
           r2.set(0);
           assertEquals(22, r2.convertForward(9));
           assertEquals(7, r1.convertForward(22));
       }

       @Test
       public void testConvertBackward() {
           r2.set(0);
           assertEquals(23, r2.convertBackward(7));
           r3.set(23);
           assertEquals(25, r3.convertBackward(23));
           r4.set(11);
           assertEquals(9, r4.convertBackward(25));
           r5.set(5);
           assertEquals(7, r5.convertBackward(9));
       }

       @Test
       public void testAtNotch() {
           assertEquals(r5.atNotch(), false);
           assertEquals(r4.atNotch(), false);
           assertEquals(r3.atNotch(), false);


    }

}
