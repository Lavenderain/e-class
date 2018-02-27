package aurora.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "userfile", catalog = "")
public class Userfile implements java.io.Serializable {
    private String id;
    private String name;
    private String extension;
    private Short haspdf = 0;
    @JSONField(serialize = false)
    private String pdffile;
    @JSONField(serialize = false)
    private String file;
    @JSONField(serialize = false)
    private String type;
    @JSONField(serialize = false)
    private Short deleted = 0;
    @JSONField(serialize = false)
    private Short istemp = 0;
    @JSONField(serialize = false)
    private Sharefile sharefile;


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
    @Column(name = "EXTENSION", length = 32)
    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Basic
    @Column(name = "HASPDF")
    public Short getHaspdf() {
        return haspdf;
    }

    public void setHaspdf(Short haspdf) {
        this.haspdf = haspdf;
    }

    @Basic
    @Column(name = "PDFFILE", length = 512)
    public String getPdffile() {
        return pdffile;
    }

    public void setPdffile(String pdffile) {
        this.pdffile = pdffile;
    }

    @Basic
    @Column(name = "FILE", length = 512)
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Basic
    @Column(name = "TYPE", length = 128)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "DELETED")
    public Short getDeleted() {
        return deleted;
    }

    public void setDeleted(Short deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name = "ISTEMP")
    public Short getIstemp() {
        return istemp;
    }

    public void setIstemp(Short istemp) {
        this.istemp = istemp;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "userfile")
    public Sharefile getSharefile() {
        return sharefile;
    }

    public void setSharefile(Sharefile sharefile) {
        this.sharefile = sharefile;
    }
}
