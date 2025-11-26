package com.dimar.frontend.strategies;

import com.badlogic.gdx.Gdx;
import com.dimar.frontend.Coin;
import com.dimar.frontend.factories.CoinFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LinePattern implements CoinPattern{
    private static float SPACING = 40f;
    private Random random = new Random();

    @Override
    public List<Coin> spawn(CoinFactory factory, float groundTopY, float spawnX, float screenHeight) {
        List<Coin> hasil = new ArrayList<>();
        int banyakKoin = 3 + random.nextInt(3);
        float randomY = groundTopY + 50 + random.nextFloat() * (Gdx.graphics.getHeight() - 100 - (groundTopY + 50));
        for (int x = 0; x < banyakKoin; x++) {
            Coin coin = factory.coinPool.obtain(spawnX + x * SPACING, randomY);
            hasil.add(coin);
        }

        return hasil;
    }

    @Override
    public String getName() {
        return "Line";
    }
}
