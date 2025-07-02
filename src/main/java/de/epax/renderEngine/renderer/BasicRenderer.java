package de.epax.renderEngine.renderer;

import de.epax.renderEngine.WindowManager;
import de.epax.texture.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;



public class BasicRenderer {

    public static void drawTexture(Texture texture, int x, int y) {
        Graphics g = WindowManager.getGraphics();
        if (g == null || texture == null || texture.getImage() == null) return;
        g.drawImage(texture.getImage(), x, y, null);
    }

    public static void drawTextureWithScaling(Texture texture, int x, int y, int width, int height) {
        Graphics g = WindowManager.getGraphics();
        if (g == null || texture == null || texture.getImage() == null) return;
        g.drawImage(texture.getImage(), x, y, width, height, null);
    }

    public static void drawText(String text, int x, int y) {
        Graphics g = WindowManager.getGraphics();
        if (g == null || text == null || text.isEmpty()) return;
        g.drawString(text, x, y);
    }

    public static void drawTextWithShadow(String text, int x, int y, Color color) {
        Graphics g = WindowManager.getGraphics();
        if (g == null || text == null || text.isEmpty() || color == null) return;
        Color originalColor = g.getColor();
        g.setColor(Color.BLACK);
        g.drawString(text, x + 2, y + 2);
        g.setColor(color);
        g.drawString(text, x, y);

        g.setColor(originalColor);
    }

    public static void drawButton(Texture texture, int x, int y, int width, int height,
                                  String text, Color textColor, Font font, float alpha) {
        Graphics gRaw = WindowManager.getGraphics();
        if (!(gRaw instanceof Graphics2D)) return;
        if (alpha < 0) alpha = 0;
        if (alpha > 1) alpha = 1;

        Graphics2D g = (Graphics2D) gRaw;

        Composite originalComposite = g.getComposite();
        Color originalColor = g.getColor();
        Font originalFont = g.getFont();

        try {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

            if (texture != null && texture.getImage() != null) {
                g.drawImage(texture.getImage(), x, y, width, height, null);
            } else {
                g.setColor(new Color(100, 100, 100, Math.round(alpha * 255)));
                g.fillRect(x, y, width, height);
            }

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
        } finally {
            g.setComposite(originalComposite);
            g.setColor(originalColor);
            g.setFont(originalFont);
        }
    }

    public static void clearScreen(Color color) {
        Graphics g = WindowManager.getGraphics();
        if (g == null || color == null) return;
        g.setColor(color);
        g.fillRect(0, 0, WindowManager.getCanvas().getWidth(), WindowManager.getCanvas().getHeight());
    }

    public static void drawTextInSize(String text, int x, int y, int width, int height,
                                      Font baseFont, Color color) {
        Graphics gRaw = WindowManager.getGraphics();
        if (gRaw == null || !(gRaw instanceof Graphics2D) || text == null || text.isEmpty() || baseFont == null || color == null) return;
        Graphics2D g = (Graphics2D) gRaw;
        float baseSize = baseFont.getSize2D();
        Font font = baseFont.deriveFont(baseSize);
        FontMetrics fm = g.getFontMetrics(font);
        if (fm.stringWidth(text) == 0) return;
        float widthScale = (float) width / fm.stringWidth(text);
        float heightScale = (float) height / fm.getHeight();
        float scale = Math.min(widthScale, heightScale);
        font = baseFont.deriveFont(baseSize * scale);
        fm = g.getFontMetrics(font);
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int textX = x + (width - textWidth) / 2;
        int textY = y + (height + textHeight) / 2 - 4;
        Color originalColor = g.getColor();
        Font originalFont = g.getFont();
        g.setFont(font);
        g.setColor(color);
        g.drawString(text, textX, textY);
        g.setFont(originalFont);
        g.setColor(originalColor);
    }

    public static void setCursorTexture(Texture texture) {
        if (texture == null || texture.getImage() == null) return;
        BufferedImage image = texture.getImage();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Point hotspot = new Point(0, 0);
        Cursor cursor = toolkit.createCustomCursor(image, hotspot, "CustomCursor");
        WindowManager.getCanvas().setCursor(cursor);
    }

    public static void drawOutblendText(String text, int x, int y, Color color, Font font) {
        Graphics gRaw = WindowManager.getGraphics();
        if (gRaw == null || !(gRaw instanceof Graphics2D) || text == null || text.isEmpty() || color == null || font == null)
            return;

        Graphics2D g = (Graphics2D) gRaw;
        Composite originalComposite = g.getComposite();
        Color originalColor = g.getColor();
        Font originalFont = g.getFont();

        try {
            // Nutze Alpha aus WindowManager
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, WindowManager.alpha));
            g.setFont(font);
            g.setColor(color);
            g.drawString(text, x, y);
        } finally {
            g.setComposite(originalComposite);
            g.setColor(originalColor);
            g.setFont(originalFont);
        }
    }
}
