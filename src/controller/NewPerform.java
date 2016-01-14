package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

import entities.Assignment;
import entities.Courseregister;
import entities.Perform;
import entities.PerformId;
import entities.Staff;
import model.AssignmentModel;
import model.CourseModel;
import model.CourseregisterModel;
import model.PerformModel;
import model.SessionModel;
import model.StaffModel;

@ManagedBean
@SessionScoped
public class NewPerform {
	private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	private Map<String, Object> sessionMap = externalContext.getSessionMap();
	private Map<String, String> params = externalContext.getRequestParameterMap();

	public void init() {
		externalContext = FacesContext.getCurrentInstance().getExternalContext();
		sessionMap = externalContext.getSessionMap();
		params = externalContext.getRequestParameterMap();
		sessionMap.put("title", "New Perform");
		if (!SessionModel.isPostback()) {
			String paramSetS = params.get("sets");
			String paramSetA = params.get("seta");
			performs = new PerformModel().findPerformStt(this.status);
			staffs = new StaffModel().findStaffActive();
			staff = staffs.get(0).getId();
			_staff = staffs.get(0).getId();
			divSet = "none";
			if (paramSetA != null && paramSetS != null) {
				String idStudent = paramSetS;
				int idAssgiment = Integer.parseInt(paramSetA);
				Perform zxc = new PerformModel().find(new PerformId(idStudent, idAssgiment));
				if (zxc != null) {
					perform = zxc;
					if (zxc.getStaff() != null)
						staff = zxc.getStaff().getId();
					System.out.println(zxc.getFile() + ":aaaaaaaaaaaaaaaaaaaaa");
					divSet = "block";
				}
			}
		}
	}

	public void filter() {
		performs = new PerformModel().findPerformStt(this.status);
	}

	public String saveStaffAss() {
		perform.setStaff(new StaffModel().find(staff));
		perform.setStatus(2);
		new PerformModel().update(perform);
		return "newperform?faces-redirect=true";
	}

	public void setStaffAss(Perform _perform) {
		_perform.setStaff(new StaffModel().find(_staff));
		_perform.setStatus(2);
		new PerformModel().update(_perform);
		performs = new PerformModel().findPerformStt(this.status);
	}

	public List<Perform> getPerforms() {
		return performs;
	}

	public void setPerforms(List<Perform> performs) {
		this.performs = performs;
	}

	public String getTableTag() {
		return tableTag;
	}

	public void setTableTag(String tableTag) {
		this.tableTag = tableTag;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<Staff> getStaffs() {
		return staffs;
	}

	public void setStaffs(List<Staff> staffs) {
		this.staffs = staffs;
	}

	public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	public Perform getPerform() {
		return perform;
	}

	public void setPerform(Perform perform) {
		this.perform = perform;
	}

	public String getDivSet() {
		return divSet;
	}

	public void setDivSet(String divSet) {
		this.divSet = divSet;
	}

	public String get_staff() {
		return _staff;
	}

	public void set_staff(String _staff) {
		this._staff = _staff;
	}

	private List<Perform> performs = new ArrayList<Perform>();
	private List<Staff> staffs = new ArrayList<Staff>();
	private String tableTag = "block";
	private int status = 1;
	private String staff = "";
	private String _staff = "";
	private Perform perform = new Perform();
	private String divSet = "none";
}
