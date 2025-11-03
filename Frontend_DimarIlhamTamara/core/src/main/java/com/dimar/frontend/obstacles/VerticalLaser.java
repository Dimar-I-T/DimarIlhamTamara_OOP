package com.dimar.frontend.obstacles;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class VerticalLaser extends BaseObstacle{
    public VerticalLaser(Vector2 startPosition, int length) {
        super(startPosition, length);
    }

    @Override
    public void initialize(Vector2 startPosition, int length) {
        super.initialize(startPosition, length);
    }

    @Override
    public void updateCollider() {
        collider = new Rectangle(getPosition().x, getPosition().y, WIDTH, length);
    }

    @Override
    public void drawShape(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(0.5f, 0.5f, 0.5f,  1f);
        shapeRenderer.rect(getPosition().x, getPosition().y, WIDTH, length);
    }

    @Override
    public float getRenderWidth() {
        return WIDTH;
    }

    @Override
    public float getRenderHeight() {
        return length;
    }
}
