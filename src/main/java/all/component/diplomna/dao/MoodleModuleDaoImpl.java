package all.component.diplomna.dao;

import all.component.diplomna.dao.interfaces.MoodleModuleDao;
import all.component.diplomna.model.MoodleModuleMO;
import all.persistence.dao.DiplomnaDaoImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;


@Transactional("diplomnaTransactionManager")
@Repository
public class MoodleModuleDaoImpl extends DiplomnaDaoImpl<MoodleModuleMO> implements MoodleModuleDao {

    private final String COURSE_MODULE_TABLE = entityClass.getSimpleName();

//    @Override
//    public MoodleModuleMO saveCourseInfo(MoodleModuleMO mo) {
//        MoodleModuleMO dbValue = getModuelByModuleId(mo.getId());
//        if (dbValue != null) {
//            // update values
//            return super.save(dbValue);
//        } else {
//            return super.save(mo);
//        }
//    }
//
//    @Override
//    public MoodleModuleMO getModuelByModuleId(Long moduleId) {
//        String selectStr = "from " + COURSE_SECTION_TABLE + " mo" +
//                " where mo.id = (:moduleId)";
//        Query query = getEntityManager().createQuery(selectStr);
//        query.setParameter("moduleId", moduleId);
//        try {
//            MoodleModuleMO retVal = (MoodleModuleMO) query.getSingleResult();
//            return retVal;
//        } catch (NoResultException e) {
//            return null;
//        }
//    }

    // no save method, should only be saved (via Hibernate) when a MoodleModuleMO is saved
    @Override
    public void deleteAllModules() {
        super.deleteAll();
    }

}
