package com.dimar.frontend.pools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.dimar.frontend.obstacles.HorizontalLaser;
import com.dimar.frontend.obstacles.VerticalLaser;

public class VerticalLaserPool extends ObjectPool<VerticalLaser> {
    @Override
    public VerticalLaser createObject() {
        return new VerticalLaser(new Vector2(0, 0), 100);
    }

    @Override
    public void resetObject(VerticalLaser object) {
        object.setPosition(Gdx.graphics.getWidth(), 0);
    }

    public VerticalLaser obtain(Vector2 position, int length) {
        VerticalLaser object = super.obtain();
        object.initialize(position, length);
        object.setActive(true);
        return object;
    }
}
