package de.epax.sound;

import javazoom.jl.player.Player;

import java.io.InputStream;

public class MP3Player {
    private String resourcePath;

    public MP3Player(String fileName) {
        this.resourcePath = "/" + fileName;
    }

    public void play() {
        new Thread(() -> {
            try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
                if (is == null) {
                    System.err.println("Sound nicht gefunden: " + resourcePath);
                    return;
                }
                Player player = new Player(is);
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
