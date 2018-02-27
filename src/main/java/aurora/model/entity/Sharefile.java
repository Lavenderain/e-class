package aurora.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "sharefile", catalog = "")
public class Sharefile implements java.io.Serializable {
    private String id;
    private Date createdatetime;
    private String name;
    private Integer downloadtimes = 0;
    @JSONField(serialize = false)
    private Folder folder;
    private Userfile userfile;


    public Sharefile(){}

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
    @Column(name = "NAME", length = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Basic
    @Column(name = "DOWNLOADTIMES")
    public Integer getDownloadtimes() {
        return downloadtimes;
    }
    public void setDownloadtimes(Integer downloadtimes) {
        this.downloadtimes = downloadtimes;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FOLDER")
    public Folder getFolder() {
        return folder;
    }
    public void setFolder(Folder folder) {
        this.folder = folder;
    }


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_USERFILE", unique = true)
    public Userfile getUserfile() {
        return userfile;
    }

    public void setUserfile(Userfile userfile) {
        this.userfile = userfile;
    }
}
