package com.bridgelabz.fundo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundo.model.Note;
import com.bridgelabz.fundo.model.UserDetailsForRegistration;
import com.bridgelabz.fundo.repository.NoteRepository;
import com.bridgelabz.fundo.repository.UserRepository;
import com.bridgelabz.fundo.util.Util;

@Service
public class ColabService {

	@Autowired
	private UserRepository userDao;

	@Autowired
	private NoteRepository noteDao;

	public void addCollaborator(String token, String emailId, Integer noteId) {

		Integer id = Util.parseToken(token);
		List<UserDetailsForRegistration> owner = userDao.getUserbyId(id);

		UserDetailsForRegistration ownerUser = owner.get(0);
		UserDetailsForRegistration colabUser = userDao.getUserByMail(emailId);

		List<Note> ownerNotes = noteDao.getNotebyUserId(id);
		Long numberOfNotes = ownerNotes.stream().filter(n -> n.getId() == noteId).count();

		List<Note> checkColab = noteDao.getNotebyUserId(colabUser.getId());

		List<Note> colabNote = checkColab.stream().filter(notes -> notes.getId() == noteId)
				.collect(Collectors.toList());

		Note note = noteDao.getNotebyNoteId(noteId);

		note.addColab(colabUser);
		noteDao.saveColab(note);

	}

	public List<Note> getCollaboratedNoteList(Integer id) {
		UserDetailsForRegistration owner = userDao.getUserbyId(id).get(0);
		List<Note> ownerNotes = userDao.getUserbyId(owner.getId()).get(0).getColabsNote();
		return ownerNotes;
	}

	public List<UserDetailsForRegistration> getCollaboratedList(String token,Integer noteId) {
		Integer id = Util.parseToken(token);
		UserDetailsForRegistration owner = userDao.getUserbyId(id).get(0);
		
		Note ownerNotes = noteDao.getNotebyNoteId(noteId);
		
		List<UserDetailsForRegistration> colabUser = ownerNotes.getColabsUser();
		
		return colabUser;
 }

}
