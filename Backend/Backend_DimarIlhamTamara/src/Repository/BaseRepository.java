package Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public abstract class BaseRepository<T, ID> {
    HashMap<ID, T> dataMap = new HashMap<>();
    ArrayList<T> allData = new ArrayList<>();

    public T findById(ID id) {
        return dataMap.get(id);
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

    public abstract void save(T player);
    public abstract UUID getId(T entity);
}
