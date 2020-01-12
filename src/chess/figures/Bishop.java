package chess.figures;

import chess.logic.*;
import javafx.geometry.Pos;

import java.util.LinkedList;

public class Bishop extends Figure {

    // possible Moves relative to position
    private static final int[][][] possMoves = new int[][][] {   { {1, 1}, {2,2}, {3,3}, {4,4}, {5,5}, {6,6}, {7,7} },  // right up
                                                    { {1,-1}, {2,-2}, {3,-3}, {4,-4}, {5,-5}, {6,-6}, {7,-7} },  // right down
                                                    { {-1,1}, {-2,2}, {-3,3}, {-4,4}, {-5,5}, {-6,6}, {-7,7} },  // left up
                                                    { {-1,-1}, {-2,-2}, {-3,-3}, {-4,-4}, {-5,-5}, {-6,-6}, {-7,-7} }  };  // left down

    public Bishop(Color col, Position2D pos) {
        super(col, FigureType.Bishop, pos);
    }

    @Override
    public LinkedList<Move> getMoves(Board board) {
        LinkedList<Move> moves = new LinkedList<>();

        Position2D newPos;
        for (int i = 0; i < possMoves.length; i++) {
            for (int k = 0; k < possMoves[i].length; k++) {
                newPos = Position2D.add(this.pos, possMoves[i][k]);

                if (newPos.outOfBounds() || board.isFigureAt(newPos, this.color)) {
                    break;
                } else if (board.isFigureAt(newPos, this.color.getInverted())) {
                    moves.add(new Move(this, board, this.pos, newPos));
                    break;
                } else {
                    moves.add(new Move(this, board, this.pos, newPos));
                }
            }
        }

        return moves;
    }

    @Override
    public Figure deepCopy() {
        return new Bishop(this.color, new Position2D(this.pos.x, this.pos.y));
    }

    @Override
    public int getValue() {
        return 3;
    }

    @Override
    public String toString() {
        if (this.color == Color.White) {return "b";} else {return "B";}
    }
}
