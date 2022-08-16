package all.component.diplomna.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoodleModuleDTO {

    private Long id;
    private String name;
    private Integer visible;
    private Boolean uservisible;
    private String modname;
    private MoodleContentDTO[] contents;

    MoodleModuleDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public Boolean isUserVisible() {
        return uservisible;
    }

    public void setUservisible(Boolean uservisible) {
        this.uservisible = uservisible;
    }

    public String getModname() {
        return modname;
    }

    public void setModname(String modname) {
        this.modname = modname;
    }

    public MoodleContentDTO[] getContents() {
        return contents != null ? contents : new MoodleContentDTO[0];
    }

    public void setContents(MoodleContentDTO[] contents) {
        this.contents = contents;
    }
}
