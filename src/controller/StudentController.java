package controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import entities.Student;
import model.SessionModel;
import model.StaffModel;
import model.StudentModel;

@ManagedBean(name = "studentController")
@SessionScoped
public class StudentController {
	public void init() {
		String paramAdd = SessionModel.params("add");
		if (paramAdd != null && paramAdd.equalsIgnoreCase("student")) {
			tableTag = "none";
			divAdd = "block";
		} else {
			students = new StudentModel().findAll();
			tableTag = "block";
			divAdd = "none";
		}
		divEdit = "none";
		String paramEdit = SessionModel.params.get("edit");
		if (paramEdit != null) {
			student = new StudentModel().find(paramEdit);
			if (student != null)
				divEdit = "block";
			if (student.getPerforms().size() == 0 && student.getCourseregisters().size() == 0)
				btnDel = "initial";
			else
				btnDel = "none";
		}

		// ===============================
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
		SessionModel.redirect("/admin/student.xhtml?add=student");
	}

	public void deleteThisStaff(Student _student) {
		if (_student.getPerforms().size() == 0 && _student.getCourseregisters().size() == 0) {
			new StudentModel().delete(new StudentModel().find(_student.getId()));
			System.out.println("Xóa Xong!!!!~!");
		}
		SessionModel.redirect("/admin/student.xhtml");
	}

}
