package de.epax.renderEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class WindowManager {

    private static JFrame frame;
    private static Canvas canvas;
    private static BufferStrategy bufferStrategy;

    private static long lastTime;
    private static double deltaTime;

    public static void createWindow(String title, int width, int height) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();

        lastTime = System.nanoTime();
    }

    public static Graphics getGraphics() {
        return bufferStrategy.getDrawGraphics();
    }

    public static void updateWindow() {
        bufferStrategy.show();
        long now = System.nanoTime();
        deltaTime = (now - lastTime) / 1e9;
        lastTime = now;
    }

    public static void closeWindow() {
        if (frame != null) {
            frame.dispose();
        }
    }

    public static double delta() {
        return deltaTime;
    }

    public static int getFPS() {
        return (int)(1.0 / deltaTime);
    }

    public static Canvas getCanvas() {
        return canvas;
    }
}
