package enigma;

import java.util.HashMap;
import java.util.Collection;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        _numRotors = numRotors;
        _pawls = pawls;
        _allRotors = allRotors;
        machineRotors = new Rotor[_numRotors];
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return _numRotors;
    }

    /** Return the number of pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _pawls;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
      for (int i = 0; i < rotors.length; i++) {
        for (Rotor r: _allRotors) {
            if (rotors[i].equalsIgnoreCase(r.name())) {
                machineRotors[i] = r;
          }
        }
      }
      if (machineRotors.length > _numRotors) {
        throw new Error("More rotors in machine than rotor slots available");
      }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  upper-case letters. The first letter refers to the leftmost
     *  rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
      for (int i = 1; i <= setting.length(); i++) {
        char c = setting.charAt(i - 1);
        machineRotors[i].set(_alphabet.toInt(c));
      }
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        _plugboard = plugboard;
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing
     *  the machine. */
    int convert(int c) {
      machineRotors[_numRotors - 1].advance();
      boolean[] advance = new boolean[_numRotors];
      for (int i = _numRotors - 1; i > 1; i--) {
        if (machineRotors[i].atNotch()) {
          advance[i-1] = true;
          advance[i] = true;
        }
      }
      int result = c;
      if (_plugboard != null) {
        result = _plugboard.permute(result);
      }
      for (int i = _numRotors - 1; i >= 0; i--) {
        result = machineRotors[i].convertForward(result);
      }
      for (int i = 1; i < _numRotors; i++) {
        result = machineRotors[i].convertBackward(result);
      }
      if (_plugboard != null) {
        result = _plugboard.permute(result);
      }
      for (int i = 0; i < _numRotors - 1; i++) {
            if (advance == null) {
                break;
            } else if (advance[i]) {
                machineRotors[i].advance();
            }
      }
      return result;
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        String result = "";
        char[] msgChars = msg.toCharArray();
        for (int i = 0; i < msgChars.length; i++) {
            result += _alphabet.toChar(convert(msgChars[i] - 65));
        }
        return result;
    }

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;

    /** The number of rotor slots I have. */
    private final int _numRotors;

    /** The number of pawls I have. */
    private final int _pawls;

    /** All the available rotors. */
    private Collection<Rotor> _allRotors;

    /** An array of all the rotors in my machine. */
    private Rotor[] machineRotors;

    /** The plugboard. */
    private Permutation _plugboard;

}
