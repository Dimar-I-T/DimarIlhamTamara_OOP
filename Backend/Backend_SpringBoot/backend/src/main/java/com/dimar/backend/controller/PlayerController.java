package com.dimar.backend.controller;

import com.dimar.backend.model.Player;
import com.dimar.backend.repository.PlayerRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private final PlayerRepository playerRepository;

    public PlayerController(PlayerRepository playerRepository) {
        System.out.println("PlayerController initialized");
        this.playerRepository = playerRepository;
    }

    @GetMapping("/{username}")
    public Optional<Player> getPlayerByUsername(@PathVariable  String username) {
        return playerRepository.findByUsername(username);
    }

    @GetMapping("/top")
    public List<Player> getTopPlayersByHighScore(@RequestParam(defaultValue = "10") int limit) {
        return playerRepository.findTopPlayersByHighScore(limit);
    }

    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody Player playerRequest) {
        System.out.println("POST /players called with username: " + playerRequest.getUsername());
        Player newPlayer = new Player();
        newPlayer.setUsername(playerRequest.getUsername());
        Player savedPlayer = playerRepository.save(newPlayer);
        return ResponseEntity.ok(savedPlayer);
    }

    @PutMapping("/{id}/highscore")
    public ResponseEntity<Player> updateHighScore(
            @PathVariable UUID id,
            @RequestParam int newScore
    ) {
        return playerRepository.findById(id)
                .map(player -> {
                    player.updateHighScore(newScore);
                    Player updated = playerRepository.save(player);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
