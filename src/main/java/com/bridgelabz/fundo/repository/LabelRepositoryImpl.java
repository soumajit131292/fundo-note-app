package com.bridgelabz.fundo.repository;

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
		return (Label) currentSession.createQuery("from Label where id='" + labelId + "'").uniqueResult();
	}

	@Override
	@Transactional
	public void deleteLabel(Integer labelId) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.createQuery("delete from Note where id='" + labelId + "'").executeUpdate();
	}

	@Override
	public List<Label> getLabel(Integer id) {
		Session currentSession = entityManager.unwrap(Session.class);
		return currentSession.createQuery("from Label where userId='" + id + "'").getResultList();
	}
}