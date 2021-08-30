package all.component.diplomna;

import all.component.diplomna.model.dto.DataDTO;
import all.component.diplomna.model.DataMO;

public interface MyApi {
    DataMO apiOperation(DataDTO dataDTO);
}
