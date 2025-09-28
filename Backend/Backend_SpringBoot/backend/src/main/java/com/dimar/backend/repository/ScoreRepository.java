package com.dimar.backend.repository;

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
    Optional<Score> findByPlayerId(UUID playerId);
    boolean existsByPlayerId(UUID playerId);

    List<Score> findByPlayerIdOrderByValueDesc(UUID playerId);

    @Query("select s from Score s order by s.value desc")
    List<Score> findTopScores(@Param("limit") int limit);

    List<Score> findHighestScoreByPlayerId(UUID playerId);
    List<Score> findByValueGreaterThan(Integer minValue);
    List<Score> findAllByOrderByCreatedAtDesc();
    Integer getTotalCoinsByPlayerId(UUID playerId);
    Integer getTotalDistanceByPlayerId(UUID playerId);
}
