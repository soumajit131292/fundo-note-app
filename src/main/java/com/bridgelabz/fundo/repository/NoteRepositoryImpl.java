package com.bridgelabz.fundo.repository;

import java.util.ArrayList;
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
		System.out.println("note saved");
	}
	
	@Override
	@Transactional
	public void saveColab(Note colab) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.save(colab);
		System.out.println("note saved");
	}
	

	@Override
	@Transactional
	public Note getNotebyNoteId(Integer noteId) {
		Session currentSession = entityManager.unwrap(Session.class);
		System.out.println("before query");
		List<Note> notes=currentSession.createQuery("from Note where Id='" + noteId + "'").getResultList();
		
		return notes.get(0);
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
		currentSession.createQuery("delete from Note where id='" + noteId + "'").executeUpdate();
	}

	@Override
	@Transactional
	public List<Note> getNotebyUserId(Integer id) {
		Session currentSession = entityManager.unwrap(Session.class);
		return currentSession.createQuery("from Note  where user_id='" + id + "'and inTrash='"+false+"'and isArchive='"+false+"'").getResultList();
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

	@Override
	@Transactional
	public List<Note> getArchiveNotebyUserId(Integer id) {
		Session currentSession = entityManager.unwrap(Session.class);
		return currentSession.createQuery("from Note where user_id='" + id + "'and isArchive=true").getResultList();
	}

	@Override
	@Transactional
	public List<Note> getTrashNotebyUserId(Integer id) {
		Session currentSession = entityManager.unwrap(Session.class);
		return currentSession.createQuery("from Note where user_id='" + id + "'and inTrash=true").getResultList();
	}

	@Override
	@Transactional
	public List<Integer> findNoteIdByUserId(Integer userId) {
		Session currentSession = entityManager.unwrap(Session.class);
		List<Integer> noteIds=new ArrayList<>();	
		List<Note> notes=currentSession.createQuery("from Note where user_id='" + userId + "'and inTrash=true").getResultList();
		for(Note obj : notes)
		{
			noteIds.add(obj.getId());
		}
		
		
		return noteIds;
	}

	@Override
	@Transactional
	public Note getNoteByColabId(Integer colabId) {
		Session currentSession = entityManager.unwrap(Session.class);
		Note notes=(Note) currentSession.createQuery("from Note where colab_id='" + colabId + "'").uniqueResult();
		
		
		return notes;
	}
	@Override
	@Transactional
	public void savenotewithRemainder(Note note) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.save(note);
		System.out.println("note saved");
	}

	@Override
	@Transactional
	public List<Note> getNotesByEmailId(String emailId) {
		Session currentSession = entityManager.unwrap(Session.class);
		System.out.println("before note");
		return currentSession.createQuery("from Note where email='" + emailId + "'and inTrash='"+false+"'and isPinned='"+false+"'and isArchive='"+false+"'").getResultList();

	}

//	@Override
//	public void saveColab(ColabModel colab) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	@Transactional
	public List<Note> getLabels(Integer id) {
		Session currentSession = entityManager.unwrap(Session.class);
		return currentSession.createQuery("from ColabModel where userId='" + id + "'").getResultList();
	}

	@Override
	public void getRemaindeNotes(Integer userId) {
		
		
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.createQuery("from Note where remainder='" + userId + "'").getResultList();
	}

	
}