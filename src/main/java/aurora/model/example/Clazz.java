package aurora.model.example;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Clazz entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "clazz", catalog = "")
public class Clazz implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Set<Groupp> groupps = new HashSet<Groupp>(0);
	private Set<User> users = new HashSet<User>(0);

	// Constructors

	/** default constructor */
	public Clazz() {
	}

	/** minimal constructor */
	public Clazz(String name) {
		this.name = name;
	}

	/** full constructor */
	public Clazz(String name, Set<Groupp> groupps, Set<User> users) {
		this.name = name;
		this.groupps = groupps;
		this.users = users;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false, length = 88)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "clazzs")
	public Set<Groupp> getGroupps() {
		return this.groupps;
	}

	public void setGroupps(Set<Groupp> groupps) {
		this.groupps = groupps;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "clazz")
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}