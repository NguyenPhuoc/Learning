package controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import entities.Assignment;
import model.AssignmentModel;

@ManagedBean
@SessionScoped
public class AssignmentClient {
	public void init() {
		assignments = new AssignmentModel().findAssgStudent("student1");
		System.out.println(assignments.size() + ":size");
	}

	private List<Assignment> assignments = new ArrayList<Assignment>();

	public List<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}
}
