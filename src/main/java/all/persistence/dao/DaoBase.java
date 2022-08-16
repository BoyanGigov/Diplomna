package all.persistence.dao;

import all.persistence.ModelBase;

import java.util.List;

public interface DaoBase<T extends ModelBase> {
    List<T> find(String query);

    List<T> find(String query, Object... params);

    @SuppressWarnings("unchecked")
    List<T> find(String query, Object[] params, int size);

    List<T> findAll();

    T save (T modelBaseObj);

    T saveAndCommit(T modelBaseObj);

    T get(long id);

    void delete(long id);

    void deleteAll();

    void persist(T modelBaseObj);

    T persistAndFlush(T modelBaseObj);
}
