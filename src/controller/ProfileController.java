package controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.*;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.jsoup.Jsoup;

import entities.Role;
import entities.Staff;
import model.Hash;
import model.StaffModel;

@ManagedBean(name = "profileController")
@SessionScoped
public class ProfileController implements Serializable {
	private ExternalContext externalContext;
	private Map<String, Object> sessionMap;
	private Map<String, String> params;

	private static final long serialVersionUID = 1L;

	private Staff staff = new Staff();
	private Role role = new Role();
	private Staff _staff = new Staff();

	public Role getRole() {
		return role;
	}

	public Staff get_staff() {
		return _staff;
	}

	public void set_staff(Staff _staff) {
		this._staff = _staff;
	}

	private String pass;
	private String newPass;
	private String rePass;
	private String displayChangeInfo = "none";
	private String displayChangeInfoFalse = "none";
	private String displayPassInvalid = "none";
	private String displayChangePass = "none";
	private String displayPassComfirm = "none";

	public String getDisplayChangeInfoFalse() {
		return displayChangeInfoFalse;
	}

	public void setDisplayChangeInfoFalse(String displayChangeInfoFalse) {
		this.displayChangeInfoFalse = displayChangeInfoFalse;
	}

	public String getDisplayPassInvalid() {
		return displayPassInvalid;
	}

	public void setDisplayPassInvalid(String displayPassInvalid) {
		this.displayPassInvalid = displayPassInvalid;
	}

	public String getDisplayChangePass() {
		return displayChangePass;
	}

	public void setDisplayChangePass(String displayChangePass) {
		this.displayChangePass = displayChangePass;
	}

	public String getDisplayPassComfirm() {
		return displayPassComfirm;
	}

	public void setDisplayPassComfirm(String displayPassComfirm) {
		this.displayPassComfirm = displayPassComfirm;
	}

	public String getDisplayChangeInfo() {
		return displayChangeInfo;
	}

	public void setDisplayChangeInfo(String displayChangeInfo) {
		this.displayChangeInfo = displayChangeInfo;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getRePass() {
		return rePass;
	}

	public void setRePass(String rePass) {
		this.rePass = rePass;
	}

	public void Init() {
		externalContext = FacesContext.getCurrentInstance().getExternalContext();
		sessionMap = externalContext.getSessionMap();
		params = externalContext.getRequestParameterMap();
		sessionMap.put("title", "Profile");
		if (sessionMap.get("user") != null) {
			staff = (Staff) sessionMap.get("user");
			_staff.setEmail(staff.getEmail());
			_staff.setId(staff.getId());
			_staff.setName(staff.getName());
			_staff.setPassword(staff.getPassword());
			_staff.setRole(staff.getRole());
			_staff.setStatus(staff.getStatus());
			_staff.setId(staff.getId());
			_staff.setAddress(staff.getAddress());
		}

		if (sessionMap.get("role") != null)
			role = (Role) sessionMap.get("role");

		if (sessionMap.get("changeinfo") != null) {
			sessionMap.put("changeinfo", null);
			this.displayChangeInfo = "block";
		} else
			this.displayChangeInfo = "none";

		if (sessionMap.get("changeinfofalse") != null) {
			sessionMap.put("changeinfofalse", null);
			this.displayChangeInfoFalse = "block";
		} else
			this.displayChangeInfoFalse = "none";

		if (sessionMap.get("changepass") != null) {
			sessionMap.put("changepass", null);
			this.displayChangePass = "block";
		} else
			this.displayChangePass = "none";

		if (sessionMap.get("passcfm") != null) {
			sessionMap.put("passcfm", null);
			this.displayPassComfirm = "block";
		} else
			this.displayPassComfirm = "none";

		if (sessionMap.get("pass") != null) {
			sessionMap.put("pass", null);
			this.displayPassInvalid = "block";
		} else
			this.displayPassInvalid = "none";

	}

	public void saveChangeInfo() {
		externalContext = FacesContext.getCurrentInstance().getExternalContext();
		sessionMap = externalContext.getSessionMap();
		params = externalContext.getRequestParameterMap();
		try {
			if (Jsoup.parse(_staff.getEmail()).text().length() == 0
					|| Jsoup.parse(_staff.getName()).text().length() == 0
					|| Jsoup.parse(_staff.getAddress()).text().length() == 0) {

				sessionMap.put("changeinfofalse", true);
			} else if (Jsoup.parse(_staff.getEmail()).text().length() > 254
					|| Jsoup.parse(_staff.getName()).text().length() > 50
					|| Jsoup.parse(_staff.getAddress()).text().length() > 100) {

				sessionMap.put("changeinfofalse", true);
			} else {
				_staff.setAddress(Jsoup.parse(_staff.getAddress()).text());
				_staff.setEmail(Jsoup.parse(_staff.getEmail()).text());
				_staff.setName(Jsoup.parse(_staff.getName()).text());
				StaffModel staffModel = new StaffModel();
				staffModel.update(_staff);
				sessionMap.put("user", _staff);
				sessionMap.put("changeinfo", true);
			}
			// SessionModel.reLoadPage();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Catch Save");

			sessionMap.put("changeinfofalse", true);
		}
	}

	public void saveChangePass() throws IOException {
		externalContext = FacesContext.getCurrentInstance().getExternalContext();
		sessionMap = externalContext.getSessionMap();
		params = externalContext.getRequestParameterMap();
		if (!staff.getPassword().equals(Hash.getHashMD5(pass))) {
			sessionMap.put("pass", true);
			externalContext.redirect("profile.xhtml?pas=false#changepass");
			// SessionModel.reLoadPage();
		} else if (!newPass.equals(rePass)) {
			sessionMap.put("passcfm", true);
			// SessionModel.reLoadPage();
			externalContext.redirect("profile.xhtml?pass=cfm#changepass");
		} else {
			StaffModel staffModel = new StaffModel();
			_staff.setPassword(Hash.getHashMD5(newPass));
			staffModel.update(_staff);
			sessionMap.put("user", _staff);
			sessionMap.put("changepass", true);
			// SessionModel.reLoadPage();
			externalContext.redirect("profile.xhtml?pass=suc#changepass");
		}
	}

	public ProfileController() {
		System.out.println("ProfileController");
	}

	// private void info() {
	// if (!SessionModel.isPostback())
	// if (sessionMap.get("user") != null) {
	// staff = (Staff) sessionMap.get("user");
	// _staff.setEmail(staff.getEmail());
	// _staff.setId(staff.getId());
	// _staff.setName(staff.getName());
	// _staff.setPassword(staff.getPassword());
	// _staff.setRole(staff.getRole());
	// _staff.setStatus(staff.getStatus());
	// _staff.setId(staff.getId());
	// _staff.setAddress(staff.getAddress());
	// }
	// }

	public String linkProfile() {
		return "profile?faces-redirect=true";
	}
}
