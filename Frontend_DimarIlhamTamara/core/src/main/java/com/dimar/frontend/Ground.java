package com.dimar.frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Ground {
    private static final float GROUND_HEIGHT = 50f;
    private Rectangle collider;
    private int maxWidth = Gdx.graphics.getWidth();
    private int maxHeight = Gdx.graphics.getHeight();

    public Ground() {
        collider = new Rectangle(0, 0, 2 * Gdx.graphics.getWidth(), GROUND_HEIGHT);
    }

    public void update(float cameraX) {
        collider.x = cameraX - Gdx.graphics.getWidth() / 2f - 500;
        collider.y = 0;
        collider.width = Gdx.graphics.getWidth();
        collider.height = GROUND_HEIGHT;
    }

    public boolean isColliding(Rectangle playerCollider) {
        return playerCollider.overlaps(collider);
    }

    public float getTopY() {
        return GROUND_HEIGHT;
    }

    public void renderShape(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.5f, 0.5f, 0.5f,  1f);
        shapeRenderer.rect(collider.x, collider.y, collider.width, collider.height);
    }
}
