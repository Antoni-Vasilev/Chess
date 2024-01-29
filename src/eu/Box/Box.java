package eu.Box;

import eu.GameView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static eu.Box.BoxColor.DARK;
import static eu.Box.BoxColor.LIGHT;
import static eu.Box.BoxTeam.*;

public abstract class Box {

    protected BoxColor color;
    protected BoxTeam team;
    protected boolean isHover = false;
    protected boolean isPressed = false;
    public boolean isCanMove = false;
    protected boolean isFirstMove = true;
    private static Box[] outList = new Box[0];

    public Box() {

    }

    public Box(BoxColor color, BoxTeam team, boolean isHover, boolean isPressed, boolean isCanMove, boolean isFirstMove) {
        this.color = color;
        this.team = team;
        this.isHover = isHover;
        this.isPressed = isPressed;
        this.isCanMove = isCanMove;
        this.isFirstMove = isFirstMove;
    }

    public abstract void draw(Graphics g, int size, int x, int y);

    public void checkHolder(Point mousePos, int size, int x, int y) {
        isHover = mousePos.x > x * size && mousePos.x < (x * size) + size
                && mousePos.y > y * size && mousePos.y < (y * size) + size;
    }

    public void checkPress(BoxTeam team) {
        isCanMove = false;
        isPressed = false;

        if (getClass() == Empty.class) return;
        if (this.team != team) return;
        isPressed = isHover;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public static void addOut(Box box) {
        outList = Arrays.copyOf(outList, outList.length + 1);
        outList[outList.length - 1] = box;
    }

    /* TODO: STATIC */

    public static Box[][] setup() {
        // Build board
        Box[][] boxes = new Box[][]{
                {new Rook(), new Horse(), new Bishop(), new Queen(), new King(), new Bishop(), new Horse(), new Rook()},
                {new Pawn(), new Pawn(), new Pawn(), new Pawn(), new Pawn(), new Pawn(), new Pawn(), new Pawn()},

                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},

                {new Pawn(), new Pawn(), new Pawn(), new Pawn(), new Pawn(), new Pawn(), new Pawn(), new Pawn()},
                {new Rook(), new Horse(), new Bishop(), new Queen(), new King(), new Bishop(), new Horse(), new Rook()},
        };

        // Set board colors
        int index = 0;
        for (int y = 0; y < boxes.length; y++) {
            for (int x = 0; x < boxes.length; x++) {
                if (index % 2 == 0) boxes[y][x].color = LIGHT;
                else boxes[y][x].color = DARK;
                index++;
            }
            index++;
        }

        // Set board teams
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < boxes.length; x++) {
                boxes[y][x].team = BLACK;
            }
        }

        for (int y = 2; y < 6; y++) {
            for (int x = 0; x < boxes.length; x++) {
                boxes[y][x].team = BoxTeam.NULL;
            }
        }

        for (int y = 6; y < 8; y++) {
            for (int x = 0; x < boxes.length; x++) {
                boxes[y][x].team = BoxTeam.WHITE;
            }
        }

        return boxes;
    }

    public static void loadResources() {
        try {
            pawn_black = ImageIO.read(Box.class.getResourceAsStream("/pawn/pawn-black.png"));
            pawn_white = ImageIO.read(Box.class.getResourceAsStream("/pawn/pawn-white.png"));
            rook_black = ImageIO.read(Box.class.getResourceAsStream("/rook/rook-black.png"));
            rook_white = ImageIO.read(Box.class.getResourceAsStream("/rook/rook-white.png"));
            horse_black = ImageIO.read(Box.class.getResourceAsStream("/horse/horse-black.png"));
            horse_white = ImageIO.read(Box.class.getResourceAsStream("/horse/horse-white.png"));
            bishop_black = ImageIO.read(Box.class.getResourceAsStream("/bishop/bishop-black.png"));
            bishop_white = ImageIO.read(Box.class.getResourceAsStream("/bishop/bishop-white.png"));
            queen_black = ImageIO.read(Box.class.getResourceAsStream("/queen/queen-black.png"));
            queen_white = ImageIO.read(Box.class.getResourceAsStream("/queen/queen-white.png"));
            king_black = ImageIO.read(Box.class.getResourceAsStream("/king/king-black.png"));
            king_white = ImageIO.read(Box.class.getResourceAsStream("/king/king-white.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Box[][] move(Box[][] boxes) {
        Box hover = null;
        Box pressed = null;
        Point hPos = null;
        Point pPos = null;

        for (int y = 0; y < boxes.length; y++) {
            for (int x = 0; x < boxes.length; x++) {
                if (boxes[y][x].isHover) {
                    hover = boxes[y][x];
                    hPos = new Point(x, y);
                }

                if (boxes[y][x].isPressed) {
                    pressed = boxes[y][x];
                    pPos = new Point(x, y);
                }
            }
        }

        if (hover != null && pressed != null && hover.isCanMove && (hover.getClass() == Empty.class || hover.team != pressed.team)) {
            if (hover.getClass() != Empty.class) addOut(hover);
            if (pressed.getClass() == Pawn.class && (hPos.y == 0 || hPos.y == 7)) System.out.println("Find end");

            Box saveHover = boxes[hPos.y][hPos.x];
            Box savePressed = boxes[pPos.y][pPos.x];

            pressed.color = hover.color;
            boxes[hPos.y][hPos.x] = pressed;

            Empty empty = new Empty();
            empty.color = setup()[pPos.y][pPos.x].color;
            boxes[pPos.y][pPos.x] = empty;

            for (int y = 0; y < boxes.length; y++) {
                for (int x = 0; x < boxes.length; x++) {
                    if (boxes[y][x].getClass() == King.class) {
                        King.checkKingSave(boxes[y][x], x, y);
                        if (((King) boxes[y][x]).isDetect && boxes[y][x].team == boxes[hPos.y][hPos.x].team) {
                            boxes[hPos.y][hPos.x] = saveHover;

                            Box box = savePressed;
                            box.color = setup()[pPos.y][pPos.x].color;
                            boxes[pPos.y][pPos.x] = box;

                            GameView.teamTurn = GameView.teamTurn == BLACK ? WHITE : BLACK;
                        }
                    }
                }
            }

            if (hover.isFirstMove) boxes[hPos.y][hPos.x].isFirstMove = false;
            GameView.teamTurn = GameView.teamTurn == BLACK ? WHITE : BLACK;
        }

        return boxes;
    }

    public static List<Point> listPlayersFromOtherTeam(BoxTeam myTeam) {
        List<Point> points = new ArrayList<>();

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (GameView.boxes[y][x].team != myTeam && GameView.boxes[y][x].team != NULL)
                    points.add(new Point(x, y));
            }
        }

        return points;
    }

    public abstract Box[][] canMove(Box[][] boxes, int x, int y);

    protected static void drawEmpty(Graphics g, BoxColor color, Box box, int size, int x, int y) {
        if (!box.isCanMove) {
            if (!box.isPressed) {
                if (color == DARK) g.setColor(box.isHover ? bg_dark_hover : bg_dark);
                else g.setColor(box.isHover ? bg_white_hover : bg_white);
            } else {
                g.setColor(box.isHover ? bg_dark_pressed_hover : bg_dark_pressed);
            }
        } else {
            if (box.getClass() == Empty.class) g.setColor(new Color(188, 255, 143));
            else g.setColor(new Color(255, 153, 85));
        }

        g.fillRect(x * size, y * size, size, size);
    }

    protected void drawObject(Graphics g, BufferedImage img, int xx, int yy) {
//        for (int y = 0; y < img.getHeight(); y++) {
//            for (int x = 0; x < img.getWidth(); x++) {
//                g.setColor(new Color(img.getRGB(x, y), true));
//                g.fillRect(x + xx, y + yy, 1, 1);
//            }
//        }

        g.drawImage(img, xx, yy, null);
    }


    /* TODO: RESOURCES */

    protected static BufferedImage pawn_black;
    protected static BufferedImage pawn_white;

    protected static BufferedImage rook_black;
    protected static BufferedImage rook_white;

    protected static BufferedImage horse_black;
    protected static BufferedImage horse_white;

    protected static BufferedImage bishop_black;
    protected static BufferedImage bishop_white;

    protected static BufferedImage queen_black;
    protected static BufferedImage queen_white;

    protected static BufferedImage king_black;
    protected static BufferedImage king_white;

    private static final Color bg_dark = Color.ORANGE;
    private static final Color bg_dark_hover = new Color(205, 150, 0);
    private static final Color bg_white = new Color(255, 236, 149);
    private static final Color bg_white_hover = new Color(205, 186, 99);
    private static final Color bg_dark_pressed = new Color(236, 33, 40);
    private static final Color bg_dark_pressed_hover = new Color(186, 0, 0);
}
