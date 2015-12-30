package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import entities.Faq;
import model.FaqModel;
import model.SessionModel;
import model.StudentModel;

@ManagedBean(name = "faqsController")
@SessionScoped
public class FAQsController {
	private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	private Map<String, Object> sessionMap = externalContext.getSessionMap();
	private Map<String, String> params = externalContext.getRequestParameterMap();

	public void init() {
		if (!SessionModel.isPostback()) {
			String paramAdd = params.get("add");
			String paramEdit = params.get("edit");
			if (paramAdd != null && paramAdd.equalsIgnoreCase("faq")) {
				tableTag = "none";
				divAdd = "block";
			} else if (paramEdit != null) {
				try {
					faq = new FaqModel().find(Integer.parseInt(paramEdit));
				} catch (Exception e) {
					faq = null;
				}
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
		// edit
		if (sessionMap.get("editSuc") != null) {
			sessionMap.put("editSuc", null);
			editSuc = "block";
		} else
			editSuc = "none";

		if (sessionMap.get("editErr") != null) {
			sessionMap.put("editErr", null);
			editErr = "block";
		} else
			editErr = "none";
	}

	public void save() {
		try {
			new FaqModel().create(_faq);
			_faq = new Faq();
			sessionMap.put("addSuc", true);
			externalContext.redirect("faqs.xhtml?add=faq");
		} catch (Exception e) {
			sessionMap.put("addErr", true);
			System.out.println("catch save faq");// TODO: handle exception
		}
	}

	public void saveChange() {
		try {
			new FaqModel().update(faq);
			sessionMap.put("editSuc", true);
			externalContext.redirect("faqs.xhtml?edit=" + faq.getId());
		} catch (Exception e) {
			sessionMap.put("editErr", true);
			System.out.println("catch save change faq");
		}
	}

	public void daleteThisFAQs(Faq faqd) {
		try {
			new FaqModel().delete(new FaqModel().find(faqd.getId()));
			externalContext.redirect("faqs.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
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
