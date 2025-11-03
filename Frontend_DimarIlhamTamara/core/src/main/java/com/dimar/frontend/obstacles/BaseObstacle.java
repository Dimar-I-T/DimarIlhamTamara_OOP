package com.dimar.frontend.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class BaseObstacle {
    private Vector2 position;
    Rectangle collider;
    float length;
    final float WIDTH = 10f;
    private boolean active = false;

    public BaseObstacle(Vector2 startPosition, int length) {
        this.position = new Vector2(startPosition);
        this.length = length;
        this.collider = new Rectangle();
        updateCollider();
    }

    public void initialize(Vector2 startPosition, int length) {
        this.position.set(startPosition);
        this.length = length;
        updateCollider();
    }

    public void render(ShapeRenderer shapeRenderer) {
        if (active) {
            drawShape(shapeRenderer);
        }
    }

    public void update() {
        updateCollider();
    }

    public boolean isActive() {
        return active;
    }

    public boolean isColliding(Rectangle playerCollider) {
        return playerCollider.overlaps(collider);
    }

    public boolean isOffScreenCamera(float cameraLeftEdge) {
        return position.x + getRenderWidth() < cameraLeftEdge;
    }

    public abstract void updateCollider();

    public abstract void drawShape(ShapeRenderer shapeRenderer);

    public abstract float getRenderWidth();

    public abstract float getRenderHeight();

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    public Vector2 getPosition() {
        return position;
    }
}
