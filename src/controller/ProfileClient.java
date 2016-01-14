package controller;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import entities.Student;
import model.Hash;
import model.StudentModel;

@ManagedBean
@SessionScoped
public class ProfileClient {
	private ExternalContext externalContext;
	private Map<String, Object> sessionMap;
	private Map<String, String> params;

	public void Init() {
		externalContext = FacesContext.getCurrentInstance().getExternalContext();
		sessionMap = externalContext.getSessionMap();
		params = externalContext.getRequestParameterMap();
		sessionMap.put("title", "Profile");
		if (sessionMap.get("student") != null) {
			student = (Student) sessionMap.get("student");
			_student.setId(student.getId());
			_student.setPassword(student.getPassword());
			_student.setName(student.getName());
			_student.setDob(student.getDob());
			_student.setAddress(student.getAddress());
			_student.setEmail(student.getEmail());
			_student.setFaculty(student.getFaculty());
		}
		if (sessionMap.get("editSuc") != null) {
			sessionMap.put("editSuc", null);
			editSuc = "block";
		} else
			editSuc = "none";

		if (sessionMap.get("editErr") != null) {
			sessionMap.put("editErr", null);
			editErr = "block";
		} else
			editErr = "none";
		/////////////////////
		if (sessionMap.get("passSuc") != null) {
			sessionMap.put("passSuc", null);
			passSuc = "block";
		} else
			passSuc = "none";

		if (sessionMap.get("passErr") != null) {
			sessionMap.put("passErr", null);
			passErr = "block";
		} else
			passErr = "none";
	}

	public String saveChange() {
		try {
			new StudentModel().update(student);
			sessionMap.put("editSuc", true);
			return "profile?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			sessionMap.put("editErr", true);
			return null;
		}
	}

	public String saveChangePass() {
		try {
			if (npass.equals(rpass) && student.getPassword().equals(Hash.getHashMD5(pass))) {
				student.setPassword(Hash.getHashMD5(npass));
				new StudentModel().update(student);
				sessionMap.put("passSuc", true);
			} else
				sessionMap.put("passErr", true);
			return "profile?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			sessionMap.put("passErr", true);
			return null;
		}
	}

	private Student student = new Student();
	private Student _student = new Student();
	private String editSuc = "none";
	private String editErr = "none";
	private String passSuc = "none";
	private String passErr = "none";
	private String pass = "";
	private String npass = "";
	private String rpass = "";

	public String getPassSuc() {
		return passSuc;
	}

	public void setPassSuc(String passSuc) {
		this.passSuc = passSuc;
	}

	public String getPassErr() {
		return passErr;
	}

	public void setPassErr(String passErr) {
		this.passErr = passErr;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getNpass() {
		return npass;
	}

	public void setNpass(String npass) {
		this.npass = npass;
	}

	public String getRpass() {
		return rpass;
	}

	public void setRpass(String rpass) {
		this.rpass = rpass;
	}

	public String getEditSuc() {
		return editSuc;
	}

	public void setEditSuc(String editSuc) {
		this.editSuc = editSuc;
	}

	public String getEditErr() {
		return editErr;
	}

	public void setEditErr(String editErr) {
		this.editErr = editErr;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Student get_student() {
		return _student;
	}

	public void set_student(Student _student) {
		this._student = _student;
	}
}
