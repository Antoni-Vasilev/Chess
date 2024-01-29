package eu.Box;

import java.awt.*;
import java.awt.image.BufferedImage;

import static eu.Box.BoxTeam.BLACK;
import static eu.Box.BoxTeam.WHITE;

public class Pawn extends Box {

    @Override
    public void draw(Graphics g, int size, int x, int y) {
        Box.drawEmpty(g, color, this, size, x, y);

        BufferedImage img = team == BLACK ? pawn_black : pawn_white;

        int offset = size / 2 - img.getWidth() / 2;
        drawObject(g, img, offset + x * size, offset + y * size);
    }

    @Override
    public Box[][] canMove(Box[][] boxes, int x, int y) {
        if (boxes[y][x].team == WHITE) {
            if (y > 0 && x > 0
                    && boxes[y - 1][x - 1].getClass() != Empty.class
                    && boxes[y - 1][x - 1].team != boxes[y][x].team) boxes[y - 1][x - 1].isCanMove = true;

            if (y > 0 && x < 7
                    && boxes[y - 1][x + 1].getClass() != Empty.class
                    && boxes[y - 1][x + 1].team != boxes[y][x].team) boxes[y - 1][x + 1].isCanMove = true;

            if (y > 0 && boxes[y - 1][x].getClass() == Empty.class && boxes[y - 1][x].team != boxes[y][x].team)
                boxes[y - 1][x].isCanMove = true;
            else return boxes;

            if (y > 1 && boxes[y - 2][x].getClass() == Empty.class && boxes[y - 2][x].team != boxes[y][x].team && isFirstMove)
                boxes[y - 2][x].isCanMove = true;
        } else {
            if (y < 7 && x < 7
                    && boxes[y + 1][x + 1].getClass() != Empty.class
                    && boxes[y + 1][x + 1].team != boxes[y][x].team) boxes[y + 1][x + 1].isCanMove = true;

            if (y < 7 && x > 0
                    && boxes[y + 1][x - 1].getClass() != Empty.class
                    && boxes[y + 1][x - 1].team != boxes[y][x].team) boxes[y + 1][x - 1].isCanMove = true;

            if (y < 7 && boxes[y + 1][x].getClass() == Empty.class && boxes[y + 1][x].team != boxes[y][x].team)
                boxes[y + 1][x].isCanMove = true;
            else return boxes;

            if (y < 6 && boxes[y + 2][x].getClass() == Empty.class && boxes[y + 2][x].team != boxes[y][x].team && isFirstMove)
                boxes[y + 2][x].isCanMove = true;
        }

        return boxes;
    }
}
