package all.component.diplomna.model;

import all.persistence.ModelBase;

import javax.persistence.*;

@Entity
@Table(name = "MOODLE_STATISTICS")
public class MoodleCourseStatisticsMO extends ModelBase {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "MoodleStatisticsGenerator")
    @SequenceGenerator(name = "MoodleStatisticsGenerator", sequenceName = "MOODLE_STATISTICS_SEQ")
    private Long id;

    private String moduleName;
    private Integer news;
    private Integer sections;
    private Integer files;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getNews() {
        return news;
    }

    public void setNews(Integer news) {
        this.news = news;
    }

    public Integer getSections() {
        return sections;
    }

    public void setSections(Integer sections) {
        this.sections = sections;
    }

    public Integer getFiles() {
        return files;
    }

    public void setFiles(Integer files) {
        this.files = files;
    }
}
