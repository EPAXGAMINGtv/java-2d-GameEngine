package de.epax.Manager;

import de.epax.entity.Entity;
import de.epax.renderEngine.WindowManager;
import de.epax.renderEngine.renderer.EntityRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private List<Entity> entities = new ArrayList<>();

    public void addEntity(Entity e) {
        entities.add(e);
    }


    public List<Entity> getEntities() {
        return entities;
    }

    public void update(double delta) {
        for (Entity e : entities) {
            e.update(delta);
        }
    }

    public void render() {
        Graphics g = WindowManager.getGraphics();
        if (g instanceof Graphics2D) {
            EntityRenderer.renderEntities((Graphics2D) g, entities);
        }
        g.dispose();
    }
}
