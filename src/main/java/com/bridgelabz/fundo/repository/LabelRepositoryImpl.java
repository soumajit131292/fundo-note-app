package com.bridgelabz.fundo.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundo.model.Label;
import com.bridgelabz.fundo.model.Note;

@Repository
public class LabelRepositoryImpl implements LabelRepository {
	private EntityManager entityManager;

	@Autowired
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public void saveNoteLabels(Note label) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.save(label);
	}

	@Override
	@Transactional
	public void saveLabel(Label label) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.save(label);
	}

	@Override
	@Transactional
	public Label getLabelByLabelId(Integer labelId) {
		Session currentSession = entityManager.unwrap(Session.class);
		return (Label) currentSession.createQuery("from Label where id='" + labelId + "'").getSingleResult();
	}

	@Override
	@Transactional
	public void deleteLabel(Integer labelId) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.createQuery("delete from Label where id='" + labelId + "'").executeUpdate();
		System.out.println("deleted");
	}

	@Override
	@Transactional
	public List<Label> getLabel(Integer id) {
		Session currentSession = entityManager.unwrap(Session.class);

		List<Label> noteDetails = currentSession.createQuery("from Label where userId='" + id + "'").getResultList();
		System.out.println("here i'm");
		return noteDetails;
	}

	@Override
	public void getId(Integer id) {
		Session currentSession = entityManager.unwrap(Session.class);
		List<Integer> list = currentSession.createQuery("from label_note where label_id='" + id + "' ").getResultList();
		System.out.println(list);
	}

	@Override
	@Transactional
	public List<Note> getNoteByLabelId(Integer id) {
		Session currentSession = entityManager.unwrap(Session.class);

		List<Label> noteDetails = currentSession.createQuery("from Label where id='" + id + "'").getResultList();
		int size=noteDetails.size();
		List<Note> notes=noteDetails.get(0).getNotes();
//		List<Note> notes=new ArrayList<Note>();
//		for(int i=0;i<size;i++) {
//			Note note=(Note) noteDetails.get(i).getNotes();
//			notes.add(note);
//		System.out.println(noteDetails.get(0).getNotes());
//		}
		return notes;

	}
}