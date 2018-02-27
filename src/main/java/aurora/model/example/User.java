package aurora.model.example;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * User entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user", catalog = "")
public class User implements java.io.Serializable {

	// Fields

	private Integer id;
	private Clazz clazz;
	private String name;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(Clazz clazz, String name) {
		this.clazz = clazz;
		this.name = name;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_CLASS", nullable = false)
	public Clazz getClazz() {
		return this.clazz;
	}

	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}

	@Column(name = "NAME", nullable = false, length = 23)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}