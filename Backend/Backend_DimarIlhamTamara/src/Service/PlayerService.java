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
            return false;
        }else {
            return true;
        }
    }

    public Player createPlayer(Player player) {
        Optional<Player> player1 = playerRepository.findByUsername(player.getUsername());
        if (!player1.isPresent()) {
            playerRepository.save(player);
            return player;
        }else {
            System.out.println("Username sudah pernah dibuat!");
            return null;
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

    public void updatePlayer(UUID playerId, Player updatedPlayer) {
        Optional<Player> player1 = playerRepository.getPlayerById(playerId);
        if (player1.isPresent()) {
            playerRepository.deleteById(playerId);
            playerRepository.save(updatedPlayer);
        }
    }

    public void deletePlayer(UUID playerId) {
        Optional<Player> player1 = playerRepository.getPlayerById(playerId);
        if (player1.isPresent()){
            playerRepository.deleteById(playerId);
        }
    }

    public void deletePlayerByUsername(String username) {
        Optional<Player> player1 = playerRepository.findByUsername(username);
        if (player1.isPresent()) {
            playerRepository.deleteById(player1.get().getPlayerId());
        }
    }

    public void updatePlayerStats(UUID playerId, int scoreValue, int coinsCollected, int distanceTravelled) {
        Optional<Player> player1 = playerRepository.getPlayerById(playerId);
        if (player1.isPresent()){
            player1.get().updateHighScore(scoreValue);
            player1.get().addCoins(coinsCollected);
            player1.get().addDistance(distanceTravelled);
            playerRepository.deleteById(playerId);
            playerRepository.save(player1.get());
        }
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
