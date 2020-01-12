package chess.logic;

public class Position2D {

    public int x;
    public int y;

    public Position2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position2D add(Position2D p, int[] p2) {
        return new Position2D(p.x + p2[0], p.y + p2[1]);
    }

    public static Position2D add(Position2D p, Position2D p2) {
        return new Position2D(p.x + p2.x, p.y + p2.y);
    }

    public boolean outOfBounds() {
        return (this.x < 0 || this.x > 7 || this.y < 0 || this.y > 7);
    }

    public boolean hasEqualValues(Position2D other) {
        return (this.x == other.x && this.y == other.y);
    }

    @Override
    public String toString() {
        return "col=" + x + "; row=" + y;
    }

    public Position2D clone() {
        return new Position2D(x,y);
    }
}
