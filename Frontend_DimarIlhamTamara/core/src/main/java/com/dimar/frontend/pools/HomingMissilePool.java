package com.dimar.frontend.pools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.dimar.frontend.obstacles.HomingMissile;
import com.dimar.frontend.obstacles.HorizontalLaser;

public class HomingMissilePool extends ObjectPool<HomingMissile>{
    @Override
    public HomingMissile createObject() {
        return new HomingMissile(new Vector2(0, 0), 0);
    }

    @Override
    public void resetObject(HomingMissile object) {
        object.setPosition(0, 0);
        object.setTarget(null);
    }

    public HomingMissile obtain(Vector2 position, int length) {
        HomingMissile object = super.obtain();
        object.initialize(position, 0);
        object.setActive(true);
        return object;
    }
}
