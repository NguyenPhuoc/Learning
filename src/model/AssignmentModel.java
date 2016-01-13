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

	@SuppressWarnings("unchecked")
	public List<Assignment> findAssgStudent(String student) {
		try {
			if (!sessionFactory.getCurrentSession().getTransaction().isActive())
				sessionFactory.getCurrentSession().getTransaction().begin();
			List<Assignment> assignments = new ArrayList<Assignment>();
			assignments = sessionFactory.getCurrentSession().createQuery("from Assignment").list();
			// assignments =
			// sessionFactory.getCurrentSession().createQuery("SELECT A.ID AS
			// ID, A.ID_Course AS ID_Course, A.Name AS Name, A.Comment AS
			// Comment, A.File AS File, A.EndDate AS EndDate FROM Assignment A,
			// Courseregister C WHERE A.ID_Course = C.ID_Course and C.ID_Student
			// = 'student1'")
			// .list();
			// assignments =
			// sessionFactory.getCurrentSession().createQuery("SELECT
			// assignment.ID, assignment.ID_Course, assignment.Name,
			// assignment.Comment, assignment.File, assignment.EndDate FROM
			// assignment, courseregister WHERE assignment.ID_Course =
			// courseregister.ID_Course and courseregister.ID_Student =
			// 'student1'")
			// .list();
			return assignments;
		} catch (Exception e) {
			return new ArrayList<Assignment>();
		}
	}
}
