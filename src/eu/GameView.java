package eu;

import eu.Box.Box;
import eu.Box.BoxTeam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static eu.Box.BoxTeam.WHITE;

public class GameView extends JPanel implements MouseMotionListener, MouseListener {

    public static Box[][] boxes;
    Point mousePos = null;
    public static boolean isShowNotifyMessage = false;
    public static BoxTeam teamTurn = WHITE;

    public GameView() {
        Box.loadResources();
        boxes = Box.setup();

        addMouseMotionListener(this);
        addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        drawBoardBackground(g);
        drawMouseCircle(g);

        repaint();
    }

    private void drawBoardBackground(Graphics g) {
        int size = getWidth() / boxes.length;

        for (int y = 0; y < boxes.length; y++) {
            for (int x = 0; x < boxes.length; x++) {
                boxes[y][x].draw(g, size, x, y);
            }
        }
    }

    private void drawMouseCircle(Graphics g) {
        if (mousePos != null)
            g.fillOval(mousePos.x - 50, mousePos.y - 50, 100, 100);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int size = getWidth() / boxes.length;

        for (int y = 0; y < boxes.length; y++) {
            for (int x = 0; x < boxes.length; x++) {
                boxes[y][x].checkHolder(e.getPoint(), size, x, y);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        boxes = Box.move(boxes);

        for (int y = 0; y < boxes.length; y++) {
            for (int x = 0; x < boxes.length; x++) {
                boxes[y][x].checkPress(teamTurn);
            }
        }

        for (int y = 0; y < boxes.length; y++) {
            for (int x = 0; x < boxes.length; x++) {
                if (boxes[y][x].isPressed()) boxes = boxes[y][x].canMove(boxes, x, y);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
