package eu.Box;

import java.awt.*;
import java.awt.image.BufferedImage;

import static eu.Box.BoxTeam.BLACK;

public class Queen extends Box {

    @Override
    public void draw(Graphics g, int size, int x, int y) {
        Box.drawEmpty(g, color, this, size, x, y);

        BufferedImage img = team == BLACK ? queen_black : queen_white;

        int offset = size / 2 - img.getWidth() / 2;
        drawObject(g, img, offset + x * size, offset + y * size);
    }

    @Override
    public Box[][] canMove(Box[][] boxes, int x, int y) {
        Box me = boxes[y][x];

        for (int i = 1; i < 8; i++) {
            if (y - i >= 0 && boxes[y - i][x].team != me.team) {
                boxes[y - i][x].isCanMove = true;
                if (boxes[y - i][x].getClass() != Empty.class) break;
            } else break;
        }

        for (int i = 1; i < 8; i++) {
            if (y - i >= 0 && x + i < 8 && boxes[y - i][x + i].team != me.team) {
                boxes[y - i][x + i].isCanMove = true;
                if (boxes[y - i][x + i].getClass() != Empty.class) break;
            } else break;
        }

        for (int i = 1; i < 8; i++) {
            if (x + i < 8 && boxes[y][x + i].team != me.team) {
                boxes[y][x + i].isCanMove = true;
                if (boxes[y][x + i].getClass() != Empty.class) break;
            } else break;
        }

        for (int i = 1; i < 8; i++) {
            if (y + i < 8 && x + i < 8 && boxes[y + i][x + i].team != me.team) {
                boxes[y + i][x + i].isCanMove = true;
                if (boxes[y + i][x + i].getClass() != Empty.class) break;
            } else break;
        }

        for (int i = 1; i < 8; i++) {
            if (y + i < 8 && boxes[y + i][x].team != me.team) {
                boxes[y + i][x].isCanMove = true;
                if (boxes[y + i][x].getClass() != Empty.class) break;
            } else break;
        }

        for (int i = 1; i < 8; i++) {
            if (y + i < 8 && x - i >= 0 && boxes[y + i][x - i].team != me.team) {
                boxes[y + i][x - i].isCanMove = true;
                if (boxes[y + i][x - i].getClass() != Empty.class) break;
            } else break;
        }

        for (int i = 1; i < 8; i++) {
            if (x - i >= 0 && boxes[y][x - i].team != me.team) {
                boxes[y][x - i].isCanMove = true;
                if (boxes[y][x - i].getClass() != Empty.class) break;
            } else break;
        }

        for (int i = 1; i < 8; i++) {
            if (y - i >= 0 && x - i >= 0 && boxes[y - i][x - i].team != me.team) {
                boxes[y - i][x - i].isCanMove = true;
                if (boxes[y - i][x - i].getClass() != Empty.class) break;
            } else break;
        }

        return boxes;
    }
}
