package controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean(name = "regular")
@SessionScoped
public class Regular {
	private String regexName = "^[a-zA-Zạảãàáâậầấẩẫăắằặẳẵóòọõỏôộổỗồốơờớợởỡéèẻẹẽêếềệểễúùụủũưựữửừứíìịỉĩýỳỷỵỹđ ẠẢÃÀÁÂẬẦẤẨẪĂẮẰẶẲẴÓÒỌÕỎÔỘỔỖỒỐƠỜỚỢỞỠÉÈẺẸẼÊẾỀỆỂỄÚÙỤỦŨƯỰỮỬỪỨÍÌỊỈĨÝỲỶỴỸĐ]*$";
	private String regexAddress = "^[0-9a-zA-Zạảãàáâậầấẩẫăắằặẳẵóòọõỏôộổỗồốơờớợởỡéèẻẹẽêếềệểễúùụủũưựữửừứíìịỉĩýỳỷỵỹđ - , . / ẠẢÃÀÁÂẬẦẤẨẪĂẮẰẶẲẴÓÒỌÕỎÔỘỔỖỒỐƠỜỚỢỞỠÉÈẺẸẼÊẾỀỆỂỄÚÙỤỦŨƯỰỮỬỪỨÍÌỊỈĨÝỲỶỴỸĐ\\s]*$";
	private String regexEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private String regexContentMail = "^[0-9a-zA-Zạảãàáâậầấẩẫăắằặẳẵóòọõỏôộổỗồốơờớợởỡéèẻẹẽêếềệểễúùụủũưựữửừứíìịỉĩýỳỷỵỹđ - , : $ % ^ & * ( ) ! @ # _ + = ~ ; \" \' ? . / ẠẢÃÀÁÂẬẦẤẨẪĂẮẰẶẲẴÓÒỌÕỎÔỘỔỖỒỐƠỜỚỢỞỠÉÈẺẸẼÊẾỀỆỂỄÚÙỤỦŨƯỰỮỬỪỨÍÌỊỈĨÝỲỶỴỸĐ\\s]*$";

	public String getRegexContentMail() {
		return regexContentMail;
	}

	public String getRegexName() {
		return regexName;
	}

	public String getRegexAddress() {
		return regexAddress;
	}

	public String getRegexEmail() {
		return regexEmail;
	}

	public String toDay() {
		return new java.text.SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
	}

	public String title() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		if (sessionMap.get("title") != null)
			return (String) sessionMap.get("title");
		return new java.text.SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
	}

}
