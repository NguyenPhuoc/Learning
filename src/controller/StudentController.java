package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import entities.Student;
import model.SessionModel;
import model.StudentModel;

@ManagedBean(name = "studentController")
@SessionScoped
public class StudentController {
	private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	private Map<String, Object> sessionMap = externalContext.getSessionMap();
	private Map<String, String> params = externalContext.getRequestParameterMap();

	public void init() {
		if (!SessionModel.isPostback()) {
			String paramAdd = params.get("add");
			String paramEdit = params.get("edit");
			if (paramAdd != null && paramAdd.equalsIgnoreCase("student")) {
				tableTag = "none";
				divAdd = "block";
			} else if (paramEdit != null) {
				student = new StudentModel().find(paramEdit);
				if (student != null) {
					divEdit = "block";
					tableTag = "none";
					if (student.getPerforms().size() == 0 && student.getCourseregisters().size() == 0)
						btnDel = "initial";
					else
						btnDel = "none";
				} else {
					students = new StudentModel().findAll();
					tableTag = "block";
					divAdd = "none";
					divEdit = "none";
				}
			} else {
				students = new StudentModel().findAll();
				tableTag = "block";
				divAdd = "none";
				divEdit = "none";
			}
		}
		// ===============================
		// add
		if (sessionMap.get("addSuc") != null) {
			sessionMap.put("addSuc", null);
			addSuc = "block";
		} else
			addSuc = "none";

		if (sessionMap.get("addErr") != null) {
			sessionMap.put("addErr", null);
			addErr = "block";
		} else
			addErr = "none";
		// edit
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
		// pass
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

	private String tableTag = "block";
	private List<Student> students = new ArrayList<Student>();
	private String divAdd = "none";
	private Student student = new Student();
	private Student _student = new Student();
	private String addSuc = "none";
	private String addErr = "none";
	private String divEdit = "none";
	private String btnDel = "none";
	private String editSuc = "none";
	private String editErr = "none";
	private String newPass = "";
	private String passSuc = "none";
	private String passErr = "none";

	public String getPassSuc() {
		return passSuc;
	}

	public String getPassErr() {
		return passErr;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getEditSuc() {
		return editSuc;
	}

	public String getEditErr() {
		return editErr;
	}

	public String getBtnDel() {
		return btnDel;
	}

	public String getDivEdit() {
		return divEdit;
	}

	public String getAddErr() {
		return addErr;
	}

	public String getAddSuc() {
		return addSuc;
	}

	public Student getStudent() {
		return student;
	}

	public Student get_student() {
		return _student;
	}

	public String getDivAdd() {
		return divAdd;
	}

	public List<Student> getStudents() {
		return students;
	}

	public String getTableTag() {
		return tableTag;
	}

	public void save() throws IOException {
		try {
			new StudentModel().create(_student);
			sessionMap.put("addSuc", true);
			_student = new Student();
		} catch (Exception e) {
			sessionMap.put("addErr", true);
		}
		externalContext.redirect("student.xhtml?add=student");
	}

	public void saveChange() throws IOException {
		try {
			new StudentModel().update(student);
			student = new StudentModel().find(student.getId());
			sessionMap.put("editSuc", true);
		} catch (Exception e) {
			sessionMap.put("editErr", true);
		}
		externalContext.redirect("student.xhtml?edit=" + student.getId());
	}

	public void deleteThisStudent(Student _student) throws IOException {
		if (_student.getCourseregisters().size() == 0) {
			new StudentModel().delete(new StudentModel().find(_student.getId()));
			System.out.println("Xóa Xong!!!!~!");
		}
		externalContext.redirect("student.xhtml");
	}

	public void changePass() throws IOException {
		try {
			new StudentModel().changePass(student, newPass);
			newPass = "";
			sessionMap.put("passSuc", true);
			externalContext.redirect("student.xhtml?edit=" + student.getId());
		} catch (Exception e) {
			sessionMap.put("passErr", true);
			externalContext.redirect("student.xhtml?edit=" + student.getId());
		}
	}

}
