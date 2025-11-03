package com.dimar.frontend.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.dimar.frontend.obstacles.BaseObstacle;
import com.dimar.frontend.obstacles.HorizontalLaser;
import com.dimar.frontend.pools.HorizontalLaserPool;

import java.util.List;
import java.util.Random;

public class HorizontalLaserCreator implements ObstacleFactory.ObstacleCreator {
    private final HorizontalLaserPool pool = new HorizontalLaserPool();
    static final int MIN_LENGTH = 100;
    static final int MAX_LENGTH = 300;

    @Override
    public BaseObstacle create(float groundTopY, float spawnX, float playerHeight, Random rng) {
        int length = rng.nextInt( MAX_LENGTH - MIN_LENGTH + 1) + MIN_LENGTH;
        float batasMinY = groundTopY + playerHeight;
        float batasMaxY = Gdx.graphics.getHeight() - playerHeight;
        float randomY = rng.nextFloat()*(batasMaxY - batasMinY) + batasMinY;
        return pool.obtain(new Vector2(spawnX, randomY), length);
    }

    @Override
    public void release(BaseObstacle obstacle) {
        HorizontalLaser object;
        if (obstacle instanceof HorizontalLaser) {
            object = (HorizontalLaser) obstacle;
            pool.release(object);
        }
    }

    @Override
    public void releaseAll(){
        pool.releaseAll();
    }

    @Override
    public List<HorizontalLaser> getInUse() {
        return pool.getInUse();
    }

    @Override
    public boolean supports(BaseObstacle obstacle) {
        return obstacle instanceof HorizontalLaser;
    }

    @Override
    public String getName() {
        return "HorizontalLaser";
    }
}
