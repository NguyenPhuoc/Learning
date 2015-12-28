package controller;

import java.io.Serializable;

import javax.faces.bean.*;

import org.jsoup.Jsoup;

import entities.Staff;
import model.Hash;
import model.SessionModel;
import model.StaffModel;

@ManagedBean(name = "profileController")
@SessionScoped
public class ProfileController implements Serializable {
	private static final long serialVersionUID = 1L;

	private Staff staff = new Staff();
	private Staff _staff = new Staff();

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
		if (SessionModel.sessionMap.get("user") != null) {
			staff = (Staff) SessionModel.sessionMap.get("user");
			_staff.setEmail(staff.getEmail());
			_staff.setId(staff.getId());
			_staff.setName(staff.getName());
			_staff.setPassword(staff.getPassword());
			_staff.setRole(staff.getRole());
			_staff.setStatus(staff.getStatus());
			_staff.setId(staff.getId());
			_staff.setAddress(staff.getAddress());
		}

//		if (SessionModel.sessionMap.get("user") != null)
//			staff = (Staff) SessionModel.sessionMap.get("user");

		if (SessionModel.sessionMap.get("changeinfo") != null) {
			SessionModel.sessionMap.put("changeinfo", null);
			this.displayChangeInfo = "block";
		} else
			this.displayChangeInfo = "none";

		if (SessionModel.sessionMap.get("changeinfofalse") != null) {
			SessionModel.sessionMap.put("changeinfofalse", null);
			this.displayChangeInfoFalse = "block";
		} else
			this.displayChangeInfoFalse = "none";

		if (SessionModel.sessionMap.get("changepass") != null) {
			SessionModel.sessionMap.put("changepass", null);
			this.displayChangePass = "block";
		} else
			this.displayChangePass = "none";

		if (SessionModel.sessionMap.get("passcfm") != null) {
			SessionModel.sessionMap.put("passcfm", null);
			this.displayPassComfirm = "block";
		} else
			this.displayPassComfirm = "none";

		if (SessionModel.sessionMap.get("pass") != null) {
			SessionModel.sessionMap.put("pass", null);
			this.displayPassInvalid = "block";
		} else
			this.displayPassInvalid = "none";

	}

	public void saveChangeInfo() {
		try {
			if (Jsoup.parse(_staff.getEmail()).text().length() == 0
					|| Jsoup.parse(_staff.getName()).text().length() == 0
					|| Jsoup.parse(_staff.getAddress()).text().length() == 0) {

				SessionModel.sessionMap.put("changeinfofalse", true);
			} else if (Jsoup.parse(_staff.getEmail()).text().length() > 254
					|| Jsoup.parse(_staff.getName()).text().length() > 50
					|| Jsoup.parse(_staff.getAddress()).text().length() > 100) {

				SessionModel.sessionMap.put("changeinfofalse", true);
			} else {
				_staff.setAddress(Jsoup.parse(_staff.getAddress()).text());
				_staff.setEmail(Jsoup.parse(_staff.getEmail()).text());
				_staff.setName(Jsoup.parse(_staff.getName()).text());
				StaffModel staffModel = new StaffModel();
				staffModel.update(_staff);
				SessionModel.sessionMap.put("user", _staff);
				SessionModel.sessionMap.put("changeinfo", true);
			}
			SessionModel.reLoadPage();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Catch Save");

			SessionModel.sessionMap.put("changeinfofalse", true);
		}
	}

	public void saveChangePass() {
		if (!staff.getPassword().equals(Hash.getHashMD5(pass))) {
			SessionModel.sessionMap.put("pass", true);
			SessionModel.redirect("profile.xhtml?pas=false#changepass");
			// SessionModel.reLoadPage();
		} else if (!newPass.equals(rePass)) {
			SessionModel.sessionMap.put("passcfm", true);
			// SessionModel.reLoadPage();
			SessionModel.redirect("profile.xhtml?pass=cfm#changepass");
		} else {
			StaffModel staffModel = new StaffModel();
			_staff.setPassword(Hash.getHashMD5(newPass));
			staffModel.update(_staff);
			SessionModel.sessionMap.put("user", _staff);
			SessionModel.sessionMap.put("changepass", true);
			// SessionModel.reLoadPage();
			SessionModel.redirect("profile.xhtml?pass=suc#changepass");
		}
	}

	public ProfileController() {
		System.out.println("ProfileController");
	}

	// private void info() {
	// if (!SessionModel.isPostback())
	// if (SessionModel.sessionMap.get("user") != null) {
	// staff = (Staff) SessionModel.sessionMap.get("user");
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
