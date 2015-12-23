package controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.*;

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
	private String displayPassInvalid = "none";
	private String displayChangePass = "none";
	private String displayPassComfirm = "none";

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
		if (SessionModel.sessionMap.get("user") != null)
			staff = (Staff) SessionModel.sessionMap.get("user");

		if (SessionModel.sessionMap.get("changeinfo") != null) {
			SessionModel.sessionMap.put("changeinfo", null);
			this.displayChangeInfo = "block";
		} else
			this.displayChangeInfo = "none";

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
			StaffModel staffModel = new StaffModel();
			staffModel.update(_staff);
			SessionModel.sessionMap.put("user", _staff);
			SessionModel.sessionMap.put("changeinfo", true);

			SessionModel.reLoadPage();
			System.out.println(SessionModel.externalContext.getApplicationContextPath() + "-------------"
					+ SessionModel.externalContext.getRequestServletPath() + "-------------"
					+ SessionModel.externalContext.getRequestPathInfo());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Catch Save");
		}
	}

	public void saveChangePass() {
		// if (!staff.getPassword().equals(Hash.getHashMD5(pass))) {
		// SessionModel.sessionMap.put("pass", true);
		// SessionModel.reLoadPage();
		// } else if (!newPass.equals(rePass)) {
		// SessionModel.sessionMap.put("passcfm", true);
		// SessionModel.reLoadPage();
		// } else {
		// EmployeesModel employeesModel = new EmployeesModel();
		// emp.setPassword(Hash.getHashMD5(newPass));
		// employeesModel.update(emp);
		// SessionModel.sessionMap.put("user", emp);
		// SessionModel.sessionMap.put("changepass", true);
		// SessionModel.reLoadPage();
		// }
	}

	public ProfileController() {
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
		System.out.println("ProfileController");
	}

	public String linkProfile() {
		return "profile?faces-redirect=true";
	}
}
