package all.component.diplomna.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoodleCourseSectionDTO {

    private Long id;
    private String name;
    private Integer visible;
    private Boolean uservisible;
    private String summary;
    private MoodleModuleDTO[] modules;

    MoodleCourseSectionDTO() {

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

    public Boolean getUservisible() {
        return uservisible;
    }

    public void setUservisible(Boolean uservisible) {
        this.uservisible = uservisible;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public MoodleModuleDTO[] getModules() {
        return modules != null ? modules : new MoodleModuleDTO[0];
    }

    public void setModules(MoodleModuleDTO[] modules) {
        this.modules = modules;
    }
}
