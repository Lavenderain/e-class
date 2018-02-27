package aurora.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "checkinnotice", catalog = "")
public class CheckinNotice implements java.io.Serializable {
    private String id;
    private String name;
    private Short state = 0;
    private Short type = 0;
    private String checkincode;
    private Date createdatetime;
    @JSONField(serialize = false)
    private Clazz clazz;
    private String classId;
    @JSONField(serialize = false)
    private Set<Checkin> cs = new HashSet<Checkin>(0);
    private Integer sum = 0;

    public CheckinNotice(){};

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

    @Basic
    @Column(name = "STATE")
    public Short getState() {
        return state;
    }
    public void setState(Short state) {
        this.state = state;
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
    @Column(name = "CHECKINCODE",length = 6)
    public String getCheckincode() {
        return checkincode;
    }

    public void setCheckincode(String checkincode) {
        this.checkincode = checkincode;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cn")
    public Set<Checkin> getCs() {
        return cs;
    }
    public void setCs(Set<Checkin> cs) {
        this.cs = cs;
    }

    @Transient
    public Integer getSum() {
        return sum;
    }
    public void setSum(Integer sum) {
        this.sum = sum;
    }
}
