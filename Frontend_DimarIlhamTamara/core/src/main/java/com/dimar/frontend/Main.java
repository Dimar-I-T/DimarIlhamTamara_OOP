package com.dimar.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private int maxHeight, maxWidth;
    private float x, y, width, height;
    private ShapeRenderer shape;
    Color[] colors = {Color.RED, Color.YELLOW, Color.BLUE};
    String[] colorsText = {"red", "yellow", "blue"};
    int i = 0;
    int vX = 200, vY = 200;

    @Override
    public void create() {
        shape = new ShapeRenderer();
        maxHeight = Gdx.graphics.getHeight();
        maxWidth = Gdx.graphics.getWidth();
        System.out.println("maxHeight = " + maxHeight);
        System.out.println("maxWidth = " + maxWidth);
        x = (float) maxWidth / 2;
        y = (float) maxHeight / 2;
        width = 50;
        height = 50;
        System.out.println("Current color: " + colorsText[i]);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            i = (i + 1) % 3;
            System.out.println("Current color: " + colorsText[i]);
        }

        if (y < (maxHeight - height) && (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.UP))) {
            y += vY * Gdx.graphics.getDeltaTime();
        }

        if (x > 0 && (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.LEFT))) {
            x -= vX * Gdx.graphics.getDeltaTime();
        }

        if (y > 0 && (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN))) {
            y -= vY * Gdx.graphics.getDeltaTime();
        }

        if (x < (maxWidth - width) && (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))) {
            x += vX * Gdx.graphics.getDeltaTime();
        }

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(colors[i]);
        shape.rect(x, y, width, height);
        shape.end();
    }

    @Override
    public void dispose() {
        shape.dispose();
    }
}
