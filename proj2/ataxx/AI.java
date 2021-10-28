package ataxx;

import java.util.ArrayList;

import static ataxx.PieceColor.*;
import static java.lang.Math.min;
import static java.lang.Math.max;

/** A Player that computes its own moves.
 *  @author Rocky Lubbers
 *  CITE -- AI Minimax Algorithm foundationally based off of
 *  Paul Hilfinger's 61B Lecture 22 Slides.
 */
class AI extends Player {

    /** Maximum minimax search depth before going to static evaluation. */
    private static final int MAX_DEPTH = 4;
    /** A position magnitude indicating a win (for red if positive, blue
     *  if negative). */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 1;
    /** A magnitude greater than a normal value. */
    private static final int INFTY = Integer.MAX_VALUE;

    /** A new AI for GAME that will play MYCOLOR. */
    AI(Game game, PieceColor myColor) {
        super(game, myColor);
    }

    @Override
    Move myMove() {
        StringBuilder s = new StringBuilder();
        if (!board().canMove(myColor())) {

            game().reportMove(myColor() + " passes" + s + ".");

            return Move.pass();
        }
        Move move = findMove();
        s.append(move.col0());
        s.append(move.row0());
        s.append("-");
        s.append(move.col1());
        s.append(move.row1());
        game().reportMove(myColor() + " moves " + s + ".");
        return move;
    }

    /** Return a move for me from the current position, assuming there
     *  is a move. */
    private Move findMove() {
        Board b = new Board(board());
        if (myColor() == RED) {
            findMove(b, MAX_DEPTH, true, 1, -INFTY, INFTY);
        } else {
            findMove(b, MAX_DEPTH, true, -1, -INFTY, INFTY);
        }
        return _lastFoundMove;
    }

    /** Used to communicate best moves found by findMove, when asked for. */
    private Move _lastFoundMove;

    /** Find a move from position BOARD and return its value, recording
     *  the move found in _lastFoundMove iff SAVEMOVE. The move
     *  should have maximal value or have value >= BETA if SENSE==1,
     *  and minimal value or value <= ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels before using a static estimate. */
    private int findMove(Board board, int depth, boolean saveMove, int sense,
                         int alpha, int beta) {

        if (depth == 0 || board.gameOver()) {
            return staticScore(board);
        }
        moveList = new ArrayList<>();
        moveCalculator(board);

        if (depth == MAX_DEPTH) {
            _lastFoundMove = moveList.get(0);
            lastmoveval = -INFTY;
        }

        int bestsofar = alpha;
        for (Move m : moveList) {
            board.makeMove(m);
            Board b = new Board(board);
            int response = findMin(b, depth - 1, alpha, beta);
            if (response >= bestsofar) {
                if (depth == MAX_DEPTH) {
                    _lastFoundMove = m;
                }
                bestsofar = response;
                alpha = max(alpha, response);
                if (beta <= alpha) {
                    break;
                }
            }
            board.undo();
        }
        return lastmoveval;
    }

    /** Find a minimizing move from position BOARD, DEPTH and return its value,
     * using ALPHA BETA pruning. */
    private int findMin(Board board, int depth, int alpha, int beta) {
        if (depth == 0 || board.gameOver()) {
            return staticScore(board);
        }
        int bestsofar = beta;
        moveList = new ArrayList<>();
        moveCalculator(board);
        for (Move m : moveList) {
            board.makeMove(m);
            Board b = new Board(board);
            int response = findMove(b, depth - 1, false, 0, alpha, beta);
            if (response <= bestsofar) {
                if (depth == MAX_DEPTH) {
                    _lastFoundMove = m;
                }
                bestsofar = response;
                beta = min(beta, response);
                if (beta <= alpha) {
                    break;
                }
            }
            board.undo();
        }
        return lastmoveval;
    }


    /** Finds all legal moves for AI in Board B. */
    private void moveCalculator(Board b) {
        for (char c = 'a'; c <= 'g'; c++) {
            for (char r = '1'; r <= '7'; r++) {
                checkneighbors(b, c, r);
            }
        }
    }

    /** Adds all legal moves to list from Board B,
     * taking in Column C and Row R. */
    private void checkneighbors(Board b, char c, char r) {
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                if (b.get(b.neighbor(b.index(c, r), i, j)) == EMPTY) {
                    char c1 = (char) (c + i);
                    char r1 = (char) (r + j);
                    Move move = Move.move(c, r, c1, r1);
                    if (b.get(c, r) == b.whoseMove() && b.legalMove(move)) {
                        moveList.add(move);
                    }
                }
            }
        }
    }

    /** Return a heuristic value for BOARD. */
    private int staticScore(Board board) {
        return (board.numPieces(board.whoseMove())
                - board.numPieces(board.whoseMove().opposite()));
    }

    /** List of all possible Maximizing Moves. */
    private ArrayList<Move> moveList = new ArrayList<>();

    /** Heuristic value for the current Board. */
    private int lastmoveval = 0;
}
