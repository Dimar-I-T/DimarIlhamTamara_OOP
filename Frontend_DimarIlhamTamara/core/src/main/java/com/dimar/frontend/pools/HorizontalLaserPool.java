package com.dimar.frontend.pools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.dimar.frontend.Ground;
import com.dimar.frontend.Player;
import com.dimar.frontend.obstacles.HorizontalLaser;

public class HorizontalLaserPool extends ObjectPool<HorizontalLaser> {
    @Override
    public HorizontalLaser createObject() {
        return new HorizontalLaser(new Vector2(0, 0), 100);
    }

    @Override
    public void resetObject(HorizontalLaser object) {
        object.setPosition(Gdx.graphics.getWidth(), 0);
    }

    public HorizontalLaser obtain(Vector2 position, int length) {
        HorizontalLaser object = super.obtain();
        object.initialize(position, length);
        object.setActive(true);
        return object;
    }
}
