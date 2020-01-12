package chess.figures;

import chess.logic.*;

import java.util.LinkedList;

public class Horse extends Figure {

    // possible Moves relative to position
    private static final int[][] possMoves = new int[][] {  {2,1}, {2,-1}, {-2,1}, {-2,-1},
                                                    {1,2}, {-1,2}, {1,-2}, {-1,-2} };

    public Horse(Color col, Position2D pos) {
        super(col, FigureType.Horse, pos);
    }

    @Override
    public LinkedList<Move> getMoves(Board board) {
        LinkedList<Move> moves = new LinkedList<>();

        Position2D newPos;

        for (int k = 0; k < possMoves.length; k++) {
            newPos = Position2D.add(this.pos, possMoves[k]);
            if (newPos.outOfBounds() || board.isFigureAt(newPos, this.color)) {
                continue;
            } else {
                moves.add(new Move(this, board, pos, newPos));
            }
        }

        return moves;
    }

    @Override
    public Figure deepCopy() {
        return new Horse(this.color, new Position2D(this.pos.x, this.pos.y));
    }

    @Override
    public int getValue() {
        return 3;
    }

    @Override
    public String toString() {
        if (this.color == Color.White) {return "h";} else {return "H";}
    }
}
