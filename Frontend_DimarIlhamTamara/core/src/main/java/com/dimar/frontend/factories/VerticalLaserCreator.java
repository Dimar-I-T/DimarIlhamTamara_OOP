package com.dimar.frontend.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.dimar.frontend.obstacles.BaseObstacle;
import com.dimar.frontend.obstacles.VerticalLaser;
import com.dimar.frontend.pools.VerticalLaserPool;

import java.util.List;
import java.util.Random;

public class VerticalLaserCreator implements ObstacleFactory.ObstacleCreator {
    private final VerticalLaserPool pool = new VerticalLaserPool();
    static final int MIN_HEIGHT = 100;
    static final int MAX_HEIGHT = 300;

    @Override
    public BaseObstacle create(float groundTopY, float spawnX, float playerHeight, Random rng) {
        int height = rng.nextInt( MAX_HEIGHT - MIN_HEIGHT + 1) + MIN_HEIGHT;
        float batasMinY = groundTopY + playerHeight;
        float batasMaxY = Gdx.graphics.getHeight() - playerHeight;
        float randomY = rng.nextFloat()*(batasMaxY - batasMinY) + batasMinY;
        return pool.obtain(new Vector2(spawnX, randomY), height);
    }

    @Override
    public void release(BaseObstacle obstacle) {
        VerticalLaser object;
        if (obstacle instanceof VerticalLaser) {
            object = (VerticalLaser) obstacle;
            pool.release(object);
        }
    }

    @Override
    public void releaseAll(){
        pool.releaseAll();
    }

    @Override
    public List<VerticalLaser> getInUse() {
        return pool.getInUse();
    }

    @Override
    public boolean supports(BaseObstacle obstacle) {
        return obstacle instanceof VerticalLaser;
    }

    @Override
    public String getName() {
        return "VerticalLaser";
    }
}
