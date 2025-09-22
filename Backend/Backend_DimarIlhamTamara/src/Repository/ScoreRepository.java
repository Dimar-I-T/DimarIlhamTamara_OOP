package Repository;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.Comparator;

import Model.Player;
import Model.Score;
import java.util.ArrayList;

public class ScoreRepository extends BaseRepository<Score, UUID> {
    ArrayList<Score> scores = new ArrayList<>();
    public Optional<Score> findByPlayerId(UUID playerId) {
        return scores.stream().filter(score -> score.getPlayerId().equals(playerId)).findFirst();
    }

    public List<Score> findByPlayerIdOrderByValueDesc(UUID playerId) {
        return scores.stream()
                .filter(score -> score.getPlayerId().equals(playerId))
                .sorted(Comparator.comparing(s -> ((Integer) (-1 * s.getValue()))))
                .toList();
    }

    public List<Score> findTopScores(int limit) {
        return scores.stream()
                .sorted(Comparator.comparing(s -> ((Integer) (-1 * s.getValue()))))
                .limit(limit)
                .toList();
    }

    public Optional<Score> findHighestScoreByPlayerId(UUID playerId) {
        return scores.stream()
                .filter(score -> score.getPlayerId().equals(playerId))
                .max(Comparator.comparingInt(Score::getValue));
    }

    public List<Score> findAllByOrderByCreatedAtDesc() {
        return scores.stream()
                .sorted(Comparator.comparing(Score::getTimeCreated))
                .toList();
    }

    public Integer getTotalCoinsByPlayerId(UUID playerId) {
        return scores.stream()
                .filter(score -> score.getPlayerId().equals(playerId))
                .mapToInt(Score::getCoinsCollected)
                .sum();
    }

    public Integer getTotalDistanceById(UUID playerId) {
        return scores.stream()
                .filter(score -> score.getPlayerId().equals(playerId))
                .mapToInt(Score::getDistance)
                .sum();
    }

    @Override
    public void save(Score score) {
        this.scores.add(score);
        map.put(score.getPlayerId(), score);
        list.add(score);
    }

    @Override
    public UUID getId(Score score) {
        return score.getPlayerId();
    }
}
