package eu;

import eu.out.OutList;
import eu.timer.Timer;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame window = new Window();
        window.setVisible(true);
        window.setLocation(window.getLocationOnScreen().x - 200, window.getLocationOnScreen().y);

        new Timer(window).setVisible(true);
        new OutList(window);
    }
}
