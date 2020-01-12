package chess.Player;

import chess.figures.Figure;
import chess.logic.Board;
import chess.logic.Color;
import chess.logic.Move;

import java.util.ArrayList;

public abstract class Player {

    protected Color c;

    public Player(Color c) {
        this.c = c;
    }

    public abstract void makeMove(Board currentBoard);



}
