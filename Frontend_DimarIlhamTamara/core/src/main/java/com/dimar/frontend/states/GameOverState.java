package com.dimar.frontend.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverState implements GameState {
    private final GameStateManager gsm;
    private final BitmapFont font;

    public GameOverState(GameStateManager gsm) {
        this.gsm = gsm;
        font = new BitmapFont();
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed((Input.Keys.SPACE))) {
            gsm.set(new PlayingState(gsm));
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        font.draw(batch, "GAME OVER \n Press SPACE to restart", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
