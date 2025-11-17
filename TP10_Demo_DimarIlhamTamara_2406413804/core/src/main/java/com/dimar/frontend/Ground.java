package com.dimar.frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Ground {
    private float width = Gdx.graphics.getWidth();
    private float height = 50f;
    private Rectangle collider;

    public Ground() {
        collider = new Rectangle(0, 0, 2*width, height);
    }

    public boolean isColliding(Rectangle playerCollider) {
        return playerCollider.overlaps(collider);
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 1f);
        shapeRenderer.rect(collider.x, collider.y, collider.width, collider.height);
    }

    public float getHeight() {
        return height;
    }
}
