package all.component.diplomna.model.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonInclude;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
public class MoodleModuleContentInfoDTO {

    private Integer filescount;

    MoodleModuleContentInfoDTO() {

    }

    public Integer getFilescount() {
        return filescount;
    }

    public void setFilescount(Integer filescount) {
        this.filescount = filescount;
    }
}
