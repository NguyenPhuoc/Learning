package controller;

import java.io.IOException;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import entities.Staff;
import model.SessionModel;
import model.StaffModel;

@ManagedBean(name = "templateController")
@SessionScoped
public class TemplateController {
	public TemplateController() {
		System.out.println("TemplateController.Contructor()");
	}

	private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	private Map<String, Object> sessionMap = externalContext.getSessionMap();
	private Map<String, String> params = externalContext.getRequestParameterMap();

	private Staff staff = new Staff();

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public void init() {
		System.out.println("init.template");
		if (!FacesContext.getCurrentInstance().isPostback()) {
			System.out.println("init.template.ispostback()");
			if (sessionMap.get("user") == null) {
				// SessionModel.redirect("login.xhtml");
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
					// System.out.println("------------------");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				staff = (Staff) sessionMap.get("user");
			}
		}
	}
}
