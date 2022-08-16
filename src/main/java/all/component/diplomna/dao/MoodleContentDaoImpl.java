package all.component.diplomna.dao;

import all.component.diplomna.dao.interfaces.MoodleContentDao;
import all.component.diplomna.model.MoodleContentMO;
import all.persistence.dao.DiplomnaDaoImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional("diplomnaTransactionManager")
@Repository
public class MoodleContentDaoImpl extends DiplomnaDaoImpl<MoodleContentMO> implements MoodleContentDao {

    // no save method, should only be saved (via Hibernate) when a MoodleModuleMO is saved
    @Override
    public void deleteAllContents() {
        super.deleteAll();
    }
}
