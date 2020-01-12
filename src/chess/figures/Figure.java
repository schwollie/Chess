package chess.figures;

import chess.logic.*;

import java.util.LinkedList;


public abstract class Figure {

    protected Color color;
    protected FigureType type;

    protected Position2D pos;

    public Figure(Color col, FigureType type, Position2D pos) {
        this.color = col;
        this.type = type;
        this.pos = pos;
    }

    public abstract LinkedList<Move> getMoves(Board board);

    public Color getColor() {
        return this.color;
    }

    public FigureType getType() {
        return type;
    }

    public void updatePos(Position2D newPos) {
        pos.x = newPos.x;
        pos.y = newPos.y;
    }

    public abstract Figure deepCopy();

    public Position2D getPos() { return this.pos; }

    public abstract int getValue();
}
