package com.dimar.frontend;

import com.badlogic.gdx.Gdx;
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

    private float baseSpeed = 300f;
    private float distanceTravelled = 0f;

    public Player(Vector2 startPosition) {
        position = startPosition;
        velocity.x = baseSpeed;
        collider = new Rectangle(startPosition.x, startPosition.y, width, height);
    }

    public void update(float delta, boolean isFlying) {
        updateDistanceAndSpeed(delta);
        updatePosition(delta);
        applyGravity(delta);
        if (isFlying) {
            fly(delta);
        }

        updateCollider();
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

        if (velocity.y < 0) {
            velocity.y = 0;
        }
    }

    private void fly(float delta) {
        velocity.y += force * delta;
    }

    private void updateCollider() {
        collider.x = position.x;
        collider.y = position.y;
    }
}
