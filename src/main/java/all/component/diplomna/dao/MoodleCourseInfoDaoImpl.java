package all.component.diplomna.dao;

import all.component.diplomna.dao.interfaces.MoodleCourseInfoDao;
import all.component.diplomna.model.MoodleCourseSectionMO;
import all.persistence.dao.DiplomnaDaoImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;


@Transactional("diplomnaTransactionManager")
@Repository
public class MoodleCourseInfoDaoImpl extends DiplomnaDaoImpl<MoodleCourseSectionMO> implements MoodleCourseInfoDao {

    @Override
    public MoodleCourseSectionMO saveCourseInfo(MoodleCourseSectionMO mo) {
        MoodleCourseSectionMO dbValue = getCourseInfoByCourseId(mo.getCourseId());
        if (dbValue != null) {
            // update values
            return super.save(dbValue);
        } else {
            return super.save(mo);
        }
    }

    @Override
    public MoodleCourseSectionMO getCourseInfoByCourseId(int courseId) {
        String selectStr = "from " + this.entityClass.getSimpleName() + " mo" +
                " where mo.courseId = (:courseId)";
        Query query = getEntityManager().createQuery(selectStr);
        query.setParameter("courseId", courseId);
        try {
            MoodleCourseSectionMO retVal = (MoodleCourseSectionMO) query.getSingleResult();
            return retVal;
        } catch (NoResultException e) {
            return null;
        }
    }

}
