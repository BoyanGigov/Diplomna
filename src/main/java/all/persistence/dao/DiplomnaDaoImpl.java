package all.persistence.dao;

import all.persistence.ModelBase;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class DiplomnaDaoImpl<T extends ModelBase> extends DaoBaseImpl<T> {

    @PersistenceContext(unitName = "diplomnaEntityManager")
    EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
