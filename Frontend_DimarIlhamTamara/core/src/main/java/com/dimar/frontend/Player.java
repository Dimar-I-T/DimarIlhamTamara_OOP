package com.dimar.frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    private Animation<TextureRegion> runAnimation;
    private TextureRegion flyTexture;
    private TextureRegion currentFrame;
    private float stateTime;
    private boolean isOnGround = false;
    private float rotation = 0f;

    public Player(Vector2 startPosition) {
        position = startPosition;
        velocity = new Vector2(baseSpeed, 0);
        collider = new Rectangle(startPosition.x, startPosition.y, width, height);
        this.startPosition = startPosition;
        initializeAnimations();
    }

    private void initializeAnimations() {
        Texture runningTexture = new Texture(Gdx.files.internal("run.png"));
        TextureRegion[][] runFrames2D = TextureRegion.split(runningTexture, 36, 36);
        TextureRegion[] runningFrames = new TextureRegion[4];
        for (int x = 0; x < 4; x++) {
            runningFrames[x] = runFrames2D[0][x];
        }

        runAnimation = new Animation<>(1f/12f, runningFrames);
        runAnimation.setPlayMode(Animation.PlayMode.LOOP);
        Texture flyTextureImg = new Texture(Gdx.files.internal("fly.png"));
        flyTexture = new TextureRegion(flyTextureImg);

        currentFrame = flyTexture;
        stateTime = 0f;
    }

    public void update(float delta) {
        if (!isDead) {
            updateDistanceAndSpeed(delta);
            updatePosition(delta);
            applyGravity(delta);
            Delta = delta;
            updateRotation();
        }

        updateCollider();
        updateAnimation(delta);
    }

    private void updateAnimation(float delta) {
        stateTime += delta;
        if (isOnGround) {
            currentFrame = runAnimation.getKeyFrame(stateTime, true);
        } else {
            currentFrame = flyTexture;
        }
    }

    private void updateRotation() {
        if (velocity.y < -100) {
            rotation = -25f;
        } else {
            rotation = 0f;
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
        rotation = 0f;
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
        if (!isDead) {
            velocity.y += force * Delta;
            isOnGround = false;
        }
    }

    private void updateCollider() {
        collider.setPosition(position.x + 10, position.y + 5);
        collider.setSize(width - 20, height - 10);
    }

    public void checkBoundaries(Ground ground, float ceilingY) {
        if (ground.isColliding(collider)) {
            position.y = ground.getTopY() - 5;
            velocity.y = 0;
            isOnGround = true;
        }

        if (position.y > ceilingY - height) {
            position.y = ceilingY - height;
            velocity.y = 0;
        }
    }

    public void render(SpriteBatch batch) {
        if (currentFrame != null) {
            batch.draw(currentFrame, position.x, position.y, width / 2, height / 2, width, height, 1f, 1f, rotation);
        }
    }

    public void renderShape(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(0f, 1f, 0f, 0.5f);
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
