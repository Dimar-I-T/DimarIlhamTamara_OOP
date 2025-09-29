package Service;

import Model.Player;
import Repository.PlayerRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public class PlayerService {
    private PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public boolean existsByUsername(String username) {
        Optional<Player> player = playerRepository.findByUsername(username);
        if (player.isPresent()){
            return true;
        }else {
            return false;
        }
    }

    public Player createPlayer(Player player) {
        Optional<Player> player1 = playerRepository.findByUsername(player.getUsername());
        if (!player1.isPresent()) {
            playerRepository.save(player);
            return player;
        }else {
            throw new RuntimeException("Username already exists: " + player.getUsername());
        }
    }

    public Optional<Player> getPlayerById(UUID playerId) {
        return playerRepository.getAllData().stream().filter(player -> player.getPlayerId().equals(playerId)).findFirst();
    }

    public Optional<Player> getPlayerByUsername(String username) {
        return playerRepository.findByUsername(username);
    }

    public ArrayList<Player> getAllPlayers() {
        return playerRepository.getAllData();
    }

    public Player updatePlayer(UUID playerId, Player updatedPlayer) {
        Player existingPlayer = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with ID: " + playerId));

        // Update username jika berbeda dan tersedia
        if (updatedPlayer.getUsername() != null &&
                !updatedPlayer.getUsername().equals(existingPlayer.getUsername())) {

            if (existsByUsername(updatedPlayer.getUsername())) {
                throw new RuntimeException("Username already exists: " + updatedPlayer.getUsername());
            }
            existingPlayer.setUsername(updatedPlayer.getUsername());
        }

        // Update high score jika lebih tinggi
        if (updatedPlayer.getScore() > existingPlayer.getScore()) {
            existingPlayer.setHighScore(updatedPlayer.getScore());
        }

        // Update fields lainnya (cara sama)
        if (updatedPlayer.getTotalCoins() > existingPlayer.getTotalCoins()) {
            existingPlayer.setTotalCoins(updatedPlayer.getTotalCoins());
        }

        if (updatedPlayer.getTotalDistance() > existingPlayer.getTotalDistance()) {
            existingPlayer.setTotalDistance(updatedPlayer.getTotalDistance());
        }

        playerRepository.save(existingPlayer);
        return existingPlayer;
    }

    public void deletePlayer(UUID playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with ID: " + playerId));
        playerRepository.deleteById(playerId);
    }

    public void deletePlayerByUsername(String username) {
        Player player = playerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Player not found with username: " + username));
        playerRepository.delete(player);
    }

    public Player updatePlayerStats(UUID playerId, int scoreValue, int coinsCollected, int distanceTravelled) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with ID: " + playerId));

        // Update high score if this score is higher
        player.updateHighScore(scoreValue);

        // Add coins and distance to totals
        player.addCoins(coinsCollected);
        player.addDistance(distanceTravelled);

        playerRepository.save(player);
        return player;
    }

    public List<Player> getLeaderboardByHighScore(int limit) {
        return playerRepository.findTopPlayersByHighScore(limit);
    }

    public List<Player> getLeaderboardByTotalCoins() {
        return playerRepository.findAllByOrderByTotalCoinsDesc();
    }

    public List<Player> getLeaderboardByTotalDistance() {
        return playerRepository.findAllByOrderByTotalDistanceTravelledDesc();
    }
}
