package com.dimar.frontend.factories;

import com.dimar.frontend.obstacles.BaseObstacle;

import java.util.*;

public class ObstacleFactory {
    /** Factory Method implementor */
    public interface ObstacleCreator {
        // TODO: Deklarasikan abstract method untuk membuat objek baru.
        // Menerima groundTopY, spawnX, playerHeight, dan Random,
        // dan mengembalikan BaseObstacle.

        public abstract BaseObstacle create(float groundTopY, float spawnX, float playerHeight, Random rng);

        // TODO: Deklarasikan abstract method release() untuk me-release
        //BaseObstacle ke pool.
        // Menerima BaseObstacle.

        public abstract void release(BaseObstacle object);

        // TODO: Deklarasikan abstract method releaseAll() untuk me-release
        //semua objek aktif.
        // Tidak menerima apapun.

        public abstract void releaseAll();

        // TODO: Deklarasikan abstract method getInUse() untuk mendapatkan
        //list objek yang sedang digunakan.
        // Mengembalikan List<? extends BaseObstacle>.
        // Tidak menerima apapun.

        public abstract List<? extends BaseObstacle> getInUse();

        // TODO: Deklarasikan abstract method supports() untuk mengecek
        //apakah creator ini mendukung tipe BaseObstacle yang diberikan.
        // Mengembalikan boolean.
        // Menerima BaseObstacle.

        public abstract boolean supports(BaseObstacle object);

        // TODO: Deklarasikan abstract method getName() untuk mendapatkan
        //nama creator.
        // Mengembalikan String.
        // Tidak menerima apapun.

        public abstract String getName();
    }

    /** Weighted creator for probability-based spawning */
    private static class WeightedCreator {
        ObstacleCreator creator;
        int weight;
        WeightedCreator(ObstacleCreator creator, int weight) {
            this.creator = creator;
            this.weight = weight;
        }
    }

    private final Map<String, ObstacleCreator> creators = new HashMap<>();
    private final List<ObstacleCreator> weightedSelection = new ArrayList<>();
    private void register(ObstacleCreator creator) {
        creators.put(creator.getName(), creator);
    }

    private final Random random = new Random();
    public ObstacleFactory() {
        register(new VerticalLaserCreator());
        register(new HorizontalLaserCreator());
        register(new HomingMissileCreator());
    }

    public void setWeights(Map<String, Integer> weights) {
        weightedSelection.clear();
        for (Map.Entry<String, Integer> entry : weights.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                weightedSelection.add(creators.get(entry.getKey()));
            }
        }
    }

    /** Factory Method using weighted random selection */
    public BaseObstacle createRandomObstacle(float groundTopY, float spawnX, float playerHeight) {
        if (weightedSelection.isEmpty()) {
            throw new IllegalStateException("No obstacle creators registered");
        }

        ObstacleCreator creator = selectWeightedCreator();
        return creator.create(groundTopY, spawnX, playerHeight, random);
    }

    private ObstacleCreator selectWeightedCreator() {
        int randomValue = random.nextInt(weightedSelection.size()) - 1;
        return weightedSelection.get(randomValue);
    }

    public void releaseObstacle(BaseObstacle obstacle) {
        for (ObstacleCreator wc : creators.values()) {
            if (wc.supports(obstacle)) {
                wc.release(obstacle);
                return;
            }
        }
    }

    public void releaseAllObstacles() {
        for (ObstacleCreator wc : creators.values()) {
            wc.releaseAll();
        }
    }

    public List<BaseObstacle> getAllInUseObstacles() {
        List<BaseObstacle> list = new ArrayList<>();
        for (ObstacleCreator wc : creators.values()) {
            list.addAll(wc.getInUse());
        }

        return list;
    }

    public List<String> getRegisteredCreatorNames() {
        List<String> names = new ArrayList<>();

        for (ObstacleCreator wc : creators.values()) {
            names.add(wc.getName());
        }
        return names;
    }
}
