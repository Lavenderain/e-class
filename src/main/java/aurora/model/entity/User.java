package aurora.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;



@Entity
@Table(name = "user", catalog = "")
public class User implements java.io.Serializable {

	private String id;
    private String account;
    private Date createdatetime;
    private Date modifydatetime;

    @JSONField(serialize = false)
    private Short type;
    private String name;
    private String sno;
    @JSONField(serialize = false)
    private String pwd;
    private String school;
    private String email = "";
    private String phone;
    private String icon = "https://i.loli.net/2017/08/21/599a521472424.jpg";//默认头像，cdn引用
    private String thirdaccount;
    private String verifycode = null;

    @JSONField(serialize = false)
    private Set<Clazz> clazzs = new HashSet<Clazz>(0);

    @JSONField(serialize = false)
    private Set<Homework> homeworks = new HashSet<Homework>(0);

    @JSONField(serialize = false)
    private Set<Checkin> checkins = new HashSet<Checkin>(0);

    @JSONField(serialize = false)
    private Set<Performance> performances = new HashSet<Performance>(0);

    @JSONField(serialize = false)
    private Set<Groupe> groups = new HashSet<Groupe>();



	public User() {
	}



    public User(String id) {
	    this.id = id;
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
    @Column(name = "ACCOUNT", length = 64, unique = true)
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFYDATETIME", length = 19)
    public Date getModifydatetime() {
        return this.modifydatetime;
    }

    public void setModifydatetime(Date modifydatetime) {
        this.modifydatetime = modifydatetime;
    }

    @Basic
    @Column(name = "`TYPE`", nullable = false)
    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    @Basic
    @Column(name = "`NAME`", length = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Basic
    @Column(name = "SNO",length = 32)
    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }


    @Basic
    @Column(name = "PWD", nullable = false, length = 64)
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


    @Basic
    @Column(name = "SCHOOL", length = 32)
    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }


    @Basic
    @Column(name = "EMAIL", length = 128)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Basic
    @Column(name = "PHONE", length = 32)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "ICON")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Basic
    @Column(name = "THIRDACCOUNT", length = 64)
    public String getThirdaccount() {
        return thirdaccount;
    }

    public void setThirdaccount(String thirdaccount) {
        this.thirdaccount = thirdaccount;
    }

    @Transient
    public String getVerifycode() {
        return verifycode;
    }

    public void setVerifycode(String verifycode) {
        this.verifycode = verifycode;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_class", catalog = "",
            joinColumns = { @JoinColumn(name = "USER_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "CLASS_ID", nullable = false, updatable = false) })
    public Set<Clazz> getClazzs() {
        return clazzs;
    }

    public void setClazzs(Set<Clazz> clazzs) {
        this.clazzs = clazzs;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    public Set<Homework> getHomeworks() {
        return homeworks;
    }

    public void setHomeworks(Set<Homework> homeworks) {
        this.homeworks = homeworks;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @OrderBy("createdatetime DESC")
    public Set<Checkin> getCheckins() {
        return checkins;
    }

    public void setCheckins(Set<Checkin> checkins) {
        this.checkins = checkins;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @OrderBy("createdatetime DESC")
    public Set<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(Set<Performance> performances) {
        this.performances = performances;
    }


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_group", catalog = "",
            joinColumns = { @JoinColumn(name = "USER_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "GROUP_ID", nullable = false, updatable = false) })
    public Set<Groupe> getGroups() {
        return groups;
    }

    public void setGroups(Set<Groupe> groups) {
        this.groups = groups;
    }
}