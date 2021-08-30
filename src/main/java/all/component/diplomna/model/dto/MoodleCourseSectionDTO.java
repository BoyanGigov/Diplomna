package all.component.diplomna.model.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
public class MoodleCourseSectionDTO {

    private Integer id;
    private String name;
    private Integer visible;
    private Boolean uservisible;
    private String summary;
    private MoodleModuleDTO[] modules;

    MoodleCourseSectionDTO() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        return modules;
    }

    public void setModules(MoodleModuleDTO[] modules) {
        this.modules = modules;
    }
}
