package de.epax.entity.entities;

import de.epax.entity.Entity;
import de.epax.inputManager.KeyboardInputHandler;
import de.epax.texture.Texture;

import java.awt.event.KeyEvent;

public class TestEntity extends Entity {



    public TestEntity(float x, float y, Texture texture) {
        super(x, y, texture);
    }

    @Override
    public void update(double delta) {
        float speed = 200f;

    }
}
