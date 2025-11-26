package com.dimar.frontend.strategies;

import com.badlogic.gdx.Gdx;
import com.dimar.frontend.Coin;
import com.dimar.frontend.factories.CoinFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RectanglePattern implements CoinPattern {
    private static float SPACING_X = 40f;
    private static float SPACING_Y = 40f;
    private Random random = new Random();

    @Override
    public List<Coin> spawn(CoinFactory factory, float groundTopY, float spawnX, float screenHeight) {
        List<Coin> hasil = new ArrayList<>();
        int banyakKolom = 3 + random.nextInt(2);
        int banyakBaris = 2 + random.nextInt(2);
        float randomY = groundTopY + 50 + random.nextFloat() * (Gdx.graphics.getHeight() - 100 - (groundTopY + 50));
        for (int x = 0; x < banyakBaris; x++) {
            for (int y = 0; y < banyakKolom; y++) {
                float posisiX = spawnX + (x * SPACING_X);
                float posisiY = randomY + (y * SPACING_Y);
                Coin coin = factory.coinPool.obtain(posisiX, posisiY);
                hasil.add(coin);
            }
        }

        return hasil;
    }

    @Override
    public String getName() {
        return "Rectangle";
    }
}
