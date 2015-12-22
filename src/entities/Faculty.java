package entities;
// Generated Dec 22, 2015 4:16:46 PM by Hibernate Tools 4.3.1.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Faculty generated by hbm2java
 */
@Entity
@Table(name = "faculty", catalog = "learning")
public class Faculty implements java.io.Serializable {

	private Integer id;
	private String name;
	private Set<Student> students = new HashSet<Student>(0);

	public Faculty() {
	}

	public Faculty(String name) {
		this.name = name;
	}

	public Faculty(String name, Set<Student> students) {
		this.name = name;
		this.students = students;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "Name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "faculty")
	public Set<Student> getStudents() {
		return this.students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

}
