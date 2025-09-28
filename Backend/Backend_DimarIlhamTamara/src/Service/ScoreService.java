package Service;

import Model.Player;
import Repository.PlayerRepository;
import Repository.ScoreRepository;
import Model.Score;

import java.util.*;

public class ScoreService {
    ScoreRepository scoreRepository;
    PlayerRepository playerRepository;
    PlayerService playerService;

    public ScoreService(ScoreRepository scoreRepository, PlayerRepository playerRepository, PlayerService playerService){
        this.scoreRepository = scoreRepository;
        this.playerRepository = playerRepository;
        this.playerService = playerService;
    }

    public void createScore(Score score) {
        scoreRepository.save(score);
    }

    public Optional<Score> getScoreById(UUID scoreId) {
        return scoreRepository.getAllData().stream().filter(score -> score.getScoreId().equals(scoreId)).findFirst();
    }

    public ArrayList<Score> getAllScores() {
        return scoreRepository.getAllData();
    }

    public List<Score> getScoresByPlayerId(UUID playerId) {
        return scoreRepository.getAllData().stream().filter(score -> score.getPlayerId().equals(playerId)).toList();
    }

    public List<Score> getScoresByPlayerIdOrderByValue(UUID playerId) {
        return scoreRepository.getAllData().stream().filter(score -> score.getPlayerId().equals(playerId)).sorted(Comparator.comparing(s -> ((Integer) (-1 * s.getValue())))).toList();
    }

    public List<Score> getLeaderboard(int limit) {
        return scoreRepository.getAllData().stream().sorted(Comparator.comparing(s -> ((Integer) (-1 * s.getValue())))).limit(limit).toList();
    }

    public Optional<Score> getHighestScoreByPlayerId(UUID playerId) {
        return scoreRepository.getAllData().stream().filter(score -> score.getPlayerId().equals(playerId)).sorted(Comparator.comparing(s -> ((Integer) (-1 * s.getValue())))).limit(1).findFirst();
    }

    public List<Score> getScoresAboveValue(int minValue) {
        return scoreRepository.getAllData().stream().filter(score -> score.getValue() > minValue).toList();
    }

    public List<Score> getRecentScores() {
        return scoreRepository.findAllByOrderByCreatedAtDesc();
    }

    public Integer getTotalCoinsByPlayerId(UUID playerId){
        return scoreRepository.getTotalCoinsByPlayerId(playerId);
    }

    public Integer getTotalDistanceByPlayerId(UUID playerId) {
        return scoreRepository.getTotalDistanceById(playerId);
    }

    public void updateScore(UUID scoreId, Score updatedScore) {
        Optional<Score> score1 = scoreRepository.getAllData().stream().filter(score -> score.getScoreId().equals(scoreId)).findFirst();
        if (score1.isPresent()){
            scoreRepository.deleteById(scoreId);
            scoreRepository.save(updatedScore);
        }
    }

    public void deleteScore(UUID scoreId) {
        Optional<Score> score1 = scoreRepository.getAllData().stream().filter(score -> score.getScoreId().equals(scoreId)).findFirst();
        if (score1.isPresent()){
            scoreRepository.deleteById(scoreId);
        }
    }

    public void deleteScoreByPlayerId(UUID playerId) {
        Optional<Score> score1 = scoreRepository.getAllData().stream().filter(score -> score.getPlayerId().equals(playerId)).findFirst();
        if (score1.isPresent()){
            List<Score> scores = scoreRepository.getAllData().stream().filter(score -> score.getPlayerId().equals(playerId)).sorted(Comparator.comparing(s -> ((Integer) (-1 * s.getValue())))).toList();
            for (Score s : scores) {
                scoreRepository.deleteById(s.getScoreId());
            }
        }
    }
}
