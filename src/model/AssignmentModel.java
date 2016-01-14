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

//	@SuppressWarnings("unchecked")
//	public List<Assignment> findAssgStudent(String student) {
//		try {
//			if (!sessionFactory.getCurrentSession().getTransaction().isActive())
//				sessionFactory.getCurrentSession().getTransaction().begin();
//			List<Assignment> assignments = new ArrayList<Assignment>();
//			assignments = sessionFactory.getCurrentSession().createQuery("from Assignment").list();
//			assignments = sessionFactory.getCurrentSession()
//					.createQuery(
//							"SELECT A FROM Assignment A LEFT JOIN CourseregisterId B WHERE B.ID_Student = 'Student1' ")
//					.list();
//			return assignments;
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//			return new ArrayList<Assignment>();
//		}
//	}
}
