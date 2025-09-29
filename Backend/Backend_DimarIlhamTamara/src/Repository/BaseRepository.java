package Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.Optional;

public abstract class BaseRepository<T, ID> {
    HashMap<ID, T> dataMap = new HashMap<>();
    ArrayList<T> allData = new ArrayList<>();

    public Optional<T> findById(ID id) {
        return Optional.ofNullable(dataMap.get(id));
    }

    public ArrayList<T> findAll() {
        return this.allData;
    }

    public void deleteById(ID id) {
        T entity = dataMap.remove(id);
        if (entity != null) {
            allData.remove(entity);
        }
    }

    public void delete(T entity) {
        ID id = getId(entity);
        deleteById(id);
    }

    public abstract void save(T player);
    public abstract ID getId(T entity);
}
