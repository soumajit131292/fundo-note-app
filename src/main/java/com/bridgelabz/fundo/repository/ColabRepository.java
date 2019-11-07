package com.bridgelabz.fundo.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.fundo.dto.ColabDto;
import com.bridgelabz.fundo.model.Colaborator;
import com.bridgelabz.fundo.model.Note;
import com.bridgelabz.fundo.model.UserDetailsForRegistration;

@Repository
public class ColabRepository {
	private EntityManager entityManager;

	@Autowired
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	public Integer getUserIdByEmail(ColabDto colabDto) {
		Session currentSession = entityManager.unwrap(Session.class);
		System.out.println("hi");
		UserDetailsForRegistration user = (UserDetailsForRegistration) currentSession
				.createQuery("from UserDetailsForRegistration where email='" + colabDto.getEmailId() + "'")
				.uniqueResult();
		System.out.println("back from database");
		return user.getId();
	}

	@Transactional
	public void addColab(Note colab) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.save(colab);
	}

	@Transactional
	public void addColabNote(UserDetailsForRegistration colab) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.save(colab);
	}

	@Transactional
	public void removeColab(Integer userId, Integer noteId) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.createQuery("delete from Colaborator where note_id='" + noteId + "'and user_id='" + userId + "'")
				.executeUpdate();
	}

	@Transactional
	public List<String> getColabUsers(Integer userId) {
		Session currentSession = entityManager.unwrap(Session.class);
		System.out.println("before fetching colab table");
		List<String> list = new ArrayList<>();
		List<Colaborator> notesFromColab = new ArrayList<>();
		List<Note> notes = currentSession.createQuery("from Note where user_id='" + userId + "'").getResultList();
		for (Note n : notes) {
			notesFromColab = currentSession.createQuery("from Colaborator where note_id='" + n.getId() + "'")
					.getResultList();
		}
		for (Colaborator c : notesFromColab) {
			list.add(c.getUserEmailId());
		}
		System.out.println("after fetching colab table");

//		for (Colaborator col : notesFromColab) {
//			System.out.println(col.getNoteId());
//			Note n = (Note) currentSession.createQuery("from Note where id='" + col.getNoteId() + "'").uniqueResult();
//			collaboratedNotes.add(n);
//		}
		return list;

	}

	@Transactional
	public boolean getColabId(Integer userId, Integer noteId) {

		Session currentSession = entityManager.unwrap(Session.class);
		System.out.println("brfore query");
		Colaborator colaborator = (Colaborator) currentSession
				.createQuery("from Colaborator where note_id='" + noteId + "'and user_id='" + userId + "'")
				.uniqueResult();
		System.out.println("after query");

		if (colaborator == null) {

			return false;
		}
		return true;

	}
	
	@Transactional
	public List<Colaborator> getColabList(Integer userId)
	{
		Session currentSession=entityManager.unwrap(Session.class);
		return currentSession.createQuery("from Colaborator where user_id='"+userId+"'").getResultList();
		
	}

}
