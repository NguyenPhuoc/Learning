package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import entities.Feedback;
import model.FeedbackModel;
import model.SessionModel;

@ManagedBean(name = "feedbackController")
@SessionScoped
public class FeedbackController {
	public void init() {
		if (!SessionModel.isPostback()) {
			String paramView = params.get("view");
			String paramDel = params.get("del");
			if (paramView != null) {
				try {
					feedback = new FeedbackModel().find(Integer.parseInt(paramView));
					if (feedback != null && feedback.getStatus() == 0) {
						feedback.setStatus(1);
						new FeedbackModel().update(feedback);
					}
				} catch (Exception e) {
					feedback = null;
				}
				if (feedback != null) {
					tableTag = "none";
					divView = "block";
				} else {
					tableTag = "block";
					divView = "none";
					feedbacks = new FeedbackModel().findAll();
				}
			} else if (paramDel != null) {
				try {
					new FeedbackModel().delete(new FeedbackModel().find(Integer.parseInt(paramDel)));
					externalContext.redirect("feedback.xhtml");
				} catch (Exception e) {
					e.printStackTrace();
					try {
						externalContext.redirect("feedback.xhtml");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			} else {
				tableTag = "block";
				divView = "none";
				feedbacks = new FeedbackModel().findAll();
			}

		}
	}

	public void delete(Feedback _feedback) {

	}

	public List<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(List<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	public String getTableTag() {
		return tableTag;
	}

	public void setTableTag(String tableTag) {
		this.tableTag = tableTag;
	}

	public String getDivView() {
		return divView;
	}

	public void setDivView(String divView) {
		this.divView = divView;
	}

	private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	private Map<String, Object> sessionMap = externalContext.getSessionMap();
	private Map<String, String> params = externalContext.getRequestParameterMap();
	private List<Feedback> feedbacks = new ArrayList<Feedback>();
	private Feedback feedback = new Feedback();
	private String tableTag = "block";
	private String divView = "none";
}
