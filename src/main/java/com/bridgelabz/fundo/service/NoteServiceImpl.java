package com.bridgelabz.fundo.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundo.dto.NoteDto;
import com.bridgelabz.fundo.model.Note;
import com.bridgelabz.fundo.model.UserDetailsForRegistration;
import com.bridgelabz.fundo.repository.NoteRepository;
import com.bridgelabz.fundo.repository.UserRepository;
import com.bridgelabz.fundo.util.Util;

@Service
public class NoteServiceImpl implements NoteService {
	/*
	 * parag:
	 * eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJJZCI6M30.Q-uA_a-lyKpuLvkvlv8Eb0h4ja1-
	 * Z0SCejPvRqtHbkzwTRLzf1LTW-8fFXzjHpNYI6JtjM19MRIm49sWawu1dg
	 */
	/*
	 * eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJJZCI6MX0.
	 * dL6z9dPcxpXnrQgKN_3b8yRKVuaNGMC2-
	 * 0o9W3SMY7oPGTizuoKkPp2MHJbCQ3Uv5S4IDfDpmhHbodVRU_mh5g
	 */
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private NoteRepository noteDao;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private UserService userService;
	@Autowired
	private ElasticService elasticSearchService;
	@Autowired
	private RedisTemplate<String, UserDetailsForRegistration> redisTemplate;
	@Autowired
	private ColabServiceImpl colabService;

	public Note dtoToEntity(NoteDto note) {
		return modelMapper.map(note, Note.class);
	}

	@Override
	public void createANote(NoteDto note, String token) {

		Integer id = Util.parseToken(token);
		System.out.println("hello man");

		Note createdNoteByUser = dtoToEntity(note);
		Date date = new Date();
		Timestamp timeStamp = new Timestamp(date.getTime());
		createdNoteByUser.setCreatedOn(timeStamp);

		createdNoteByUser.setArchive(false);
		createdNoteByUser.setInTrash(false);
		createdNoteByUser.setPinned(false);
		List<UserDetailsForRegistration> user = userDao.getUserbyId(id);

		UserDetailsForRegistration obj = user.get(0);

		obj.addNote(createdNoteByUser);
		noteDao.saveNote(obj);
		elasticSearchService.save(createdNoteByUser);

	}

	@Override
	public void updateNote(NoteDto note, String token, Integer noteId) {
		Integer id = Util.parseToken(token);
		if (userService.isUserPresent(id)) {
			Date date = new Date();
			Timestamp timeStamp = new Timestamp(date.getTime());
			Note createdNote = noteDao.getNotebyNoteId(noteId);
			createdNote.setDescription(note.getDescription());
			createdNote.setTitle(note.getTitle());
			createdNote.setUpdatedOn(timeStamp);
			noteDao.updateNote(noteId, createdNote);
			elasticSearchService.update(createdNote);
			System.out.println("note inserted");
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
		List<Note> arrayOfNotes = new ArrayList<>();
		arrayOfNotes = noteDao.getNotebyUserId(id);
		List<String> listOfColabNotes = colabService.getNotes(id);
		System.out.println(listOfColabNotes);
//		List<Note> listOfColabNotes = colabService.getNotes(id);
//		if (listOfColabNotes.size() > 0) {
//			for (Note n : listOfColabNotes) {
//				arrayOfNotes.add(n);
//			}
//		}
		return arrayOfNotes;
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

	@Override
	public void doArchive(Integer noteId, String token) {

		Integer id = Util.parseToken(token);
		if (userService.isUserPresent(id)) {
			Note n = noteDao.getNotebyNoteId(noteId);

			if (n.isArchive() == true)
				n.setArchive(false);
			else
				n.setArchive(true);
			noteDao.updateNote(noteId, n);
		}

	}

	@Override
	public List<Note> getArchiveNote(String token) {

		Integer id = Util.parseToken(token);

		return noteDao.getArchiveNotebyUserId(id);
	}

	@Override
	public void trash(Integer noteId, String token) {
		Integer id = Util.parseToken(token);
		if (userService.isUserPresent(id)) {
			Note n = noteDao.getNotebyNoteId(noteId);

			if (n.isInTrash() == true)
				n.setInTrash(false);
			else
				n.setInTrash(true);
			noteDao.updateNote(noteId, n);
		}

	}

	@Override
	public List<Note> getTrasheNote(String token) {
		Integer id = Util.parseToken(token);

		return noteDao.getTrashNotebyUserId(id);
	}

	@Override
	public List<Note> searchNotes(String token, String keyword, String field) {
		Integer userId = Util.parseToken(token);

		List<Integer> noteIds = noteDao.findNoteIdByUserId(userId);

		if (noteIds.size() > 0) {
			System.out.println("in searchNotes of note service");
			return elasticSearchService.search(keyword, field);

		}
		return null;

	}

}