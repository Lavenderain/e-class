package aurora.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "folder", catalog = "")
public class Folder {
    private String id;
    private String name;
    @JSONField(serialize = false)
    private Clazz clazz;
    @JSONField(serialize = false)
    private Folder pfolder;
    private Set<Folder> childfolders = new HashSet<Folder>(0);
    private Set<Sharefile> sharefiles = new HashSet<Sharefile>(0);

    public Folder(){}

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
    @Column(name = "NAME" , length = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CLASS", unique = true)
    public Clazz getClazz() {
        return clazz;
    }
    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PFOLDER")
    public Folder getPfolder() {
        return pfolder;
    }

    public void setPfolder(Folder pfolder) {
        this.pfolder = pfolder;
    }


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pfolder")
    public Set<Folder> getChildfolders() {
        return childfolders;
    }

    public void setChildfolders(Set<Folder> childfolders) {
        this.childfolders = childfolders;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "folder")
    public Set<Sharefile> getSharefiles() {
        return sharefiles;
    }

    public void setSharefiles(Set<Sharefile> sharefiles) {
        this.sharefiles = sharefiles;
    }
}
