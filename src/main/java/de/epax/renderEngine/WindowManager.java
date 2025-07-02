package de.epax.renderEngine;

import de.epax.texture.Texture;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class WindowManager {
    public static Color backcolor = new Color(154, 78, 143, 234);

    private static JFrame frame;
    private static Canvas canvas;
    private static BufferStrategy bufferStrategy;

    private static long lastTime;
    private static double deltaTime;

    public static float alpha = 1.0f;

    public static void createWindow(String title, int width, int height) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.BLACK);

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

    public static void setIconTexture(Texture texture) {
        if (texture == null || texture.getImage() == null) return;
        Image icon = texture.getImage();
        if (frame != null) {
            frame.setIconImage(icon);
        }
    }

    public static Graphics getGraphics() {
        if (bufferStrategy == null) return null;
        return bufferStrategy.getDrawGraphics();
    }

    public static void updateWindow() {
        if (bufferStrategy == null) return;

        bufferStrategy.show();
        Graphics g = bufferStrategy.getDrawGraphics();
        if (g != null) {
            g.dispose();
        }

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
        if (deltaTime == 0) return 0;
        return (int) (1.0 / deltaTime);
    }

    public static Canvas getCanvas() {
        return canvas;
    }

    // Zeitbasiertes Alpha-Fading (Alpha pro Sekunde)
    public static void fadeOut(float alphaPerSecond) {
        alpha -= alphaPerSecond * (float) deltaTime;
        if (alpha < 0f) alpha = 0f;
        if (alpha > 1f) alpha = 1f;
    }

    public static void resetAlpha() {
        alpha = 1.0f;
    }
}
