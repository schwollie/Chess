package chess.logic;

import chess.figures.Figure;
import chess.figures.Pawn;
import chess.figures.Queen;

import java.util.LinkedList;

public class Move {

    public Figure figure;
    public Figure figureOnNewPos;
    public boolean converted2Queen = false;

    public Board board;
    public Position2D oldPos;
    public Position2D newPos;

    public Move(Figure f, Board b, Position2D oldPos, Position2D newPos) {
        this.figure = f;
        this.board = b;
        this.oldPos = oldPos.clone();
        this.newPos = newPos.clone();
    }

    public void makeMove() {  // this changes the current board because it does not make a deepcopy
        // test if enemy figure stand on the destination and remove it
        figureOnNewPos = this.board.board[newPos.x][newPos.y];
        if (figureOnNewPos != null) { this.board.removeFigure(figureOnNewPos); }

        // remove Figure from old field
        this.board.board[oldPos.x][oldPos.y] = null;

        // move figure and update its position
        this.board.board[newPos.x][newPos.y] = figure;
        figure.updatePos(newPos);

        // convert pawn to queen
        //convertPawn2Queen(this.board, this.figure);
    }

    public void reverseMove() {  // this changes the current board because it does not make a deepcopy

        // convert queen to pawn if it has been a pawn before
        //if (converted2Queen) {
            // remove queen from board
          //  this.board.removeFigure(this.board.board[newPos.x][newPos.y]);
        //}

        // move figure back and remove it from old position:
        this.board.board[newPos.x][newPos.y] = null;
        this.board.board[oldPos.x][oldPos.y] = figure;
        figure.updatePos(oldPos);

        // add enemy figure if there has been one before
        if (figureOnNewPos != null) {
            this.board.board[newPos.x][newPos.y] = figureOnNewPos;
            figureOnNewPos.updatePos(newPos);
            this.board.addFigure(figureOnNewPos);
        }

    }

    public Board getFutureBoard() {
        Board newBoard = Board.DeepCopy(this.board);
        Figure movingFig = newBoard.board[oldPos.x][oldPos.y];  // because it is a deep copy we can't use the old reference

        // remove moving figure from old field
        newBoard.board[oldPos.x][oldPos.y] = null;

        // test if a Figure stands on the new Field and remove it when there is one
        Figure figOnField = newBoard.board[newPos.x][newPos.y];
        if (figOnField != null) { newBoard.removeFigure(figOnField); }

        // move this figure to the its new Position and update its position
        newBoard.board[newPos.x][newPos.y] = movingFig;
        movingFig.updatePos(newPos);

        convertPawn2Queen(newBoard, movingFig); // when pawn reaches the end field most of the time a queen is the best option

        return newBoard;
    }

    public void convertPawn2Queen(Board newB, Figure fm) {
        if (fm instanceof Pawn && (fm.getPos().y == 0 || fm.getPos().y == 7)) {
            newB.removeFigure(fm);
            newB.addFigure(new Queen(fm.getColor(), new Position2D(fm.getPos().x, fm.getPos().y)));
            converted2Queen = true;
        }
    }

    public boolean isInList(LinkedList<Move> mL) {
        for (Move m: mL) {
            if (this.isEqual(m)) { return true; }
        }
        return false;
    }

    public boolean isEqual(Move m) {
        return (this.figure == m.figure && this.board == m.board &&
                m.oldPos.hasEqualValues(this.oldPos) && m.newPos.hasEqualValues(this.newPos));
    }

    @Override
    public String toString() {
        return String.format("%s: (%s)->(%s)", figure, oldPos, newPos);
    }
}
