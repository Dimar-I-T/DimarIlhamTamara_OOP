package com.dimar.backend.controller;

import com.dimar.backend.model.Player;
import com.dimar.backend.model.Score;
import com.dimar.backend.repository.PlayerRepository;
import com.dimar.backend.service.ScoreService;
import com.dimar.backend.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private PlayerService playerService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping
    ResponseEntity<List<Score>> getAllScores() {
        List<Score> semuaScore = scoreService.getAllScores();
        return ResponseEntity.status(HttpStatus.OK).body(semuaScore);
    }

    @PostMapping
    ResponseEntity<?> createScore(@RequestBody Score score) {
        try {
            Optional<Player> player = playerService.getPlayerById(score.getPlayerId());
            if (player.isPresent()) {
                Score scoreTerbuat = scoreService.createScore(score);
                return ResponseEntity.status(HttpStatus.CREATED).body(scoreTerbuat);
            }else {
                throw new RuntimeException("Player doesn't exist");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{scoreId}")
    ResponseEntity<?> getScoreById(@PathVariable UUID scoreId) {
        try {
            Optional<Score> score = scoreService.getScoreById(scoreId);
            return ResponseEntity.status(HttpStatus.OK).body(score.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/player/{playerId}")
    ResponseEntity<?> getScoresByPlayerId(@PathVariable UUID playerId) {
        System.out.println("cek " + playerId);
        try {
            List<Score> scores = scoreService.getScoresByPlayerId(playerId);
            return ResponseEntity.status(HttpStatus.OK).body(scores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/player/ordered/{playerId}")
    ResponseEntity<?> getScoresByPlayerIdOrderByValue(@PathVariable UUID playerId) {
        try {
            List<Score> scores = scoreService.getScoresByPlayerIdOrderByValue(playerId);
            return ResponseEntity.status(HttpStatus.OK).body(scores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/leaderboard")
    ResponseEntity<?> getLeaderboard(@RequestParam(defaultValue = "10") int limit) {
        try {
            List<Score> scores = scoreService.getLeaderboard(limit);
            return ResponseEntity.status(HttpStatus.OK).body(scores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
