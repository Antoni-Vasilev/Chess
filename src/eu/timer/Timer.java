package eu.timer;

import javax.swing.*;
import java.awt.*;

public class Timer extends JFrame {

    public static long startTime = 0;

    public Timer(JFrame frame) {
        setUndecorated(true);
        setSize(60, 30);
        setLayout(new BorderLayout());
        setLocation(frame.getLocationOnScreen().x - 70, frame.getLocationOnScreen().y);

        startTime = System.currentTimeMillis();

        JLabel time = new JLabel("Text", SwingConstants.CENTER);
        add(time);

        new Thread(() -> {
            while (true) {
                time.setText(calc(System.currentTimeMillis() - startTime));
            }
        }).start();
    }

    public String calc(long time) {
        long mill = time / 100;
        long sec = 0, min = 0;

        while (mill >= 10) {
            mill -= 10;
            sec++;
        }

        while (sec >= 60) {
            sec -= 60;
            min++;
        }

        return min + ":" + sec + "." + mill;
    }
}
