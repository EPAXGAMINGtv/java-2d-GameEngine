package de.epax.texture;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

public class Texture {
    private BufferedImage image;

    public Texture(String resourceName) throws IOException {
        String resourcePath = "/" + resourceName + ".png";  // z.B. "/cookie.png"
        try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IOException("Texture nicht gefunden: " + resourcePath);
            }
            image = ImageIO.read(is);
        }
    }

    public BufferedImage getImage() {
        return image;
    }
}
