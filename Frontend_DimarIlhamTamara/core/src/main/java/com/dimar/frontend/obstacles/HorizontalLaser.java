package com.dimar.frontend.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class HorizontalLaser extends BaseObstacle{
    public HorizontalLaser(Vector2 startPosition, int length) {
        super(startPosition, length);
    }

    @Override
    public void initialize(Vector2 startPosition, int length) {
        super.initialize(startPosition, length);
    }

    @Override
    public void updateCollider() {
        collider = new Rectangle(getPosition().x, getPosition().y, length, WIDTH);
    }

    @Override
    public void drawShape(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(0.5f, 0.5f, 0.5f,  1f);
        shapeRenderer.rect(getPosition().x, getPosition().y, length, WIDTH);
    }

    @Override
    public float getRenderWidth() {
        return length;
    }

    @Override
    public float getRenderHeight() {
        return WIDTH;
    }
}
