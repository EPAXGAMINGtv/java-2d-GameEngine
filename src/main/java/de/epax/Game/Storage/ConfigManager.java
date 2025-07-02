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
            Main.isFPSOn = Boolean.parseBoolean(fpsOnStr);
        } catch (IOException e) {
            Main.isFPSOn = false;
        }
    }

    public static void saveConfig() {
        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            props.setProperty("isFPSOn", Boolean.toString(Main.isFPSOn));
            props.store(out, "Game config");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
