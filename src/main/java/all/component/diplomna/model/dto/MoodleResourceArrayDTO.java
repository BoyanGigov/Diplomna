package all.component.diplomna.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoodleResourceArrayDTO {

    private MoodleResourceDTO[] resources;

    MoodleResourceArrayDTO() {

    }

    public MoodleResourceDTO[] getResources() {
        return resources;
    }

    public void setResources(MoodleResourceDTO[] resources) {
        this.resources = resources;
    }
}
