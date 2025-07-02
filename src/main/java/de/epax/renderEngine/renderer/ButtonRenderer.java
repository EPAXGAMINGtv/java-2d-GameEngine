package de.epax.renderEngine.renderer;

import de.epax.Main;
import de.epax.inputManager.MouseInputHandler;
import de.epax.sound.MP3Player;
import de.epax.texture.Texture;
import de.epax.vector.Vector2;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ButtonRenderer {
    private static MP3Player clickSoundPlayer = new MP3Player("click.mp3");
    private static final Map<String, Boolean> wasClickedMap = new HashMap<>();
    private static final Map<String, Boolean> toggleStateMap = new HashMap<>();

    private static String getKey(int x, int y, int width, int height) {
        return x + ":" + y + ":" + width + ":" + height;
    }

    private static boolean isClicked(String key, boolean hovered) {
        boolean wasClicked = wasClickedMap.getOrDefault(key, false);
        boolean pressed = hovered && MouseInputHandler.isButtonPressed(1);

        if (pressed) {
            if (!wasClicked) {
                if (clickSoundPlayer != null) {
                    if (Main.isSoundOn){
                        clickSoundPlayer.play();
                    }

                }
                wasClickedMap.put(key, true);
                return true;
            }
        } else {
            wasClickedMap.put(key, false);
        }
        return false;
    }

    private static float getAlpha(boolean clicked, boolean hovered) {
        if (clicked) return 0.6f;
        if (hovered) return 0.85f;
        return 1.0f;
    }

    private static void drawButtonBackground(Graphics2D g, Texture texture, int x, int y, int width, int height, float alpha) {
        Composite original = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        if (texture != null && texture.getImage() != null) {
            g.drawImage(texture.getImage(), x, y, width, height, null);
        } else {
            g.setColor(new Color(50, 50, 50));
            g.fillRoundRect(x, y, width, height, 20, 20);
        }

        g.setComposite(original);
    }

    public static boolean drawToggleButton(Graphics2D g, Texture toggleOffTex, Texture toggleOnTex, int x, int y, int width, int height, boolean initialState) {
        String key = getKey(x, y, width, height);
        boolean currentState = toggleStateMap.getOrDefault(key, initialState);

        Vector2 mouse = MouseInputHandler.getMousePosition();
        boolean hovered = mouse.x >= x && mouse.x <= x + width && mouse.y >= y && mouse.y <= y + height;

        boolean clicked = isClicked(key, hovered);

        if (clicked) {
            currentState = !currentState;
            toggleStateMap.put(key, currentState);
        }

        float alpha = getAlpha(clicked, hovered);
        Texture textureToUse = currentState ? toggleOnTex : toggleOffTex;
        drawButtonBackground(g, textureToUse, x, y, width, height, alpha);

        return currentState;
    }

    public static boolean drawClickableButton(Graphics2D g, Texture texture, int x, int y, int width, int height, String text, Font font, Color textColor) {
        Vector2 mouse = MouseInputHandler.getMousePosition();
        boolean hovered = mouse.x >= x && mouse.x <= x + width && mouse.y >= y && mouse.y <= y + height;

        String key = getKey(x, y, width, height);
        boolean clicked = isClicked(key, hovered);

        float alpha = getAlpha(clicked, hovered);
        drawButtonBackground(g, texture, x, y, width, height, alpha);

        if (text != null && !text.isEmpty()) {
            g.setFont(font);
            g.setColor(textColor);
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getAscent();
            int textX = x + (width - textWidth) / 2;
            int textY = y + (height + textHeight) / 2 - 4;
            g.drawString(text, textX, textY);
        }

        return clicked;
    }

    public static boolean drawClickableButtonWithoutText(Graphics2D g, Texture texture, int x, int y, int width, int height) {
        Vector2 mouse = MouseInputHandler.getMousePosition();
        boolean hovered = mouse.x >= x && mouse.x <= x + width && mouse.y >= y && mouse.y <= y + height;

        String key = getKey(x, y, width, height);
        boolean clicked = isClicked(key, hovered);

        float alpha = getAlpha(clicked, hovered);
        drawButtonBackground(g, texture, x, y, width, height, alpha);

        return clicked;
    }
}