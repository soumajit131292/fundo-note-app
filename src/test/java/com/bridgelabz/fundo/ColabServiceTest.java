package com.bridgelabz.fundo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bridgelabz.fundo.model.Note;
import com.bridgelabz.fundo.model.UserDetailsForRegistration;
import com.bridgelabz.fundo.repository.NoteRepository;
import com.bridgelabz.fundo.repository.UserRepository;
import com.bridgelabz.fundo.service.ColabService;
import com.bridgelabz.fundo.service.ElasticService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ColabServiceTest {

	@Mock
	private UserRepository userDao;

	private static String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJJZCI6MX0.dL6z9dPcxpXnrQgKN_3b8yRKVuaNGMC2-0o9W3SMY7oPGTizuoKkPp2MHJbCQ3Uv5S4IDfDpmhHbodVRU_mh5g";

	@Mock
	private NoteRepository noteDao;
	
	@Mock
	private ElasticService elasticSearchService;
	
	@Spy
	@InjectMocks
	private ColabService colabService;

	@Test
	public void testGetCollaboratoredNoteList() {
		UserDetailsForRegistration user = new UserDetailsForRegistration();
		user.setId(1);
		List<UserDetailsForRegistration> owner = new ArrayList<>();
		owner.add(user);
		when(userDao.getUserbyId(1)).thenReturn(owner);
		List<Note> ownerNotes = owner.get(0).getColabsNote();
		assertEquals(ownerNotes, colabService.getCollaboratedNoteList(1));
	}

	@Test
	public void testDeleteCollaboratorNOteList() {
		UserDetailsForRegistration user = new UserDetailsForRegistration();
		user.setId(1);
		UserDetailsForRegistration colabUser = new UserDetailsForRegistration();
		colabUser.setEmail("abcd");
		List<UserDetailsForRegistration> owner = new ArrayList<>();
		owner.add(user);
		Note note = new Note();
		note.setDescription("hello note");
		List<Note> ownerNotes = new ArrayList<>();
		ownerNotes.add(note);
		Note colabnote = new Note();
		colabnote.setTitle("hello");
		List<Note> checkColab = new ArrayList<>();
		checkColab.add(colabnote);
		Note finalNote = new Note();
		finalNote.setTitle("Final Note");
		when(userDao.getUserbyId(1)).thenReturn(owner);
		when(userDao.getUserByMail("abcd")).thenReturn(colabUser);
		when(noteDao.getNotebyUserId(1)).thenReturn(ownerNotes);
		when(noteDao.getNotebyUserId(1)).thenReturn(checkColab);
		when(noteDao.getNotebyNoteId(2)).thenReturn(finalNote);
		colabService.deletCollaboratedList(token, "abcd", 2);
		verify(noteDao).saveColab(finalNote);
		verify(elasticSearchService).save(finalNote);

	}
}
