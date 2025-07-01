package de.epax.vector;

public class Vector2 {
    public float x, y;

    public Vector2() {
        this(0,0);
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public String toString() {
        return "Vector2(" + x + ", " + y + ")";
    }
}
