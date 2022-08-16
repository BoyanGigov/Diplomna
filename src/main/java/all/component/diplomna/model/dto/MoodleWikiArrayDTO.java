package all.component.diplomna.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoodleWikiArrayDTO {

    private MoodleWikiDTO[] wikis;

    MoodleWikiArrayDTO() {

    }

    public MoodleWikiDTO[] getWikis() {
        return wikis;
    }

    public void setWikis(MoodleWikiDTO[] wikis) {
        this.wikis = wikis;
    }
}
