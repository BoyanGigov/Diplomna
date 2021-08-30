package all.component.diplomna.dao;

import all.component.diplomna.dao.interfaces.MoodleActivityDao;
import all.component.diplomna.model.MoodleContentMO;
import all.persistence.dao.DiplomnaDaoImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional("diplomnaTransactionManager")
@Repository
public class MoodleActivityDaoImpl extends DiplomnaDaoImpl<MoodleContentMO> implements MoodleActivityDao {

    // no save method, should only be saved when Course is saved
}
