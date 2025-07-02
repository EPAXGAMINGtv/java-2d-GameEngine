package de.epax.Game.Storage;

import java.io.*;

public class CookieStorage {

    private static final String SAVE_FILE = "cookiesave.txt";

    public static void saveClicks(double clicks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))) {
            writer.write(String.valueOf(clicks));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double loadClicks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
            String line = reader.readLine();
            if (line != null) {
                return Double.parseDouble(line);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
