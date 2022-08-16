package all.component.diplomna.model;

import all.persistence.ModelBase;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "MOODLE_MODULE")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MoodleModuleMO extends ModelBase {

    private static final long serialVersionUID = -12345678901L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "MoodleModuleGenerator")
    @SequenceGenerator(name = "MoodleModuleGenerator", sequenceName = "MOODLE_MODULE_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SECTION_ID", referencedColumnName = "ID", nullable = false, unique = true)
    private MoodleCourseSectionMO courseSection;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "module", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<MoodleContentMO> contents;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "VISIBLE", nullable = false)
    private Integer visible;

    @Column(name = "USER_VISIBLE", nullable = false)
    private Boolean userVisible;

    @Transient // will not be saved to the DB
    private String modName;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Set<MoodleContentMO> getContents() {
        return contents;
    }

    public MoodleCourseSectionMO getCourseSection() {
        return courseSection;
    }

    public void setCourseSection(MoodleCourseSectionMO courseSection) {
        this.courseSection = courseSection;
    }

    public void setContents(Set<MoodleContentMO> contents) {
        if (this.contents == null) {
            this.contents = new HashSet<>();
        }
        this.contents.addAll(contents);
    }

    public void addContent(MoodleContentMO content) {
        if (this.contents == null) {
            this.contents = new HashSet<>();
        }
        this.contents.add(content);
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

    public Boolean getUserVisible() {
        return userVisible;
    }

    public void setUserVisible(Boolean userVisible) {
        this.userVisible = userVisible;
    }

    public String getModName() {
        return modName;
    }

    public void setModName(String modName) {
        this.modName = modName;
    }
}
