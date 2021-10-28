package enigma;

import java.util.ArrayList;

import java.util.Collection;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author Rocky Lubbers
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        this._alphabet = alpha;
        _numRotors = numRotors;
        _pawls = pawls;
        _allRotors = allRotors;
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return _numRotors;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _pawls;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        _rotorset = new ArrayList<Rotor>();
        for (int i = 1; i < rotors.length - 1; i++) {
            for (Rotor r : _allRotors) {
                if (r.name().toUpperCase().equals(rotors[i])) {
                    _rotorset.add(r);
                    break;
                }
            }
        }
        for (int i = 0; i < _rotorset.size(); i++) {
            for (int j = 1; j < _rotorset.size(); j++) {
                if (_rotorset.get(i) == _rotorset.get(j) && i != j) {
                    throw new EnigmaException("Duplicate rotors found.");

                }
            }
        }
    }

    /** Set my rotors according to SETTING, which must be a string of four
     *  upper-case letters. The first letter refers to the leftmost
     *  rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
        int i = 0;
        for (Rotor r : _rotorset) {
            if (!r.reflecting()) {
                r.set(setting.charAt(i));
                i++;
            }
        }
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        this._plugboard = plugboard;
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing
     *  the machine. */
    int convert(int c) {
        return 0;
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        char[] code = msg.toUpperCase().toCharArray();
        String returncode = new String();
        int fastrotor = _rotorset.size() - 1;
        for (char c : code) {
            c = _plugboard.permute(c);
            if (!_rotorset.get(fastrotor).atNotch()) {
                _rotorset.get(fastrotor).advance();
            } else if (_rotorset.get(fastrotor).atNotch()) {
                _rotorset.get(fastrotor).advance();
                _rotorset.get(fastrotor - 1).advance();
            }
            for (int i = _rotorset.size() - 1; i >= 0; i--) {
                if (_rotorset.get(i).atNotch()
                        && _rotorset.get(i) != _rotorset.get(fastrotor)) {
                    _rotorset.get(i - 1).advance();
                }
                int newc = this._alphabet.toInt(c);
                c = _alphabet.toChar(_rotorset.get(i).convertForward(newc));
                if (i == 0) {
                    for (int j = 1; j < _rotorset.size(); j++) {
                        newc = this._alphabet.toInt(c);
                        int setter = _rotorset.get(j).convertBackward(newc);
                        c = _alphabet.toChar(setter);
                    }
                }
            }
            c = _plugboard.invert(c);
            returncode += c;
        }
        return returncode + " ";
    }

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;

    /** Number of rotors. */
    private int _numRotors;

    /** Number of pawls. */
    private int _pawls;

    /** Data structure to hold Rotors. */
    private Collection<Rotor> _allRotors = new ArrayList<Rotor>();

    /** Empty structure to add new rotors. */
    private ArrayList<Rotor> _rotorset = new ArrayList<Rotor>();

    /** Create new plugboard. */
    private Permutation _plugboard;

}


