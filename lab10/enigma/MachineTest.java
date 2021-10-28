package enigma;

import java.util.Arrays;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

/** The suite of all JUnit tests for the Machine class.
 *  @author
 */
public class MachineTest {

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTS ***** */

    @Test
    public void testConvertChar() {
      Alphabet az = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
      Rotor one = new Reflector("B", new Permutation("(AE) (BN) (CK) (DQ) (FU) (GY) (HW) (IJ) (LO) (MP) (RX) (SZ) (TV)", az));
      Rotor two = new FixedRotor("BETA", new Permutation("(ALBEVFCYODJWUGNMQTZSKPR) (HIX)", az));
      Rotor three = new MovingRotor("III", new Permutation("(ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)", az), "V");
      Rotor four = new MovingRotor("IV", new Permutation("(AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)", az), "J");
      Rotor five = new MovingRotor("I", new Permutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", az), "Q");
      String setting = "AXLE";
      Rotor[] machineRotors = {one, two, three, four, five};
      String[] rotors = {"B", "BETA", "III", "IV", "I"};
      Machine mach = new Machine(az, 5, 3, new ArrayList<Rotor>(Arrays.asList(machineRotors)));
      mach.insertRotors(rotors);
      mach.setRotors(setting);
      mach.setPlugboard(new Permutation("(YF) (HZ)", az));
      assertEquals(25, mach.convert(24));
    }

    @Test
    public void testConvertMsg() {
      Alphabet az = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
      Rotor one = new Reflector("B", new Permutation("(AE) (BN) (CK) (DQ) (FU) (GY) (HW) (IJ) (LO) (MP) (RX) (SZ) (TV)", az));
      Rotor two = new FixedRotor("BETA", new Permutation("(ALBEVFCYODJWUGNMQTZSKPR) (HIX)", az));
      Rotor three = new MovingRotor("III", new Permutation("(ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)", az), "V");
      Rotor four = new MovingRotor("IV", new Permutation("(AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)", az), "J");
      Rotor five = new MovingRotor("I", new Permutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", az), "Q");
      String setting = "AXLE";
      Rotor[] machineRotors = {one, two, three, four, five};
      String[] rotors = {"B", "BETA", "III", "IV", "I"};
      Machine mach = new Machine(az, 5, 3, new ArrayList<Rotor>(Arrays.asList(machineRotors)));
      mach.insertRotors(rotors);
      mach.setRotors(setting);
      mach.setPlugboard(new Permutation("(HQ) (EX) (IP) (TR) (BY)", az));
      assertEquals("QVPQSOKOILPUBKJZPISFXDW", mach.convert("FROMHISSHOULDERHIAWATHA"));
      }
}
