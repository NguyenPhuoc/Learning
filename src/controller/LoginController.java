package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import entities.Staff;
import model.SessionModel;
import model.StaffModel;

@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController {
//	public void checkIsNotLogin() {
//		if (!FacesContext.getCurrentInstance().isPostback())
//			if (SessionModel.sessionMap.get("user") == null) {
//				SessionModel.redirect("login.xhtml");
//			}
//	}

	private Staff staff = new Staff();
	private String notice;

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

	public void checkIsLogin() {
		System.out.println("checkIsLogin");
		if (!FacesContext.getCurrentInstance().isPostback())
			if (SessionModel.sessionMap.get("user") != null)
				SessionModel.redirect("index.xhtml");
	}

	public void login() {
		System.out.println("Login");
		try {
			Staff staff = new StaffModel().login(this.staff);

			if (staff != null && staff.getStatus() == 1) {
				SessionModel.sessionMap.put("user", staff);
				//return "/admin/index?faces-redirect=true";
				SessionModel.redirect("index.xhtml");
			} else {
				this.notice = "Username or password invalid";
				this.staff.setPassword(null);
				//return null;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			//return null;
		}
	}

	public void logout() {
		staff = new Staff();
		this.notice = null;
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		SessionModel.redirect("login.xhtml");
	}
}
