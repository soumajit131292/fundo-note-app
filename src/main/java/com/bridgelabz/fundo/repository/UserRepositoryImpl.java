package com.bridgelabz.fundo.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.fundo.model.UserDetailsForRegistration;

@Repository
public class UserRepositoryImpl implements UserRepository {
	private Logger logger = Logger.getLogger(this.getClass());
	private EntityManager entityManager;

	@Autowired
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Transactional
	public List<UserDetailsForRegistration> retriveUserDetails() {
		Session currentSession = entityManager.unwrap(Session.class);
		List<UserDetailsForRegistration> users = currentSession.createQuery("from UserDetailsForRegistration")
				.getResultList();
		return users;
	}

	@Override
	@Transactional
	public boolean isValidUser(Integer Id) {
		Session currentSession = entityManager.unwrap(Session.class);
		List<UserDetailsForRegistration> result = currentSession
				.createQuery("from UserDetailsForRegistration where id='" + Id + "'").getResultList();
		return (result.size() > 0) ? true : false;
	}

	@Transactional
	public boolean checkActiveStatus(String emailId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String activeStatus = "true";
		List<UserDetailsForRegistration> result = currentSession
				.createQuery("from UserDetailsForRegistration where activeStatus='" + activeStatus + "'")
				.getResultList();
		return (result.size() > 0) ? true : false;
	}

	@Transactional
	public boolean deletFromDatabase(Integer userid) {
		Session currentSession = entityManager.unwrap(Session.class);
		int status = 0;
		String hql = "from UserDetailsForRegistration where id=:id";
		Query query = currentSession.createQuery(hql);
		query.setParameter("id", userid);
		List<UserDetailsForRegistration> list = query.getResultList();
		if (list.size() > 0)
			status = currentSession.createQuery("delete from UserDetailsForRegistration where id='" + userid + "'")
					.executeUpdate();
		return (status > 0) ? true : false;

	}

	@Transactional
	public UserDetailsForRegistration getUser(Integer userId) {
		Session currentSession = entityManager.unwrap(Session.class);

		UserDetailsForRegistration user = (UserDetailsForRegistration) currentSession
				.createQuery("from UserDetailsForRegistration where id='" + userId + "'").uniqueResult();
		return user;
	}

	@Transactional
	public void changeStatus(Integer Id) {
		Session currentSession = entityManager.unwrap(Session.class);
		String status = "true";
		currentSession
				.createQuery(
						"update UserDetailsForRegistration set activeStatus='" + status + "' where id='" + Id + "'")
				.executeUpdate();
	}
@Override
	@Transactional
	public void saveToDatabase(UserDetailsForRegistration userDetails) {
		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.save(userDetails);
	}

	@Transactional
	public int setToDatabase(UserDetailsForRegistration userDetails) {
		Session currentSession = entityManager.unwrap(Session.class);

		if (getUserByMailId(userDetails.getEmail())) {
			currentSession.save(userDetails);
			return 1;
		}
		return 0;
	}

	@Transactional
	public int updatePassword(Integer Id, UserDetailsForRegistration userDetails) {
		Session currentSession = entityManager.unwrap(Session.class);
		return currentSession.createQuery("update UserDetailsForRegistration set password='" + userDetails.getPassword()
				+ "' where id='" + Id + "'").executeUpdate();
	}

	@Transactional
	public String getUserById(Integer id) {
		Session currentSession = entityManager.unwrap(Session.class);
		// System.out.println(loginUser.getEmail());
		List<UserDetailsForRegistration> detailsList = currentSession
				.createQuery("from UserDetailsForRegistration where Id='" + id + "'").getResultList();
		System.out.println(detailsList.get(0).getId());
		return detailsList.get(0).getEmail();

	}

	@Transactional
	public List<UserDetailsForRegistration> getId(String email) {
		Session currentSession = entityManager.unwrap(Session.class);
		// System.out.println(email.getEmail());
		return currentSession.createQuery("from UserDetailsForRegistration where email='" + email + "'")
				.getResultList();
		// return detailsList.get(0).getId();

	}

	@Override
	@Transactional
	public List<UserDetailsForRegistration> getUserbyId(Integer id) {
		Session currentSession = entityManager.unwrap(Session.class);
		List<UserDetailsForRegistration> oneUser = currentSession
				.createQuery("from UserDetailsForRegistration where id='" + id + "'").getResultList();
		return oneUser;
	}

	public boolean getUserByMailId(String email) {
		Session currentSession = entityManager.unwrap(Session.class);
		System.out.println(email);
		List<UserDetailsForRegistration> user = currentSession
				.createQuery("from UserDetailsForRegistration where email='" + email + "'").getResultList();
		System.out.println(user);
		if (user.size() > 0) {
			System.out.println("email checed now returning");
			return false;
		}
		return true;

	}

	@Override
	@Transactional
	public UserDetailsForRegistration getUserByMail(String email) {
		Session currentSession = entityManager.unwrap(Session.class);
		UserDetailsForRegistration user = (UserDetailsForRegistration) currentSession
				.createQuery("from UserDetailsForRegistration where email='" + email + "'").uniqueResult();
		return user;

	}

	@Transactional
	public List<UserDetailsForRegistration> checkUser(Integer Id) {
		String activeStatus = "true";
		List<UserDetailsForRegistration> result = new ArrayList<>();
		Session currentSession = entityManager.unwrap(Session.class);
		if (isValidUser(Id)) {
			result = currentSession.createQuery(
					"from UserDetailsForRegistration where activeStatus='" + activeStatus + "'and id='" + Id + "'")
					.getResultList();
		}
		return result;
	}

	@Transactional
	public UserDetailsForRegistration getLoggedInUser(Integer id) {
		Session currentSession = entityManager.unwrap(Session.class);
		UserDetailsForRegistration user = (UserDetailsForRegistration) currentSession
				.createQuery("from UserDetailsForRegistration where  id='" + id + "'").uniqueResult();
		return user;

	}
}