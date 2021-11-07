package all.component.diplomna.model.dto;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
public class MoodleModuleDTO {

    private Long id;
    private String name;
    private Integer visible;
    private Boolean uservisible;
    private MoodleContentDTO[] contents;
    private MoodleModuleContentInfoDTO contentsinfo;

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

    public MoodleContentDTO[] getContents() {
        return contents;
    }

    public void setContents(MoodleContentDTO[] contents) {
        this.contents = contents;
    }

    public MoodleModuleContentInfoDTO getContentsinfo() {
        return contentsinfo;
    }

    public void setContentsinfo(MoodleModuleContentInfoDTO contentsinfo) {
        this.contentsinfo = contentsinfo;
    }
}
