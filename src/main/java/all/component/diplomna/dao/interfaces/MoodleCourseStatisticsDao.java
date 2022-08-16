package all.component.diplomna.dao.interfaces;

import all.component.diplomna.model.MoodleCourseSectionMO;
import all.component.diplomna.model.MoodleCourseStatisticsMO;

import java.util.List;

public interface MoodleCourseStatisticsDao {

    MoodleCourseStatisticsMO saveCourseStatistics(MoodleCourseStatisticsMO mo);

    void deleteAllStatistics();

    MoodleCourseStatisticsMO getCourseStatisticsByCourseId(Long courseId);
}
