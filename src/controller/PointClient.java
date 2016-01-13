package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import entities.Perform;
import entities.Student;
import model.PerformModel;

@ManagedBean
@SessionScoped
public class PointClient {
	public void init() {
		externalContext = FacesContext.getCurrentInstance().getExternalContext();
		sessionMap = externalContext.getSessionMap();
		params = externalContext.getRequestParameterMap();
		if (sessionMap.get("student") != null) {
			Student student = (Student) sessionMap.get("student");
			performs = new PerformModel().findPerformStudent(student.getId());
			System.out.println(student.getId());
		}
	}

	private ExternalContext externalContext;
	private Map<String, Object> sessionMap;
	private Map<String, String> params;
	private List<Perform> performs = new ArrayList<Perform>();

	public List<Perform> getPerforms() {
		return performs;
	}

	public void setPerforms(List<Perform> performs) {
		this.performs = performs;
	}
}
