package chess.graphics;

import chess.display.MainPanel;
import chess.figures.Figure;
import chess.logic.Board;
import chess.Main;
import chess.logic.Color;

public class VisualBoard {

    public static final String path= "/resources/images/";

    public static final FigureImage whitePawn = new FigureImage(new double[] {Main.width/8, Main.height/8}, path +"chess_pawn_white.png");
    public static final FigureImage blackPawn = new FigureImage(new double[] {Main.width/8, Main.height/8}, path +"chess_pawn_black.png");

    public static final FigureImage whiteKing = new FigureImage(new double[] {Main.width/8, Main.height/8}, path +"chess_king_white.png");
    public static final FigureImage blackKing = new FigureImage(new double[] {Main.width/8, Main.height/8}, path +"chess_king_black.png");

    public static final FigureImage whiteQueen = new FigureImage(new double[] {Main.width/8, Main.height/8}, path +"chess_queen_white.png");
    public static final FigureImage blackQueen = new FigureImage(new double[] {Main.width/8, Main.height/8}, path +"chess_queen_black.png");

    public static final FigureImage whiteHorse = new FigureImage(new double[] {Main.width/8, Main.height/8}, path +"chess_horse_white.png");
    public static final FigureImage blackHorse = new FigureImage(new double[] {Main.width/8, Main.height/8}, path +"chess_horse_black.png");

    public static final FigureImage whiteCastle = new FigureImage(new double[] {Main.width/8, Main.height/8}, path +"chess_castle_white.png");
    public static final FigureImage blackCastle = new FigureImage(new double[] {Main.width/8, Main.height/8}, path +"chess_castle_black.png");

    public static final FigureImage whiteBishop = new FigureImage(new double[] {Main.width/8, Main.height/8}, path +"chess_bishop_white.png");
    public static final FigureImage blackBishop = new FigureImage(new double[] {Main.width/8, Main.height/8}, path +"chess_bishop_black.png");

    public static void drawBoard(Board b) {
        for (int col = 0; col < b.board.length; col++) {
            for (int row = 0; row < b.board[col].length; row++) {
                Figure current = b.board[row][col];
                drawFigure(Main.getPanel(), current, col, row);
            }
        }
    }

    public static void drawFigure(MainPanel panel, Figure c, int col, int row) {
        if (c==null) { panel.delFig(col, row); return; }
        FigureImage img;

        switch (c.getType()) {
            case King:
                img = c.getColor() == Color.White ? whiteKing : blackKing;
                panel.addFig(img, col, row);
                break;
            case Queen:
                img = c.getColor() == Color.White ? whiteQueen : blackQueen;
                panel.addFig(img, col, row);
                break;
            case Pawn:
                img = c.getColor() == Color.White ? whitePawn : blackPawn;
                panel.addFig(img, col, row);
                break;
            case Bishop:
                img = c.getColor() == Color.White ? whiteBishop : blackBishop;
                panel.addFig(img, col, row);
                break;
            case Castle:
                img = c.getColor() == Color.White ? whiteCastle : blackCastle;
                panel.addFig(img, col, row);
                break;
            case Horse:
                img = c.getColor() == Color.White ? whiteHorse : blackHorse;
                panel.addFig(img, col, row);
                break;
        }


    }

}
