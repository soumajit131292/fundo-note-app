package com.bridgelabz.fundo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundo.model.Note;
import com.bridgelabz.fundo.model.UserDetailsForRegistration;

@Repository
public class NoteRepositoryImpl implements NoteRepository {

	private EntityManager entityManager;

	@Autowired
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public void saveNote(UserDetailsForRegistration note) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.save(note);
	}

	@Override
	@Transactional
	public Note getNotebyNoteId(Integer noteId) {
		Session currentSession = entityManager.unwrap(Session.class);
		Note note = (Note) currentSession.createQuery("from Note where Id='" + noteId + "'").uniqueResult();
		return note;
	}

	@Override
	@Transactional
	public void updateNote(Integer noteId, Note createdNoteByUser) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.save(createdNoteByUser);
	}

	@Override
	@Transactional
	public void deleteNote(Integer noteId) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.createQuery("delete from Note where Id='" + noteId + "'").executeUpdate();
	}

	@Override
	@Transactional
	public List<Note> getNotebyUserId(Integer id) {
		Session currentSession = entityManager.unwrap(Session.class);
		return currentSession.createQuery("from Note where user_id='" + id + "'").getResultList();
	}

	@Override
	@Transactional
	public void changePinStatus(Integer noteId, boolean isPinned) {
		Session currentSession = entityManager.unwrap(Session.class);
		// currentSession.createQuery("update Note set isPinned='"+status+"'where
		// noteId='"+noteId+"'").executeUpdate();
		Query query = currentSession.createQuery("update Note set isPinned=:isPinned where Id=:noteId");
		query.setParameter("isPinned", isPinned);
		query.setParameter("noteId", noteId);
		query.executeUpdate();
	}
}