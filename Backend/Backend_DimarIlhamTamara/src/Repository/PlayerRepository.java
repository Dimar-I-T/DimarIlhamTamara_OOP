package Repository;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.UUID;

public class PlayerRepository extends BaseRepository{
    ArrayList<Model.Player> allData;
    public Optional<Model.Player> findByUsername(String username)
    {
        return allData.stream()
                .filter(player ->
                        player.getUsername().equals(username)).findFirst();
    }

    List<Model.Player> findTopPlayersByHighScore(int limit) {
        Stream<Model.Player> SPlayer = allData.stream();
        List<Model.Player> hasilStream = SPlayer.sorted((p1, p2) -> p1.getScore()).limit(limit).collect(Collectors.toList());
        return hasilStream;
    }

    List<Model.Player> findByHighscoreGreaterThan(int minScore) {
        Stream<Model.Player> SPlayer = allData.stream();
        List<Model.Player> hasilStream = SPlayer.filter(player -> player.getScore() > minScore).collect(Collectors.toList());
        return hasilStream;
    }

    List<Model.Player> findAllByOrderByTotalCoinsDesc() {
        Stream<Model.Player> SPlayer = allData.stream();
        List<Model.Player> hasilStream = SPlayer.sorted((p1, p2) -> p1.getTotalCoins()).collect(Collectors.toList());
        return hasilStream;
    }

    List<Model.Player> findAllByOrderByTotalDistanceTravelledDesc() {
        Stream<Model.Player> SPlayer = allData.stream();
        List<Model.Player> hasilStream = SPlayer.sorted((p1, p2) -> p1.getTotalDistance()).collect(Collectors.toList());
        return hasilStream;
    }

    @Override
    UUID getId(Model.Player entity) {
        return entity.getPlayerId();
    }
}
