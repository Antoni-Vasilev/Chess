package eu.Box;

import java.awt.*;
import java.awt.image.BufferedImage;

import static eu.Box.BoxTeam.BLACK;

public class Horse extends Box {
    @Override
    public void draw(Graphics g, int size, int x, int y) {
        Box.drawEmpty(g, color, this, size, x, y);

        BufferedImage img = team == BLACK ? horse_black : horse_white;

        int offset = size / 2 - img.getWidth() / 2;
        drawObject(g, img, offset + x * size, offset + y * size);
    }

    @Override
    public Box[][] canMove(Box[][] boxes, int x, int y) {
        Box me = boxes[y][x];

        if (y - 2 >= 0 && x + 1 < 8 && boxes[y - 2][x + 1].team != me.team)
            boxes[y - 2][x + 1].isCanMove = true;

        if (y - 2 >= 0 && x - 1 >= 0 && boxes[y - 2][x - 1].team != me.team)
            boxes[y - 2][x - 1].isCanMove = true;

        if (y - 1 >= 0 && x + 2 < 8 && boxes[y - 1][x + 2].team != me.team)
            boxes[y - 1][x + 2].isCanMove = true;

        if (y - 1 >= 0 && x - 2 >= 0 && boxes[y - 1][x - 2].team != me.team)
            boxes[y - 1][x - 2].isCanMove = true;

        if (y + 2 < 8 && x + 1 < 8 && boxes[y + 2][x + 1].team != me.team)
            boxes[y + 2][x + 1].isCanMove = true;

        if (y + 2 < 8 && x - 1 >= 0 && boxes[y + 2][x - 1].team != me.team)
            boxes[y + 2][x - 1].isCanMove = true;

        if (y + 1 < 8 && x + 2 < 8 && boxes[y + 1][x + 2].team != me.team)
            boxes[y + 1][x + 2].isCanMove = true;

        if (y + 1 < 8 && x - 2 >= 0 && boxes[y + 1][x - 2].team != me.team)
            boxes[y + 1][x - 2].isCanMove = true;

        return boxes;
    }
}
