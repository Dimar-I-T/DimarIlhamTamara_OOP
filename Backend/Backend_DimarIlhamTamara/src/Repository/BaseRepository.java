package Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public abstract class BaseRepository<T, ID> {
    HashMap<ID, T> map;
    ArrayList<T> list;

    T findById(ID id) {
        return this.map.get(id);
    }

    ArrayList<T> findAll() {
        return this.list;
    }

    abstract UUID getId(Model.Player entity);
}
