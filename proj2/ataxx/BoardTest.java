package ataxx;

import org.junit.Test;
import static org.junit.Assert.*;

/** Tests of the Board class.
 *  @author Rocky Lubbers
 */
public class BoardTest {

    private static final String[]
        GAME1 = { "a7-b7", "a1-a2",
            "a7-a6", "a2-a3",
            "a6-a5", "a3-a4" };

    private static final String[]
            GAME2 = { "a7-b7", "a1-a2",
                "g1-g2", "g7-f7", "a7-a6",
                "a2-a4", "b7-c7", "a4-b6"};

    private static final String[] GAME3 = {"d4-d2"};

    private static final String[]
            JUMPGAME = { "a7-a5", "a1-a3",
                "g1-g3", "g7-e7", "g3-g5", "e7-e6"};

    private static final String[]
            JUMPGAME2 = { "a7-a5", "a1-a3",
                "g1-g3", "g7-e7", "g3-g5"};


    private static void makeMoves(Board b, String[] moves) {
        for (String s : moves) {
            b.makeMove(s.charAt(0), s.charAt(1),
                       s.charAt(3), s.charAt(4));
        }
    }

    @Test public void testUndo() {
        Board b0 = new Board();
        Board b1 = new Board(b0);
        makeMoves(b0, GAME1);
        Board b2 = new Board(b0);
        for (int i = 0; i < GAME1.length; i += 1) {
            b0.undo();
        }
        assertEquals("failed to return to start", b1, b0);
        makeMoves(b0, GAME1);
        assertEquals("second pass failed to reach same position", b2, b0);
    }

    @Test public void testGAME2() {
        Board b0 = new Board();
        Board b1 = new Board(b0);
        makeMoves(b0, GAME2);
        Board b2 = new Board(b0);
        for (int i = 0; i < GAME2.length; i += 1) {
            b0.undo();
        }
        assertEquals("failed to return to start", b1, b0);
        makeMoves(b0, GAME2);
        assertEquals("second pass failed to reach same position", b2, b0);
    }


    @Test
    public void jumptest() {
        Board b0 = new Board();
        makeMoves(b0, JUMPGAME);
        Board b2 = new Board(b0);
        assertEquals(0, b2.numJumps());
        for (int i = 0; i < JUMPGAME.length; i += 1) {
            b0.undo();
        }
        assertEquals("wrong number of jumps", 0, b0.numJumps());
    }

    @Test
    public void jumpEXTEND() {
        Board b0 = new Board();
        makeMoves(b0, JUMPGAME2);
        Board b2 = new Board(b0);
        assertEquals(5, b2.numJumps());
        for (int i = 0; i < JUMPGAME2.length; i += 1) {
            b0.undo();
        }
        assertEquals("wrong number of jumps", 5, b0.numJumps());
    }
}
