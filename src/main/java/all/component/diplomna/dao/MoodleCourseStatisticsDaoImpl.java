package all.component.diplomna.dao;

import all.component.diplomna.dao.interfaces.MoodleCourseStatisticsDao;
import all.component.diplomna.model.MoodleCourseStatisticsMO;
import all.persistence.dao.DiplomnaDaoImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Transactional("diplomnaTransactionManager")
@Repository
public class MoodleCourseStatisticsDaoImpl extends DiplomnaDaoImpl<MoodleCourseStatisticsMO> implements MoodleCourseStatisticsDao {

    private final String COURSE_STATISTICS_TABLE = entityClass.getSimpleName();

    @Override
    public MoodleCourseStatisticsMO saveCourseStatistics(MoodleCourseStatisticsMO mo) {
        return super.save(mo);
    }

    @Override
    public void deleteAllStatistics() {
        super.deleteAll();
    }

    @Override
    public MoodleCourseStatisticsMO getCourseStatisticsByCourseId(Long courseId) {
        String selectStr = "from " + COURSE_STATISTICS_TABLE + " mo" +
                " where mo.courseId = (:courseId)";
        Query query = getEntityManager().createQuery(selectStr);
        query.setParameter("courseId", courseId);
        try {
            MoodleCourseStatisticsMO retVal = (MoodleCourseStatisticsMO) query.getSingleResult();
            return retVal;
        } catch (NoResultException e) {
            return null;
        }
    }

}
