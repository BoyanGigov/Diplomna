package all.component.diplomna.dao.interfaces;

import all.component.diplomna.model.MoodleCourseSectionMO;

import java.util.List;

public interface MoodleCourseDao {
    MoodleCourseSectionMO saveCourse(MoodleCourseSectionMO mo);

    List<MoodleCourseSectionMO> getCoursesByCourseId(int courseId);
}
