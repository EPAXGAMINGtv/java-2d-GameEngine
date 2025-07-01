package de.epax.renderEngine.renderer;

import de.epax.entity.Entity;

import java.awt.*;
import java.util.List;

public class EntityRenderer {

    public static void renderEntities(Graphics2D g, List<Entity> entities) {
        for (Entity e : entities) {
            e.render(g);
        }
    }
}
