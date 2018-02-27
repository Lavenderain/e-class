package aurora.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "bulletin", catalog = "")
public class Bulletin implements java.io.Serializable {
    private String id;
    private String title;
    private String content;
    private String author;
    private Date createdatetime;
    @JSONField(serialize = false)
    private Clazz clazz;
    private String classId ;


    public Bulletin(){}

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    public String getId() {
        if (!StringUtils.isBlank(this.id)) {
            return this.id;
        }
        return UUID.randomUUID().toString();
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TITLE", length = 128)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "CONTENT", length = 10000)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "AUTHOR",length = 128)
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATEDATETIME")
    public Date getCreatedatetime() {
        if (this.createdatetime != null)
            return this.createdatetime;
        return new Date();
    }

    public void setCreatedatetime(Date createdatetime) {
        this.createdatetime = createdatetime;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CLASS", nullable = false)
    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    @Transient
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
