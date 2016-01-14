package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import entities.Course;
import entities.Courseregister;
import entities.CourseregisterId;
import entities.Student;
import model.CourseModel;
import model.CourseregisterModel;
import model.SessionModel;

@ManagedBean
@SessionScoped
public class CourseClient {
	public void init() {
		externalContext = FacesContext.getCurrentInstance().getExternalContext();
		sessionMap = externalContext.getSessionMap();
		params = externalContext.getRequestParameterMap();
		if (!SessionModel.isPostback()) {
			String paramCourse = params.get("course");
			if (paramCourse != null) {
				try {
					divCourse = false;
					divTable = true;
					if (courses.size() == 0)
						courses = new CourseModel().findAllClent(new java.text.SimpleDateFormat("yyyy-MM-dd")
								.format(java.util.Calendar.getInstance().getTime()));
					for (Course _course : courses) {
						if (_course.getId() == Integer.parseInt(paramCourse)) {
							this.course = _course;
							divCourse = true;
							divTable = false;
						}
					}
				} catch (Exception e) {
					divCourse = false;
					divTable = true;
				}
			} else {
				courses = new CourseModel().findAllClent(new java.text.SimpleDateFormat("yyyy-MM-dd")
						.format(java.util.Calendar.getInstance().getTime()));
				divTable = true;
				divCourse = false;
			}
		}
	}

	public String register(Course course) {
		Student student = (Student) sessionMap.get("student");
		System.out.println(student.getId());
		Courseregister courseregister = new Courseregister();
		courseregister.setId(new CourseregisterId(student.getId(), course.getId()));
		courseregister.setCourse(course);
		courseregister.setStudent(student);
		courseregister.setStatus(1);
		new CourseregisterModel().create(courseregister);
		return "course?faces-redirect=true";
	}

	public boolean check(Course course) {
		Student student = (Student) sessionMap.get("student");
		boolean ck = new CourseregisterModel().check(student.getId(), course.getId().toString());
		return ck;
	}

	public void view(Course course) {
		this.course = course;
		divCourse = true;
	}

	private ExternalContext externalContext;
	private Map<String, Object> sessionMap;
	private Map<String, String> params;
	private List<Course> courses = new ArrayList<Course>();
	private Course course = new Course();
	private boolean divTable = true;
	private boolean divCourse = false;

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public boolean isDivTable() {
		return divTable;
	}

	public void setDivTable(boolean divTable) {
		this.divTable = divTable;
	}

	public boolean isDivCourse() {
		return divCourse;
	}

	public void setDivCourse(boolean divCourse) {
		this.divCourse = divCourse;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
}
