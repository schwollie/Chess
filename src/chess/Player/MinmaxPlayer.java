package chess.Player;

import chess.Main;
import chess.logic.Board;
import chess.logic.Color;
import chess.logic.Move;

import java.util.ArrayList;

public class MinmaxPlayer extends Player {

    private int depth;
    private Move desiredMove;
    private int investigatedMoves = 0;

    public MinmaxPlayer(Color c, int depth) {
        super(c);
        this.depth = depth;
    }

    @Override
    public void makeMove(Board currentBoard) {
        long start = System.currentTimeMillis();
        investigatedMoves = 0;
        desiredMove = null;
        Board b = Board.DeepCopy(currentBoard);
        int value = max(this.c, depth, b);

        if (desiredMove==null) {
            System.out.println("Minmax Player has Lost: " + this.c);
        } else {
            desiredMove.makeMove();
            Main.setCurrentBoard(b);
        }
        System.out.println("Investigated " + investigatedMoves + " moves in " + (System.currentTimeMillis()-start) + " ms");
    }

    private int max(Color playerC, int depth, Board board) {
        ArrayList<Move> pMoves = board.getAllMoves(playerC);
        if (depth == 0 || pMoves.isEmpty()) { return board.boardValue(playerC.getInverted()); }

        int maxVal = Integer.MIN_VALUE;

        for (Move m: pMoves) {
            investigatedMoves++;
            //String state1 = board.toString();
            m.makeMove();
            //String state2 = board.toString();
            int value = min(playerC.getInverted(), depth-1, board);
            m.reverseMove();
           // String state3 = board.toString();
            //System.out.println("Move " + m + " depth=" + depth + " value=" +value + ":");
            //System.out.println("Before\n" + state1);
            //System.out.println("After\n" + state2);
            if (value > maxVal) {
                maxVal = value;
                if (depth == this.depth) {
                    desiredMove = m;
                }
            }
        }

        return maxVal;
    }

    private int min(Color playerC, int depth, Board board) {
        ArrayList<Move> pMoves = board.getAllMoves(playerC);
        if (depth == 0 || pMoves.isEmpty()) { return board.boardValue(playerC.getInverted()); }

        int minVal = Integer.MAX_VALUE;

        for (Move m: pMoves) {
            //Board futureB = m.getFutureBoard();
            m.makeMove();
            int value = max(playerC.getInverted(), depth-1, board);
            m.reverseMove();
            if (value < minVal) {
                minVal = value;
            }
        }

        return minVal;
    }
}
