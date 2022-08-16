package all.component.diplomna.dao.interfaces;

import all.component.diplomna.model.MoodleCourseSectionMO;

import java.util.List;

public interface MoodleCourseSectionDao {

    MoodleCourseSectionMO saveCourseSection(MoodleCourseSectionMO mo);

    void deleteAllSections();

    List<MoodleCourseSectionMO> getAllCourses();

    List<MoodleCourseSectionMO> getAllCourseSectionsByCourseId(Long courseId);

    MoodleCourseSectionMO getCourseSection(Long courseId, Long sectionId);
}
