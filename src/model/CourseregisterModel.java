package model;

import java.util.ArrayList;
import java.util.List;

import entities.Courseregister;

public class CourseregisterModel extends AbstractModel<Courseregister> {
	public CourseregisterModel() {
		super(Courseregister.class);
	}

	@SuppressWarnings("unchecked")
	public List<Courseregister> findCourseregisteNew() {
		try {
			if (!sessionFactory.getCurrentSession().getTransaction().isActive())
				sessionFactory.getCurrentSession().getTransaction().begin();
			List<Courseregister> courseregisters = new ArrayList<Courseregister>();
			courseregisters = sessionFactory.getCurrentSession().createQuery("from Courseregiste where Status = 1").list();
			return courseregisters;
		} catch (Exception e) {
			return null;
		}
	}
}
