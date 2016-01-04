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

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Quota;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.imap.IMAPStore;

@ManagedBean(name = "feedbackController")
@SessionScoped
public class FeedbackController {
	public void init() {
		if (!SessionModel.isPostback()) {
			String paramView = params.get("view");
			String paramDel = params.get("del");
			String paramReply = params.get("reply");
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
			} else if (paramReply != null) {
				divReply = "block";
				divView = "none";
				tableTag = "none";
			} else {
				tableTag = "block";
				divView = "none";
				divReply = "none";
				feedbacks = new FeedbackModel().findAll();
			}

		}
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

	public String getDivReply() {
		return divReply;
	}

	public void setDivReply(String divReply) {
		this.divReply = divReply;
	}

	public String getTitleMail() {
		return titleMail;
	}

	public void setTitleMail(String titleMail) {
		this.titleMail = titleMail;
	}

	public String getContentMail() {
		return contentMail;
	}

	public void setContentMail(String contentMail) {
		this.contentMail = contentMail;
	}

	public void setDivView(String divView) {
		this.divView = divView;
	}

	public void name() {
		titleMail += "aaaaaaaaaaa";
	}

	public void sendMail() {
		String username = "pharmacyeproject";
		String password = "e123456780";
		String from = "pharmacyeproject@gmail.com";
		String to = "hailua5gs@gmail.com";

		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");
			//props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.fallback", "false");
			Session session = Session.getInstance(props, null);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Customer Contact");
			message.setText("From");
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", username, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		System.out.println("Done");
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	private Map<String, Object> sessionMap = externalContext.getSessionMap();
	private Map<String, String> params = externalContext.getRequestParameterMap();
	private List<Feedback> feedbacks = new ArrayList<Feedback>();
	private Feedback feedback = new Feedback();
	private String tableTag = "block";
	private String divView = "none";
	private String divReply = "none";
	private String titleMail = "";
	private String contentMail = "";
}
