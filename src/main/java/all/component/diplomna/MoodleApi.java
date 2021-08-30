package all.component.diplomna;

import java.io.OutputStream;

public interface MoodleApi {
    void updateDataInDB();

    void getCourseFilesFromDB(int courseId, OutputStream outputStream);
}
