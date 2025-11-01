package com.dimar.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private Player player;
    private Ground ground;
    private GameManager gameManager;

    private OrthographicCamera camera;
    private float cameraOffset = 0.2f;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        gameManager = GameManager.getInstance();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false);
        player = new Player(new Vector2(100, Gdx.graphics.getHeight() / 2f));
        ground = new Ground();
        gameManager.startGame();
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        player.renderShape(shapeRenderer);
        ground.renderShape(shapeRenderer);
        shapeRenderer.end();
    }

    private void update(float delta) {
        boolean isFlying = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        player.update(delta, isFlying);
        updateCamera(delta);
        ground.update(camera.position.x);
        player.checkBoundaries(ground, Gdx.graphics.getHeight());
        gameManager.setScore((int) player.getDistanceTravelled());
        System.out.println("Distance Travelled = " + player.getDistanceTravelled());
    }

    private void updateCamera(float delta) {
        float cameraFocus = player.getPosition().x + cameraOffset;
        camera.position.set(cameraFocus, camera.viewportHeight / 2f, 0);
        camera.update();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
