package de.epax.Game.Storage;

import de.epax.Main;

import java.io.*;
import java.util.Properties;

public class ConfigManager {

    private static final String CONFIG_FILE = "config.properties";
    private static Properties props = new Properties();

    public static void loadConfig() {
        try (FileInputStream in = new FileInputStream(CONFIG_FILE)) {
            props.load(in);
            String fpsOnStr = props.getProperty("isFPSOn", "false");
            String soundOnStr = props.getProperty("isSoundOn", "true");
            String MusicOnStr = props.getProperty("isMusicOn", "false");
            Main.isFPSOn = Boolean.parseBoolean(fpsOnStr);
            Main.isSoundOn = Boolean.parseBoolean(soundOnStr);
            Main.isMusikOn = Boolean.parseBoolean(MusicOnStr);
        } catch (IOException e) {
            Main.isFPSOn = false;
            Main.isSoundOn = true;
            Main.isMusikOn = false;
        }
    }

    public static void saveConfig() {
        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            props.setProperty("isFPSOn", Boolean.toString(Main.isFPSOn));
            props.setProperty("isSoundOn", Boolean.toString(Main.isSoundOn));
            props.setProperty("isMusicOn",Boolean.toString(Main.isMusikOn));
            props.store(out, "Game config");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
