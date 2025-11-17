package com.dimar.frontend.strategies;

import java.util.HashMap;
import java.util.Map;

public class MediumDifficultyStrategy implements DifficultyStrategy {
    @Override
    public float getSpawnInterval() {
        return 1.5f;
    }

    @Override
    public int getDensity() {
        return 1;
    }

    @Override
    public float getMinGap() {
        return 100f;
    }

    @Override
    public Map<String, Integer> getObstacleWeights() {
        Map<String, Integer> obstacleWeights = new HashMap<>();
        obstacleWeights.put("HomingMissile", 2);
        obstacleWeights.put("HorizontalLaser", 3);
        obstacleWeights.put("VerticalLaser", 3);

        return obstacleWeights;
    }

    @Override
    public String getName() {
        return "Medium";
    }
}
