package entities;
// Generated Dec 22, 2015 4:16:46 PM by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PerformId generated by hbm2java
 */
@Embeddable
public class PerformId implements java.io.Serializable {

	private String idStudent;
	private int idAssignment;

	public PerformId() {
	}

	public PerformId(String idStudent, int idAssignment) {
		this.idStudent = idStudent;
		this.idAssignment = idAssignment;
	}

	@Column(name = "ID_Student", nullable = false, length = 15)
	public String getIdStudent() {
		return this.idStudent;
	}

	public void setIdStudent(String idStudent) {
		this.idStudent = idStudent;
	}

	@Column(name = "ID_Assignment", nullable = false)
	public int getIdAssignment() {
		return this.idAssignment;
	}

	public void setIdAssignment(int idAssignment) {
		this.idAssignment = idAssignment;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PerformId))
			return false;
		PerformId castOther = (PerformId) other;

		return ((this.getIdStudent() == castOther.getIdStudent()) || (this.getIdStudent() != null
				&& castOther.getIdStudent() != null && this.getIdStudent().equals(castOther.getIdStudent())))
				&& (this.getIdAssignment() == castOther.getIdAssignment());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getIdStudent() == null ? 0 : this.getIdStudent().hashCode());
		result = 37 * result + this.getIdAssignment();
		return result;
	}

}
