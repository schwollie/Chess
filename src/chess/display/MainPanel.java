package chess.display;

import chess.Main;
import chess.figures.Figure;
import chess.graphics.FigureImage;
import chess.logic.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class MainPanel extends JPanel {

    private FigureImage[][] figs_2_draw = new FigureImage[8][8];
    private Dimension lastSize = new Dimension();

    public MainPanel(int width, int height) {
        this.setDoubleBuffered(true);
        this.setSize(width, height);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Dimension size = Main.getDimensions();

        int xSize = size.width/8;
        int ySize = size.width/8;


        if (!lastSize.equals(size)) { resizeImgs(xSize, ySize); }
        lastSize = size;


        // draw board
        int count = 0;

        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {

                if (count%2 == 0) { g.setColor(Main.whiteCol); }
                else { g.setColor(Main.blackCol); }

                g.fillRect(row*xSize, col*ySize, xSize, ySize);
                count ++;
            }
            count++;
        }

        // draw figures
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                if (figs_2_draw[col][row]!=null) {
                    g.drawImage(figs_2_draw[col][row].getImg(), row*xSize, col*ySize, null);
                }
            }
        }

        // draw possible Moves
        g.setColor(new Color(50, 253, 0, 165));
        //Graphics2D g2 = (Graphics2D)g;
        //g2.setStroke(new BasicStroke(5));
        if (Main.getMoves2draw() != null) {
            for (Move m : Main.getMoves2draw()) {
                g.fillOval(xSize * m.newPos.x + (xSize/4), ySize * m.newPos.y + (ySize/4), xSize/2, ySize/2);
                //g.drawRect(xSize * m.newPos.x, ySize * m.newPos.y, xSize, ySize);
            }
        }

    }

    public void refreshFigures() {
        figs_2_draw = new FigureImage[8][8];
    }

    public void resizeImgs(int w, int h) {
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                if (figs_2_draw[col][row]!=null) {
                    figs_2_draw[col][row].rescale(w, h);
                }
            }
        }
    }

    public void addFig(FigureImage b, int col, int row) {
        this.figs_2_draw[col][row] = b;
    }

    public void delFig(int col, int row) {
        this.figs_2_draw[col][row] = null;
    }
}
