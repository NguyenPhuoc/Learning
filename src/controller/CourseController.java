package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import entities.Assignment;
import entities.Course;
import entities.Courseregister;
import helper.UploadHelper;
import model.AssignmentModel;
import model.CourseModel;
import model.CourseregisterModel;
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
			String paramCourse = params.get("course");
			if (paramAdd != null && paramAdd.equalsIgnoreCase("course")) {
				tableTag = "none";
				divAdd = "block";
				tableCourse = "none";
				divEdit = "none";
			} else if (paramEdit != null) {
				String paramAss = params.get("ass");
				try {
					course = new CourseModel().find(Integer.parseInt(paramEdit));
				} catch (Exception e) {
					course = null;
				}
				if (course != null) {
					divEdit = "block";
					tableTag = "none";
					tableCourse = "none";
					if (course.getAssignments().size() == 0 && course.getCourseregisters().size() == 0)
						btnDel = "initial";
					assignments = new AssignmentModel().findAssg(course.getId());
					if (paramAss != null) {
						try {
							assignment = new AssignmentModel().find(Integer.parseInt(paramAss));
						} catch (Exception e) {
							assignment = null;
							System.out.println("aaaaaaaaaaaamhagsjdgjasdgjgjdgjsag");
						}
						if (assignment != null) {
							divAss = "block";
							if (assignment.getPerforms().size() == 0)
								btnDelA = "initial";
							else
								btnDelA = "none";
						} else
							divAss = "none";

					} else {
						divAss = "none";
					}
				} else {
					courses = new CourseModel().findAll();
					tableTag = "block";
					divAdd = "none";
					divEdit = "none";
					tableCourse = "none";
				}
			} else if (paramCourse != null) {
				try {
					courseregisters = new CourseregisterModel().findCourseregister(Integer.parseInt(paramCourse));
				} catch (Exception e) {
					courseregisters = new ArrayList<Courseregister>();
				}
				if (courseregisters.size() != 0) {
					tableCourse = "block";
					tableTag = "none";
				} else {
					courses = new CourseModel().findAll();
					tableTag = "block";
					divAdd = "none";
					divEdit = "none";
					tableCourse = "none";
				}
			} else {
				courses = new CourseModel().findAll();
				tableTag = "block";
				divAdd = "none";
				divEdit = "none";
				tableCourse = "none";
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
		// ====Ass
		if (sessionMap.get("editSucA") != null) {
			sessionMap.put("editSucA", null);
			editSucA = "block";
		} else
			editSucA = "none";

		if (sessionMap.get("editErrA") != null) {
			sessionMap.put("editErrA", null);
			editErrA = "block";
		} else
			editErrA = "none";
		// =====
		if (sessionMap.get("addSucA") != null) {
			sessionMap.put("addSucA", null);
			addSucA = "block";
		} else
			addSucA = "none";

		if (sessionMap.get("addErrA") != null) {
			sessionMap.put("addErrA", null);
			addErrA = "block";
		} else
			addErrA = "none";
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

	public void saveChangeAss() {
		try {
			String fi = "";
			if (this.p != null) {
				fi = new UploadHelper().processUpload(this.p);
			}
			if (!fi.equals("nofile")) {
				assignment.setFile(fi);
				new AssignmentModel().update(assignment);
				sessionMap.put("editSucA", true);
				externalContext.redirect("course.xhtml?edit=" + course.getId() + "&ass=" + assignment.getId());
			}
		} catch (Exception e) {
			sessionMap.put("editErrA", true);
			System.out.println("catch add Asss");
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

	public void daleteThisAss(Assignment assignment) {
		try {
			int idA = assignment.getCourse().getId();
			if (assignment.getPerforms().size() == 0)
				new AssignmentModel().delete(new AssignmentModel().find(assignment.getId()));
			externalContext.redirect("course.xhtml?edit=" + idA);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveAss() {
		try {
			_assignment.setFile(new UploadHelper().processUpload(this.p));
			_assignment.setCourse(course);
			if (!_assignment.getFile().equals("nofile")) {
				new AssignmentModel().create(_assignment);
				sessionMap.put("addSucA", true);
				_assignment = new Assignment();
				externalContext.redirect("course.xhtml?edit=" + course.getId());
			}
		} catch (Exception e) {
			sessionMap.put("addErrA", true);
			System.out.println("catch add  Asss");
		}
		System.out.println(_assignment.getFile());
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

	public Assignment get_assignment() {
		return _assignment;
	}

	public void set_assignment(Assignment _assignment) {
		this._assignment = _assignment;
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

	public List<Courseregister> getCourseregisters() {
		return courseregisters;
	}

	public void setCourseregisters(List<Courseregister> courseregisters) {
		this.courseregisters = courseregisters;
	}

	public String getTableCourse() {
		return tableCourse;
	}

	public void setTableCourse(String tableCourse) {
		this.tableCourse = tableCourse;
	}

	public Part getP() {
		return p;
	}

	public void setP(Part p) {
		this.p = p;
	}

	public String getDivAss() {
		return divAss;
	}

	public void setDivAss(String divAss) {
		this.divAss = divAss;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	public String getEditSucA() {
		return editSucA;
	}

	public void setEditSucA(String editSucA) {
		this.editSucA = editSucA;
	}

	public String getEditErrA() {
		return editErrA;
	}

	public void setEditErrA(String editErrA) {
		this.editErrA = editErrA;
	}

	public String getBtnDelA() {
		return btnDelA;
	}

	public void setBtnDelA(String btnDelA) {
		this.btnDelA = btnDelA;
	}

	public void setBtnDel(String btnDel) {
		this.btnDel = btnDel;
	}

	public String getAddSucA() {
		return addSucA;
	}

	public void setAddSucA(String addSucA) {
		this.addSucA = addSucA;
	}

	public String getAddErrA() {
		return addErrA;
	}

	public void setAddErrA(String addErrA) {
		this.addErrA = addErrA;
	}

	private List<Course> courses = new ArrayList<Course>();
	private List<Assignment> assignments = new ArrayList<Assignment>();
	private List<Courseregister> courseregisters = new ArrayList<Courseregister>();
	private String tableCourse = "none";
	private Part p;

	private Course course = new Course();
	private Course _course = new Course();
	private Assignment assignment = new Assignment();
	private Assignment _assignment = new Assignment();
	private String tableTag = "block";
	private String divAdd = "none";
	private String divAss = "none";
	private String addSuc = "none";
	private String addErr = "none";
	private String divEdit = "none";
	private String editSuc = "none";
	private String editErr = "none";
	private String btnDel = "none";
	private String editSucA = "none";
	private String editErrA = "none";
	private String btnDelA = "none";
	private String addSucA = "none";
	private String addErrA = "none";

}
