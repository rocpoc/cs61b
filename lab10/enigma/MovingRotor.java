package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author
 */
class MovingRotor extends Rotor {

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initally in its 0 setting (first character of its
     *  alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        _notches = notches;
    }

    /** Returns true since a moving rotor is positioned to allow the rotor to
     *  my left to advance. */
    @Override
    boolean atNotch() {
        return _notches.contains(Character.toString(_permutation.alphabet().toChar(_setting)));
    }

    /** Advances SETTING one position. */
    @Override
    void advance() {
        _setting = _permutation.wrap(_setting + 1);
    }

    /** String indicating what positions the notches of the rotor are at. */
    private String _notches;
}
