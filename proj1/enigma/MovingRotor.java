package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author Rocky Lubbers
 */
class MovingRotor extends Rotor {

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initally in its 0 setting (first character of its
     *  alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        this.charnotches = notches.toCharArray();
    }

    @Override
    boolean rotates() {
        return true;
    }

    @Override
    boolean atNotch() {
        for (char cn : charnotches) {
            if (this.alphabet().toInt(cn) == this.setting()) {
                return true;
            }
        }
        return false;
    }

    @Override
    void advance() {
        this.set(this.setting() + 1);
    }

    /** Initialize a new character array for notches. */
    private char[] charnotches;



}
