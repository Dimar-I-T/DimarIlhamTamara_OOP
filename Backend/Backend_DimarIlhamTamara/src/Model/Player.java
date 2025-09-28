package Model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Player implements ShowDetail{
    private UUID playerId;
    private String username;
    private int highscore;
    private int totalCoins;
    private int totalDistance;
    private LocalDateTime createdAt;

    public Player(String username) {
        this.username = username;
        this.playerId = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.highscore = 0;
        this.totalCoins = 0;
        this.totalDistance = 0;
    }

    public UUID getPlayerId(){
        return this.playerId;
    }

    public String getUsername() {
        return this.username;
    }

    public int getScore() {
        return this.highscore;
    }

    public int getTotalDistance() {
        return this.totalDistance;
    }

    public int getTotalCoins() {
        return this.totalCoins;
    }

    public void updateHighScore(int newScore) {
        if (newScore > highscore) {
            this.highscore = newScore;
        }
    }

    public void addCoins(int coins) {
        this.totalCoins += coins;
    }

    public void addDistance(int distance) {
        this.totalDistance += distance;
    }

    @Override
    public void showDetail() {
        System.out.print("Player ID: " + this.playerId);
        System.out.print("\n");
        System.out.println("Username: " + this.username);
        System.out.printf("High Score: %d\n", this.highscore);
        System.out.printf("Total Coins: %d\n", this.totalCoins);
        System.out.printf("Total Distance: %d\n", this.totalDistance);
        System.out.println("Created At: " + this.createdAt);
        System.out.print("\n");
    }
}
