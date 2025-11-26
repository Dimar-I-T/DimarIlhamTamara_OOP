package com.dimar.frontend.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dimar.frontend.Player;

public class HomingMissile extends BaseObstacle {
    private Player target;
    private Vector2 velocity;
    private float speed = 200f;
    private float width = 40f;
    private float height = 20f;
    private TextureRegion texture;
    private float rotation = 0f;

    public HomingMissile(Vector2 startPosition, int length) {
        super(startPosition, length);
        velocity = new Vector2(0, 0);
        collider = new Rectangle(startPosition.x, startPosition.y, width, height);
        Texture img = new Texture(Gdx.files.internal("missile.png"));
        texture = new TextureRegion(img);
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

        return (target.getPosition().x + target.getWidth() / 2f <= position.x + width/2f);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!isActive()) {
            return;
        }

        batch.draw(texture, position.x, position.y, width / 2, height / 2, width, height, 1f, 1f, rotation);
    }

    public void update(float delta) {
        if (isActive() && isTargetingPlayer()) {
            Vector2 targetPosition = target.getPosition();
            velocity.set(targetPosition).sub(getPosition()).nor().scl(speed);
            setPosition(getPosition().x + velocity.x * delta, getPosition().y + velocity.y * delta);
            updateCollider();
        }

        if (!isTargetingPlayer()) {
            setPosition(getPosition().x + velocity.x * delta, getPosition().y);
        }
    }

    public void updateCollider() {
        collider.x = getPosition().x;
        collider.y = getPosition().y;
    }

    @Override
    public void drawShape(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(1f, 1f, 0f, 1f);
        shapeRenderer.rect(getPosition().x, getPosition().y, this.width, this.height);
    }

    @Override
    public float getRenderWidth() {
        return this.width;
    }

    @Override
    public float getRenderHeight() {
        return this.height;
    }
}
