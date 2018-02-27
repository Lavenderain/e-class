package aurora.model.example;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Groupp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "groupp", catalog = "")
public class Groupp implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Set<Clazz> clazzs = new HashSet<Clazz>(0);

	// Constructors

	/** default constructor */
	public Groupp() {
	}

	/** minimal constructor */
	public Groupp(String name) {
		this.name = name;
	}

	/** full constructor */
	public Groupp(String name, Set<Clazz> clazzs) {
		this.name = name;
		this.clazzs = clazzs;
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

	@Column(name = "NAME", nullable = false, length = 64)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "class_group", catalog = "", joinColumns = { @JoinColumn(name = "GROUP_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "CLASS_ID", nullable = false, updatable = false) })

	public Set<Clazz> getClazzs() {
		return this.clazzs;
	}

	public void setClazzs(Set<Clazz> clazzs) {
		this.clazzs = clazzs;
	}

}