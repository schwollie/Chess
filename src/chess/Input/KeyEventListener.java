package chess.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Queue;


public class KeyEventListener implements KeyListener {

    public Queue<KeyEvent> events;

    public KeyEventListener() {
        events = new LinkedList<>();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        events.add(e);
    }
}

