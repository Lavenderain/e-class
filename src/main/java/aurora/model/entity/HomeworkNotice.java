package aurora.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "homeworknotice", catalog = "")
public class HomeworkNotice implements java.io.Serializable {
    private String id;
    private String title;
    private String content;
    private Short type = 0;
    private Short state = 0;
    private Integer fullscore;
    private Date cutoffdatetime;
    private Date createdatetime;
    private Integer unsubmit = 0;
    private Integer reviewed = 0;
    private Integer unreviewed = 0;



    @JSONField(serialize = false)
    private Clazz clazz;

    @JSONField(serialize = false)
    private Set<Homework> homeworks = new HashSet<Homework>(0);
    private String classId;


    public HomeworkNotice(){}


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
    @Column(name = "TITLE", nullable = false, length = 64)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "CONTENT",length = 10000)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "TYPE")
    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    @Basic
    @Column(name = "STATE")
    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    @Basic
    @Column(name = "FULLSCORE")
    public Integer getFullscore() {
        return fullscore;
    }

    public void setFullscore(Integer fullscore) {
        this.fullscore = fullscore;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CUTOFFDATETIME")
    public Date getCutoffdatetime() {
        return cutoffdatetime;
    }

    public void setCutoffdatetime(Date cutoffdatetime) {
        this.cutoffdatetime = cutoffdatetime;
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

    @Basic
    @Column(name = "UNSUBMIT")
    public Integer getUnsubmit() {
        return unsubmit;
    }

    public void setUnsubmit(Integer unsubmit) {
        this.unsubmit = unsubmit;
    }
    @Basic
    @Column(name = "REVIEWED")
    public Integer getReviewed() {
        return reviewed;
    }

    public void setReviewed(Integer reviewed) {
        this.reviewed = reviewed;
    }
    @Basic
    @Column(name = "UNREVIEWED")
    public Integer getUnreviewed() {
        return unreviewed;
    }

    public void setUnreviewed(Integer unreviewed) {
        this.unreviewed = unreviewed;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CLASS", nullable = false)
    public Clazz getClazz() {
        return clazz;
    }
    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "hn")
    public Set<Homework> getHomeworks() {
        return homeworks;
    }

    public void setHomeworks(Set<Homework> homeworks) {
        this.homeworks = homeworks;
    }

    @Transient
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
