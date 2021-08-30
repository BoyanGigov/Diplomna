package all.component.diplomna.dao;

import all.component.diplomna.dao.interfaces.DataDao;
import all.component.diplomna.model.DataMO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import all.persistence.dao.DiplomnaDaoImpl;

import java.util.List;

@Transactional("diplomnaTransactionManager")
@Repository
public class DataDaoImpl extends DiplomnaDaoImpl<DataMO> implements DataDao {

    @Override
    public DataMO saveMo(DataMO mo) {
        return super.save(mo);
    }
}
