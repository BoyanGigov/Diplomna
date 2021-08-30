package all.component.diplomna.dao.interfaces;

import all.component.diplomna.model.MoodleCourseSectionMO;

public interface MoodleCourseInfoDao {
    MoodleCourseSectionMO saveCourseInfo(MoodleCourseSectionMO mo);

    MoodleCourseSectionMO getCourseInfoByCourseId(int courseId);
}
