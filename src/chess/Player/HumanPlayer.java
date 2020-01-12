package chess.Player;

import chess.Main;
import chess.figures.Figure;
import chess.logic.Board;
import chess.logic.Color;
import chess.logic.Move;
import chess.logic.Position2D;
import javafx.scene.input.KeyCode;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.SimpleTimeZone;


public class HumanPlayer extends Player {

    private Figure activeFig;
    private Position2D pos;
    private ArrayList<Move> possMoves;

    public HumanPlayer(Color c) {
        super(c);
    }

    public void makeMove(Board currentBoard) {
        boolean isNext = false;
        activeFig = null;

        Main.setMoves2draw(null);

        Move lastMove = null;
        boolean isConfirmed = false;
        boolean waitForConfirmation = false;
        while (!isConfirmed) {
            // Handle Mouse Events
            MouseEvent m = Main.pollMouseEvent();

            if (m != null && !waitForConfirmation) {
                int xIndex = m.getX(); int yIndex = m.getY();
                int width = Main.getDimensions().width; int height = Main.getDimensions().height;
                int xCoordinate = xIndex*8/width; int yCoordinate = yIndex*8/height;

                if (activeFig==null) {
                    selectFig(xCoordinate, yCoordinate, currentBoard);
                } else {
                    Move newMove = new Move(activeFig, currentBoard, pos, new Position2D(xCoordinate, yCoordinate));
                    isNext = tryMove(newMove);
                    if (isNext) {
                        waitForConfirmation = true;
                        lastMove = newMove;
                    } else {
                        selectFig(xCoordinate, yCoordinate, currentBoard);
                    }
                }
            }
            // Handle Key Events
            KeyEvent k = Main.pollKeyEvent();
            if (k != null) {
                switch (k.getKeyCode()) {
                    case KeyEvent.VK_BACK_SPACE:
                        //Main.undo2Steps();
                        currentBoard = Main.getCurrentBoard();
                        break;
                    case KeyEvent.VK_ENTER:
                        if (waitForConfirmation) isConfirmed = true;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        if (waitForConfirmation) {
                            lastMove.reverseMove();
                            waitForConfirmation = false;
                        }
                        break;
                }
            }
        }
    }

    private void selectFig(int xCoordinate, int yCoordinate, Board currentBoard) {
        Figure f = Main.getCurrentBoard().board[xCoordinate][yCoordinate];

        if (f!=null && f.getColor()==this.c) { activeFig = f; Main.setMoves2draw(activeFig.getMoves(currentBoard));}
        pos = new Position2D(xCoordinate, yCoordinate);
    }

    private boolean tryMove(Move newMove) {
        LinkedList<Move> possMoves = activeFig.getMoves(newMove.board);
        if (!newMove.isInList(possMoves)) {
            return false;
        }

        //Board newB = newMove.getFutureBoard();
        //Main.setCurrentBoard(newB);

        newMove.makeMove();
        //newMove.reverseMove();

        return true;
    }
}
