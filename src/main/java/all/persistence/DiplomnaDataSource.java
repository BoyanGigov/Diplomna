package all.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

public class DiplomnaDataSource extends BasicDataSource {

    private static org.apache.log4j.Logger logger = Logger.getLogger(DiplomnaDataSource.class);

    public DiplomnaDataSource() {
        super();
    }

//    public synchronized void setPassword(String encryptedPassword) {
//
//    }
}
