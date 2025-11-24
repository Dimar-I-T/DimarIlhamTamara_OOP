package com.dimar.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dimar.frontend.commands.Command;
import com.dimar.frontend.commands.JetpackCommand;
import com.dimar.frontend.commands.RestartCommand;
import com.dimar.frontend.factories.ObstacleFactory;
import com.dimar.frontend.observers.ScoreUIObserver;
import com.dimar.frontend.obstacles.BaseObstacle;
import com.dimar.frontend.obstacles.HomingMissile;
import com.dimar.frontend.states.GameStateManager;
import com.dimar.frontend.states.MenuState;
import com.dimar.frontend.states.PlayingState;

import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private GameStateManager gsm;
    private SpriteBatch spriteBatch;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        gsm = new GameStateManager();
        gsm.push(new MenuState(gsm));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(spriteBatch);
    }

    @Override
    public void dispose() {
        super.dispose();
        if (spriteBatch != null) {
            spriteBatch.dispose();
        }
    }
}
