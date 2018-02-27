package aurora.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "homework", catalog = "")
public class Homework implements java.io.Serializable {
    private String id;
    private String name;
    private Short state = 0;
    private Date submitdatetime;
    private Integer score;
    private String comment;
    private String msg;
    @JSONField(serialize = false)
    private HomeworkNotice hn;
    private Set<Userfile> files = new HashSet<Userfile>(0);
    @JSONField(serialize = false)
    private User user;
    private String username;
    public Homework(){}

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
    @Column(name = "NAME", nullable = false, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "USERNAME", nullable = false, length = 32)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "STATE", nullable = false)
    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SUBMITDATETIME")
    public Date getSubmitdatetime() {
        return submitdatetime;
    }

    public void setSubmitdatetime(Date submitdatetime) {
        this.submitdatetime = submitdatetime;
    }
    @Basic
    @Column(name = "SCORE")
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Basic
    @Column(name = "COMMENT", length = 512)
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Basic
    @Column(name = "MSG", length = 128)
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_HOMEWORKNOTICE", nullable = false)
    public HomeworkNotice getHn() {
        return hn;
    }

    public void setHn(HomeworkNotice hn) {
        this.hn = hn;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_USER", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "homework_userfile", catalog = "",
            joinColumns = { @JoinColumn(name = "HOMEWORK_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "USERFILE_ID", nullable = false, updatable = false) })
    public Set<Userfile> getFiles() {
        return files;
    }
    public void setFiles(Set<Userfile> files) {
        this.files = files;
    }


}
