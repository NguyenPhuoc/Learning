package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.jsoup.Jsoup;

import entities.Staff;
import model.SessionModel;
import model.StaffModel;

@ManagedBean(name = "staffController")
@SessionScoped
public class StaffController {
	private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	private Map<String, Object> sessionMap = externalContext.getSessionMap();
	private Map<String, String> params = externalContext.getRequestParameterMap();

	public void init() {
		System.out.println("StaffController.init()");
		if (!SessionModel.isPostback()) {
			String paramAdd = params.get("add");
			String paramEdit = params.get("edit");

			if (paramAdd != null && paramAdd.equalsIgnoreCase("staff")) {
				tableTag = "none";
				divAdd = "block";
			} else if (paramEdit != null) {
				staff = new StaffModel().find(paramEdit);
				if (staff != null && staff.getRole().getId() != 1) {
					divEdit = "block";
					tableTag = "none";
					if (staff.getPerforms().size() == 0)
						btnDel = "initial";
					else
						btnDel = "none";
				} else {
					staffs = new StaffModel().findStaff();
					tableTag = "block";
					divAdd = "none";
					divEdit = "none";
				}
			} else {
				staffs = new StaffModel().findStaff();
				tableTag = "block";
				divAdd = "none";
				divEdit = "none";
			}
			if (sessionMap.get("editSuc") != null) {
				sessionMap.put("editSuc", null);
				this.aletSuc = "block";
			} else
				this.aletSuc = "none";

			if (sessionMap.get("editErr") != null) {
				sessionMap.put("editErr", null);
				this.aletErr = "block";
			} else
				this.aletErr = "none";

			if (sessionMap.get("addSuc") != null) {
				sessionMap.put("addSuc", null);
				addSuc = "block";
			} else
				addSuc = "none";

			if (sessionMap.get("addErr") != null) {
				sessionMap.put("addErr", null);
				addErr = "block";
			} else
				addErr = "none";

			// pass change
			if (sessionMap.get("passSuc") != null) {
				sessionMap.put("passSuc", null);
				passSuc = "block";
			} else
				passSuc = "none";

			if (sessionMap.get("passErr") != null) {
				sessionMap.put("passErr", null);
				passErr = "block";
			} else
				passErr = "none";
		}
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
	private String passSuc = "none";
	private String passErr = "none";

	public String getPassErr() {
		return passErr;
	}

	public String getPassSuc() {
		return passSuc;
	}

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

	public void saveChange() throws IOException {
		// System.out.println(Jsoup.parse(staff.getAddress()).text().length()+":HTML");
		try {
			if (Jsoup.parse(staff.getEmail()).text().length() == 0 || Jsoup.parse(staff.getName()).text().length() == 0
					|| Jsoup.parse(staff.getAddress()).text().length() == 0) {

				sessionMap.put("editErr", true);
			} else if (Jsoup.parse(staff.getEmail()).text().length() > 254
					|| Jsoup.parse(staff.getName()).text().length() > 50
					|| Jsoup.parse(staff.getAddress()).text().length() > 100) {

				sessionMap.put("editErr", true);
			} else {
				staff.setAddress(Jsoup.parse(staff.getAddress()).text());
				staff.setEmail(Jsoup.parse(staff.getEmail()).text());
				staff.setName(Jsoup.parse(staff.getName()).text());
				new StaffModel().update(staff);
				sessionMap.put("editSuc", true);
			}
			staff = new StaffModel().find(staff.getId());
			externalContext.redirect("staff.xhtml?edit=" + staff.getId());
		} catch (Exception e) {
			sessionMap.put("editErr", true);
			externalContext.redirect("staff.xhtml?edit=" + staff.getId());
		}

	}

	public void deleteThisStaff(Staff _staff) throws IOException {
		System.out.println(_staff.getPerforms().size() + ":size");
		if (_staff.getPerforms().size() == 0) {
			new StaffModel().delete(new StaffModel().find(_staff.getId()));
			System.out.println("Xóa Xong!!!!~!");
		}
		externalContext.redirect("staff.xhtml");
	}

	public void save() throws IOException {
		if (Jsoup.parse(_staff.getAddress()).text().length() == 0
				|| Jsoup.parse(_staff.getName()).text().length() == 0) {
			sessionMap.put("addErr", true);
		} else {
			new StaffModel().create(_staff);
			_staff = new Staff();
			sessionMap.put("addSuc", true);
		}
		externalContext.redirect("staff.xhtml?add=staff");
	}

	public void changePass() throws IOException {
		try {
			new StaffModel().changePass(staff, newPass);
			newPass = "";
			sessionMap.put("passSuc", true);
			externalContext.redirect("staff.xhtml?edit=" + staff.getId());
		} catch (Exception e) {
			sessionMap.put("passErr", true);
			externalContext.redirect("staff.xhtml?edit=" + staff.getId());
		}
	}
}
