package controller;

import java.io.IOException;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import entities.Role;
import entities.Staff;
import model.SessionModel;
import model.StaffModel;

@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController {
	private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	private Map<String, Object> sessionMap = externalContext.getSessionMap();
	private Map<String, String> params = externalContext.getRequestParameterMap();

	private Staff staff = new Staff();
	private String notice = "";

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public void checkIsLogin() throws IOException {
		System.out.println("checkIsLogin no post");
		if (!FacesContext.getCurrentInstance().isPostback()) {
			if (sessionMap.get("user") != null)
				externalContext.redirect("index.xhtml");
			System.out.println("checkIsLogin");
		}
	}

	public void login() {
		System.out.println("Login");
		try {
			Staff _staff = new StaffModel().login(this.staff);

			if (_staff != null && _staff.getStatus() == 1) {
				sessionMap.put("user", _staff);
				Role role = _staff.getRole();
				sessionMap.put("role", role);
				externalContext.redirect("index.xhtml");
			} else {
				this.notice = "Username or password invalid";
				this.staff.setPassword(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void logout() throws IOException {
		staff = new Staff();
		this.notice = "";
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		externalContext.redirect("login.xhtml");
	}
}
