package eu.Box;

import eu.GameView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static eu.Box.BoxTeam.BLACK;
import static eu.Box.BoxTeam.NULL;

public class King extends Box {

    boolean isDetect = false;

    public King() {
    }

    public King(BoxColor color, BoxTeam team, boolean isHover, boolean isPressed, boolean isCanMove, boolean isFirstMove) {
        this.color = color;
        this.team = team;
        this.isHover = isHover;
        this.isPressed = isPressed;
        this.isCanMove = isCanMove;
        this.isFirstMove = isFirstMove;
    }

    @Override
    public void draw(Graphics g, int size, int x, int y) {
        Box.drawEmpty(g, color, this, size, x, y);

        if (isDetect) {
            g.setColor(new Color(252, 109, 109));
            g.fillRect(x * size, y * size, size, size);
        }

        BufferedImage img = team == BLACK ? king_black : king_white;

        int offset = size / 2 - img.getWidth() / 2;
        drawObject(g, img, offset + x * size, offset + y * size);
    }

    @Override
    public Box[][] canMove(Box[][] boxes, int x, int y) {
        Box me = boxes[y][x];

        for (int yy = y - 1; yy <= y + 1; yy++) {
            for (int xx = x - 1; xx <= x + 1; xx++) {
                if (yy >= 8 || xx >= 8 || xx < 0 || yy < 0) continue;
                if (boxes[yy][xx] == me) continue;

                if (boxes[yy][xx].team != me.team)
                    boxes[yy][xx].isCanMove = true;
            }
        }

        return boxes;
    }

    public static void checkKingSave(Box box, int x, int y) {
        List<Point> points = Box.listPlayersFromOtherTeam(box.team);
        ((King) GameView.boxes[y][x]).isDetect = false;

        for (Point point : points) {
            Box[][] boxes = GameView.boxes;
            boxes = boxes[point.y][point.x].canMove(boxes, point.x, point.y);

            if (boxes[y][x].isCanMove && boxes[point.y][point.x].team != boxes[y][x].team && boxes[point.y][point.x].team != NULL) {
                GameView.boxes[y][x].isCanMove = true;
                ((King) boxes[y][x]).isDetect = true;
            }
        }
    }

    private static Box[][] copy(Box[][] arr) {
        Box[][] matrix = new Box[8][8];

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Box box = arr[y][x];
                matrix[y][x] = new King(box.color, box.team, box.isHover, box.isPressed, box.isCanMove, box.isFirstMove);
            }
        }

        return matrix;
    }
}
