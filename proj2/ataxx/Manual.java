package ataxx;
import static ataxx.PieceColor.*;

/** A Player that receives its moves from its Game's getMoveCmnd method.
 *  @author Rocky Lubbers
 */
class Manual extends Player {

    /** A Player that will play MYCOLOR on GAME, taking its moves from
     *  GAME. */
    Manual(Game game, PieceColor myColor) {
        super(game, myColor);
    }

    @Override
    Move myMove() {
        Command com = game().getMoveCmnd(board().whoseMove().toString() + ": ");
        Move move;
        if (com.commandType() == Command.Type.PASS) {
            move = Move.pass();
        } else {
            String[] ops = com.operands();
            char c0 = ops[0].charAt(0);
            char r0 = ops[1].charAt(0);
            char c1 = ops[2].charAt(0);
            char r1 = ops[3].charAt(0);
            move =  Move.move(c0, r0, c1, r1);
        }
        return move;
    }
}

