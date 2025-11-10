package com.dimar.frontend;

import com.badlogic.gdx.math.Vector2;

public class Player {
    private Vector2 position;

    public Player(float x, float y) {
        position = new Vector2(x, y);
    }

    public void move(float dx, float dy) {
        position.add(dx, dy);
    }

    public Vector2 getPosition() {
        return position;
    }
}
