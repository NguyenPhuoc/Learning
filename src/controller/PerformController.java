package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import entities.Perform;
import entities.PerformId;
import entities.Staff;
import model.PerformModel;
import model.SessionModel;
import model.StaffModel;

@ManagedBean
@SessionScoped
public class PerformController {
	private ExternalContext externalContext;
	private Map<String, Object> sessionMap;
	private Map<String, String> params;

	public void init() {
		externalContext = FacesContext.getCurrentInstance().getExternalContext();
		sessionMap = externalContext.getSessionMap();
		params = externalContext.getRequestParameterMap();
		if (!SessionModel.isPostback() && sessionMap.get("userS") != null) {
			String paramSetS = params.get("sets");
			String paramSetA = params.get("seta");
			Staff _staff = new Staff();
			_staff = (Staff) sessionMap.get("userS");
			staff = _staff.getId();
			performs = new PerformModel().findPerformStaff(staff, status);
			divSet = "none";
			point = "";
			if (paramSetA != null && paramSetS != null) {
				String idStudent = paramSetS;
				int idAssgiment = Integer.parseInt(paramSetA);
				Perform zxc = new PerformModel().find(new PerformId(idStudent, idAssgiment));
				if (zxc != null) {
					perform = zxc;
					if (zxc.getPoint() != null)
						point = zxc.getPoint().toString();
					if (zxc.getStaff() != null)
						staff = zxc.getStaff().getId();
					divSet = "block";
				}
			}
		}
	}

	public void filter() {
		performs = new PerformModel().findPerformStt(this.status);
	}

	public String saveStaffAss() {
		if (perform.getStatus() == 2) {
			perform.setPoint(Integer.parseInt(point));
			perform.setStatus(3);
			new PerformModel().update(perform);
			return "index?faces-redirect=true";
		}
		return null;
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

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	private List<Perform> performs = new ArrayList<Perform>();
	private String tableTag = "block";
	private int status = 2;
	private String staff = "";
	private Perform perform = new Perform();
	private String divSet = "none";
	private String point = "";
}
