package controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import entities.Student;
import model.SessionModel;
import model.StudentModel;

@ManagedBean(name = "studentController")
@SessionScoped
public class StudentController {
	public void init() {
		if (!SessionModel.isPostback()) {
			String paramAdd = SessionModel.params("add");
			String paramEdit = SessionModel.params.get("edit");
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
		if (SessionModel.get("addSuc") != null) {
			SessionModel.put("addSuc", null);
			addSuc = "block";
		} else
			addSuc = "none";

		if (SessionModel.get("addErr") != null) {
			SessionModel.put("addErr", null);
			addErr = "block";
		} else
			addErr = "none";
		// edit
		if (SessionModel.get("editSuc") != null) {
			SessionModel.put("editSuc", null);
			editSuc = "block";
		} else
			editSuc = "none";

		if (SessionModel.get("editErr") != null) {
			SessionModel.put("editErr", null);
			editErr = "block";
		} else
			editErr = "none";
		// pass
		if (SessionModel.get("passSuc") != null) {
			SessionModel.put("passSuc", null);
			passSuc = "block";
		} else
			passSuc = "none";

		if (SessionModel.get("passErr") != null) {
			SessionModel.put("passErr", null);
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

	public void save() {
		try {
			new StudentModel().create(_student);
			SessionModel.put("addSuc", true);
			_student = new Student();
		} catch (Exception e) {
			SessionModel.put("addErr", true);
		}
		SessionModel.redirect("student.xhtml?add=student");
	}

	public void saveChange() {
		try {
			new StudentModel().update(student);
			student = new StudentModel().find(student.getId());
			SessionModel.put("editSuc", true);
		} catch (Exception e) {
			SessionModel.put("editErr", true);
		}
		SessionModel.redirect("student.xhtml?edit=" + student.getId());
	}

	public void deleteThisStudent(Student _student) {
		if (_student.getCourseregisters().size() == 0) {
			new StudentModel().delete(new StudentModel().find(_student.getId()));
			System.out.println("Xóa Xong!!!!~!");
		}
		SessionModel.redirect("student.xhtml");
	}

	public void changePass() {
		try {
			new StudentModel().changePass(student, newPass);
			newPass = "";
			SessionModel.sessionMap.put("passSuc", true);
			SessionModel.redirect("student.xhtml?edit=" + student.getId());
		} catch (Exception e) {
			SessionModel.sessionMap.put("passErr", true);
			SessionModel.redirect("student.xhtml?edit=" + student.getId());
		}
	}

}
