package controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import entities.Staff;
import model.SessionModel;
import model.StaffModel;

@ManagedBean(name = "staffController")
@SessionScoped
public class StaffController {
	public void init() {
		staffs = new StaffModel().findAll();
	}

	private List<Staff> staffs;

	public List<Staff> getStaffs() {
		return staffs;
	}
}
