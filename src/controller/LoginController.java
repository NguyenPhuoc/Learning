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
	private boolean isLogin = false;

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

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
			// if (sessionMap.get("user") != null)
			if (this.isLogin)
				externalContext.redirect("index.xhtml");
			System.out.println("checkIsLogin");
		}
	}

	public void verifyLogin() {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			System.out.println("init.template .ispostback");
			// if (sessionMap.get("user") == null) {
			if (!this.isLogin) {
				try {
					externalContext.redirect("login.xhtml");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				staff = (Staff) sessionMap.get("user");
			}
		}
		int[] ls = new int[] { 3, 6, 7, 2, 5, 8, 9 };
	}

	public void login() {
		System.out.println("Login");
		try {
			Staff _staff = new StaffModel().login(this.staff);

			if (_staff != null && _staff.getStatus() == 1) {
				sessionMap.put("user", _staff);
				Role role = _staff.getRole();
				sessionMap.put("role", role);
				this.isLogin = true;
				externalContext.redirect("index.xhtml");
			} else {
				this.notice = "Username or password invalid";
				this.staff.setPassword(null);
				this.isLogin = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.isLogin = false;
		}
	}

	public void logout() throws IOException {
		staff = new Staff();
		this.notice = "";
		this.isLogin = false;
		externalContext.invalidateSession();
		externalContext.redirect("login.xhtml");
	}
}
