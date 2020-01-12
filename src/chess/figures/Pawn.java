package chess.figures;

import chess.logic.*;

import java.util.LinkedList;

public class Pawn extends Figure {

    public Pawn(Color col, Position2D pos) {
        super(col, FigureType.Pawn, pos);
    }

    @Override
    public LinkedList<Move> getMoves(Board board) {
        if (this.color == Color.White) {
            return getWhiteMoves(board);
        } else {
            return getBlackMoves(board);
        }
    }

    public LinkedList<Move> getWhiteMoves(Board board) {
        LinkedList<Move> moves = new LinkedList<Move>();
        Position2D newPos;

        // 2 steps forward at the beginning
        newPos = new Position2D(this.pos.x, this.pos.y + 2);
        if (this.pos.y == 1 &&  ! board.isFigureAt(newPos) && ! board.isFigureAt(new Position2D(this.pos.x, this.pos.y + 1))) {
            moves.add(new Move(this, board, pos, newPos));
        }

        // 1 step forward
        newPos = new Position2D(this.pos.x, this.pos.y + 1);
        if (! newPos.outOfBounds() && ! board.isFigureAt(newPos)) {
            moves.add(new Move(this, board, pos, newPos));
        }

        // 1 step forward and left
        newPos = new Position2D(this.pos.x - 1, this.pos.y + 1);
        if (! newPos.outOfBounds() && board.isFigureAt(newPos, this.color.getInverted())) {
            moves.add(new Move(this, board, pos, newPos));
        }
        // 1 step forward and right
        newPos = new Position2D(this.pos.x + 1, this.pos.y + 1);
        if (! newPos.outOfBounds() && board.isFigureAt(newPos, this.color.getInverted())) {
            moves.add(new Move(this, board, pos, newPos));
        }

        return moves;
    }

    public LinkedList<Move> getBlackMoves(Board board) {
        LinkedList<Move> moves = new LinkedList<Move>();
        Position2D newPos;

        // 2 steps forward at the beginning
        newPos = new Position2D(this.pos.x, this.pos.y - 2);
        if (this.pos.y == 6 &&  ! board.isFigureAt(newPos) && ! board.isFigureAt(new Position2D(this.pos.x, this.pos.y - 1))) {
            moves.add(new Move(this, board, pos, newPos));
        }

        // 1 step forward
        newPos = new Position2D(this.pos.x, this.pos.y - 1);
        if (! newPos.outOfBounds() && ! board.isFigureAt(newPos)) {
            moves.add(new Move(this, board, pos, newPos));
        }

        // 1 step forward and left
        newPos = new Position2D(this.pos.x - 1, this.pos.y - 1);
        if (! newPos.outOfBounds() && board.isFigureAt(newPos, this.color.getInverted())) {
            moves.add(new Move(this, board, pos, newPos));
        }
        // 1 step forward and right
        newPos = new Position2D(this.pos.x + 1, this.pos.y - 1);
        if (! newPos.outOfBounds() && board.isFigureAt(newPos, this.color.getInverted())) {
            moves.add(new Move(this, board, pos, newPos));
        }

        return moves;
    }

    @Override
    public Figure deepCopy() {
        return new Pawn(this.color, new Position2D(this.pos.x, this.pos.y));
    }

    @Override
    public int getValue() {
        return 1;
    }

    @Override
    public String toString() {
        if (this.color == Color.White) {return "p";} else {return "P";}
    }
}
