package com.dimar.frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.*;

public class Background {
    private Texture backgroundTexture;
    private TextureRegion backgroundRegion;
    private float width;
    private float height;
    private float currentCameraX;
    float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();
    int i = 0;

    public Background() {
        backgroundTexture = new Texture("background.png");
        backgroundRegion = new TextureRegion(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        width = 2688f;
        height = 1536f;
    }

    public void update(float cameraX) {
        currentCameraX = cameraX;
    }

    public void render(SpriteBatch batch) {
        float sizeW = (width / height) * Gdx.graphics.getHeight();
        batch.draw(backgroundTexture, sizeW * (i - 1 + 0.2f), 0, sizeW, Gdx.graphics.getHeight());
        batch.draw(backgroundTexture, sizeW * (i + 0.2f), 0, sizeW, Gdx.graphics.getHeight());
        batch.draw(backgroundTexture, sizeW * (i + 1 + 0.2f), 0, sizeW, Gdx.graphics.getHeight());
        if (currentCameraX > sizeW*(i + 1 + 0.2f)) {
            i++;
        }
    }

    public void dispose() {
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }
    }
}
