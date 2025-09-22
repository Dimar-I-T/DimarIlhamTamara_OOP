package Repository;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Optional;
import java.util.List;
import java.util.Comparator;
import Model.Player;

public class PlayerRepository extends BaseRepository<Player, UUID>{
    private ArrayList<Player> allData = new ArrayList<>();
    public Optional<Player> findByUsername(String username) {
        return allData.stream().filter(player -> player.getUsername().equals(username)).findFirst();
    }

    public List<Player> findTopPlayersByHighScore(int limit) {
        return allData.stream().sorted(Comparator.comparing(p -> ((Integer) (-1 * p.getScore())))).limit(limit).toList();
    }

    public List<Player> findByHighscoreGreaterThan(int minScore) {
        return allData.stream().filter(player -> player.getScore() > minScore).toList();
    }

    public List<Player> findAllByOrderByTotalCoinsDesc() {
        return allData.stream().sorted(Comparator.comparing(p -> ((Integer) (-1 * p.getTotalCoins())))).toList();
    }

    public List<Player> findAllByOrderByTotalDistanceTravelledDesc() {
        return allData.stream().sorted(Comparator.comparing(p -> ((Integer) (-1 * p.getTotalDistance())))).toList();
    }

    @Override
    public void save(Player player) {
        allData.add(player);
        map.put(player.getPlayerId(), player);
        list.add(player);
    }

    @Override
    public UUID getId(Player player) {
        return player.getPlayerId();
    }
}
