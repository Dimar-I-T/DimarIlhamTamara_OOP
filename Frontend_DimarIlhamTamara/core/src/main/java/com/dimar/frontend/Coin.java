package com.dimar.frontend;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Coin {
    private Vector2 position;
    private Rectangle collider;
    private float radius = 15f;
    private boolean active;

    private float bobOffset;
    private float bobSpeed;

    public Coin(Vector2 startPosition) {
        position = startPosition;
        collider = new Rectangle(startPosition.x, startPosition.y, radius * 2, radius * 2);
    }

    public void update(float delta) {
        bobOffset = bobSpeed * delta;
        collider.setPosition(position.x, bobOffset);
        position.set(position.x, bobOffset);
    }

    public void renderShape(ShapeRenderer shapeRenderer) {
        float drawY = position.y + (float)(Math.sin(bobOffset) * 5f);
        shapeRenderer.setColor(1f, 1f, 0f,  1f);
        shapeRenderer.circle(collider.x, drawY, radius);
    }

    public boolean isColliding(Rectangle playerCollider) {
        return active && playerCollider.overlaps(collider);
    }

    public void initialize(float x, float y) {
        this.position.set(x, y);
        this.collider.setPosition(x, y);
    }

    public void setActive(boolean newBool) {
        this.active = newBool;
    }
}
