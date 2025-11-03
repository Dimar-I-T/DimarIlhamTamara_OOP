package com.dimar.frontend.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.dimar.frontend.obstacles.BaseObstacle;
import com.dimar.frontend.obstacles.HomingMissile;
import com.dimar.frontend.pools.HomingMissilePool;

import java.util.List;
import java.util.Random;

public class HomingMissileCreator implements ObstacleFactory.ObstacleCreator {
    private final HomingMissilePool pool = new HomingMissilePool();

    @Override
    public BaseObstacle create(float groundTopY, float spawnX, float playerHeight, Random rng) {
        float batasMinY = groundTopY + playerHeight;
        float batasMaxY = Gdx.graphics.getHeight() - playerHeight;
        float randomY = rng.nextFloat()*(batasMaxY - batasMinY) + batasMinY;
        return pool.obtain(new Vector2(spawnX, randomY), 0);
    }

    @Override
    public void release(BaseObstacle obstacle) {
        HomingMissile object;
        if (obstacle instanceof HomingMissile) {
            object = (HomingMissile) obstacle;
            pool.release(object);
        }
    }

    @Override
    public void releaseAll(){
        pool.releaseAll();
    }

    @Override
    public List<HomingMissile> getInUse() {
        return pool.getInUse();
    }

    @Override
    public boolean supports(BaseObstacle obstacle) {
        return obstacle instanceof HomingMissile;
    }

    @Override
    public String getName() {
        return "HomingMissile";
    }
}
