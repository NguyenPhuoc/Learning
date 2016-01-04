package model;

import java.util.ArrayList;
import java.util.List;

import entities.Assignment;

public class AssignmentModel extends AbstractModel<Assignment> {
	public AssignmentModel() {
		super(Assignment.class); // TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<Assignment> findAssg(int id) {
		try {
			if (!sessionFactory.getCurrentSession().getTransaction().isActive())
				sessionFactory.getCurrentSession().getTransaction().begin();
			List<Assignment> assignments = new ArrayList<Assignment>();
			assignments = sessionFactory.getCurrentSession().createQuery("from Assignment where ID_Course = " + id)
					.list();
			return assignments;
		} catch (Exception e) {
			return null;
		}
	}
}
