package all.component.diplomna;

import all.component.diplomna.model.MoodleCourseSectionMO;

import java.io.OutputStream;
import java.util.List;

public interface MoodleApi {
    void updateDataInDB();

    List<MoodleCourseSectionMO> getCourseFilesFromDB(Long courseId);

    void getCourseFilesFromDB(Long courseId, OutputStream outputStream);

    void testArchiving(Long courseId, OutputStream outputStream);
}
