package model;

import java.util.ArrayList;
import java.util.List;

import entities.Perform;

public class PerformModel extends AbstractModel<Perform> {
	public PerformModel() {
		super(Perform.class); // TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<Perform> findPerformStt(int stt) {
		try {
			if (!sessionFactory.getCurrentSession().getTransaction().isActive())
				sessionFactory.getCurrentSession().getTransaction().begin();
			List<Perform> performs = new ArrayList<Perform>();
			performs = sessionFactory.getCurrentSession().createQuery("from Perform where Status = " + stt).list();
			return performs;
		} catch (Exception e) {
			return new ArrayList<Perform>();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Perform> findPerformStaff(String staff, int stt) {
		try {
			if (!sessionFactory.getCurrentSession().getTransaction().isActive())
				sessionFactory.getCurrentSession().getTransaction().begin();
			List<Perform> performs = new ArrayList<Perform>();
			performs = sessionFactory.getCurrentSession()
					.createQuery("from Perform where Status = " + stt + " and ID_Staff ='" + staff + "'").list();
			return performs;
		} catch (Exception e) {
			return new ArrayList<Perform>();
		}
	}
}
