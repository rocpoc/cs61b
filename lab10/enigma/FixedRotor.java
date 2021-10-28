package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a rotor that has no ratchet and does not advance.
 *  @author
 */
class FixedRotor extends Rotor {

    /** A non-moving rotor named NAME whose permutation at the 0 setting
     *  is given by PERM. */
    FixedRotor(String name, Permutation perm) {
        super(name, perm);
    }

    /** Return false since a fixed rotor does not have a ratchet
     *  and can not move. */
    @Override
    boolean rotates() {
        return false;
    }

    /** Return true since a fixed rotor can reflect. */
    @Override
    boolean reflecting() {
        return true;
    }
}
