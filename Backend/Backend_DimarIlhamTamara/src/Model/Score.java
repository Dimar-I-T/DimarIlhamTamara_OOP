package Model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Score implements ShowDetail {
    private UUID scoreId;
    private UUID playerId;
    private Player player;
    private int value;
    private int coinsCollected;
    private int distance;
    private LocalDateTime createdAt;

    // konstruktor
    public Score(UUID playerId, int value, int coinsCollected, int distance) {
        this.scoreId = UUID.randomUUID();
        this.playerId = playerId;
        this.value = value;
        this.coinsCollected = coinsCollected;
        this.distance = distance;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getScoreId() {
        return this.scoreId;
    }

    public UUID getPlayerId() {
        return this.playerId;
    }

    public int getValue() {
        return this.value;
    }

    public int getCoinsCollected() {
        return this.coinsCollected;
    }

    public int getDistance() {
        return this.distance;
    }

    public LocalDateTime getTimeCreated() {
        return this.createdAt;
    }

    @Override
    public void showDetail(){
        System.out.println("Score ID: " + this.scoreId);
        System.out.println("Player ID: " + this.playerId);
        System.out.printf("Score Value: %d\n", this.value);
        System.out.printf("Distance: %d\n", this.distance);
        System.out.println("Created At: " + this.createdAt);
    }
}
