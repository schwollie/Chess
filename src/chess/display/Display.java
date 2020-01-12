package chess.display;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {

    public Display(int width, int height) {
        super();
        this.setResizable(false);
        this.setTitle("chess");
        this.setSize(new Dimension(width, height+28));
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
