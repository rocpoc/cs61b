package ataxx;

/* Author: P. N. Hilfinger, (C) 2008. */
import java.util.Observable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.Arrays;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.Formatter;
import static ataxx.PieceColor.*;
import static ataxx.GameException.error;

/** An Ataxx board.   The squares are labeled by column (a char value between
 *  'a' - 2 and 'g' + 2) and row (a char value between '1' - 2 and '7'
 *  + 2) or by linearized index, an integer described below.  Values of
 *  the column outside 'a' and 'g' and of the row outside '1' to '7' denote
 *  two layers of border squares, which are always blocked.
 *  This artificial border (which is never actually printed) is a common
 *  trick that allows one to avoid testing for edge conditions.
 *  For example, to look at all the possible moves from a square, sq,
 *  on the normal board (i.e., not in the border region), one can simply
 *  look at all squares within two rows and columns of sq without worrying
 *  about going off the board. Since squares in the border region are
 *  blocked, the normal logic that prevents moving to a blocked square
 *  will apply.
 *
 *  For some purposes, it is useful to refer to squares using a single
 *  integer, which we call its "linearized index".  This is simply the
 *  number of the square in row-major order (counting from 0).
 *
 *  Moves on this board are denoted by Moves.
 *  @author Rocky Lubbers
 *  CITE-used Slack/Piazza discussion to use stack of HashMaps for undo method.
 */
class Board extends Observable {

    /** Number of squares on a side of the board. */
    static final int SIDE = 7;
    /** Length of a side + an artificial 2-deep border region. */
    static final int EXTENDED_SIDE = SIDE + 4;

    /** Number of non-extending moves before game ends. */
    static final int JUMP_LIMIT = 25;

    /** A new, cleared board at the start of the game. */
    Board() {

        _board = new PieceColor[EXTENDED_SIDE * EXTENDED_SIDE];
        redPieces = 2;
        bluePieces = 2;
        numMoves = 0;
        numJumps = 0;
        movelist = new ArrayList<>();
        clear();
    }

    /** A copy of B. */
    Board(Board b) {
        _board = b._board.clone();
        _whoseMove = b.whoseMove();
        this.redPieces = b.redPieces();
        this.bluePieces = b.bluePieces();
        this.numMoves = b.numMoves();
        this.numJumps = b.numJumps();
        this.movelist = b.movelist;

    }

    /** Sets blocks for grid based on known numbers. */
    void blocker() {
        for (int i = 0; i < MIN; i++) {
            _board[i] = BLOCKED;
        }
        for (int i = MIN + 7; i <= MAX + 7; i += 11) {
            _board[i] = BLOCKED;
            _board[i + 1] = BLOCKED;
            _board[i + 2] = BLOCKED;
            _board[i + 3] = BLOCKED;
        }
        for (int i = MAX + 7; i < _board.length; i++) {
            _board[i] = BLOCKED;
        }
    }

    /** Return the linearized index of square COL ROW. */
    static int index(char col, char row) {
        return (row - '1' + 2) * EXTENDED_SIDE + (col - 'a' + 2);
    }

    /** Return the linearized index of the square that is DC columns and DR
     *  rows away from the square with index SQ. */
    static int neighbor(int sq, int dc, int dr) {
        return sq + dc + dr * EXTENDED_SIDE;
    }

    /** Clear me to my starting state, with pieces in their initial
     *  positions and no blocks. */
    void clear() {
        _whoseMove = RED;
        for (int i = 0; i < _board.length; i++) {
            _board[i] = EMPTY;
        }
        blocker();
        set('a', '1', BLUE);
        set('g', '7', BLUE);
        set('a', '7', RED);
        set('g', '1', RED);
        movelist.clear();
        numMoves = 0;
        setChanged();
        notifyObservers();
    }

    /** Return true iff the game is over: i.e., if neither side has
     *  any moves, if one side has no pieces, or if there have been
     *  MAX_JUMPS consecutive jumps without intervening extends. */
    boolean gameOver() {
        if ((redPieces() == 0 || bluePieces() == 0)
            || (!canMove(RED) && !canMove(BLUE))
                || (numJumps() >= JUMP_LIMIT)) {
            return true;
        }
        return false;
    }

    /** Return number of red pieces on the board. */
    int redPieces() {
        return numPieces(RED);
    }

    /** Return number of blue pieces on the board. */
    int bluePieces() {
        return numPieces(BLUE);
    }

    /** Return number of COLOR pieces on the board. */
    int numPieces(PieceColor color) {
        if (color == RED) {
            return redPieces;
        } else {
            return bluePieces;
        }
    }

    /** Increment numPieces(COLOR) by K. */
    private void incrPieces(PieceColor color, int k) {
        if (color == BLUE) {
            bluePieces += k;
        } else if (color == RED) {
            redPieces += k;
        }
    }

    /** The current contents of square CR, where 'a'-2 <= C <= 'g'+2, and
     *  '1'-2 <= R <= '7'+2.  Squares outside the range a1-g7 are all
     *  BLOCKED.  Returns the same value as get(index(C, R)). */
    PieceColor get(char c, char r) {
        return _board[index(c, r)];
    }

    /** Return the current contents of square with linearized index SQ. */
    PieceColor get(int sq) {
        return _board[sq];
    }

    /** Set get(C, R) to V, where 'a' <= C <= 'g', and
     *  '1' <= R <= '7'. */
    private void set(char c, char r, PieceColor v) {
        set(index(c, r), v);
    }

    /** Set square with linearized index SQ to V.  This operation is
     *  undoable. */
    public void set(int sq, PieceColor v) {
        _board[sq] = v;
    }

    /** Set square at C R to V (not undoable). */
    private void unrecordedSet(char c, char r, PieceColor v) {
        _board[index(c, r)] = v;
    }

    /** Set square at linearized index SQ to V (not undoable). */
    private void unrecordedSet(int sq, PieceColor v) {
        _board[sq] = v;
    }

    /** Return true iff MOVE is legal on the current board. */
    boolean legalMove(Move move) {
        char col0 = move.col0();
        char row0 = move.row0();
        char col1 = move.col1();
        char row1 = move.row1();
        if ((_board[move.toIndex()] == EMPTY)
                && (_board[index(col0, row0)] == _whoseMove)
                && (row0 <= '7') && (row1 <= '7')
                && ((col0 <= 'g') && (col1 <= 'g'))
                && (canMove(_whoseMove))
                && (Math.abs(row1 - row0) <= 2)
                && (Math.abs(col1 - col0) <= 2)) {
            return true;
        } else {
            throw new GameException("Move illegal.");
        }
    }

    /** Return true iff player WHO can move, ignoring whether it is
     *  that player's move and whether the game is over. */
    boolean canMove(PieceColor who) {
        assert numPieces(who) > 0;
        for (char c = 'a'; c <= 'g'; c++) {
            for (char r = '1'; r <= '7'; r++) {
                for (int i = -2; i <= 2; i++) {
                    for (int j = -2; j <= 2; j++) {
                        if (get(index(c, r)) == who
                                && this.get(neighbor(index(c, r), i, j))
                                == EMPTY) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /** Return the color of the player who has the next move.  The
     *  value is arbitrary if gameOver(). */
    PieceColor whoseMove() {
        return _whoseMove;
    }

    /** Return total number of moves and passes since the last
     *  clear or the creation of the board. */
    int numMoves() {
        return numMoves;
    }

    /** Return number of non-pass moves made in the current game since the
     *  last extend move added a piece to the board (or since the
     *  start of the game). Used to detect end-of-game. */
    int numJumps() {
        return numJumps;
    }

    /** Perform the move C0R0-C1R1, or pass if C0 is '-'.  For moves
     *  other than pass, assumes that legalMove(C0, R0, C1, R1). */
    void makeMove(char c0, char r0, char c1, char r1) {
        if (c0 == '-') {
            makeMove(Move.pass());
        } else {
            makeMove(Move.move(c0, r0, c1, r1));
        }
    }

    /** Make the MOVE on this Board, assuming it is legal. */
    void makeMove(Move move) {
        startUndo();
        if (move == null) {
            throw new GameException("Move illegal.");
        }
        if (move.isPass()) {
            pass();
            movelist.add(move);
            return;
        }
        legalMove(move);
        if (move.isExtend()) {
            set(move.toIndex(), whoseMove());
            addUndo(move.fromIndex(), _whoseMove);
            addUndo(move.toIndex(), EMPTY);
            numJumps = 0;
            jumpList.add(index, numJumps);
            index++;
            incrPieces(_whoseMove, 1);
        }
        if (move.isJump()) {
            set(move.toIndex(), whoseMove());
            _board[move.toIndex()] = _whoseMove;
            _board[move.fromIndex()] = EMPTY;
            addUndo(move.fromIndex(), _whoseMove);
            addUndo(move.toIndex(), EMPTY);
            numJumps++;
            jumpList.add(index, numJumps);
            index++;
        }
        merger(move);
        numMoves += 1;
        movelist.add(move);
        PieceColor opponent = _whoseMove.opposite();
        _whoseMove = opponent;
        setChanged();
        notifyObservers();
    }

    /** Modifies neighboring blocks from MOVE. */
    void merger(Move move) {
        int to = move.toIndex();
        PieceColor opponent = _whoseMove.opposite();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (_board[neighbor(to, i, j)] == opponent) {
                    _board[neighbor(to, i, j)] = _whoseMove;
                    incrPieces(opponent, -1);
                    addUndo(neighbor(to, i, j), opponent);
                    incrPieces(_whoseMove, 1);
                }
            }
        }
    }

    /** Update to indicate that the current player passes, assuming it
     *  is legal to do so.  The only effect is to change whoseMove(). */
    void pass() {
        if (canMove(_whoseMove)) {
            throw new GameException("You can still move, "
                    + "therefore you shall not pass.");
        } else {
            PieceColor opponent = _whoseMove.opposite();
            _whoseMove = opponent;
            setChanged();
            notifyObservers();
        }
    }

    /** Undo the last move. */
    void undo() {
        HashMap H = movestack.pop();
        Set E = H.entrySet();
        Iterator iter = E.iterator();
        index--;
        while (iter.hasNext()) {
            Map.Entry pair = (Map.Entry) iter.next();
            Object k = pair.getKey();
            Object v = pair.getValue();
            Integer sq = (Integer) k;
            PieceColor pieceColor = (PieceColor) v;
            _board[sq] = pieceColor;
            incrPieces(pieceColor, 1);
            incrPieces(pieceColor.opposite(), -1);
            iter.remove();
        }
        _whoseMove = _whoseMove.opposite();
        setChanged();
        notifyObservers();
    }

    /** Indicate beginning of a move in the undo stack. */
    private void startUndo() {
        HashMap<Integer, PieceColor> H = new HashMap<>();
        movestack.push(H);
    }

    /** Add an undo action for changing SQ to NEWCOLOR on current
     *  board. */
    private void addUndo(int sq, PieceColor newColor) {
        movestack.peek().put(sq, newColor);
    }

    /** Return true iff it is legal to place a block at C R. */
    boolean legalBlock(char c, char r) {
        return _board[index(c, r)] == EMPTY && numMoves() == 0;
    }

    /** Return true iff it is legal to place a block at CR. */
    boolean legalBlock(String cr) {
        return legalBlock(cr.charAt(0), cr.charAt(1));
    }

    /** Set a block on the square C R and its reflections across the middle
     *  row and/or column, if that square is unoccupied and not
     *  in one of the corners. Has no effect if any of the squares is
     *  already occupied by a block.  It is an error to place a block on a
     *  piece. */
    void setBlock(char c, char r) {
        if (!legalBlock(c, r)) {
            throw error("illegal block placement");
        }
        int cdis = ('d' - c);
        char newc = (char) ('d' + cdis);
        int rdis = ('4' - r);
        char newr = (char) ('4' + rdis);
        if (r == '4') {
            _board[index(c, r)] = BLOCKED;
            _board[index(newc, r)] = BLOCKED;
        }
        if (c == 'd') {
            _board[index(c, r)] = BLOCKED;
            _board[index(c, newr)] = BLOCKED;
        } else {
            _board[index(c, r)] = BLOCKED;
            _board[index(newc, newr)] = BLOCKED;
            _board[index(c, newr)] = BLOCKED;
            _board[index(newc, r)] = BLOCKED;
        }
        setChanged();
        notifyObservers();
    }

    /** Place a block at CR. */
    void setBlock(String cr) {
        setBlock(cr.charAt(0), cr.charAt(1));
    }

    /** Return a list of all moves made since the last clear (or start of
     *  game). */
    List<Move> allMoves() {
        return movelist;
    }

    @Override
    public String toString() {
        return toString(false);
    }

    /* .equals used only for testing purposes. */
    @Override
    public boolean equals(Object obj) {
        Board other = (Board) obj;
        return Arrays.equals(_board, other._board);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(_board);
    }

    /** Return a text depiction of the board (not a dump).  If LEGEND,
     *  supply row and column numbers around the edges. */
    String toString(boolean legend) {
        StringBuilder S = new StringBuilder();
        Formatter out = new Formatter();
        out.format("%s%n", "===");
        for (int i = MAX; i >= MIN; i -= 11) {
            out.format("%s", "  ");
            for (int j = i; j < i + 7; j++) {
                char x = (_board[j].toString().toLowerCase().charAt(0));
                S.append(x);
                if (x == 'e') {
                    out.format("%c", '-');
                } else if (_board[j].toString().equals("Blocked")) {
                    out.format("%c", 'X');
                } else {
                    out.format("%s", x);
                }
                if (j == i + 6) {
                    break;
                } else {
                    out.format("%s", " ");
                }
            }
            out.format("%n", S);
        }
        out.format("%s", "===");
        return out.toString();
    }

    /** For reasons of efficiency in copying the board,
     *  we use a 1D array to represent it, using the usual access
     *  algorithm: row r, column c => index(r, c).
     *
     *  Next, instead of using a 7x7 board, we use an 11x11 board in
     *  which the outer two rows and columns are blocks, and
     *  row 2, column 2 actually represents row 0, column 0
     *  of the real board.  As a result of this trick, there is no
     *  need to special-case being near the edge: we don't move
     *  off the edge because it looks blocked.
     *
     *  Using characters as indices, it follows that if 'a' <= c <= 'g'
     *  and '1' <= r <= '7', then row c, column r of the board corresponds
     *  to board[(c -'a' + 2) + 11 (r - '1' + 2) ], or by a little
     *  re-grouping of terms, board[c + 11 * r + SQUARE_CORRECTION]. */
    private final PieceColor[] _board;

    /** Player that is on move. */
    private PieceColor _whoseMove;

    /** Tracker for RED Pieces. */
    private int redPieces;

    /** Tracker for BLUE Pieces. */
    private  int bluePieces;

    /** Tracker for number of Moves. */
    private int numMoves;

    /** Number of Jumps. */
    private int numJumps;

    /** List of Jumps. */
    private ArrayList<Integer> jumpList = new ArrayList<>();

    /** Index for jumpList. */
    private int index = 0;

    /** Stack for keeping track of moves. */
    private Stack<HashMap<Integer, PieceColor>> movestack = new Stack<>();

    /** ArrayList for all moves. */
    private ArrayList<Move> movelist = new ArrayList<>();

    /** Maximum Linearized Index of the board. */
    private static final int MAX = 90;

    /** Minimum Linearized Index of the board. */
    private static final int MIN = 24;
}
