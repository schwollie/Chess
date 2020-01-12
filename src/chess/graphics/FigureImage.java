package chess.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FigureImage {

    private BufferedImage img;
    private BufferedImage original;
    private double[] rect;

    public FigureImage(double[] size, String filename) {
        try {
            img = ImageIO.read(getClass().getResource(filename));
            original = ImageIO.read(getClass().getResource(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        img = rescaleImage((int) size[0], (int) size[1]);
        this.rect = size;
    }

    private BufferedImage rescaleImage(int width, int height) {
        Image tmp = original.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public void rescale(int width, int height) {
        this.img = rescaleImage(width, height);
    }

    public double[] getRect() {
        return rect;
    }

    public BufferedImage getImg() {
        return this.img;
    }
}
