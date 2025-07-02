package de.epax.Game.Storage;

import de.epax.Game.Upgrade.Upgrade;

import java.io.*;
import java.util.Properties;

public class UpgradeStorage {

    private static final String FILE_PATH = "upgrades.properties";

    public static Upgrade loadUpgrade(String key) {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(FILE_PATH)) {
            props.load(input);
            String levelStr = props.getProperty(key + ".level");
            String costStr = props.getProperty(key + ".cost");
            String addsStr = props.getProperty(key + ".addsPerClickIncrease");
            String name = props.getProperty(key + ".name");

            if (levelStr == null || costStr == null || addsStr == null || name == null) {
                return null;
            }

            int level = Integer.parseInt(levelStr);
            double cost = Double.parseDouble(costStr);
            double addsPerClickIncrease = Double.parseDouble(addsStr);

            return new Upgrade(name, cost, addsPerClickIncrease, level);

        } catch (FileNotFoundException e) {
            // Datei existiert noch nicht, Upgrade noch nicht gespeichert
            return null;
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void saveUpgrade(String key, Upgrade upgrade) {
        Properties props = new Properties();

        try (InputStream input = new FileInputStream(FILE_PATH)) {
            props.load(input);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        props.setProperty(key + ".name", upgrade.getName());
        props.setProperty(key + ".level", String.valueOf(upgrade.getLevel()));
        props.setProperty(key + ".cost", String.valueOf(upgrade.getCost()));
        props.setProperty(key + ".addsPerClickIncrease", String.valueOf(upgrade.getAddsPerClickIncrease()));

        try (OutputStream output = new FileOutputStream(FILE_PATH)) {
            props.store(output, "Upgrade Data");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
