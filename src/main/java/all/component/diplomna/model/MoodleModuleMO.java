package all.component.diplomna.model;

import all.persistence.ModelBase;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "MOODLE_MODULE")
public class MoodleModuleMO extends ModelBase {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "MoodleModuleGenerator")
    @SequenceGenerator(name = "MoodleModuleGenerator", sequenceName = "MOODLE_MODULE_SEQ")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "COURSE_ID", referencedColumnName = "COURSE_ID", nullable = false)
    private MoodleCourseSectionMO courseSection;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "module", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<MoodleContentMO> contents;

    @Column(name = "MODULE_ID", nullable = false)
    private Integer moduleId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "VISIBLE", nullable = false)
    private Integer visible;

    @Column(name = "USER_VISIBLE", nullable = false)
    private Boolean userVisible;

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

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
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
}
