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
		if (!FacesContext.getCurrentInstance().isPostback()) {
			if (sessionMap.get("user") == null) {
				// SessionModel.redirect("login.xhtml");
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
					System.out.println("dmmmmmmmmmmmmmmmmmmmmmmmmmmmm============================");
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
