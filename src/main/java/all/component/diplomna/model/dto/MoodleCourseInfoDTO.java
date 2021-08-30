package all.component.diplomna.model.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonInclude;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
public class MoodleCourseInfoDTO {

    private int id;
    private String displayname;
    private String summary; // html format
    private int newsitems;
    private int numsections;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getNewsitems() {
        return newsitems;
    }

    public void setNewsitems(int newsitems) {
        this.newsitems = newsitems;
    }

    public int getNumsections() {
        return numsections;
    }

    public void setNumsections(int numsections) {
        this.numsections = numsections;
    }
}
