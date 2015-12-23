package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import entities.Staff;
import model.SessionModel;
import model.StaffModel;

@ManagedBean(name = "templateController")
@SessionScoped
public class TemplateController {
	private Staff staff = new Staff();

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public void init() {
		if (SessionModel.sessionMap.get("user") != null) {
			staff = (Staff) SessionModel.sessionMap.get("user");
		}
	}
}
