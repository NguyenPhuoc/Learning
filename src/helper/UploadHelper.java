package helper;

import java.io.*;
import java.util.*;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

public class UploadHelper {

	private final int limit_max_size = 10240000;
	private final String limit_type_file = "doc|docx|pdf|rar|zip";
	private final String path_to = "/resources/rcs";

	public UploadHelper() {
	}

	// /Learning/javax.faces.resource/#{courseController.assignment.file}.xhtml?ln=rcs
	public String processUpload(Part fileUpload) {
		String fileSaveData = "nofile";
		try {
			if (fileUpload.getSize() > 0) {
				String submittedFileName = getFilename(fileUpload);
				if (checkFileType(submittedFileName)) {
					if (fileUpload.getSize() > this.limit_max_size) {
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "File size too large!", ""));
					} else {
						String currentFileName = submittedFileName;
						String extension = currentFileName.substring(currentFileName.lastIndexOf("."),
								currentFileName.length());
						Long nameRadom = Calendar.getInstance().getTimeInMillis();
						String newfilename = nameRadom + extension;
						fileSaveData = newfilename;
						String fileSavePath = FacesContext.getCurrentInstance().getExternalContext()
								.getRealPath(this.path_to);
						try {
							byte[] fileContent = new byte[(int) fileUpload.getSize()];
							InputStream in = fileUpload.getInputStream();
							in.read(fileContent);

							File fileToCreate = new File(fileSavePath, newfilename);
							File folder = new File(fileSavePath);
							if (!folder.exists()) {
								folder.mkdirs();
							}
							FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
							fileOutStream.write(fileContent);
							fileOutStream.flush();
							fileOutStream.close();
							fileSaveData = newfilename;
						} catch (IOException e) {
							fileSaveData = "nofile";
						}
					}
				} else {
					fileSaveData = "nofile";
				}
			}
		} catch (Exception ex) {
			fileSaveData = "nofile";
		}
		return fileSaveData;
	}

	public String processUpload(Part fileUpload, String newfilename) {
		String fileSaveData = "nofile";
		try {
			if (fileUpload.getSize() > 0) {
				String submittedFileName = getFilename(fileUpload);
				if (checkFileType(submittedFileName)) {
					if (fileUpload.getSize() > this.limit_max_size) {
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "File size too large!", ""));
					} else {
						fileSaveData = newfilename;
						String fileSavePath = FacesContext.getCurrentInstance().getExternalContext()
								.getRealPath(this.path_to);
						try {
							byte[] fileContent = new byte[(int) fileUpload.getSize()];
							InputStream in = fileUpload.getInputStream();
							in.read(fileContent);

							File fileToCreate = new File(fileSavePath, newfilename);
							File folder = new File(fileSavePath);
							if (!folder.exists()) {
								folder.mkdirs();
							}
							FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
							fileOutStream.write(fileContent);
							fileOutStream.flush();
							fileOutStream.close();
							fileSaveData = newfilename;
						} catch (IOException e) {
							fileSaveData = "nofile";
						}
					}
				} else {
					fileSaveData = "nofile";
				}
			}
		} catch (Exception ex) {
			fileSaveData = "nofile";
		}
		return fileSaveData;
	}

	private String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
			}
		}
		return null;
	}

	private boolean checkFileType(String fileName) {
		if (fileName.length() > 0) {
			String[] parts = fileName.split("\\.");
			if (parts.length > 0) {
				String extention = parts[parts.length - 1];
				return this.limit_type_file.contains(extention);
			}
		}
		return false;
	}

}
