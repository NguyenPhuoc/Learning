package model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import entities.Staff;

public class StaffModel extends AbstractModel<Staff> {
	String a = "a";
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public StaffModel() {
		super(Staff.class);
	}
	@SuppressWarnings("unchecked")
	public Staff login(Staff staff) {
		try {
			if (!sessionFactory.getCurrentSession().getTransaction().isActive())
				sessionFactory.getCurrentSession().getTransaction().begin();
			List<Staff> users = new ArrayList<Staff>();
			users = sessionFactory.getCurrentSession().createQuery("from Staff where ID=? and Password=?")
					.setParameter(0, staff.getId()).setParameter(1, Hash.getHashMD5(staff.getPassword()))
					.list();
			if (users.size() != 0)
				return users.get(0);
			else
				return null;
		} catch (Exception e) {
			return null;// TODO: handle exception
		}
	}
}
