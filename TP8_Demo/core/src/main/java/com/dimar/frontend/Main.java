package com.dimar.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private Random random;
    private ShapeRenderer shapeRenderer;
    private ShapeFactory shapeFactory;
    ArrayList<Shape> activeShapes;

    @Override
    public void create() {
        // TODO: Inisialisasi shapeRenderer, shapePool,
        //shapeFactory, activeShapes, dan random.
        System.out.println("Press 1=Circle, 2=Square, R=Release");
        random = new Random();
        shapeRenderer = new ShapeRenderer();
        shapeFactory = new ShapeFactory();
        activeShapes = new ArrayList<>();
    }

    @Override
    public void render() {
        // TODO: Cek input: Jika tombol “1” baru ditekan, maka
        //akan membuat sebuah Shape dengan tipe “Circle”
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            createShape("Circle");
        }

        // TODO: Cek input: Jika tombol “2” baru ditekan, maka
        //akan membuat sebuah Shape dengan tipe “Square”
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            createShape("Square");
        }

        // TODO: Cek input: Jika tombol “R” baru ditekan, maka
        //akan me-release semua Shape
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            releaseAllShapes();
        }

        // TODO: Mulai sesi ShapeRenderer dengan tipe Filled
        // TODO: Melakukan render untuk semua Shape yang aktif
        // TODO: Akhiri sesi ShapeRenderer
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClearColor(0.529f, 0.808f, 0.922f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Shape s : activeShapes) {
            s.renderShape(shapeRenderer);
        }

        shapeRenderer.end();
    }

    private void createShape(String type) {
        if (activeShapes.size() >= 3) {
            System.out.println("Maximum 3 shapes active!");
            return;
        }

        // Panggil shapeFactory untuk membuat Shape sesuai
        //dengan type
        Shape shape = shapeFactory.createShape(type);
        if (shape != null) {
            shape.setPosition(new Vector2(
                random.nextFloat() * (Gdx.graphics.getWidth() -
                    100) + 50,
                random.nextFloat() * (Gdx.graphics.getHeight() -
                    100) + 50
                )
            );

            activeShapes.add(shape);
            System.out.println("New " + type + " created");
        }
    }

    private void releaseAllShapes() {
        // TODO: Me-release semua pool pada activeShapes
        // TOOO: Membersihkan activeShapes.
        for (Shape s : activeShapes) {
            shapeFactory.release(s);
        }

        activeShapes.clear();
        System.out.println("All shapes returned to pool");
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
