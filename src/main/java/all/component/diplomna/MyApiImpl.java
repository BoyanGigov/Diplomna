package all.component.diplomna;

//import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import com.google.gson.Gson;
import all.component.diplomna.dao.interfaces.DataDao;
import all.component.diplomna.model.dto.DataDTO;
import all.component.diplomna.model.DataMO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyApiImpl implements MyApi {

    private static Logger logger = Logger.getLogger(MyApiImpl.class);

    @Autowired
    private DataDao dataDao;

    @Override
    public DataMO apiOperation(DataDTO dataDTO) {
        DataMO dataMO = null;
        try {
            dataMO = new ObjectMapper().readValue(new Gson().toJson(dataDTO), DataMO.class);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Failed to transform DTO to MO: ", e);
        }
        return dataDao.saveMo(dataMO);
    }
}
