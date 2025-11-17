package com.dimar.frontend.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dimar.frontend.strategies.DifficultyStrategy;

public class DifficultyTransitionState implements GameState {
    private GameStateManager gsm;
    private PlayingState playingState;
    private DifficultyStrategy newStrategy;
    private BitmapFont font;
    private float timer = 2f;
    private OrthographicCamera camera;

    public DifficultyTransitionState(GameStateManager gsm, PlayingState playingState, DifficultyStrategy difficultyStrategy) {
        this.gsm = gsm;
        this.playingState = playingState;
        newStrategy = difficultyStrategy;
        font = new BitmapFont(Gdx.files.internal("arial.fnt"));
        font.setColor(Color.WHITE);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void update(float delta) {
        timer -= delta;
        if (timer <= 0) {
            playingState.setDifficulty(newStrategy);
            gsm.pop();
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        playingState.render(spriteBatch);
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        GlyphLayout layout = new GlyphLayout(font, "DIFFICULTY INCREASED!");
        GlyphLayout layout1 = new GlyphLayout(font, newStrategy.getName());
        float x = (Gdx.graphics.getWidth() - layout.width) / 2f;
        float y = (Gdx.graphics.getHeight() + layout.height) / 2f;
        float x1 = (Gdx.graphics.getWidth() - layout1.width) / 2f;
        float y1 = (Gdx.graphics.getHeight() + layout1.height) / 2f;
        font.draw(spriteBatch, layout, x, y);
        font.draw(spriteBatch, layout1, x1, y1 - layout.height - 20f);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}

