package com.dimar.frontend.observers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScoreUIObserver implements Observer{
    private BitmapFont font;
    private SpriteBatch batch;
    private int score;
    public ScoreUIObserver() {
        font = new BitmapFont(Gdx.files.internal("arial.fnt"));
        font.setColor(Color.WHITE);
        batch = new SpriteBatch();
    }

    @Override
    public void update(int score) {
        this.score = score;
        System.out.println("Score has been updated to " + score);
    }

    public void render(int score) {
        batch.begin();
        String teks = "Score: " + score;
        GlyphLayout layout = new GlyphLayout(font, teks);
        font.draw(batch, teks, Gdx.graphics.getWidth() - layout.width - 10, Gdx.graphics.getHeight()  - 10);
        batch.end();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public int getScore() {
        return this.score;
    }
}
