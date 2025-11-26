package com.dimar.frontend.factories;

import com.badlogic.gdx.Gdx;
import com.dimar.frontend.Coin;
import com.dimar.frontend.obstacles.BaseObstacle;
import com.dimar.frontend.obstacles.HomingMissile;
import com.dimar.frontend.pools.CoinPool;

import java.util.List;
import java.util.Random;

public class CoinFactory {
    public final CoinPool coinPool = new CoinPool();

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
