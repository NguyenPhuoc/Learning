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
					.setParameter(0, staff.getId()).setParameter(1, Hash.getHashMD5(staff.getPassword())).list();
			if (users.size() != 0)
				return users.get(0);
			else
				return null;
		} catch (Exception e) {
			return null;// TODO: handle exception
		}
	}

	@SuppressWarnings("unchecked")
	public List<Staff> findStaff() {
		try {
			if (!sessionFactory.getCurrentSession().getTransaction().isActive())
				sessionFactory.getCurrentSession().getTransaction().begin();
			List<Staff> staffs = new ArrayList<Staff>();
			staffs = sessionFactory.getCurrentSession().createQuery("from Staff where ID_role = 2").list();
			return staffs;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void create(Staff staff) {
		staff.setRole(new RoleModel().find(2));
		staff.setStatus(1);
		staff.setPassword(Hash.getHashMD5(staff.getPassword()));
		super.create(staff);
		SessionModel.sessionMap.put("addSuc", true);
	}

	public void changePass(Staff staff, String newPass) {
		staff = super.find(staff.getId());
		staff.setPassword(Hash.getHashMD5(newPass));
		super.update(staff);
	}

}
