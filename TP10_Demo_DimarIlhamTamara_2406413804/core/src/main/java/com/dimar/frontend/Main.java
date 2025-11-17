package com.dimar.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private Player player;
    private Ground ground;
    private BitmapFont font, font1, font2, font3;
    private SpriteBatch batch;
    private StrategiBertarung strategiBertarung;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        player = new Player(new Vector2(100, Gdx.graphics.getHeight() / 2f));
        ground = new Ground();
        font = new BitmapFont();
        font1 = new BitmapFont();
        font2 = new BitmapFont();
        font3 = new BitmapFont();
        batch = new SpriteBatch();
        strategiBertarung = new StrategiDefensif();
        player.bertarung(strategiBertarung);
        System.out.println("Tekan A untuk bergerak ke kiri, Tekan D untuk bergerak ke kanan, Tekan Shift untuk berlari.");
        System.out.println("Tekan Q untuk mode defensif, Tekan E untuk mode agresif.");
        font.setColor(Color.BLUE);
        font3.setColor(Color.YELLOW);
        font.getData().setScale(1.1f);
        font3.getData().setScale(1.1f);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float delta = Gdx.graphics.getDeltaTime();

        player.berdiri();
        player.berjalan();
        player.berlari();
        player.setD(Gdx.input.isKeyPressed(Input.Keys.D));
        player.setA(Gdx.input.isKeyPressed(Input.Keys.A));
        player.setSHIFT(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT));

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            strategiBertarung = new StrategiDefensif();
            font.setColor(Color.BLUE);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            strategiBertarung = new StrategiAgresif();
            font.setColor(Color.RED);
        }

        player.bertarung(strategiBertarung);
        player.update(delta);
        player.checkBoundaries(ground, Gdx.graphics.getHeight());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        batch.begin();
        String teks = "Mode Bertarung: " + strategiBertarung.getModeBertarung();
        String teks1 = "Tekan A atau D untuk ke kiri atau ke kanan dan Tekan shift untuk berlari";
        String teks2 = "Tekan Q atau E untuk mengubah mode menjadi Defensif atau Agresif";
        String teks3 = "State: " + player.getCurrentState().getStateName();
        font.draw(batch, teks, 10f, Gdx.graphics.getHeight() - 10f);
        font3.draw(batch, teks3, 10f, Gdx.graphics.getHeight() - 40f);
        font1.draw(batch, teks1, 10f, Gdx.graphics.getHeight() - 90f);
        font2.draw(batch, teks2, 10f, Gdx.graphics.getHeight() - 120f);
        batch.end();
        player.render(shapeRenderer);
        ground.render(shapeRenderer);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
