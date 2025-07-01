package de.epax.renderEngine.renderer;

import de.epax.renderEngine.WindowManager;
import de.epax.texture.Texture;

import java.awt.*;

public class BasicRenderer {

    public static  void drawTexture(Texture texture, int x, int y){
        Graphics g = WindowManager.getGraphics();
        if (texture != null && texture.getImage() != null) {
            g.drawImage(texture.getImage(), x, y, null);
        }
    }

    public static void drawTextureWithScaling(Texture texture, int x, int y, int width, int height){
        Graphics g = WindowManager.getGraphics();
        if (texture != null && texture.getImage() != null) {
            g.drawImage(texture.getImage(), x, y, width, height, null);
        }
    }

    public static void drawText(String text, int x, int y){
        Graphics g = WindowManager.getGraphics();
        if (text != null && text.length() > 0) {
            g.drawString(text, x, y);
        }
    }

  public static  void drawTextWithShadow(String text, int x, int y, Color color){
        Graphics g = WindowManager.getGraphics();
      if (text != null && text.length() > 0) {
          g.setColor(color);
          g.drawString(text, x + 2, y + 2);
          g.setColor(color);
          g.drawString(text, x, y);

          g.dispose();
      }
  }

    public static void drawButton(Texture texture, int x, int y, int width, int height, String text, Color textColor, Font font, float alpha) {
        Graphics gRaw = WindowManager.getGraphics();
        if (!(gRaw instanceof Graphics2D)) return;
        Graphics2D g = (Graphics2D) gRaw;
        Composite original = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        if (texture != null && texture.getImage() != null) {
            g.drawImage(texture.getImage(), x, y, width, height, null);
        } else {
            g.setColor(new Color(100, 100, 100, (int)(alpha * 255)));
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

        g.setComposite(original);
        g.dispose();
    }

    public static void clearScreen(Color color) {
        Graphics g = WindowManager.getGraphics();
        if (g != null) {
            g.setColor(color);
            g.fillRect(0, 0, WindowManager.getCanvas().getWidth(), WindowManager.getCanvas().getHeight());
            g.dispose();
        }
    }

    public static void drawTextInSize( String text, int x, int y, int width, int height, Font baseFont, Color color) {
        Graphics2D g = (Graphics2D) WindowManager.getGraphics();
        float fontSize = baseFont.getSize2D();
        Font font = baseFont.deriveFont(fontSize);
        FontMetrics fm = g.getFontMetrics(font);
        float widthScale = (float) width / fm.stringWidth(text);
        float heightScale = (float) height / fm.getHeight();
        float scale = Math.min(widthScale, heightScale);
        font = baseFont.deriveFont(fontSize * scale);
        fm = g.getFontMetrics(font);
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();

        int textX = x + (width - textWidth) / 2;
        int textY = y + (height + textHeight) / 2 - 4;

        g.setFont(font);
        g.setColor(color);
        g.drawString(text, textX, textY);
    }


}
