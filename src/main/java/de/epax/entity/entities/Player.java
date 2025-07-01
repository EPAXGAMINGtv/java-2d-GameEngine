package de.epax.entity.entities;

import de.epax.entity.Entity;
import de.epax.inputManager.KeyboardInputHandler;
import de.epax.texture.Texture;

import java.awt.event.KeyEvent;

public class Player extends Entity {

    public Player(float x, float y, Texture texture) {
        super(x, y, texture);
    }

    @Override
    public void update(double delta) {
        float speed = 200f;

        if (KeyboardInputHandler.isKeyHeld(KeyEvent.VK_W)) position.y -= speed * delta;
        if (KeyboardInputHandler.isKeyHeld(KeyEvent.VK_S)) position.y += speed * delta;
        if (KeyboardInputHandler.isKeyHeld(KeyEvent.VK_A)) position.x -= speed * delta;
        if (KeyboardInputHandler.isKeyHeld(KeyEvent.VK_D)) position.x += speed * delta;
    }
}
