package controller;

import javax.faces.bean.*;
import javax.servlet.http.Part;
import helper.*;

@ManagedBean(name = "uploadController")
@SessionScoped
public class UploadController {

	private String photo = "";
	private Part p;

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Part getP() {
		return p;
	}

	public void setP(Part p) {
		this.p = p;
	}

	public String processUpload(){
		UploadHelper uh = new UploadHelper();
		this.photo = uh.processUpload(this.p);
		return "success";
	}
	
	
	
}
