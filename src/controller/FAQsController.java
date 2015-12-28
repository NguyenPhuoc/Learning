package controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import entities.Faq;
import model.FaqModel;
import model.SessionModel;
import model.StudentModel;

@ManagedBean(name = "faqsController")
@SessionScoped
public class FAQsController {
	public void init() {
		if (!SessionModel.isPostback()) {
			String paramAdd = SessionModel.params("add");
			String paramEdit = SessionModel.params.get("edit");
			if (paramAdd != null && paramAdd.equalsIgnoreCase("faq")) {
				tableTag = "none";
				divAdd = "block";
			} else if (paramEdit != null) {
				faq = new FaqModel().find(paramEdit);
				if (faq != null) {
					divEdit = "block";
					tableTag = "none";
				} else {
					faqs = new FaqModel().findAll();
					tableTag = "block";
					divAdd = "none";
					divEdit = "none";
				}
			} else {
				faqs = new FaqModel().findAll();
				tableTag = "block";
				divAdd = "none";
				divEdit = "none";
			}
		}
		// ===============================
		// add
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
		// edit
		if (SessionModel.get("editSuc") != null) {
			SessionModel.put("editSuc", null);
			editSuc = "block";
		} else
			editSuc = "none";

		if (SessionModel.get("editErr") != null) {
			SessionModel.put("editErr", null);
			editErr = "block";
		} else
			editErr = "none";
	}

	public String getTableTag() {
		return tableTag;
	}

	public void setTableTag(String tableTag) {
		this.tableTag = tableTag;
	}

	public List<Faq> getFaqs() {
		return faqs;
	}

	public void setFaqs(List<Faq> faqs) {
		this.faqs = faqs;
	}

	public String getDivAdd() {
		return divAdd;
	}

	public void setDivAdd(String divAdd) {
		this.divAdd = divAdd;
	}

	public Faq getFaq() {
		return faq;
	}

	public void setFaq(Faq faq) {
		this.faq = faq;
	}

	public Faq get_faq() {
		return _faq;
	}

	public void set_faq(Faq _faq) {
		this._faq = _faq;
	}

	public String getAddSuc() {
		return addSuc;
	}

	public void setAddSuc(String addSuc) {
		this.addSuc = addSuc;
	}

	public String getAddErr() {
		return addErr;
	}

	public void setAddErr(String addErr) {
		this.addErr = addErr;
	}

	public String getDivEdit() {
		return divEdit;
	}

	public void setDivEdit(String divEdit) {
		this.divEdit = divEdit;
	}

	public String getEditSuc() {
		return editSuc;
	}

	public void setEditSuc(String editSuc) {
		this.editSuc = editSuc;
	}

	public String getEditErr() {
		return editErr;
	}

	public void setEditErr(String editErr) {
		this.editErr = editErr;
	}

	private String tableTag = "block";
	private List<Faq> faqs = new ArrayList<Faq>();
	private String divAdd = "none";
	private Faq faq = new Faq();
	private Faq _faq = new Faq();
	private String addSuc = "none";
	private String addErr = "none";
	private String divEdit = "none";
	private String editSuc = "none";
	private String editErr = "none";
}
