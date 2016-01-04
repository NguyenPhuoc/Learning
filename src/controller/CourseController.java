package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import entities.Assignment;
import entities.Course;
import model.AssignmentModel;
import model.CourseModel;
import model.SessionModel;

@ManagedBean(name = "courseController")
@SessionScoped
public class CourseController {
	private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	private Map<String, Object> sessionMap = externalContext.getSessionMap();
	private Map<String, String> params = externalContext.getRequestParameterMap();

	public void init() {
		if (!SessionModel.isPostback()) {
			String paramAdd = params.get("add");
			String paramEdit = params.get("edit");
			if (paramAdd != null && paramAdd.equalsIgnoreCase("course")) {
				tableTag = "none";
				divAdd = "block";
			} else if (paramEdit != null) {
				try {
					course = new CourseModel().find(Integer.parseInt(paramEdit));
				} catch (Exception e) {
					course = null;
				}
				if (course != null) {
					divEdit = "block";
					tableTag = "none";
					if (course.getAssignments().size() == 0 && course.getCourseregisters().size() == 0)
						btnDel = "initial";
					assignments = new AssignmentModel().findAssg(course.getId());
				} else {
					courses = new CourseModel().findAll();
					tableTag = "block";
					divAdd = "none";
					divEdit = "none";
				}
			} else {
				courses = new CourseModel().findAll();
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
	}

	public void save() {
		try {
			new CourseModel().create(_course);
			_course = new Course();
			sessionMap.put("addSuc", true);
			externalContext.redirect("course.xhtml?add=course");
		} catch (Exception e) {
			sessionMap.put("addErr", true);
			System.out.println("catch save faq");
		}
	}

	public void saveChange() {
		try {
			new CourseModel().update(course);
			sessionMap.put("editSuc", true);
			externalContext.redirect("course.xhtml?edit=" + course.getId());
		} catch (Exception e) {
			sessionMap.put("editErr", true);
			System.out.println("catch save change course");
		}
	}

	public void daleteThisCourse(Course course) {
		try {
			if (course.getAssignments().size() == 0 && course.getCourseregisters().size() == 0)
				new CourseModel().delete(new CourseModel().find(course.getId()));
			externalContext.redirect("course.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getTableTag() {
		return tableTag;
	}

	public void setTableTag(String tableTag) {
		this.tableTag = tableTag;
	}

	public String getDivAdd() {
		return divAdd;
	}

	public void setDivAdd(String divAdd) {
		this.divAdd = divAdd;
	}

	public String getAddSuc() {
		return addSuc;
	}

	public void setAddSuc(String addSuc) {
		this.addSuc = addSuc;
	}

	public String getAddErr() {
		return addErr;
	}

	public void setAddErr(String addErr) {
		this.addErr = addErr;
	}

	public String getDivEdit() {
		return divEdit;
	}

	public void setDivEdit(String divEdit) {
		this.divEdit = divEdit;
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

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Course get_course() {
		return _course;
	}

	public void set_course(Course _course) {
		this._course = _course;
	}

	public String getBtnDel() {
		return btnDel;
	}

	public List<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}

	private String tableTag = "block";
	private List<Course> courses = new ArrayList<Course>();
	private List<Assignment> assignments = new ArrayList<Assignment>();
	private String divAdd = "none";
	private Course course = new Course();
	private Course _course = new Course();
	private String addSuc = "none";
	private String addErr = "none";
	private String divEdit = "none";
	private String editSuc = "none";
	private String editErr = "none";
	private String btnDel = "none";

}
