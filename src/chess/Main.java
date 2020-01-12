package chess;

import chess.Input.KeyEventListener;
import chess.Input.MouseEventListener;
import chess.Player.AlphaBetaPlayer;
import chess.Player.HumanPlayer;
import chess.Player.MinmaxPlayer;
import chess.Player.Player;
import chess.display.Display;
import chess.display.MainPanel;
import chess.figures.Figure;
import chess.graphics.*;
import chess.logic.Board;
import chess.logic.Move;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public abstract class Main {

    public static final int width = 800;
    public static final int height = 800;
    public static Color blackCol = new Color(87, 54, 15);
    public static Color whiteCol = new Color(178, 175, 172);

    private static Display frame;
    private static MainPanel panel;

    private static MouseEventListener mouseListener;
    private static KeyEventListener keyListener;

    private static Player whiteP;
    private static Player blackP;
    private static Board currentBoard;
    private static LinkedList<Board> boardHistory = new LinkedList<>();
    private static LinkedList<Move> moves2draw;

    public static void initialize() {
        mouseListener = new MouseEventListener();
        keyListener = new KeyEventListener();
        frame = new Display(width, height);
        frame.addMouseListener(mouseListener);
        frame.addKeyListener(keyListener);

        panel = new MainPanel(width, height);
        panel.addMouseListener(mouseListener);
        frame.add(panel);
        frame.setVisible(true);

        Board b = new Board();
        currentBoard = b;
        whiteP = new HumanPlayer(chess.logic.Color.White);
        //whiteP = new AlphaBetaPlayer(chess.logic.Color.White, 2);
        //blackP = new HumanPlayer(chess.logic.Color.Black);
        blackP = new AlphaBetaPlayer(chess.logic.Color.Black, 5);

        Thread paintThread = new Thread(Main::paint);
        paintThread.start();
    }

    public static void paint() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            VisualBoard.drawBoard(getCurrentBoard());
            EventQueue.invokeLater(frame::repaint);
        }
    }

    public static void run() {
        initialize();

        boolean checkmate = false;

        while (!checkmate) {
            whiteP.makeMove(currentBoard);
            blackP.makeMove(currentBoard);

        }
    }

    public static void main(String[] args) {
        Thread t = new Thread(Main::run);
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized MouseEvent pollMouseEvent() {
        return mouseListener.events.poll();
    }

    public static synchronized KeyEvent pollKeyEvent() { return keyListener.events.poll(); }

    public static synchronized Dimension getDimensions() {
        return frame.getSize();
    }

    public static synchronized Board getCurrentBoard() {
        return currentBoard;
    }

    public synchronized static Display getFrame() {
        return frame;
    }

    public synchronized static MainPanel getPanel() {
        return panel;
    }

    public static synchronized void setCurrentBoard(Board b) {
        boardHistory.add(currentBoard);
        currentBoard = b;
    }

    public static synchronized void setMoves2draw(LinkedList<Move> m) {
        moves2draw = m;
    }

    public static synchronized LinkedList<Move> getMoves2draw() { return moves2draw; }

    public static synchronized void undo2Steps() {
        if (boardHistory.size() < 2) { return; }

        currentBoard = boardHistory.get(boardHistory.size() - 2);
        boardHistory.remove(boardHistory.size()-1);
        boardHistory.remove(boardHistory.size()-1);
        moves2draw = null;
    }
}
