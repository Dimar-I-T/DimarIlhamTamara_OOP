import Model.Player;
import Model.Score;
import Repository.PlayerRepository;
import Repository.ScoreRepository;
import Service.PlayerService;
import Service.ScoreService;

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

        PlayerService playerService = new PlayerService(playerRepo);
        ScoreService scoreService = new ScoreService(scoreRepo, playerRepo, playerService);

        System.out.println("=== CS 4 === \n");

        Player player1 = new Player("NanaBanana");
        Player player2 = new Player("Yingko");
        Player player3 = new Player("LegdontWork");

        playerService.createPlayer(player1);
        playerService.createPlayer(player2);
        playerService.createPlayer(player3);

        System.out.println("Players created\n");
        for (Player p : playerService.getAllPlayers()) {
            p.showDetail();
        }

        Score score1 = new Score(player1.getPlayerId(), 1500, 50, 3000);
        Score score2 = new Score(player2.getPlayerId(), 2000, 75, 4500);
        Score score3 = new Score(player2.getPlayerId(), 1800, 60, 3500);
        Score score4 = new Score(player3.getPlayerId(), 1200, 40, 2500);
        Score score5 = new Score(player3.getPlayerId(), 2500, 90, 5000);

        scoreService.createScore(score1);
        scoreService.createScore(score2);
        scoreService.createScore(score3);
        scoreService.createScore(score4);
        scoreService.createScore(score5);

        player1.updateHighScore(score1.getValue());
        player1.addCoins(score1.getCoinsCollected());
        player1.addDistance(score1.getDistance());
        player2.updateHighScore(score2.getValue());
        player2.addCoins(score2.getCoinsCollected());
        player2.addDistance(score2.getDistance());
        player2.updateHighScore(score3.getValue());
        player2.addCoins(score3.getCoinsCollected());
        player2.addDistance(score3.getDistance());
        player3.updateHighScore(score4.getValue());
        player3.addCoins(score4.getCoinsCollected());
        player3.addDistance(score4.getDistance());
        player3.updateHighScore(score5.getValue());
        player3.addCoins(score5.getCoinsCollected());
        player3.addDistance(score5.getDistance());

        System.out.println("Scores created!\n");
        System.out.println("Player Score:");
        for (Player p : playerService.getAllPlayers()) {
            p.showDetail();
        }

        System.out.println("Top 2 players by high score");
        List<Player> playerTop2 = playerService.getLeaderboardByHighScore(2);
        for (Player p : playerTop2) {
            p.showDetail();
        }

        System.out.println("All scores for " + player1.getUsername() + ":");
        List<Score> scoreP1 = scoreService.getScoresByPlayerId(player1.getPlayerId());
        for (Score s : scoreP1) {
            s.showDetail();
        }

        System.out.println("Top 3 scores overall:");
        List<Score> scoreTop3 = scoreService.getLeaderboard(3);
        for (Score s : scoreTop3) {
            s.showDetail();
        }

        System.out.println("Searching for player 'NanaBanana':");
        Optional<Player> playerCari = playerService.getPlayerByUsername("NanaBanana");
        if (playerCari.isPresent()) {
            System.out.println("Player ditemukan");
            playerCari.get().showDetail();
        }else{
            System.out.println("Player not found!");
        }

        System.out.println("Totals for " + player3.getUsername() + ":");
        Integer totalCoins = scoreService.getTotalCoinsByPlayerId(player3.getPlayerId());
        Integer totalDistance = scoreService.getTotalDistanceByPlayerId(player3.getPlayerId());
        System.out.println("Total Coins: " + totalCoins);
        System.out.println("Total Distance: " + totalDistance);

        System.out.println("\nRecent scores (ordered by creation time):");
        List<Score> scoreTerbaru = scoreService.getRecentScores();
        for (Score s : scoreTerbaru) {
            s.showDetail();
        }
    }

    static void updatePlayer(UUID playerId, int score, int coins, int distance) {
        playersMap.get(playerId).updateHighScore(score);
        playersMap.get(playerId).addCoins(coins);
        playersMap.get(playerId).addDistance(distance);
    }
}
