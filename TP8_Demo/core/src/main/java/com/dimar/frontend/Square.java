package com.dimar.frontend;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Square extends Shape{
    public Square() {
        setTipe("Square");
    }

    @Override
    public void renderShape(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(getPosition().x, getPosition().y, getUkuran(), getUkuran());
    }
}
