package de.epax.entity;

import de.epax.texture.Texture;
import de.epax.vector.Vector2;

import java.awt.*;
import java.util.List;

public abstract class Entity {

    protected Vector2 position;
    protected int width, height;
    protected Texture texture;

    // Liste aller Entities, wird z.B. vom GameManager gesetzt
    protected List<Entity> entities;

    public Entity(float x, float y, Texture texture) {
        this.position = new Vector2(x, y);
        this.texture = texture;

        if (texture != null && texture.getImage() != null) {
            this.width = texture.getImage().getWidth();
            this.height = texture.getImage().getHeight();
        }
    }

    public abstract void update(double delta);

    public void render(Graphics2D g) {
        if (texture != null && texture.getImage() != null) {
            g.drawImage(texture.getImage(), (int) position.x, (int) position.y, width, height, null);
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    public boolean collidesWith(Entity other) {
        return this.position.x < other.position.x + other.width &&
                this.position.x + this.width > other.position.x &&
                this.position.y < other.position.y + other.height &&
                this.position.y + this.height > other.position.y;
    }


    public boolean isColliding() {
        if (entities == null) return false;
        for (Entity other : entities) {
            if (other != this && collidesWith(other)) {
                return true;
            }
        }
        return false;
    }


    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }
}
