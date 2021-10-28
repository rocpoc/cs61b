package enigma;

import java.util.ArrayList;
import java.util.List;



import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Rocky Lubbers
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters not
     *  included in any cycle map to themselves. Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        this._alphabet = alphabet;
        this._cycles = cycles;
        String[] splitCycles = _cycles.split("\\W+");
        for (String elem: splitCycles) {
            if (!elem.isEmpty()) {
                cyclelist.add(elem);
            }
        }
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return _alphabet.size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        char i = _alphabet.toChar(wrap(p));
        char result = '.';
        if (cyclelist.size() == 0) {
            return p;
        }
        outside:
        for (String cycle : cyclelist) {
            if (cycle.indexOf(i) != -1) {
                for (int j = 0; j < cycle.length(); j++) {
                    if (cycle.charAt(j) == i && j != cycle.length() - 1) {
                        result = cycle.charAt(j + 1);
                        break outside;
                    } else if (j == cycle.length() - 1) {
                        result = cycle.charAt(0);
                    }
                }
            }
        }
        return _alphabet.toInt(result);
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        char i = _alphabet.toChar(wrap(c));
        char result = '.';
        if (cyclelist.size() == 0) {
            return c;
        }
        outside:
        for (String cycle : cyclelist) {
            if (cycle.indexOf(i) != -1) {
                for (int j = 0; j < cycle.length(); j++) {
                    if (cycle.charAt(j) == i
                            && cycle.charAt(j) != cycle.charAt(0)) {
                        result = cycle.charAt(j - 1);
                        break outside;
                    } else if (cycle.charAt(j) == cycle.charAt(0)) {
                        result = cycle.charAt(cycle.length() - 1);
                    }
                }
            }
        }
        return _alphabet.toInt(result);
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        char i = _alphabet.toChar(wrap(_alphabet.toInt(p)));
        char result = p;
        if (cyclelist.size() == 0) {
            return p;
        }
        outside:
        for (String cycle : cyclelist) {
            if (cycle.indexOf(i) != -1) {
                for (int j = 0; j < cycle.length(); j++) {
                    if (cycle.charAt(j) == i && j != cycle.length() - 1) {
                        result = cycle.charAt(j + 1);
                        break outside;
                    } else if (j == cycle.length() - 1) {
                        result = cycle.charAt(0);
                    }
                }
            }
        }
        return result;
    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        char i = _alphabet.toChar(wrap(_alphabet.toInt(c)));
        char result = c;
        if (cyclelist.size() == 0) {
            return c;
        }

        outside:
        for (String cycle : cyclelist) {
            if (cycle.indexOf(i) != -1) {
                for (int j = 0; j < cycle.length(); j++) {
                    if (cycle.charAt(j) == i
                            && cycle.charAt(j) != cycle.charAt(0)) {
                        result = cycle.charAt(j - 1);
                        break outside;
                    } else if (cycle.charAt(j) == cycle.charAt(0)) {
                        result = cycle.charAt(cycle.length() - 1);
                    }
                }
            }
        }
        return result;
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        for (String cycle : cyclelist) {
            if (cycle.length() == 1) {
                return false;
            } else {
                continue;
            }
        }
        return true;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;

    /** Cycles of permutations. */
    private String _cycles;

    /** Data structure to hold cycles. */
    private List<String> cyclelist = new ArrayList<String>();

}

