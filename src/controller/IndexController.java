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
import entities.Feedback;
import entities.Role;
import entities.Staff;
import entities.Student;
import model.FaqModel;
import model.FeedbackModel;
import model.SessionModel;
import model.StaffModel;
import model.StudentModel;

@ManagedBean
@SessionScoped
public class IndexController {
	public void init() {
		if (!SessionModel.isPostback()) {
			faqs = new FaqModel().findAll();
		}
	}

	public String sendFeedback() {
		try {
			feedback.setStatus(0);
			new FeedbackModel().create(feedback);
			feedback = new Feedback();
			return "index?faces-redirect=true";
		} catch (Exception e) {
			return null;
		}
	}

	public String login() {
		externalContext = FacesContext.getCurrentInstance().getExternalContext();
		sessionMap = externalContext.getSessionMap();
		try {
			Student _student = new StudentModel().login(student);

			if (_student != null && _student.getStatus() == 1) {
				sessionMap.put("student", _student);
				this.isLogin = true;
				return "student?faces-redirect=true";
			} else {
				this.isLogin = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.isLogin = false;
		}
		return null;

	}

	public void verifyLogin() {
		externalContext = FacesContext.getCurrentInstance().getExternalContext();
		sessionMap = externalContext.getSessionMap();

		if (!FacesContext.getCurrentInstance().isPostback()) {
			System.out.println("init.template .ispostback");
			if (!this.isLogin) {
				try {
					externalContext.redirect("index.xhtml");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				student = (Student) sessionMap.get("student");
			}
		}
	}

	public String logout() {
		externalContext = FacesContext.getCurrentInstance().getExternalContext();
		student = new Student();
		this.isLogin = false;
		externalContext.invalidateSession();
		return "student?faces-redirect=true";
	}

	private ExternalContext externalContext;
	private Map<String, Object> sessionMap;
	private List<Faq> faqs = new ArrayList<Faq>();
	private Feedback feedback = new Feedback();
	private Student student = new Student();
	private boolean isLogin = false;

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	public List<Faq> getFaqs() {
		return faqs;
	}

	public void setFaqs(List<Faq> faqs) {
		this.faqs = faqs;
	}
}
