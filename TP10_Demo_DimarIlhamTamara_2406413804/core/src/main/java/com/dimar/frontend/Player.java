package com.dimar.frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private boolean A, D, SHIFT = false;
    private boolean kanan = true;
    private PlayerState berdiriState;
    private PlayerState berjalanState;
    private PlayerState berlariState;
    private PlayerState currentState;
    private Vector2 position;
    private Vector2 velocity;
    private float gravity = 2000f;
    private Rectangle collider;
    private float speed = 0;
    private float width = 64f;
    private float height = 64f;

    public Player(Vector2 startPosition) {
        berdiriState = new BerdiriState(this);
        berjalanState = new BerjalanState(this);
        berlariState = new BerlariState(this);
        currentState = berdiriState;
        position = startPosition;
        velocity = new Vector2(0, 0);
        collider = new Rectangle(startPosition.x, startPosition.y, width, height);
    }

    public void bertarung(StrategiBertarung strategiBertarung) {
        strategiBertarung.bertarung();
    }

    public void update(float delta) {
        updateCollider();
        applyGravity(delta);
        updatePosition(delta);
        updateVelocity();
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(position.x, position.y, width, height);
    }

    public void setState(PlayerState state) {
        currentState = state;
        setSpeed(state.getSpeed());
    }

    public boolean isA() {
        return A;
    }

    public void setA(boolean a) {
        A = a;
        kanan = false;
    }

    public boolean isD() {
        return D;
    }

    public void setD(boolean d) {
        D = d;
        kanan = true;
    }

    public boolean isSHIFT() {
        return SHIFT;
    }

    public void setSHIFT(boolean SHIFT) {
        this.SHIFT = SHIFT;
    }

    public void berdiri() {
        currentState.berdiri();
    }

    public void berjalan() {
        currentState.berjalan();
    }

    public void berlari() {
        currentState.berlari();
    }

    public PlayerState getBerdiriState() {
        return berdiriState;
    }

    public PlayerState getBerjalanState() {
        return berjalanState;
    }

    public PlayerState getBerlariState() {
        return berlariState;
    }

    public PlayerState getCurrentState() {
        return currentState;
    }

    private void updatePosition(float delta) {
        position.y += velocity.y * delta;
        position.x += velocity.x * delta;
    }

    private void updateVelocity() {
        speed = currentState.getSpeed();
        velocity.x = (isA()) ? -speed : speed;
    }

    private void applyGravity(float delta) {
        velocity.y -= gravity * delta;
        if (velocity.y < -700f) {
            velocity.y = -700f;
        }
    }

    private void updateCollider() {
        collider.x = position.x;
        collider.y = position.y;
    }

    public void checkBoundaries(Ground ground, float ceilingY) {
        if (ground.isColliding(collider)) {
            position.y = ground.getHeight();
            velocity.y = 0;
        }

        if (position.x > Gdx.graphics.getWidth() - width) {
            position.x = Gdx.graphics.getWidth() - width;
            velocity.x = 0;
        }

        if (position.x < 0) {
            position.x = 0;
            velocity.x = 0;
        }

        if (position.y > ceilingY - height) {
            position.y = ceilingY - height;
            velocity.y = 0;
        }
    }

    public PlayerState getState() {
        return currentState;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
