package enigma;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters not
     *  included in any cycle map to themselves. Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _cycles = cycles;
        _alphabet = alphabet;
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
        _cycles += " (" + cycle + ")";
    }

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
        p = p % size();
        char nextChar = permute(_alphabet.toChar(p));
        return _alphabet.toInt(nextChar);
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        c = c % size();
        char nextChar = invert(_alphabet.toChar(c));
        return _alphabet.toInt(nextChar);
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        if (_cycles.length() == 0) {
            return p;
        }
        char firstChar = _cycles.charAt(0);
        for (int i = 0; i < _cycles.length(); i++) {
            if (_cycles.charAt(i) == '(') {
                firstChar = _cycles.charAt(i + 1);
            }
            if (_cycles.charAt(i) == p) {
                if (_cycles.charAt(i + 1) == ')') {
                    return firstChar;
                }
                return _cycles.charAt(i + 1);
            }
        }
        return p;
    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        if (_cycles.length() == 0) {
            return c;
        }
        char lastChar = _cycles.charAt(0);
        for (int i = _cycles.length() - 1; i >= 0; i--) {
            if (_cycles.charAt(i) == ')') {
                lastChar = _cycles.charAt(i - 1);
            }
            if (_cycles.charAt(i) == c) {
                if (_cycles.charAt(i - 1) == '(') {
                    return lastChar;
                }
                return _cycles.charAt(i - 1);
            }
        }
        return c;
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself. */
    boolean derangement() {
        for (int i = 0; i < _alphabet.size(); i++) {
            char c = this.alphabet().toChar(i);
            if (c == this.permute(c)) {
                return false;
            }
        }
        return true;
    }

    /** String in the form "(cccc) (cc) ..." where the c's are characters
     *  in ALPHABET. */
    private String _cycles;

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;
}
