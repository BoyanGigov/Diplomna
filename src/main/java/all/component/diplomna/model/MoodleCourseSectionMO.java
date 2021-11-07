package all.component.diplomna.model;


import all.persistence.ModelBase;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "MOODLE_SECTION")
public class MoodleCourseSectionMO extends ModelBase {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "MoodleCourseSectionGenerator")
    @SequenceGenerator(name = "MoodleCourseSectionGenerator", sequenceName = "MOODLE_SECTION_SEQ", allocationSize = 1)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "courseSection", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<MoodleModuleMO> modules;

    @Column(name = "COURSE_ID", nullable = false)
    private Long courseId;

    @Column(name = "SECTION_ID", nullable = false, unique = true)
    private Long sectionId;

    @Column(name = "COURSE_NAME", nullable = false)
    private String courseName;

    @Column(name = "SECTION_NAME", nullable = false)
    private String sectionName;

    @Column(name = "USER_VISIBLE", nullable = false)
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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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
