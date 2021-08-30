package all.component.diplomna.dao;

import all.component.diplomna.dao.interfaces.MoodleCourseDao;
import all.component.diplomna.model.MoodleCourseSectionMO;
import all.persistence.dao.DiplomnaDaoImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional("diplomnaTransactionManager")
@Repository
public class MoodleCourseDaoImpl extends DiplomnaDaoImpl<MoodleCourseSectionMO> implements MoodleCourseDao {

    @Override
    public MoodleCourseSectionMO saveCourse(MoodleCourseSectionMO mo) {
        MoodleCourseSectionMO dbValue = getCoursesByCourseId(mo.getCourseId()).get(0);
        if (dbValue != null) {
            // update values
            return super.save(dbValue);
        } else {
            return super.save(mo);
        }
    }

    @Override
    public List<MoodleCourseSectionMO> getCoursesByCourseId(int courseId) {
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
}
