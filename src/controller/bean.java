package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;

@ManagedBean(name = "bean")
@SessionScoped
public class bean {
	private Part file; // +getter+setter

	public void save() {
		try (InputStream input = file.getInputStream()) {
			Files.copy(input, new File("\\Learning\\css", "111.txt").toPath());
		} catch (IOException e) {
			System.out.println("ccccccccccccccccccccc");
		}
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}
}
