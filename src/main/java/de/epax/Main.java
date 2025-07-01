package de.epax;

import de.epax.Manager.GameManager;
import de.epax.entity.Entity;
import de.epax.entity.entities.Player;
import de.epax.entity.entities.PlayerWithGravity;
import de.epax.inputManager.KeyboardInputHandler;
import de.epax.inputManager.MouseInputHandler;
import de.epax.renderEngine.renderer.BasicRenderer;
import de.epax.renderEngine.renderer.ButtonRenderer;
import de.epax.renderEngine.WindowManager;
import de.epax.texture.Texture;

import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GameManager gameManager = new GameManager();
        WindowManager.createWindow("2D Engine Window", 1920, 1080);
        KeyboardInputHandler.attachTo(WindowManager.getCanvas());
        MouseInputHandler.attachTo(WindowManager.getCanvas());
        MouseInputHandler.setCursorVisible(true);
        Texture texture = new Texture("textureTest");
        Font font = new Font("Arial", Font.BOLD, 20);
        boolean running = true;
        while (running) {
            if (!WindowManager.getCanvas().isDisplayable()) {
                running = false;
                continue;
            }
            Graphics gRaw = WindowManager.getGraphics();
            if (!(gRaw instanceof Graphics2D)) continue;
            Graphics2D g = (Graphics2D) gRaw;
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, WindowManager.getCanvas().getWidth(), WindowManager.getCanvas().getHeight());
            g.setColor(Color.BLACK);
            BasicRenderer.drawText("FPS: " + WindowManager.getFPS(), 10, 20);
            boolean isClicked = ButtonRenderer.drawClickableButton(g, texture, 100, 100, 200, 60, "Click Me", font, Color.BLACK);
            if (isClicked) {
                System.out.println("Button wurde gedrückt!");
            }
            gameManager.update(WindowManager.delta());
            gameManager.render();
            g.dispose();
            WindowManager.updateWindow();
        }

        WindowManager.closeWindow();
    }
}
