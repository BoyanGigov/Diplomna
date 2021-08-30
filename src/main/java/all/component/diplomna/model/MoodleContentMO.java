package all.component.diplomna.model;

import all.persistence.ModelBase;

import javax.persistence.*;

@Entity
@Table(name = "MOODLE_CONTENT")
public class MoodleContentMO extends ModelBase {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "MoodleContentGenerator")
    @SequenceGenerator(name = "MoodleContentGenerator", sequenceName = "MOODLE_CONTENT_SEQ")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MODULE_ID", referencedColumnName = "MODULE_ID", nullable = false)
    private MoodleModuleMO module;

    @Column(name = "CONTENT_ID", nullable = false)
    private Integer contentId;

    @Column(name = "TYPE", nullable = false)
    private String type; // "file" or "url"

    @Column(name = "FILE_NAME", nullable = false)
    private String fileName;

//    @Column(name = "FILE_SIZE", nullable = false)
//    private Long filesize;

    @Column(name = "FILE_URL", nullable = false)
    private String fileurl;

    @Column(name = "MIME_TYPE", nullable = false)
    private String mimetype; // null if type is "url"

    @Column(name = "AUTHOR", nullable = false)
    private String author;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public MoodleModuleMO getModule() {
        return module;
    }

    public void setModule(MoodleModuleMO module) {
        this.module = module;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

//    public Long getFilesize() {
//        return filesize;
//    }
//
//    public void setFilesize(Long filesize) {
//        this.filesize = filesize;
//    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
