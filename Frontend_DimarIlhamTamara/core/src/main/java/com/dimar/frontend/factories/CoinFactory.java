package com.dimar.frontend.factories;

import com.badlogic.gdx.Gdx;
import com.dimar.frontend.Coin;
import com.dimar.frontend.obstacles.BaseObstacle;
import com.dimar.frontend.obstacles.HomingMissile;
import com.dimar.frontend.pools.CoinPool;

import java.util.List;
import java.util.Random;

public class CoinFactory {
    private CoinPool coinPool;
    private Random random;

    public void createCoinPattern(float spawnX, float groundTopY) {
        int randomValue = random.nextInt(10);
        float randomY = random.nextFloat(Gdx.graphics.getHeight() - groundTopY) + groundTopY;
        if (randomValue == 0 || randomValue == 1 || randomValue == 2) {
            float jarak = 40f;
            for (int x = 0; x < 3; x++) {
                coinPool.obtain(spawnX + jarak*x, randomY);
            }
        }
    }

    public void release(Coin coin) {
        coinPool.release(coin);
    }

    public void releaseAll() {
        coinPool.releaseAll();
    }

    public List<Coin> getInUse() {
        return coinPool.getInUse();
    }
}
