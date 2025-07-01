package de.epax.entity.entities;

import de.epax.entity.Entity;
import de.epax.inputManager.KeyboardInputHandler;
import de.epax.texture.Texture;

import java.awt.event.KeyEvent;
import java.util.List;

public class PlayerWithGravity extends Entity {

    private List<Entity> entities;

    public PlayerWithGravity(float x, float y, Texture texture, List<Entity> entities) {
        super(x, y, texture);
        this.entities = entities;
    }

    @Override
    public void update(double delta) {
        float speed = 200f;
        float gravity = 0.1f;
        boolean colliding = false;

        for (Entity other : entities) {
            if (other != this && this.collidesWith(other)) {
                colliding = true;
                break;
            }
        }

        if (!colliding) {

            colliding = false;
        }else {
            colliding = true;
        }
        if (!colliding) {
            position.y += gravity;
        }

        if (KeyboardInputHandler.isKeyHeld(KeyEvent.VK_A)) position.x -= speed * delta;
        if (KeyboardInputHandler.isKeyHeld(KeyEvent.VK_D)) position.x += speed * delta;
    }
}
