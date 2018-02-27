package aurora.model.entity;

import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "message", catalog = "")
public class Message implements java.io.Serializable {

    private String id;
    private String msg;
    private Date createdatetime;
    private Short state = 0;
    private String currentuser;
    private String targetuser;

    public Message(){}

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
    @Column(name = "MSG", length = 1000)
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATEDATETIME", length = 7)
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

    @Basic
    @Column(name = "CURRENTUSER", length = 36, nullable = false)
    public String getCurrentuser() {
        return currentuser;
    }

    public void setCurrentuser(String currentuser) {
        this.currentuser = currentuser;
    }

    @Basic
    @Column(name = "TARGETUSER", length = 36, nullable = false)
    public String getTargetuser() {
        return targetuser;
    }

    public void setTargetuser(String targetuser) {
        this.targetuser = targetuser;
    }
}
