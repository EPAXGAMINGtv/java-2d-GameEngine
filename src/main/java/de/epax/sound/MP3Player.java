package de.epax.sound;

import javazoom.jl.player.Player;

import java.io.InputStream;

public class MP3Player {
    private String resourcePath;
    private volatile boolean loopRunning = false;
    private Thread loopThread;
    private float volume = 1.0f;

    // Für Stoppen: aktueller Player und Stream müssen zugreifbar sein
    private volatile Player currentPlayer;
    private volatile InputStream currentStream;

    public MP3Player(String fileName) {
        this.resourcePath = "/" + fileName;
    }

    public void play(float volume) {
        setVolume(volume);
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

    public void playLoop(float volume) {
        if (loopRunning) return;
        setVolume(volume);
        loopRunning = true;
        loopThread = new Thread(() -> {
            while (loopRunning) {
                try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
                    if (is == null) {
                        System.err.println("Sound nicht gefunden: " + resourcePath);
                        loopRunning = false;
                        return;
                    }

                    currentStream = is;
                    currentPlayer = new Player(is);

                    currentPlayer.play();

                    currentPlayer = null;
                    currentStream = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    loopRunning = false;
                }
            }
        });
        loopThread.start();
    }

    public void stopLoop() {
        loopRunning = false;

        // Player stoppen, indem Stream geschlossen wird und Thread unterbrochen
        if (currentPlayer != null) {
            try {
                if (currentStream != null) {
                    currentStream.close();
                }
            } catch (Exception ignored) {}

            currentPlayer = null;
        }

        if (loopThread != null) {
            loopThread.interrupt();
            loopThread = null;
        }
    }

    private void setVolume(float volume) {
        if (volume < 0f) volume = 0f;
        if (volume > 1f) volume = 1f;
        this.volume = volume;
    }

    public float getVolume() {
        return volume;
    }
}
