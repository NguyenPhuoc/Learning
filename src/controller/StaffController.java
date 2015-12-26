package controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jsoup.Jsoup;

import entities.Role;
import entities.Staff;
import model.RoleModel;
import model.SessionModel;
import model.StaffModel;

@ManagedBean(name = "staffController")
@SessionScoped
public class StaffController {
	public void init() {
		String paramAdd = SessionModel.params("add");
		if (paramAdd != null && paramAdd.equalsIgnoreCase("staff")) {
			tableTag = "none";
			divAdd = "block";
		} else {
			staffs = new StaffModel().findStaff();
			tableTag = "block";
			divAdd = "none";
		}
		divEdit = "none";
		String paramEdit = SessionModel.params.get("edit");
		if (paramEdit != null) {
			staff = new StaffModel().find(paramEdit);
			if (staff != null && staff.getRole().getId() != 1)
				divEdit = "block";
			if (staff.getPerforms().size() == 0)
				btnDel = "initial";
			else
				btnDel = "none";
		}
		if (SessionModel.sessionMap.get("editSuc") != null) {
			SessionModel.sessionMap.put("editSuc", null);
			this.aletSuc = "block";
		} else
			this.aletSuc = "none";

		if (SessionModel.sessionMap.get("editErr") != null) {
			SessionModel.sessionMap.put("editErr", null);
			this.aletErr = "block";
		} else
			this.aletErr = "none";

		if (SessionModel.get("addSuc") != null) {
			SessionModel.put("addSuc", null);
			addSuc = "block";
		} else
			addSuc = "none";

		if (SessionModel.get("addErr") != null) {
			SessionModel.put("addErr", null);
			addErr = "block";
		} else
			addErr = "none";
	}

	private List<Staff> staffs;

	public List<Staff> getStaffs() {
		return staffs;
	}

	private String divEdit = "none";
	private String aletSuc = "none";
	private String aletErr = "none";
	private String btnDel = "none";
	private String tableTag = "block";
	private String addSuc = "none";
	private String addErr = "none";
	private String divAdd = "none";
	private String newPass = "";

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public String getDivAdd() {
		return divAdd;
	}

	public String getAddSuc() {
		return addSuc;
	}

	public String getAddErr() {
		return addErr;
	}

	public String getTableTag() {
		return tableTag;
	}

	public String getBtnDel() {
		return btnDel;
	}

	public String getAletSuc() {
		return aletSuc;
	}

	public String getAletErr() {
		return aletErr;
	}

	public String getDivEdit() {
		return divEdit;
	}

	private Staff staff = new Staff();

	public Staff getStaff() {
		return staff;
	}

	private Staff _staff = new Staff();

	public Staff get_staff() {
		return _staff;
	}

	public void saveChange() {
		// System.out.println(Jsoup.parse(staff.getAddress()).text().length()+":HTML");
		try {
			if (Jsoup.parse(staff.getEmail()).text().length() == 0 || Jsoup.parse(staff.getName()).text().length() == 0
					|| Jsoup.parse(staff.getAddress()).text().length() == 0) {

				SessionModel.sessionMap.put("editErr", true);
			} else if (Jsoup.parse(staff.getEmail()).text().length() > 254
					|| Jsoup.parse(staff.getName()).text().length() > 50
					|| Jsoup.parse(staff.getAddress()).text().length() > 100) {

				SessionModel.sessionMap.put("editErr", true);
			} else {
				staff.setAddress(Jsoup.parse(staff.getAddress()).text());
				staff.setEmail(Jsoup.parse(staff.getEmail()).text());
				staff.setName(Jsoup.parse(staff.getName()).text());
				new StaffModel().update(staff);
				SessionModel.sessionMap.put("editSuc", true);
			}
			SessionModel.redirect("/admin/staff.xhtml?edit=" + staff.getId());
		} catch (Exception e) {
			SessionModel.sessionMap.put("editErr", true);
			SessionModel.redirect("/admin/staff.xhtml?edit=" + staff.getId());
		}

	}

	public void deleteThisStaff(Staff _staff) {
		System.out.println(_staff.getPerforms().size() + ":size");
		if (_staff.getPerforms().size() == 0) {
			new StaffModel().delete(new StaffModel().find(_staff.getId()));
			System.out.println("X�a Xong!!!!~!");
		}
		SessionModel.redirect("/admin/staff.xhtml");
	}

	public void save() {
		if (Jsoup.parse(_staff.getAddress()).text().length() == 0
				|| Jsoup.parse(_staff.getName()).text().length() == 0) {
			SessionModel.sessionMap.put("addErr", true);
		} else {
			new StaffModel().create(_staff);
			_staff = new Staff();
			SessionModel.sessionMap.put("addSuc", true);
		}
		SessionModel.redirect("/admin/staff.xhtml?add=staff");
	}

	public void changePass() {
		try {
			new StaffModel().changePass(staff, newPass);
			newPass = "";
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
