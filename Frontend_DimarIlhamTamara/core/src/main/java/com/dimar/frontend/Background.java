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
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        float faktorH = h / height;
        float faktorW = w / width;
        batch.draw(backgroundTexture, currentCameraX - w/2f, 0, w, h);
    }

    public void dispose() {
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }
    }
}
