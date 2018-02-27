package aurora.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "classetting", catalog = "")
public class ClassSetting implements java.io.Serializable{
    private String id;
    private Short freejoingroup = 0;
    private Integer memberslimit = 10000;
    @JSONField(serialize = false)
    private Clazz clazz;

    public ClassSetting(){}

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
    @Column(name = "FREEJOINGROUP")
    public Short getFreejoingroup() {
        return freejoingroup;
    }
    public void setFreejoingroup(Short freejoingroup) {
        this.freejoingroup = freejoingroup;
    }

    @Basic
    @Column(name = "MEMBERSLIMIT")
    public Integer getMemberslimit() {
        return memberslimit;
    }
    public void setMemberslimit(Integer memberslimit) {
        this.memberslimit = memberslimit;
    }


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CLASS", unique = true)
    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }
}
