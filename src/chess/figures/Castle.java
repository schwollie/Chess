package chess.figures;;

import chess.logic.*;

import java.util.LinkedList;

public class Castle extends Figure {

    // possible Moves relative to position
    private static final int[][][] possMoves = new int[][][] {   { {1,0}, {2,0}, {3,0}, {4,0}, {5,0}, {6,0}, {7,0} },  // right
            { {0,1}, {0,2}, {0,3}, {0,4}, {0,5}, {0,6}, {0,7} },  // up
            { {0,-1}, {0,-2}, {0,-3}, {0,-4}, {0,-5}, {0,-6}, {0,-7} },  // down
            { {-1,0}, {-2,0}, {-3,0}, {-4,0}, {-5,0}, {-6,0}, {-7,0} }  };  // left

    public Castle(Color col, Position2D pos) {
        super(col, FigureType.Castle, pos);
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
        return new Castle(this.color, new Position2D(this.pos.x, this.pos.y));
    }

    @Override
    public int getValue() {
        return 5;
    }

    @Override
    public String toString() {
        if (this.color == Color.White) {return "c";} else {return "C";}
    }
}
