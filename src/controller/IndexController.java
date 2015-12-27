package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import entities.Feedback;

@ManagedBean(name = "indexController")
@SessionScoped
public class IndexController {

	private Feedback feedback = new Feedback();

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}
}
