package enigma;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Scanner;
import static enigma.EnigmaException.*;

/** Enigma simulator.
 *  @author Rocky Lubbers
 */
public final class Main {

    /** Process a sequence of encryptions and decryptions, as
     *  specified by ARGS, where 1 <= ARGS.length <= 3.
     *  ARGS[0] is the name of a configuration file.
     *  ARGS[1] is optional; when present, it names an input file
     *  containing messages.  Otherwise, input comes from the standard
     *  input.  ARGS[2] is optional; when present, it names an output
     *  file for processed messages.  Otherwise, output goes to the
     *  standard output. Exits normally if there are no errors in the input;
     *  otherwise with code 1. */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {

            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /** Check ARGS and open the necessary files (see comment on main). */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }
        _config = getInput(args[0]);
        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);
        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /** Return a Scanner reading from the file named NAME. */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Return a PrintStream writing to the file named NAME. */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Configure an Enigma machine from the contents of configuration
     *  file _config and apply it to the messages in _input, sending the
     *  results to _output. */
    private void process() {
        Machine newmachine = readConfig();
        String inp = _input.nextLine();
        setUp(newmachine, inp);
        String secret = new String();
        while (_input.hasNext()) {
            secret += (newmachine.convert(_input.next()));
            if (!_input.hasNext()) {
                printMessageLine(secret);
            }
            if (_input.hasNext("\\*")) {
                _input.next();
                String inp2 = "*" + _input.nextLine();
                setUp(newmachine, inp2);
                printMessageLine(secret);
                secret = new String();
            }
        }
    }

    /** Return an Enigma machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() {
        try {
            Collection<Rotor> allRotors = new ArrayList<>();
            String alpha = _config.nextLine();
            _alphabet = new UpperCaseAlphabet();
            int numrotors = _config.nextInt();
            int pawls = _config.nextInt();
            while (_config.hasNext()) {
                allRotors.add(readRotor());
            }
            _config.close();
            return new Machine(_alphabet, numrotors, pawls, allRotors);
        } catch (NoSuchElementException excp) {
            throw new EnigmaException("configuration file truncated");
        }
    }

    /** Return a rotor, reading its description from _config. */
    private Rotor readRotor() {
        String name = _config.next();
        String typenotch = _config.next();
        String notch = new String();
        String cycles = "";
        char newtype = typenotch.charAt(0);
        char[] notcharray = typenotch.toCharArray();
        for (char elem : notcharray) {
            if (elem == notcharray[0]) {
                continue;
            } else {
                notch += elem;
            }
        }
        while (_config.hasNext("\\(.*")) {
            cycles += _config.next();
        }
        Permutation perm = new Permutation(cycles, _alphabet);
        try {
            if (newtype == 'M') {
                return new MovingRotor(name, perm, notch);
            }
            if (newtype == 'N') {
                return new FixedRotor(name, perm);
            }
            if (newtype == 'R') {
                return new Reflector(name, perm);
            }
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
        return new Rotor(name, perm);
    }

    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void setUp(Machine M, String settings) {
        ArrayList<String> settingarray = new ArrayList<>();
        String[] splitsettings = settings.split(" ");
        if (settings.charAt(0) != '*') {
            throw new EnigmaException("Bad input file.");
        }
        String cycles = new String();
        int fourchar = M.numRotors() + 1;

        for (String s : splitsettings) {
            settingarray.add(s);
        }
        for (int i = fourchar + 1; i <= settingarray.size() - 1; i++) {
            cycles += settingarray.get(i);
        }

        String justrotors = settingarray.get(fourchar);
        Permutation plugboard = new Permutation(cycles, _alphabet);
        M.insertRotors(splitsettings);
        M.setRotors(justrotors);
        M.setPlugboard(plugboard);
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private void printMessageLine(String msg) {
        String sms = msg.replaceAll("\\s+", "");
        if (sms.length() < 5) {
            _output.print(sms);
        }
        for (int i = 0; i < sms.length() - 1; i += 5) {
            if (i == 0) {
                _output.print(sms.substring(0, i + 5) + " ");
                continue;
            }
            if (sms.substring(i).length() > 5) {
                _output.print(sms.substring(i, i + 5) + " ");
            } else if (sms.substring(i).length() <= 5) {
                _output.print(sms.substring(i));
            }
        }
        _output.println();
    }

    /** Alphabet used in this machine. */
    private Alphabet _alphabet;

    /** Source of input messages. */
    private Scanner _input;

    /** Source of machine configuration. */
    private Scanner _config;

    /** File for encoded/decoded messages. */
    private PrintStream _output;
}
