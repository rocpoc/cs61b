package ataxx;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.awt.HeadlessException;

/** The main program for Ataxx.
 *  @author Rocky Lubbers
 */
public class Main {

    /** Run Ataxx game.  Use display if ARGS[k] is '--display'. */
    public static void main(String[] args) {
        boolean useGUI;
        useGUI = false;
        for (int i = 0; i < args.length; i += 1) {
            switch (args[i]) {
            case "--display":
                useGUI = true;
                break;
            default:
                usage();
                break;
            }
        }

        Game game;
        Board board = new Board();

        game = null;
        if (useGUI) {
            try {
                PipedWriter writer = new PipedWriter();
                AtaxxGUI display = new AtaxxGUI("Ataxx", board, writer);
                game = new Game(board,
                                new ReaderSource(new PipedReader(writer,
                                                                 BUFFER_LEN),
                                                 false),
                                display);
                display.display(true);
            } catch (HeadlessException excp) {
                System.err.printf("Could not connect to display.%n");
                System.exit(1);
            } catch (IOException excp) {
                System.err.printf("An internal error occurred.%n");
                System.exit(1);
            }
        } else {
            game = new Game(board,
                            new ReaderSource(new InputStreamReader(System.in),
                                             true),
                            new TextReporter());
        }
        game.process(false);
    }

    /** Give usage message and exit. */
    static void usage() {
        System.err.println("Usage: java ataxx.Main [--display] [--timing]"
                           + " [--strict]");
        System.exit(1);
    }

    /** Size of the buffer for reading commands from a GUI (bytes). */
    private static final int BUFFER_LEN = 128;

}
