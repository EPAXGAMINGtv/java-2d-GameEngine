package de.epax.texture;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Texture {
    private BufferedImage image;

    public Texture(String resourceName) throws IOException {
        try {
            File dir = new File("src/resources");
            File file = new File(dir, resourceName + ".png");
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public BufferedImage getImage() {
        return image;
    }
}

