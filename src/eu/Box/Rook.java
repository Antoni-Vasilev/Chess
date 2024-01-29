package eu.Box;

import java.awt.*;
import java.awt.image.BufferedImage;

import static eu.Box.BoxTeam.BLACK;

public class Rook extends Box {

    @Override
    public void draw(Graphics g, int size, int x, int y) {
        Box.drawEmpty(g, color, this, size, x, y);

        BufferedImage img = team == BLACK ? rook_black : rook_white;

        int offset = size / 2 - img.getWidth() / 2;
        drawObject(g, img, offset + x * size, offset + y * size);
    }

    @Override
    public Box[][] canMove(Box[][] boxes, int x, int y) {
        for (int xx = x + 1; xx < 8; xx++) {
            if (boxes[y][xx].getClass() == Empty.class) boxes[y][xx].isCanMove = true;
            else if (boxes[y][xx].team != boxes[y][x].team) {
                boxes[y][xx].isCanMove = true;
                break;
            }
            else break;
        }

        for (int xx = x - 1; xx >= 0; xx--) {
            if (boxes[y][xx].getClass() == Empty.class) boxes[y][xx].isCanMove = true;
            else if (boxes[y][xx].team != boxes[y][x].team) {
                boxes[y][xx].isCanMove = true;
                break;
            }
            else break;
        }

        for (int yy = y + 1; yy < 8; yy++) {
            if (boxes[yy][x].getClass() == Empty.class) boxes[yy][x].isCanMove = true;
            else if (boxes[yy][x].team != boxes[y][x].team) {
                boxes[yy][x].isCanMove = true;
                break;
            }
            else break;
        }

        for (int yy = y - 1; yy >= 0; yy--) {
            if (boxes[yy][x].getClass() == Empty.class) boxes[yy][x].isCanMove = true;
            else if (boxes[yy][x].team != boxes[y][x].team) {
                boxes[yy][x].isCanMove = true;
                break;
            }
            else break;
        }

        return boxes;
    }
}
