package all.component.diplomna.api;

import all.component.diplomna.model.MoodleCourseSectionMO;
import all.component.diplomna.model.MoodleCourseStatisticsMO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface MoodleApi {

    List<MoodleCourseSectionMO> getAllCourses();

    List<MoodleCourseSectionMO> getArchivedCourseFilesFromMoodle(Long courseId);

    MoodleCourseStatisticsMO getCourseStatistics(Long courseId);

    void getArchivedCourseFilesFromMoodle(Long courseId, OutputStream outputStream) throws IOException;

    void testArchiving(Long courseId, OutputStream outputStream) throws Exception;

    void updateDB();
}
