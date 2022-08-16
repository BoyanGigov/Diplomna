package all.component.diplomna.dao;

import all.component.diplomna.dao.interfaces.MoodleCourseSectionDao;
import all.component.diplomna.model.MoodleCourseSectionMO;
import all.persistence.dao.DiplomnaDaoImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Transactional("diplomnaTransactionManager")
@Repository
public class MoodleCourseSectionDaoImpl extends DiplomnaDaoImpl<MoodleCourseSectionMO> implements MoodleCourseSectionDao {

    private final String COURSE_SECTION_TABLE = entityClass.getSimpleName();

    @Override
    public MoodleCourseSectionMO saveCourseSection(MoodleCourseSectionMO mo) {
            return super.save(mo);
    }

    @Override
    public void deleteAllSections() {
        super.deleteAll();
    }

    @Override
    public List<MoodleCourseSectionMO> getAllCourses() {
        String selectStr = "select distinct courseId, courseName from " + COURSE_SECTION_TABLE;
        Query query = getEntityManager().createQuery(selectStr);
        try {
            List<MoodleCourseSectionMO> retVal = new ArrayList<>();
            ((List<Object[]>) query.getResultList()).forEach(element -> retVal.add(new MoodleCourseSectionMO((Long) element[0], (String) element[1])));
            return retVal;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<MoodleCourseSectionMO> getAllCourseSectionsByCourseId(Long courseId) {
        String selectStr = "from " + COURSE_SECTION_TABLE + " mo" +
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
        String selectStr = "from " + COURSE_SECTION_TABLE + " mo" +
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
