package aurora.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "checkin", catalog = "")
public class Checkin implements java.io.Serializable {
    private String id;
    private String name;
    private Date createdatetime;
    private Short state = 0;
    @JSONField(serialize = false)
    private CheckinNotice cn;
    @JSONField(serialize = false)
    private User user;
    @JSONField(serialize = false)
    private String classId;

    public Checkin(){}

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
    @Column(name = "NAME", length = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "STATE")
    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CHECKINNOTICE", nullable = false)
    public CheckinNotice getCn() {
        return cn;
    }

    public void setCn(CheckinNotice cn) {
        this.cn = cn;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_USER", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Basic
    @Column(name = "CLASSID")
    public String getClassId() {
        return classId;
    }
    public void setClassId(String classId) {
        this.classId = classId;
    }
}
