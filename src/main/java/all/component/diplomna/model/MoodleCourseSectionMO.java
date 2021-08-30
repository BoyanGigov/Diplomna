package all.component.diplomna.model;


import all.persistence.ModelBase;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "MOODLE_COURSE_SECTION")
public class MoodleCourseSectionMO extends ModelBase {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "MoodleCourseSectionGenerator")
    @SequenceGenerator(name = "MoodleCourseSectionGenerator", sequenceName = "MOODLE_COURSE_SECTION_SEQ")
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "courseSection", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<MoodleModuleMO> modules;

    @Column(name = "COURSE_ID", nullable = false)
    private Integer courseId;

    @Column(name = "COURSE_DISPLAY_NAME", nullable = false)
    private String courseDisplayName;

    @Column(name = "SECTION_ID", nullable = false)
    private Integer sectionId;

    private String summary; // html format

    private String sectionName;

    private Boolean uservisible;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Set<MoodleModuleMO> getModules() {
        return modules;
    }

    public void setModules(Set<MoodleModuleMO> modules) {
        if (this.modules == null) {
            this.modules = new HashSet<>();
        } else {
            this.modules.addAll(modules);
        }
    }

    public void addMoudle(MoodleModuleMO module) {
        if (this.modules == null) {
            this.modules = new HashSet<>();
        } else {
            this.modules.add(module);
        }
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseDisplayName() {
        return courseDisplayName;
    }

    public void setCourseDisplayName(String courseDisplayName) {
        this.courseDisplayName = courseDisplayName;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Boolean getUservisible() {
        return uservisible;
    }

    public void setUservisible(Boolean uservisible) {
        this.uservisible = uservisible;
    }
}
