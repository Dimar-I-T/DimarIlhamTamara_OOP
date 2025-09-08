import java.time.LocalDateTime;
import java.util.UUID;

public class Player implements ShowDetail{
    private UUID playerId;
    private String username;
    private int highscore;
    private int totalCoins;
    private int totalDistance;
    private LocalDateTime createdAt;

    Player(String username) {
        this.username = username;
        this.playerId = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.highscore = 0;
        this.totalCoins = 0;
        this.totalDistance = 0;
    }

    UUID getPlayerId(){
        return this.playerId;
    }

    int updateHighScore(int newScore) {
        if (newScore > highscore) {
            this.highscore = newScore;
        }

        return this.highscore;
    }

    int addCoins(int coins) {
        this.totalCoins += coins;
        return this.totalCoins;
    }

    int addDistance(int distance) {
        this.totalDistance += distance;
        return this.totalDistance;
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
    }
}
