package all.persistence.dao;

import all.persistence.ModelBase;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public abstract class DiplomnaDaoImpl<T extends ModelBase> extends DaoBaseImpl<T> {

    @PersistenceContext(unitName = "diplomnaEntityManager")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
