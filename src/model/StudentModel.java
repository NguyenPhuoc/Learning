package model;

import java.util.ArrayList;
import java.util.List;

import entities.Student;

public class StudentModel extends AbstractModel<Student> {
	public StudentModel() {
		super(Student.class);
	}

	@SuppressWarnings("unchecked")
	public Student login(Student student) {
		try {
			if (!sessionFactory.getCurrentSession().getTransaction().isActive())
				sessionFactory.getCurrentSession().getTransaction().begin();
			List<Student> users = new ArrayList<Student>();
			users = sessionFactory.getCurrentSession().createQuery("from Student where ID=? and Password=?")
					.setParameter(0, student.getId()).setParameter(1, Hash.getHashMD5(student.getPassword())).list();
			if (users.size() != 0)
				return users.get(0);
			else
				return null;
		} catch (Exception e) {
			return null;// TODO: handle exception
		}
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

	public void changePass(Student student, String newPass) {
		student = super.find(student.getId());
		student.setPassword(Hash.getHashMD5(newPass));
		super.update(student);
	}
}
