package chess.Player;

import chess.Main;
import chess.logic.Board;
import chess.logic.Color;
import chess.logic.Move;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        //long start = System.currentTimeMillis();
        //investigatedMoves = 0;
        desiredMove = null;
        Board b = Board.DeepCopy(currentBoard);
        Board b2 = Board.DeepCopy(currentBoard);

        //long start = System.currentTimeMillis();
        //int value = multiThreadedMinMax(this.c, depth, Integer.MIN_VALUE + 5000, Integer.MAX_VALUE - 5000, b);
        //System.out.println("time for multithreaded: " + (System.currentTimeMillis() - start));

        long start = System.currentTimeMillis();
        int value2 = minMax(this.c, depth, Integer.MIN_VALUE + 5000, Integer.MAX_VALUE - 5000, b2);
        System.out.println("time no multithreading: " + (System.currentTimeMillis() - start));

        if (desiredMove == null) {
            System.out.println("Minmax Player has Lost: " + this.c);
        } else {
            desiredMove.makeMove();
            Main.setCurrentBoard(desiredMove.board);
        }
        //System.out.println("Investigated " + investigatedMoves + " moves in " + (System.currentTimeMillis()-start) + " ms");
    }


    private int minMax(Color cP, int depth, int alpha, int beta, Board board) {

        ArrayList<Move> pMoves = board.getAllMoves(cP);
        if (depth == 0 || pMoves.isEmpty()) {
            return board.boardValue(cP);
        }

        int maxValue = alpha;

        for (Move m : pMoves) {
            //investigatedMoves++;
            m.makeMove();
            int val = -minMax(cP.getInverted(), depth - 1, -beta, -maxValue, board);
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

    private int multiThreadedMinMax(Color cP, int depth, int alpha, int beta, Board board) {
        ArrayList<Move> pMoves = board.getAllMoves(cP);
        if (depth == 0 || pMoves.isEmpty()) {
            return board.boardValue(cP);
        }

        ArrayList<AlphaBetaCore> cores = new ArrayList<>();
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < pMoves.size(); i++) {
            AlphaBetaCore core = new AlphaBetaCore(cP, depth, alpha, beta, board, pMoves.subList(i, i + 1));
            cores.add(core);
            Thread t = new Thread(core);
            t.start();
            threads.add(t);
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Optional<Integer> bestValue = Optional.empty();
        Move bestMove = null;
        for (AlphaBetaCore core : cores) {
            Integer value = core.getMaxVal();
            if (bestValue.orElse(Integer.MIN_VALUE) < value) {
                bestValue = Optional.of(value);
                bestMove = core.getBestMove();
            }
        }
        desiredMove = bestMove;
        return bestValue.get();
        // return Pair.with(bestMove, maxValue);
    }
}

class AlphaBetaCore implements Runnable {

    private Color c;
    private int depth;
    private int alpha;
    private int beta;
    private Board b;
    private List<Move> moves;

    private int maxVal;
    private Move bestMove;

    public AlphaBetaCore(Color c, int depth, int alpha, int beta, Board b, List<Move> moves) {
        this.c = c;
        this.depth = depth;
        this.alpha = alpha;
        this.beta = beta;
        this.b = Board.DeepCopy(b);
        this.moves = moves;

        for (Move m : this.moves) {
            m.board = this.b;
        }
    }

    @Override
    public void run() {
        maxVal = minMax(this.c, this.depth, this.alpha, this.beta, this.b);
    }

    public int getMaxVal() {
        return this.maxVal;
    }

    public Move getBestMove() {
        return this.bestMove;
    }

    private int minMax(Color cP, int depth, int alpha, int beta, Board board) {

        ArrayList<Move> pMoves = board.getAllMoves(cP);
        if (depth == 0 || pMoves.isEmpty()) {
            return board.boardValue(cP);
        }

        int maxValue = alpha;

        for (Move m : pMoves) {
            //investigatedMoves++;
            m.makeMove();
            int val = -minMax(cP.getInverted(), depth - 1, -beta, -maxValue, board);
            m.reverseMove();

            if (val > maxValue) {
                maxValue = val;
                if (depth == this.depth) {
                    bestMove = m;
                }

                if (maxValue >= beta) {
                    break;
                }
            }

        }

        return maxValue;
    }
}
