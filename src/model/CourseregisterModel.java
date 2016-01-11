package model;

import java.util.ArrayList;
import java.util.List;

import entities.Courseregister;

public class CourseregisterModel extends AbstractModel<Courseregister> {
	public CourseregisterModel() {
		super(Courseregister.class);
	}

	@SuppressWarnings("unchecked")
	public List<Courseregister> findCourseregisteNew(int stt) {
		try {
			if (!sessionFactory.getCurrentSession().getTransaction().isActive())
				sessionFactory.getCurrentSession().getTransaction().begin();
			List<Courseregister> courseregisters = new ArrayList<Courseregister>();
			courseregisters = sessionFactory.getCurrentSession()
					.createQuery("from Courseregister where Status = " + stt).list();
			return courseregisters;
		} catch (Exception e) {
			return new ArrayList<Courseregister>();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Courseregister> findCourseregister(int stt) {
		try {
			if (!sessionFactory.getCurrentSession().getTransaction().isActive())
				sessionFactory.getCurrentSession().getTransaction().begin();
			List<Courseregister> courseregisters = new ArrayList<Courseregister>();
			courseregisters = sessionFactory.getCurrentSession()
					.createQuery("from Courseregister where ID_Course = " + stt).list();
			return courseregisters;
		} catch (Exception e) {
			return new ArrayList<Courseregister>();
		}
	}
}
