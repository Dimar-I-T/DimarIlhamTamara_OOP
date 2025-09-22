import Model.Player;
import Model.Score;
import Repository.PlayerRepository;
import Repository.ScoreRepository;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class Main {
    static HashMap<UUID, Player> playersMap = new HashMap<>();

    public static void main(String[] args) {
        PlayerRepository playerRepo = new PlayerRepository();
        ScoreRepository scoreRepo = new ScoreRepository();
        Player player1 = new Player("Stelle");
        Player player2 = new Player("gamerLooxmaxxing");
        Player player3 = new Player ("Stelle123");
        Player player4 = new Player ("Banananana");

        Player[] players = {player1, player2, player3, player4};
        for (Player p : players) {
            playerRepo.save(p);
            playersMap.put(p.getPlayerId(), p);
        }

        player1.updateHighScore(1500);
        player1.addCoins(250);
        player1.addDistance(5000);

        player2.updateHighScore(3200);
        player2.addCoins(750);
        player2.addDistance(12000);

        // Buat dan simpan nilai dengan ketentuan;
        // Score 1: Untuk player 2, memiliki score 1500, coins
        //250, dan distance 5000
        Score score1 = new Score(player2.getPlayerId(), 1500, 250, 5000);
        // Score 2: Untuk Player 4, memiliki score 3200, coins
        //750, dan distance 12000
        Score score2 = new Score(player4.getPlayerId(), 3200, 750, 12000);
        // Score 3: Untuk Player 1, memiliki score 4000, coins
        //400, dan distance 32000
        Score score3 = new Score(player1.getPlayerId(), 4000, 400, 32000);
        // Score 4: Untuk Player 4, memiliki score 1800, coins
        //300, dan distance 6000
        Score score4 = new Score(player4.getPlayerId(), 1800, 300, 6000);
        // Score 5: Untuk Player 3, memiliki score 2400, coins
        //240, dan distance 2400
        Score score5 = new Score(player3.getPlayerId(), 2400, 240, 2400);
        // Score 6: Untuk Player 2, memiliki score 6200, coins
        //320, dan distance 5000
        Score score6 = new Score(player2.getPlayerId(), 6200, 320, 5000);
        // Score 7: Untuk Player 4, memiliki score 1800, coins 60,
        //dan distance 1200
        Score score7 = new Score(player4.getPlayerId(), 1800, 60, 1200);
        // Score 8: Untuk Player 1, memiliki score 2100, coins
        //200, dan distance 7000
        Score score8 = new Score(player1.getPlayerId(), 2100, 200, 7000);
        // Score 9: Untuk Player 1, memiliki score 8000, coins
        //720, dan distance 6200
        Score score9 = new Score(player1.getPlayerId(), 8000, 720, 6200);
        // Score 10: Untuk Player 3,memiliki score 1900, coins
        //210, dan distance 4200
        Score score10 = new Score(player3.getPlayerId(), 1900, 210, 4200);

        Score[] scores = {score1, score2, score3, score4, score5, score6, score7, score8, score9, score10};

        for (Score score : scores) {
            scoreRepo.save(score);
            updatePlayer(score.getPlayerId(), score.getValue(), score.getCoinsCollected(), score.getDistance());
        }

        System.out.println("=== TESTING CS3 ===");

        System.out.println("Find player by ID:");
        Player player3_ = playerRepo.findById(player3.getPlayerId());
        player3_.showDetail();

        System.out.println("\nAll players:");
        for (Player player : players) {
            player.showDetail();
        }

        System.out.println("\nPlayer dengan Score Tertinggi");
        int i = 1;
        List<Player> playersTertinggi = playerRepo.findTopPlayersByHighScore(players.length);
        for (Player player : playersTertinggi) {
            System.out.printf("%d. %s Skor: %d\n", i, player.getUsername(), player.getScore());
            i++;
        }

        System.out.println("\nScores for player1:");
        Optional<Score> skorPlayer1 = scoreRepo.findHighestScoreByPlayerId(player1.getPlayerId());
        System.out.println(skorPlayer1.get().getValue());
    }

    static void updatePlayer(UUID playerId, int score, int coins, int distance) {
        playersMap.get(playerId).updateHighScore(score);
        playersMap.get(playerId).addCoins(coins);
        playersMap.get(playerId).addDistance(distance);
    }
}
