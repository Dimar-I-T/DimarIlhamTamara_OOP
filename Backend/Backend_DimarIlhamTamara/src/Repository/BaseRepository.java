package Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public abstract class BaseRepository<T, ID> {
    HashMap<ID, T> map = new HashMap<>();
    ArrayList<T> list = new ArrayList<>();

    public T findById(ID id) {
        return this.map.get(id);
    }

    public ArrayList<T> findAll() {
        return this.list;
    }

    public abstract void save(T player);
    public abstract UUID getId(T entity);
}
