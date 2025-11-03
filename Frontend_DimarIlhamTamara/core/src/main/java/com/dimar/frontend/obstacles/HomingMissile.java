package com.dimar.frontend.obstacles;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.dimar.frontend.Player;

public class HomingMissile extends BaseObstacle{
    private Player target;
    private Vector2 velocity;
    private float speed = 200f;
    private float width = 40f;
    private float height = 20f;

    public HomingMissile(Vector2 startPosition, int length) {
        super(startPosition, length);
        velocity = new Vector2(0, 0);
    }

    @Override
    public void initialize(Vector2 startPosition, int length) {
        super.initialize(startPosition, length);
        velocity.set(new Vector2(0, 0));
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public boolean isTargetingPlayer() {
        if (target == null) {
            return false;
        }

        return (target.getPosition().x > this.getPosition().x);
    }

    public void update(float delta) {
        if (isActive()) {
            if (isTargetingPlayer()) {
                Vector2 targetPosition = target.getPosition();
                velocity.set(targetPosition).sub(getPosition()).scl(speed);
                setPosition(velocity.x * delta, velocity.y * delta);
                updateCollider();
            }
        }
    }

    public void updateCollider() {
        collider.x = getPosition().x;
        collider.y = getPosition().y;
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
