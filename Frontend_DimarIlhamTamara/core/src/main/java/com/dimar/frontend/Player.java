package com.dimar.frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private Vector2 position;
    private Vector2 velocity;
    private float gravity = 2000f;
    private float force = 4500f;
    private float maxVerticalSpeed = 700f;
    private Rectangle collider;
    private float width = 64f;
    private float height = 64f;
    private boolean isDead;
    private Vector2 startPosition;

    private float baseSpeed = 500f;
    private float distanceTravelled = 0f;
    private float Delta;

    public Player(Vector2 startPosition) {
        position = startPosition;
        velocity = new Vector2(baseSpeed, 0);
        collider = new Rectangle(startPosition.x, startPosition.y, width, height);
        this.startPosition = startPosition;
    }

    public void update(float delta) {
        if (!isDead) {
            updateDistanceAndSpeed(delta);
            updatePosition(delta);
            applyGravity(delta);
            Delta = delta;
            updateCollider();
        }
    }

    public void die() {
        isDead = true;
        velocity.set(new Vector2(0, 0));
    }

    public void reset() {
        isDead = false;
        position.set(startPosition);
        velocity.set(new Vector2(baseSpeed, 0));
        distanceTravelled = 0f;
    }

    public boolean getIsDead() {
        return isDead;
    }

    private void updateDistanceAndSpeed(float delta) {
        distanceTravelled += velocity.x * delta;
    }

    private void updatePosition(float delta) {
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
    }

    private void applyGravity(float delta) {
        velocity.y -= gravity * delta;
        velocity.x = baseSpeed;
        if (velocity.y > maxVerticalSpeed) {
            velocity.y = maxVerticalSpeed;
        }

        if (velocity.y < -maxVerticalSpeed) {
            velocity.y = -maxVerticalSpeed;
        }
    }

    public void fly() {
        velocity.y += force * Delta;
    }

    private void updateCollider() {
        collider.x = position.x;
        collider.y = position.y;
    }

    public void checkBoundaries(Ground ground, float ceilingY) {
        if (ground.isColliding(collider)) {
            position.y = ground.getTopY();
        }

        if (position.y > ceilingY - height) {
            position.y = ceilingY - height;
            velocity.y = 0;
        }
    }

    public void renderShape(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(position.x, position.y, width, height);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getCollider() {
        return collider;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getDistanceTravelled() {
        return distanceTravelled / 10f;
    }
}
