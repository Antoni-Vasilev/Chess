package eu.Box;

import java.awt.*;

public class Empty extends Box {

    @Override
    public void draw(Graphics g, int size, int x, int y) {
        Box.drawEmpty(g, color, this, size, x, y);
    }

    @Override
    public Box[][] canMove(Box[][] boxes, int x, int y) {
        return boxes;
    }
}
