package all.component.diplomna.dao;

import all.component.diplomna.dao.interfaces.MoodleCourseStatisticsDao;
import all.component.diplomna.model.MoodleCourseStatisticsMO;
import all.persistence.dao.DiplomnaDaoImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional("diplomnaTransactionManager")
@Repository
public class MoodleCourseStatisticsDaoImpl extends DiplomnaDaoImpl<MoodleCourseStatisticsMO> implements MoodleCourseStatisticsDao {
}
