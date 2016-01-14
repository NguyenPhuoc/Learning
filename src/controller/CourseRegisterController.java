package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

import entities.Courseregister;
import entities.CourseregisterId;
import model.AssignmentModel;
import model.CourseModel;
import model.CourseregisterModel;
import model.SessionModel;

@ManagedBean(name = "courseRegisterController")
@SessionScoped
public class CourseRegisterController {
	private ExternalContext externalContext;
	private Map<String, Object> sessionMap;
	private Map<String, String> params;

	public void init() {
		externalContext = FacesContext.getCurrentInstance().getExternalContext();
		sessionMap = externalContext.getSessionMap();
		params = externalContext.getRequestParameterMap();
		sessionMap.put("title", "Student Register Course");
		if (!SessionModel.isPostback()) {
			String paramAdd = params.get("add");
			String paramEdit = params.get("edit");
			courseregisters = new CourseregisterModel().findCourseregisteNew(status);
			// System.out.println(courseregisters.size()+"size");

			// if (paramAdd != null && paramAdd.equalsIgnoreCase("course")) {
			// tableTag = "none";
			// divAdd = "block";
			// } else if (paramEdit != null) {
			// try {
			// course = new CourseModel().find(Integer.parseInt(paramEdit));
			// } catch (Exception e) {
			// course = null;
			// }
			// if (course != null) {
			// divEdit = "block";
			// tableTag = "none";
			// if (course.getAssignments().size() == 0 &&
			// course.getCourseregisters().size() == 0)
			// btnDel = "initial";
			// assignments = new AssignmentModel().findAssg(course.getId());
			// } else {
			// courses = new CourseModel().findAll();
			// tableTag = "block";
			// divAdd = "none";
			// divEdit = "none";
			// }
			// } else {
			// courses = new CourseModel().findAll();
			// tableTag = "block";
			// divAdd = "none";
			// divEdit = "none";
			// }
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

	public void allow(Courseregister courseregister) {
		externalContext = FacesContext.getCurrentInstance().getExternalContext();
		System.out.println(courseregister.getCourse().getName());
		courseregister.setStatus(2);
		new CourseregisterModel().update(courseregister);
		try {
			courseregisters = new CourseregisterModel().findCourseregisteNew(status);
			externalContext.redirect("courseregister.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void notAllow(Courseregister courseregister) {
		externalContext = FacesContext.getCurrentInstance().getExternalContext();
		System.out.println(courseregister.getCourse().getName());
		courseregister.setStatus(3);
		new CourseregisterModel().update(courseregister);
		try {
			courseregisters = new CourseregisterModel().findCourseregisteNew(status);
			externalContext.redirect("courseregister.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean showAllow(Courseregister courseregister) {
		java.util.Date today = java.util.Calendar.getInstance().getTime();
		if (!today.after(courseregister.getCourse().getEndDate()))
			return true;
		else
			return false;
	}
	public boolean showNotAllow(Courseregister courseregister) {
		java.util.Date today = java.util.Calendar.getInstance().getTime();
		if (!today.after(courseregister.getCourse().getStartDate()))
			return true;
		else
			return false;
	}

	public void filter() {
		courseregisters = new CourseregisterModel().findCourseregisteNew(status);
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

	public String getBtnDel() {
		return btnDel;
	}

	public void setBtnDel(String btnDel) {
		this.btnDel = btnDel;
	}

	public List<Courseregister> getCourseregisters() {
		return courseregisters;
	}

	public void setCourseregisters(List<Courseregister> courseregisters) {
		this.courseregisters = courseregisters;
	}

	public Courseregister getCourseregister() {
		return courseregister;
	}

	public void setCourseregister(Courseregister courseregister) {
		this.courseregister = courseregister;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	private List<Courseregister> courseregisters = new ArrayList<Courseregister>();
	private Courseregister courseregister = new Courseregister();
	private int status = 1;
	private String tableTag = "block";
	private String divAdd = "none";
	private String addSuc = "none";
	private String addErr = "none";
	private String divEdit = "none";
	private String editSuc = "none";
	private String editErr = "none";
	private String btnDel = "none";

}
