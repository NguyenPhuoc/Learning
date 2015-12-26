package controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jsoup.Jsoup;

import entities.Staff;
import model.SessionModel;
import model.StaffModel;

@ManagedBean(name = "staffController")
@SessionScoped
public class StaffController {
	public void init() {
		staffs = new StaffModel().findStaff();
		divEdit = "none";
		String paramEdit = SessionModel.params.get("edit");
		if (paramEdit != null) {
			staff = new StaffModel().find(paramEdit);
			if (staff != null && staff.getRole().getId() != 1)
				divEdit = "block";
		}
		if (SessionModel.sessionMap.get("editSuc") != null) {
			SessionModel.sessionMap.put("editSuc", null);
			this.aletSuc = "block";
			// this.divEdit = "block";
		} else
			this.aletSuc = "none";

		if (SessionModel.sessionMap.get("editErr") != null) {
			SessionModel.sessionMap.put("editErr", null);
			this.aletErr = "block";
			// this.divEdit = "block";
		} else
			this.aletErr = "none";
	}

	private List<Staff> staffs;

	public List<Staff> getStaffs() {
		return staffs;
	}

	private String divEdit = "none";
	private String aletSuc = "none";
	private String aletErr = "none";

	public String getAletSuc() {
		return aletSuc;
	}

	public String getAletErr() {
		return aletErr;
	}

	public String getDivEdit() {
		return divEdit;
	}

	private Staff staff;

	public Staff getStaff() {
		return staff;
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
}
