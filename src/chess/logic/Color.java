package chess.logic;

public enum Color {
    White, Black;

    public Color getInverted() {
        if (this == Black) { return White; }
        else if ( this == White) { return Black; }

        throw new Error("Color has no Invert");
    }

    public static Color getInverted(Color col) {
        if (col == Color.White) return Black;
        else return White;
    }
}
