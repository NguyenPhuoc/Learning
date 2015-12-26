package model;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class SessionModel {
	public static final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	public static final Map<String, Object> sessionMap = externalContext.getSessionMap();
	public static final Map<String, String> params = externalContext.getRequestParameterMap();

	public static void redirect(String url) {
		try {
			externalContext.redirect(externalContext.getRequestContextPath() + url);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("catch redirect()");
		}
	}

	public static void reLoadPage() {
		try {
			SessionModel.externalContext.redirect(SessionModel.externalContext.getApplicationContextPath()
					+ SessionModel.externalContext.getRequestServletPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("catch reLoadPage()");
	}

	public static Object get(Object key) {
		return sessionMap.get(key);
	}

	public static Object put(String key, Object value) {
		return sessionMap.put(key, value);
	}

	public static String params(Object key) {
		return params.get(key);
	}
}