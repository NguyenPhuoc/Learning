package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import entities.Assignment;
import entities.Courseregister;
import entities.Perform;
import entities.PerformId;
import entities.Student;
import helper.UploadHelper;
import model.AssignmentModel;
import model.CourseregisterModel;
import model.PerformModel;
import model.SessionModel;
import sun.net.www.content.text.plain;

@ManagedBean
@SessionScoped
public class AssignmentClient {
	public void init() {
		externalContext = FacesContext.getCurrentInstance().getExternalContext();
		sessionMap = externalContext.getSessionMap();
		params = externalContext.getRequestParameterMap();
		if (!SessionModel.isPostback()) {
			String paramAssig = params.get("assig");
			if (paramAssig != null)
				try {
					assig = new AssignmentModel().find(Integer.parseInt(paramAssig));
				} catch (Exception e) {
					assig = null;
				}
			divAssig = false;
			divTable = true;
			if (paramAssig != null && assig != null) {
				Student student = (Student) sessionMap.get("student");
				Perform perform = new PerformModel().find(new PerformId(student.getId(), assig.getId()));
				if (perform == null)
					for (Courseregister courseregister : new CourseregisterModel().findCourseStudent(student.getId())) {
						if (courseregister.getStatus() == 2 && courseregister.getCourse() == assig.getCourse()) {
							divAssig = true;
							divTable = false;
							break;
						}
					}
			} else if (sessionMap.get("student") != null) {
				Student student = (Student) sessionMap.get("student");
				assignments = new ArrayList<Assignment>();
				List<Courseregister> courseregisters = new CourseregisterModel().findCourseStudent(student.getId());
				for (Courseregister courseregister : courseregisters) {
					for (Assignment assignment : courseregister.getCourse().getAssignments()) {
						Perform perform = new PerformModel().find(new PerformId(student.getId(), assignment.getId()));
						if (perform == null)
							assignments.add(assignment);
					}
				}
				divTable = true;
			}
			// new
			// java.text.SimpleDateFormat("yyyy-MM-dd").format(java.util.Calendar.getInstance().getTime());
		}
	}

	public String submit() {
		try {
			Student student = (Student) sessionMap.get("student");
			Perform perform = new Perform();
			perform.setId(new PerformId(student.getId(), assig.getId()));
			perform.setFile(new UploadHelper().processUpload(this.p));
			perform.setStatus(1);
			perform.setUploadDate(java.util.Calendar.getInstance().getTime());
			if (!perform.getFile().equals("nofile")) {
				new PerformModel().create(perform);
				return "assginment?faces-redirect=true";
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	private ExternalContext externalContext;
	private Map<String, Object> sessionMap;
	private Map<String, String> params;
	private List<Assignment> assignments = new ArrayList<Assignment>();
	private Assignment assig = new Assignment();
	private boolean divTable = false;
	private boolean divAssig = false;
	private Part p;

	public Part getP() {
		return p;
	}

	public void setP(Part p) {
		this.p = p;
	}

	public boolean isDivAssig() {
		return divAssig;
	}

	public void setDivAssig(boolean divAssig) {
		this.divAssig = divAssig;
	}

	public boolean isDivTable() {
		return divTable;
	}

	public void setDivTable(boolean divTable) {
		this.divTable = divTable;
	}

	public Assignment getAssig() {
		return assig;
	}

	public void setAssig(Assignment assig) {
		this.assig = assig;
	}

	public List<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}
}
