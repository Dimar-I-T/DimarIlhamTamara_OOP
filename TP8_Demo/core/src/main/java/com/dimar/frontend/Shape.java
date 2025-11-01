package com.dimar.frontend;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class Shape {
    private Vector2 position = new Vector2(0, 0);
    private String tipe;
    private float ukuran = 50f;

    public void setPosition(Vector2 positionBaru) {
        position = positionBaru;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setTipe(String tipeBaru) {
        tipe = tipeBaru;
    }

    public String getTipe() {
        return tipe;
    }

    public void setUkuran(float ukuranBaru) {
        ukuran = ukuranBaru;
    }

    public float getUkuran() {
        return ukuran;
    }

    public abstract void renderShape(ShapeRenderer shapeRenderer);
}
