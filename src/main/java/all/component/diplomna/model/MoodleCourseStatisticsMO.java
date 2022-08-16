package all.component.diplomna.model;

import all.persistence.ModelBase;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@Table(name = "MOODLE_STATISTICS")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MoodleCourseStatisticsMO extends ModelBase {

    private static final long serialVersionUID = -12345678902L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "MoodleStatisticsGenerator")
    @SequenceGenerator(name = "MoodleStatisticsGenerator", sequenceName = "MOODLE_STATISTICS_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "COURSE_ID", nullable = false)
    private Long courseId;

    @Column(name = "COURSE_NAME", nullable = false)
    private String courseName;

    @Column(name = "FILES")
    private Integer numberOfFiles;

    @Column(name = "URLS")
    private Integer numberOfUrls;

    @Column(name = "RESOURCES")
    private Integer numberOfResources;

    @Column(name = "WIKIS")
    private Integer numberOfWikis;

    @Column(name = "FORUM_DISCUSSIONS")
    private Integer numberOfForumDiscussions;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getNumberOfFiles() {
        return numberOfFiles;
    }

    public void setNumberOfFiles(Integer numberOfFiles) {
        this.numberOfFiles = numberOfFiles;
    }

    public Integer getNumberOfUrls() {
        return numberOfUrls;
    }

    public void setNumberOfUrls(Integer numberOfUrls) {
        this.numberOfUrls = numberOfUrls;
    }

    public Integer getNumberOfResources() {
        return numberOfResources;
    }

    public void setNumberOfResources(Integer numberOfResources) {
        this.numberOfResources = numberOfResources;
    }

    public Integer getNumberOfWikis() {
        return numberOfWikis;
    }

    public void setNumberOfWikis(Integer numberOfWikis) {
        this.numberOfWikis = numberOfWikis;
    }

    public Integer getNumberOfForumDiscussions() {
        return numberOfForumDiscussions;
    }

    public void setNumberOfForumDiscussions(Integer numberOfForumDiscussions) {
        this.numberOfForumDiscussions = numberOfForumDiscussions;
    }
}
