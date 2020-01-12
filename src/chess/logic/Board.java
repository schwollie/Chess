package chess.logic;

import chess.figures.*;

import java.util.ArrayList;

public class Board {

    public Figure[][] board = new Figure[8][8];
    public ArrayList<Figure> whiteFigs = new ArrayList<>();
    public ArrayList<Figure> blackFigs = new ArrayList<>();

    public Board() {
        this.init();
    }

    public Board(boolean init) {
        if (init) {
            this.init();
        }
    }

    public static Board DeepCopy(Board board2DeepCopy) {
        Board deepCopy = new Board(false);

        // deep copy all figures to new Board
        for (Figure wF : board2DeepCopy.whiteFigs) {
            deepCopy.whiteFigs.add(wF.deepCopy());
        }

        for (Figure bF : board2DeepCopy.blackFigs) {
            deepCopy.blackFigs.add(bF.deepCopy());
        }

        // also put copied figures on board
        for (Figure wF : deepCopy.whiteFigs) {
            deepCopy.board[wF.getPos().x][wF.getPos().y] = wF;
        }

        for (Figure bF : deepCopy.blackFigs) {
            deepCopy.board[bF.getPos().x][bF.getPos().y] = bF;
        }

        return deepCopy;
    }

    public void init() {
        // region White:

        this.board[0][0] = new Castle(Color.White, new Position2D(0, 0));
        this.board[7][0] = new Castle(Color.White, new Position2D(7, 0));

        this.board[1][0] = new Horse(Color.White, new Position2D(1, 0));
        this.board[6][0] = new Horse(Color.White, new Position2D(6, 0));

        this.board[2][0] = new Bishop(Color.White, new Position2D(2, 0));
        this.board[5][0] = new Bishop(Color.White, new Position2D(5, 0));

        this.board[3][0] = new King(Color.White, new Position2D(3, 0));
        this.board[4][0] = new Queen(Color.White, new Position2D(4, 0));

        for (int i = 0; i < 8; i++) {
            this.board[i][1] = new Pawn(Color.White, new Position2D(i, 1));
        }

        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 2; k++) {
                whiteFigs.add(this.board[i][k]);
            }
        }

        // endregion


        // region Black:

        this.board[0][7] = new Castle(Color.Black, new Position2D(0, 7));
        this.board[7][7] = new Castle(Color.Black, new Position2D(7, 7));

        this.board[1][7] = new Horse(Color.Black, new Position2D(1, 7));
        this.board[6][7] = new Horse(Color.Black, new Position2D(6, 7));

        this.board[2][7] = new Bishop(Color.Black, new Position2D(2, 7));
        this.board[5][7] = new Bishop(Color.Black, new Position2D(5, 7));

        this.board[3][7] = new King(Color.Black, new Position2D(3, 7));
        this.board[4][7] = new Queen(Color.Black, new Position2D(4, 7));

        for (int i = 0; i < 8; i++) {
            this.board[i][6] = new Pawn(Color.Black, new Position2D(i, 6));
        }

        for (int i = 0; i < 8; i++) {
            for (int k = 6; k < 8; k++) {
                blackFigs.add(this.board[i][k]);
            }
        }

        // endregion
    }

    public void removeFigure(Figure f) {
        if (f.getColor() == Color.White) {
            whiteFigs.remove(f);
        } else {
            blackFigs.remove(f);
        }
        board[f.getPos().x][f.getPos().y] = null;
    }

    public void addFigure(Figure f) {
        this.board[f.getPos().x][f.getPos().y] = f;
        if (f.getColor() == Color.White) {
            this.whiteFigs.add(f);
        } else {
            this.blackFigs.add(f);
        }
    }

    public boolean isFigureAt(Position2D p) {
        return this.board[p.x][p.y] != null;
    }

    public boolean isFigureAt(Position2D p, Color c) {
        return (this.board[p.x][p.y] != null && this.board[p.x][p.y].getColor() == c);
    }

    public int boardValue(Color c) {
        int whiteVal = 0;
        int blackVal = 0;

        for (Figure f : whiteFigs) {
            whiteVal += f.getValue();
        }
        for (Figure f : blackFigs) {
            blackVal += f.getValue();
        }

        if (c == Color.White) {
            return whiteVal - blackVal;
        } else {
            return blackVal - whiteVal;
        }
    }

    public ArrayList<Move> getAllMoves(Color c) {
        ArrayList<Move> moves = new ArrayList<>();
        if (c == Color.White) {
            for (Figure f : whiteFigs) {
                moves.addAll(f.getMoves(this));
            }
        } else {
            for (Figure f : blackFigs) {
                moves.addAll(f.getMoves(this));
            }
        }
        return moves;
    }

    public boolean colorHasWon(Color c) {
        if (boardValue(c) < 100) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        StringBuffer b = new StringBuffer();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Figure f = this.board[col][row];
                if (f != null) {
                    b.append(f.toString());
                    b.append(" ");
                } else {
                    b.append("- ");
                }
            }
            b.append("\n");
        }
        return b.toString();
    }
}
