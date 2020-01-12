package chess.Player;

import chess.Main;
import chess.logic.Board;
import chess.logic.Color;
import chess.logic.Move;

import java.util.ArrayList;

public class AlphaBetaPlayer extends Player {

    private int depth;
    private Move desiredMove;
    private int investigatedMoves = 0;

    public AlphaBetaPlayer(Color c, int depth) {
        super(c);
        this.depth = depth;
    }

    @Override
    public void makeMove(Board currentBoard) {
        long start = System.currentTimeMillis();
        investigatedMoves = 0;
        desiredMove = null;
        Board b = Board.DeepCopy(currentBoard);

        int value = minMax(this.c, depth, Integer.MIN_VALUE+5000, Integer.MAX_VALUE-5000, b);

        if (desiredMove==null) {
            System.out.println("Minmax Player has Lost: " + this.c);
        } else {
            desiredMove.makeMove();
            Main.setCurrentBoard(b);
        }
        System.out.println("Investigated " + investigatedMoves + " moves in " + (System.currentTimeMillis()-start) + " ms");
    }


    private int minMax(Color cP, int depth, int alpha, int beta, Board board) {

        ArrayList<Move> pMoves = board.getAllMoves(cP);
        if (depth == 0 || pMoves.isEmpty()) { return board.boardValue(cP); }

        int maxValue = alpha;

        for (Move m: pMoves) {
            investigatedMoves++;
            m.makeMove();
            int val = -minMax(cP.getInverted(), depth-1, -beta, -maxValue, board);
            m.reverseMove();

            if (val > maxValue) {
                maxValue = val;
                if (depth == this.depth) {
                    desiredMove = m;
                }

                if (maxValue >= beta) {
                    break;
                }
            }

        }

        return maxValue;
    }
}
