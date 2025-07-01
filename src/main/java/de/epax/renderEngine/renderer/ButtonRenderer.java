package de.epax.renderEngine.renderer;

import de.epax.inputManager.MouseInputHandler;
import de.epax.texture.Texture;
import de.epax.vector.Vector2;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ButtonRenderer {
    private static final Map<String, Boolean> wasClickedMap = new HashMap<>();

    private static String getKey(int x, int y, int width, int height) {
        return x + ":" + y + ":" + width + ":" + height;
    }

    public static boolean drawClickableButton(Graphics2D g, Texture texture, int x, int y, int width, int height, String text, Font font, Color textColor) {
        String key = getKey(x, y, width, height);
        boolean wasClicked = wasClickedMap.getOrDefault(key, false);

        Vector2 mouse = MouseInputHandler.getMousePosition();
        boolean hovered = mouse.x >= x && mouse.x <= x + width && mouse.y >= y && mouse.y <= y + height;
        boolean clicked = hovered && MouseInputHandler.isButtonPressed(1);

        if (clicked) {
            if (wasClicked) {
                clicked = false;
            } else {
                wasClickedMap.put(key, true);
            }
        } else {
            wasClickedMap.put(key, false);
        }

        float alpha = clicked ? 0.6f : (hovered ? 0.85f : 1.0f);

        Composite original = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        if (texture != null && texture.getImage() != null) {
            g.drawImage(texture.getImage(), x, y, width, height, null);
        } else {
            g.setColor(new Color(150, 150, 150));
            g.fillRect(x, y, width, height);
        }

        g.setComposite(original);

        g.setFont(font);
        g.setColor(textColor);
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int textX = x + (width - textWidth) / 2;
        int textY = y + (height + textHeight) / 2 - 4;

        g.drawString(text, textX, textY);

        return clicked;
    }

    public static boolean drawClickableButtonWithoutText(Graphics2D g, Texture texture, int x, int y, int width, int height) {
        String key = getKey(x, y, width, height);
        boolean wasClicked = wasClickedMap.getOrDefault(key, false);

        Vector2 mouse = MouseInputHandler.getMousePosition();
        boolean hovered = mouse.x >= x && mouse.x <= x + width && mouse.y >= y && mouse.y <= y + height;
        boolean clicked = hovered && MouseInputHandler.isButtonPressed(1);

        if (clicked) {
            if (wasClicked) {
                clicked = false;
            } else {
                wasClickedMap.put(key, true);
            }
        } else {
            wasClickedMap.put(key, false);
        }

        float alpha = clicked ? 0.6f : (hovered ? 0.85f : 1.0f);

        Composite original = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        if (texture != null && texture.getImage() != null) {
            g.drawImage(texture.getImage(), x, y, width, height, null);
        } else {
            g.setColor(new Color(150, 150, 150));
            g.fillRect(x, y, width, height);
        }

        g.setComposite(original);

        return clicked;
    }
}
