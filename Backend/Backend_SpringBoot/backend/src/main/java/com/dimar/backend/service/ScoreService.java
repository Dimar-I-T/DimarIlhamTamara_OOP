package com.dimar.backend.service;

import com.dimar.backend.repository.ScoreRepository;
import com.dimar.backend.repository.PlayerRepository;
import com.dimar.backend.model.Player;
import com.dimar.backend.model.Score;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScoreService {
    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerService playerService;

    @Transactional
    public Score createScore(Score score) {
        Optional<Player> player = playerService.getPlayerById(score.getPlayerId());
        if (player.isEmpty()) {
            throw new RuntimeException("Player doesn't exist");
        }else {
            Score scoreTersimpan = scoreRepository.save(score);
            playerService.updatePlayerStats(player.get().getPlayerId(), score.getValue(), score.getCoinsCollected(), score.getDistanceTravelled());
            return scoreTersimpan;
        }
    }

    public Optional<Score> getScoreById(UUID score_id) {
        return scoreRepository.findById(score_id);
    }

    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }

    public List<Score> getScoresByPlayerId(UUID player_id) {
        return scoreRepository.findByPlayerId(player_id);
    }

    public List<Score> getScoresByPlayerIdOrderByValue(UUID player_id) {
        return scoreRepository.findHighestScoreByPlayerId(player_id);
    }

    public List<Score> getLeaderboard(int limit) {
        return scoreRepository.findTopScores(limit);
    }
}
