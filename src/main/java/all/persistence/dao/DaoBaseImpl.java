package all.persistence.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import all.persistence.ModelBase;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.Query;

public abstract class DaoBaseImpl<T extends ModelBase> implements DaoBase<T> {

    public static final int NO_MAX_RESULT_LIMIT = -1;
    private static final Object[] NO_PARAMS = null;
    protected Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public DaoBaseImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    @Override
    public List<T> find(String query) {
        return find(query, NO_PARAMS, NO_MAX_RESULT_LIMIT);
    }

    @Override
    public List<T> find(String query, Object... params) {
        return find(query, params, NO_MAX_RESULT_LIMIT);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> find(final String query, final Object[] params, final int size) {
        Query queryObj = getEntityManager().createQuery(query);
        if (size != -1) {
            queryObj.setMaxResults(size);
        }
        setParams(queryObj, params);
        return queryObj.getResultList();
    }

    @Override
    public List<T> findAll() {
        return find("from " + entityClass.getSimpleName());
    }

    @Override
    public T save (T modelBaseObj) {
        return getEntityManager().merge(modelBaseObj);
    }

    @Override
    public T saveAndCommit(T modelBaseObj) {
        getEntityManager().setFlushMode(FlushModeType.COMMIT);
        return getEntityManager().merge(modelBaseObj);
    }

    @Override
    public T get(long id) {
        List<T> result = find("from " + entityClass.getSimpleName() + " where id = " + id);
        if (result == null || result.size() == 0) {
            return null;
        } else {
            return result.get(0);
        }
    }

    @Override
    public void delete(long id) {
        T modelBaseObj = get(id);
        getEntityManager().remove(modelBaseObj);
    }

    @Override
    public void deleteAll() {
        String deleteStr = "delete from " + entityClass.getSimpleName();
        Query query = getEntityManager().createQuery(deleteStr);
        query.executeUpdate();
    }

    private void setParams(Query query, final Object[] params) {
        if (params != null) {
            for (int index = 0; index < params.length; index++) {
                query.setParameter(index + 1, params[index]);
            }
        }
    }

    protected abstract EntityManager getEntityManager();

    @Override
    public void persist(T modelBaseObj) {
        getEntityManager().persist(modelBaseObj);
    }

    @Override
    public T persistAndFlush(T modelBaseObj) {
        getEntityManager().persist(modelBaseObj);
        getEntityManager().flush();
        return modelBaseObj;
    }
}
