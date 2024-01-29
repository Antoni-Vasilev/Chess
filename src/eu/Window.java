package eu;

import javax.swing.*;

public class Window extends JFrame {

    public Window() {
        setTitle("Chess AI");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);

        setContentPane(new GameView());
    }
}
