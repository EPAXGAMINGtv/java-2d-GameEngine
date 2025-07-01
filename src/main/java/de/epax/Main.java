package de.epax;

import de.epax.Game.Storage.CookieStorage;
import de.epax.Game.Storage.UpgradeStorage;
import de.epax.Game.Upgrade.Upgrade;
import de.epax.Manager.GameManager;
import de.epax.inputManager.KeyboardInputHandler;
import de.epax.inputManager.MouseInputHandler;
import de.epax.renderEngine.renderer.BasicRenderer;
import de.epax.renderEngine.renderer.ButtonRenderer;
import de.epax.renderEngine.WindowManager;
import de.epax.texture.Texture;

import java.awt.*;
import java.io.IOException;

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

    public static void main(String[] args) throws IOException {
        GameManager gameManager = new GameManager();
        WindowManager.createWindow("2D Engine Window", 1920, 1080);
        KeyboardInputHandler.attachTo(WindowManager.getCanvas());
        MouseInputHandler.attachTo(WindowManager.getCanvas());
        MouseInputHandler.setCursorVisible(true);

        Texture cookietex = new Texture("cookie");
        Texture exittex = new Texture("exit1");

        long clicks = CookieStorage.loadClicks();
        Font font = new Font("Arial", Font.BOLD, 20);
        boolean running = true;

        Upgrade clickUpgrade = UpgradeStorage.loadUpgrade("clickUpgrade");
        Upgrade priceUpgrade = UpgradeStorage.loadUpgrade("priceUpgrade");

        if (clickUpgrade == null) {
            clickUpgrade = new Upgrade("Click Power", 1000L, 10, 0);
        }

        if (priceUpgrade == null) {
            priceUpgrade = new Upgrade("Price Upgrade", 500, 0, 0);
        }

        int addsPerClick = 1 + clickUpgrade.getAddsPerClickIncrease();

        while (running) {
            if (!WindowManager.getCanvas().isDisplayable()) {
                running = false;
                continue;
            }

            Graphics gRaw = WindowManager.getGraphics();
            if (!(gRaw instanceof Graphics2D)) continue;
            Graphics2D g = (Graphics2D) gRaw;

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, WindowManager.getCanvas().getWidth(), WindowManager.getCanvas().getHeight());

            BasicRenderer.drawText("FPS: " + WindowManager.getFPS(), 10, 20);

            boolean isClickedcookie = ButtonRenderer.drawClickableButtonWithoutText(g, cookietex, 860, 440, 200, 200);
            if (isClickedcookie) {
                clicks += addsPerClick;
                System.out.println("Button wurde gedrückt!");
            }

            boolean isClickedExit = ButtonRenderer.drawClickableButtonWithoutText(g, exittex, 1800, 10, 100, 50);
            if (isClickedExit) {
                CookieStorage.saveClicks(clicks);
                UpgradeStorage.saveUpgrade("clickUpgrade", clickUpgrade);
                UpgradeStorage.saveUpgrade("priceUpgrade", priceUpgrade);
                WindowManager.closeWindow();
            }

            boolean upgradeClicked = ButtonRenderer.drawClickableButton(
                    g, new Texture("button1"), 740, 700, 450, 100,
                    clickUpgrade.getName() + " Lv." + clickUpgrade.getLevel() + " Cost: " + clickUpgrade.getCost(),
                    font, Color.WHITE
            );
            if (upgradeClicked && clickUpgrade.canBuy(clicks)) {
                clicks = clickUpgrade.buy(clicks);
                addsPerClick = 1 + clickUpgrade.getAddsPerClickIncrease();
            }

            boolean priceUpgradeClicked = ButtonRenderer.drawClickableButton(
                    g, new Texture("button1"), 740, 820, 450, 100,
                    priceUpgrade.getName() + " Lv." + priceUpgrade.getLevel() + " Cost: " + priceUpgrade.getCost(),
                    font, Color.WHITE
            );
            if (priceUpgradeClicked && priceUpgrade.canBuy(clicks)) {
                clicks = priceUpgrade.buy(clicks);

                long reduction = getPriceReductionForLevel(priceUpgrade.getLevel());
                clickUpgrade.reduceCost(reduction);
                System.out.println("ClickUpgrade Kosten um " + reduction + " reduziert!");
            }

            BasicRenderer.drawTextInSize("Cookies: " + clicks, 820, 300, 300, 150, font, Color.MAGENTA);
            BasicRenderer.drawTextInSize("Cookies Per Click: " + addsPerClick, 840, 270, 250, 100, font, Color.MAGENTA);
            BasicRenderer.drawTextInSize("Rabatt pro Upgrade: -" + getPriceReductionForLevel(priceUpgrade.getLevel()), 840, 240, 250, 100, font, Color.MAGENTA);

            gameManager.update(WindowManager.delta());
            gameManager.render();
            g.dispose();
            WindowManager.updateWindow();
        }

        CookieStorage.saveClicks(clicks);
        UpgradeStorage.saveUpgrade("clickUpgrade", clickUpgrade);
        UpgradeStorage.saveUpgrade("priceUpgrade", priceUpgrade);
        WindowManager.closeWindow();
    }
}
