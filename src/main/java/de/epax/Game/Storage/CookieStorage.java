package de.epax.Game.Storage;

import java.io.*;

public class CookieStorage {

    private static final String SAVE_FILE = "cookiesave.txt";

    public static void saveClicks(long clicks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))) {
            writer.write(String.valueOf(clicks));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long loadClicks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
            String line = reader.readLine();
            if (line != null) {
                return Integer.parseInt(line);
            }
        } catch (IOException | NumberFormatException e) {
            // Datei existiert evtl. nicht oder falsches Format, dann 0 zurückgeben
        }
        return 0;
    }
}
