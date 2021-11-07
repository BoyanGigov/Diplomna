package all.component.diplomna.dao;

import all.component.diplomna.dao.interfaces.MoodleCourseSectionDao;
import all.component.diplomna.model.MoodleCourseSectionMO;
import all.persistence.dao.DiplomnaDaoImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Transactional("diplomnaTransactionManager")
@Repository
public class MoodleCourseSectionDaoImpl extends DiplomnaDaoImpl<MoodleCourseSectionMO> implements MoodleCourseSectionDao {

    @Override
    public MoodleCourseSectionMO saveCourseSection(MoodleCourseSectionMO mo) {
        MoodleCourseSectionMO dbValue = getCourseSection(mo.getCourseId(), mo.getSectionId());
        if (dbValue != null) {
            // update values
            return super.save(dbValue);
        } else {
            return super.save(mo);
        }
    }

    @Override
    public List<MoodleCourseSectionMO> getAllCourseSectionsByCourseId(Long courseId) {
        String selectStr = "from " + this.entityClass.getSimpleName() + " mo" +
                " where mo.courseId = (:courseId)";
        Query query = getEntityManager().createQuery(selectStr);
        query.setParameter("courseId", courseId);
        try {
            List<MoodleCourseSectionMO> retVal = query.getResultList();
            return retVal;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public MoodleCourseSectionMO getCourseSection(Long courseId, Long sectionId) {
        String selectStr = "from " + this.entityClass.getSimpleName() + " mo" +
                " where mo.courseId = (:courseId) and mo.sectionId = (:sectionId)";
        Query query = getEntityManager().createQuery(selectStr);
        query.setParameter("courseId", courseId);
        query.setParameter("sectionId", sectionId);
        try {
            MoodleCourseSectionMO retVal = (MoodleCourseSectionMO) query.getSingleResult();
            return retVal;
        } catch (NoResultException e) {
            return null;
        }
    }
}
