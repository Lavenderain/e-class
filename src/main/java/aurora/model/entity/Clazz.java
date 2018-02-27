package aurora.model.entity;

import aurora.service.HomeworknoticeServiceI;
import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.StringUtils;


import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "class", catalog = "")
public class Clazz implements java.io.Serializable {
    private String id;
    private String name;
    private String builder;
    private String invitecode;
    private Date createdatetime;
    private Integer membersum = 1;

    @JSONField(serialize = false)
    private Set<User>  users = new HashSet<User>(0);
    @JSONField(serialize = false)
    private Set<HomeworkNotice> hns = new HashSet<HomeworkNotice>(0);
    @JSONField(serialize = false)
    private Set<TopicNotice> topns = new HashSet<TopicNotice>(0);
    @JSONField(serialize = false)
    private Set<Bulletin> bulletins = new HashSet<Bulletin>(0);
    @JSONField(serialize = false)
    private Set<CheckinNotice> cns = new HashSet<CheckinNotice>(0);
    @JSONField(serialize = false)
    private Set<PerformanceNotice> pns = new HashSet<PerformanceNotice>(0);
    @JSONField(serialize = false)
    private Set<Groupe> groups = new HashSet<Groupe>(0);
    @JSONField(serialize = false)
    private Folder folder;
    @JSONField(serialize = false)
    private ClassSetting setting;



    public Clazz(){}

    public Clazz(String id){
        id = this.id;
    }

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
    @Column(name = "NAME", nullable = false, length = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "BUILDER", nullable = false, length = 32)
    public String getBuilder() {
        return builder;
    }

    public void setBuilder(String builder) {
        this.builder = builder;
    }

    @Basic
    @Column(name = "INVITECODE", nullable = false, length = 6)
    public String getInvitecode() {
        return invitecode;
    }

    public void setInvitecode(String invitecode) {
        this.invitecode = invitecode;
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
    @Column(name = "MEMBERSUM")
    public Integer getMembersum() {
        return membersum;
    }

    public void setMembersum(Integer membersum) {
        this.membersum = membersum;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_class", catalog = "",
            joinColumns = { @JoinColumn(name = "CLASS_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "USER_ID", nullable = false, updatable = false) })
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "clazz")
    public Set<HomeworkNotice> getHns() {
        return hns;
    }

    public void setHns(Set<HomeworkNotice> hns) {
        this.hns = hns;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "clazz")
    public Set<TopicNotice> getTopns() {
        return topns;
    }

    public void setTopns(Set<TopicNotice> topns) {
        this.topns = topns;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "clazz")
    public Set<Bulletin> getBulletins() {
        return bulletins;
    }

    public void setBulletins(Set<Bulletin> bulletins) {
        this.bulletins = bulletins;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "clazz")
    public Set<CheckinNotice> getCns() {
        return cns;
    }

    public void setCns(Set<CheckinNotice> cns) {
        this.cns = cns;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "clazz")
    public Set<PerformanceNotice> getPns() {
        return pns;
    }
    public void setPns(Set<PerformanceNotice> pns) {
        this.pns = pns;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "clazz")
    public Set<Groupe> getGroups() {
        return groups;
    }

    public void setGroups(Set<Groupe> groups) {
        this.groups = groups;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "clazz")
    public Folder getFolder() {
        return folder;
    }
    public void setFolder(Folder folder) {
        this.folder = folder;
    }


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "clazz")
    public ClassSetting getSetting() {
        return setting;
    }
    public void setSetting(ClassSetting setting) {
        this.setting = setting;
    }
}
