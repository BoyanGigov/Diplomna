package all.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class DiplomnaDataSource extends BasicDataSource {

    private static Logger logger = LogManager.getLogger(DiplomnaDataSource.class);

    public DiplomnaDataSource() {
        super();
    }

//    public synchronized void setPassword(String encryptedPassword) {
//
//    }
}
