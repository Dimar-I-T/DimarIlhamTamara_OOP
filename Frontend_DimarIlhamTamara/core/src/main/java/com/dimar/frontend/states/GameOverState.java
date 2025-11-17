package com.dimar.frontend.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dimar.frontend.Player;

public class GameOverState implements GameState {
    private final GameStateManager gsm;
    private final BitmapFont font, font1;
    private OrthographicCamera camera;

    public GameOverState(GameStateManager gsm) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.gsm = gsm;
        font = new BitmapFont(Gdx.files.internal("arial.fnt"));
        font.setColor(Color.RED);
        font1 = new BitmapFont(Gdx.files.internal("arial.fnt"));
        font1.setColor(Color.WHITE);
    }

    @Override
    public void update(float delta) {
        System.out.println("You are dead.");
        if (Gdx.input.isKeyJustPressed((Input.Keys.SPACE))) {
            gsm.set(new PlayingState(gsm));
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        GlyphLayout layout = new GlyphLayout(font, "GAME OVER");
        GlyphLayout layout1 = new GlyphLayout(font1, "Press SPACE to restart");
        float x = (Gdx.graphics.getWidth() - layout.width) / 2f;
        float y = (Gdx.graphics.getHeight() + layout.height) / 2f;
        float x1 = (Gdx.graphics.getWidth() - layout1.width) / 2f;
        float y1 = (Gdx.graphics.getHeight() + layout1.height) / 2f - layout.height - 20;
        font.draw(batch, layout, x, y);
        font1.draw(batch, layout1, x1, y1);
        batch.end();
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
