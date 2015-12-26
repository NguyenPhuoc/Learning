package model;

import entities.Student;

public class StudentModel extends AbstractModel<Student> {
	public StudentModel() {
		super(Student.class);
	}

	public String newID() {
		try {
			if (!sessionFactory.getCurrentSession().getTransaction().isActive())
				sessionFactory.getCurrentSession().getTransaction().begin();
			Student a = (Student) sessionFactory.getCurrentSession().createQuery("from Student order by ID desc")
					.setMaxResults(1).list().get(0);
			String ID = a.getId();
			ID = ID.substring(7);
			int i = Integer.parseInt(ID);
			i++;
			return "Student" + i;
		} catch (Exception e) {
			return null;
		}
	}
	@Override
	public void create(Student student) {
		student.setStatus(1);
		student.setId(newID());
		student.setPassword(Hash.getHashMD5(student.getPassword()));
		super.create(student);
	}
}
