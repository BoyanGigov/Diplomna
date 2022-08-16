package all.component.diplomna.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoodleForumDTO {

    private Integer numdiscussions;

    MoodleForumDTO() {

    }

    public Integer getNumdiscussions() {
        return numdiscussions;
    }

    public void setNumdiscussions(Integer numdiscussions) {
        this.numdiscussions = numdiscussions;
    }
}
