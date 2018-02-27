package aurora.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "topicnotice", catalog = "")
public class TopicNotice implements java.io.Serializable {
    private String id;
    private String name;
    private String content;
    private String author;
    private Integer commentsum = 0;
    private Date createdatetime;
    @JSONField(serialize = false)
    private Clazz clazz;
    @JSONField(serialize = false)
    private String classId;
    @JSONField(serialize = false)
    private Set<Comment> comments = new HashSet<Comment>();


    public TopicNotice(){}


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
    @Column(name = "NAME", length = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "AUTHOR", length = 64)
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Basic
    @Column(name = "COMMENTSUM")
    public Integer getCommentsum() {
        return commentsum;
    }

    public void setCommentsum(Integer commentsum) {
        this.commentsum = commentsum;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "topicnotice")
    @OrderBy("createdatetime DESC")
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
