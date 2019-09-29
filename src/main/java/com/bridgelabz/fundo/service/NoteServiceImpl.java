package com.bridgelabz.fundo.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundo.dto.NoteDto;
import com.bridgelabz.fundo.exception.ErrorResponse;
import com.bridgelabz.fundo.model.Note;
import com.bridgelabz.fundo.model.UserDetailsForRegistration;
import com.bridgelabz.fundo.repository.NoteRepository;
import com.bridgelabz.fundo.repository.UserRepository;
import com.bridgelabz.fundo.util.Util;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private NoteRepository noteDao;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private UserService userService;

	public Note dtoToEntity(NoteDto note) {
		return modelMapper.map(note, Note.class);
	}

	@Override
	public void createANote(NoteDto note, String token) {
		Integer id = Util.parseToken(token);
		if (userService.isUserPresent(id)) {
			Note createdNoteByUser = dtoToEntity(note);
			Date date = new Date();
			Timestamp timeStamp = new Timestamp(date.getTime());
			createdNoteByUser.setCreatedOn(timeStamp);
			List<UserDetailsForRegistration> user = userDao.getUserbyId(id);
			UserDetailsForRegistration obj = user.get(0);
			System.out.println(createdNoteByUser);
			obj.addNote(createdNoteByUser);
			noteDao.saveNote(obj);
		}
	}

	@Override
	public void updateNote(NoteDto note, String token, Integer noteId) {
		Integer id = Util.parseToken(token);
		if (userService.isUserPresent(id)) {
			Date date = new Date();
			Timestamp timeStamp = new Timestamp(date.getTime());
			List<Note> createdNote = noteDao.getNotebyNoteId(noteId);
			createdNote.get(0).setDescription(note.getDescription());
			createdNote.get(0).setTitle(note.getTitle());
			createdNote.get(0).setUpdatedOn(timeStamp);
			noteDao.updateNote(noteId, createdNote.get(0));
		}
	}

	@Override
	public void deleteNote(String token, Integer noteId) {
		Integer id = Util.parseToken(token);
		if (userService.isUserPresent(id)) {
			noteDao.deleteNote(noteId);
		}
	}

	@Override
	public List<Note> getAllNotes(String token) {
		Integer id = Util.parseToken(token);
		if (userService.isUserPresent(id)) {
			return noteDao.getNotebyUserId(id);
		}
		return null;
	}

	@Override
	public void changePin(String token, Integer noteId, NoteDto note, boolean status) {

		Integer id = Util.parseToken(token);
		if (userService.isUserPresent(id)) {
			noteDao.changePinStatus(noteId, status);
		}
	}

	@Override
	public void sortByDateDescending(String token) {
		Integer id = Util.parseToken(token);
		if (userService.isUserPresent(id)) {
			List<Note> notes = noteDao.getNotebyUserId(id);
			notes.stream().sorted((p1, p2) -> p2.getCreatedOn().compareTo(p1.getCreatedOn()))
					.collect(Collectors.toList()).forEach(System.out::println);
			
		}
	}

	@Override
	public void sortByDateAscending(String token) {
		Integer id = Util.parseToken(token);
		if (userService.isUserPresent(id)) {
			List<Note> notes = noteDao.getNotebyUserId(id);
			notes.stream().sorted((p1, p2) -> p1.getCreatedOn().compareTo(p2.getCreatedOn()))
					.collect(Collectors.toList()).forEach(System.out::println);
		}
	}
}