package com.dimar.backend.repository;

import com.dimar.backend.model.Player;
import com.dimar.backend.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ScoreRepository extends JpaRepository<Score, UUID> {
    List<Score> findByPlayerId(UUID playerId);
    boolean existsByPlayerId(UUID playerId);

    List<Score> findByPlayerIdOrderByValueDesc(UUID playerId);
    List<Score> findByValueGreaterThan(Integer minValue);
    List<Score> findAllByOrderByCreatedAtDesc();

    @Query(value = "select * from scores s order by s.value desc limit :limit", nativeQuery = true)
    List<Score> findTopScores(@Param("limit") int limit);

    @Query(value = "select * from scores s where s.playerId = :playerId order by s.value", nativeQuery = true)
    List<Score> findHighestScoreByPlayerId(@Param("playerId") UUID playerId);

    @Query(value = "select sum(s.coinsCollected) from scores s where s.playerId = :playerId", nativeQuery = true)
    Integer getTotalCoinsByPlayerId(@Param("playerId") UUID playerId);

    @Query(value = "select sum(s.distanceTravelled) from scores s where s.playerId = :playerId", nativeQuery = true)
    Integer getTotalDistanceByPlayerId(UUID playerId);

    UUID player(Player player);
}
