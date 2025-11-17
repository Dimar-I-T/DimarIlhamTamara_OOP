package com.dimar.frontend.strategies;

import java.util.HashMap;
import java.util.Map;

public class EasyDifficultyStrategy implements DifficultyStrategy {
    @Override
    public float getSpawnInterval() {
        return 2f;
    }

    @Override
    public int getDensity() {
        return 1;
    }

    @Override
    public float getMinGap() {
        return 150f;
    }

    @Override
    public Map<String, Integer> getObstacleWeights() {
        Map<String, Integer> obstacleWeights = new HashMap<>();
        obstacleWeights.put("HomingMissile", 2);
        obstacleWeights.put("HorizontalLaser", 2);
        obstacleWeights.put("VerticalLaser", 1);

        return obstacleWeights;
    }

    @Override
    public String getName() {
        return "Easy";
    }
}
