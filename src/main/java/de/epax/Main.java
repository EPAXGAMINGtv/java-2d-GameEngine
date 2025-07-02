package de.epax;

import de.epax.Game.Storage.ConfigManager;
import de.epax.Game.Storage.CookieStorage;
import de.epax.Game.Storage.UpgradeStorage;
import de.epax.Game.Upgrade.Upgrade;
import de.epax.Manager.GameManager;
import de.epax.inputManager.KeyboardInputHandler;
import de.epax.inputManager.MouseInputHandler;
import de.epax.renderEngine.renderer.BasicRenderer;
import de.epax.renderEngine.renderer.ButtonRenderer;
import de.epax.renderEngine.WindowManager;
import de.epax.sound.MP3Player;
import de.epax.texture.Texture;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class Main {

    public static long getPriceReductionForLevel(long level) {
        if (level <= 0) return 0L;
        double base = 100;
        double multiplier = 1.5;
        double reduction = base * Math.pow(multiplier, level - 1);
        if (reduction > Long.MAX_VALUE) {
            return Long.MAX_VALUE;
        }
        return (long) reduction;
    }

    public static boolean isinSettings = false;
    public static boolean isFPSOn = true;
    public static boolean isSoundOn = true;
    public static boolean isMusikOn = true;
    public static int x;
    public static int y;

    public static void main(String[] args) throws IOException {
        Random random = new Random();

        GameManager gameManager = new GameManager();
        WindowManager.createWindow("NeonEPAXClicker", 1920, 1080);
        KeyboardInputHandler.attachTo(WindowManager.getCanvas());
        MouseInputHandler.attachTo(WindowManager.getCanvas());
        MouseInputHandler.setCursorVisible(true);

        Texture toggleOffTex = new Texture("switchoff");
        Texture toggleOnTex = new Texture("switchon");
        Texture shopExt = new Texture("shopext");
        Texture cookietex = new Texture("cookieneon1");
        Texture exittex = new Texture("exit1");
        Texture settingsTex = new Texture("settings");
        Texture button1 = new Texture("button1");
        Texture panel = new Texture("panel");
        ConfigManager.loadConfig();
        WindowManager.setIconTexture(cookietex);
        double clicks = CookieStorage.loadClicks();
        Font font = new Font("Arial", Font.BOLD, 20);
        boolean running = true;

        Upgrade clickUpgrade = UpgradeStorage.loadUpgrade("clickUpgrade");
        Upgrade priceUpgrade = UpgradeStorage.loadUpgrade("priceUpgrade");

        if (clickUpgrade == null) {
            clickUpgrade = new Upgrade("Click Power", 100L, 10, 0);
        }
        if (priceUpgrade == null) {
            priceUpgrade = new Upgrade("Price Upgrade", 50, 0, 0);
        }

        double addsPerClick = 1 + clickUpgrade.getAddsPerClickIncrease();
        BasicRenderer.setCursorTexture(new Texture("cursor"));
        MP3Player backmusik = new MP3Player("gameback.mp3");
        if (isMusikOn) {
            backmusik.playLoop(0.1f);
        }
        WindowManager.alpha = 0f;
        String fadeText = "";
        boolean showFadeText = false;

        boolean oldIsMusikOn = isMusikOn;

        while (running) {
            if (!WindowManager.getCanvas().isDisplayable()) {
                running = false;
            }

            Graphics gRaw = WindowManager.getGraphics();
            if (!(gRaw instanceof Graphics2D)) continue;
            Graphics2D g = (Graphics2D) gRaw;

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, WindowManager.getCanvas().getWidth(), WindowManager.getCanvas().getHeight());

            Color FPSColor = new Color(225, 12, 193, 255);

            boolean isClickedcookie = ButtonRenderer.drawClickableButtonWithoutText(g, cookietex, 830, 400, 300, 300);
            if (isClickedcookie) {
                clicks += addsPerClick;
                fadeText = "+" + addsPerClick;
                showFadeText = true;
                x = random.nextInt(WindowManager.getCanvas().getWidth());
                y = random.nextInt(WindowManager.getCanvas().getHeight());
                WindowManager.alpha = 1.0f;
            }

            boolean isClickedSettings = ButtonRenderer.drawClickableButton(g, settingsTex, 0, 1000, 70, 70, "", font, FPSColor);
            if (isClickedSettings) {
                if (!isinSettings) {
                    isinSettings = true;
                } else {
                    ConfigManager.saveConfig();
                    isinSettings = false;
                    BasicRenderer.clearScreen(Color.black);
                }
            }

            boolean isClickedExit = ButtonRenderer.drawClickableButtonWithoutText(g, exittex, 1800, 10, 100, 50);
            if (isClickedExit) {
                CookieStorage.saveClicks(clicks);
                UpgradeStorage.saveUpgrade("clickUpgrade", clickUpgrade);
                UpgradeStorage.saveUpgrade("priceUpgrade", priceUpgrade);
                ConfigManager.saveConfig();
                WindowManager.closeWindow();
                System.exit(0);
            }

            boolean upgradeClicked = ButtonRenderer.drawClickableButton(
                    g, button1, 670, 700, 600, 100,
                    clickUpgrade.getName() + " Lv." + clickUpgrade.getLevel() + " Cost: " + clickUpgrade.getCost(),
                    font, Color.WHITE
            );
            if (upgradeClicked && clickUpgrade.canBuy(clicks)) {
                clicks = clickUpgrade.buy(clicks);
                addsPerClick = 1 + clickUpgrade.getAddsPerClickIncrease();
            }

            boolean priceUpgradeClicked = ButtonRenderer.drawClickableButton(
                    g, button1, 670, 820, 600, 100,
                    priceUpgrade.getName() + " Lv." + priceUpgrade.getLevel() + " Cost: " + priceUpgrade.getCost(),
                    font, Color.WHITE
            );
            if (priceUpgradeClicked && priceUpgrade.canBuy(clicks)) {
                clicks = priceUpgrade.buy(clicks);
                long reduction = getPriceReductionForLevel(priceUpgrade.getLevel());
                clickUpgrade.reduceCost(reduction);
            }

            BasicRenderer.drawTextInSize("Cookies: " + clicks, 820, 300, 300, 150, font, Color.MAGENTA);
            BasicRenderer.drawTextInSize("Cookies Per Click: " + addsPerClick, 840, 270, 250, 100, font, Color.MAGENTA);
            BasicRenderer.drawTextInSize("Rabatt auf ClickUpgrade: -" + getPriceReductionForLevel(priceUpgrade.getLevel()), 840, 240, 250, 100, font, Color.MAGENTA);

            if (isFPSOn) {
                BasicRenderer.drawTextInSize("FPS: " + WindowManager.getFPS(), 0, 0, 100, 50, font, FPSColor);
            }

            if (isinSettings) {
                boolean isExitButtonShop = ButtonRenderer.drawClickableButton(g, shopExt, 1200, 87, 50, 65, "", font, Color.WHITE);
                BasicRenderer.drawTexture(panel, 600, 60);
                BasicRenderer.drawTextInSize("Settings:", 900, 130, 100, 100, font, Color.MAGENTA);
                BasicRenderer.drawTextInSize("FPS:", 750, 200, 50, 50, font, Color.MAGENTA);
                BasicRenderer.drawTextInSize("Sound:", 750, 250, 50, 50, font, Color.MAGENTA);
                BasicRenderer.drawTextInSize("Musik:", 750, 295, 50, 50, font, Color.MAGENTA);
                isFPSOn = ButtonRenderer.drawToggleButton(g, toggleOffTex, toggleOnTex, 800, 210, 70, 30, isFPSOn);
                isSoundOn = ButtonRenderer.drawToggleButton(g, toggleOffTex, toggleOnTex, 800, 260, 70, 30, isSoundOn);

                boolean newIsMusikOn = ButtonRenderer.drawToggleButton(g, toggleOffTex, toggleOnTex, 800, 300, 70, 30, isMusikOn);
                if (newIsMusikOn != isMusikOn) {
                    isMusikOn = newIsMusikOn;
                    if (isMusikOn) {
                        backmusik.playLoop(0.1f);
                    } else {
                        backmusik.stopLoop();
                    }
                }

                if (isExitButtonShop) {
                    isinSettings = false;
                    ConfigManager.saveConfig();
                }
            }

            if (showFadeText) {
                BasicRenderer.drawOutblendText(fadeText, x, y, Color.MAGENTA, new Font("Arial", Font.BOLD, 24));
                WindowManager.fadeOut(1.5f);
                if (WindowManager.alpha <= 0) {
                    showFadeText = false;
                }
            }

            gameManager.update(WindowManager.delta());
            gameManager.render();

            WindowManager.updateWindow();
        }

        ConfigManager.saveConfig();
        CookieStorage.saveClicks(clicks);
        UpgradeStorage.saveUpgrade("clickUpgrade", clickUpgrade);
        UpgradeStorage.saveUpgrade("priceUpgrade", priceUpgrade);
        WindowManager.closeWindow();
    }
}
