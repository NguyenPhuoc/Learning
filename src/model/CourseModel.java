package model;

import java.util.ArrayList;
import java.util.List;

import entities.Course;

public class CourseModel extends AbstractModel<Course> {
	public CourseModel() {
		super(Course.class); // TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<Course> findAllClent(String date) {
		try {
			if (!sessionFactory.getCurrentSession().getTransaction().isActive())
				sessionFactory.getCurrentSession().getTransaction().begin();
			List<Course> courses = new ArrayList<Course>();
			courses = sessionFactory.getCurrentSession().createQuery("from Course where EndDate >= '" + date + "'")
					.list();
			return courses;
		} catch (Exception e) {
			return new ArrayList<Course>();
		}
	}


}
