package com.dimar.frontend;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Circle extends Shape{
    public Circle() {
        setTipe("Circle");
    }

    @Override
    public void renderShape(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(getPosition().x, getPosition().y, getUkuran() / 2f);
    }
}
