package com.bridgelabz.fundo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyInt;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.bridgelabz.fundo.dto.NoteDto;
import com.bridgelabz.fundo.model.Note;
import com.bridgelabz.fundo.model.UserDetailsForRegistration;
import com.bridgelabz.fundo.repository.NoteRepository;
import com.bridgelabz.fundo.repository.UserRepository;
import com.bridgelabz.fundo.repository.UserRepositoryImpl;
import com.bridgelabz.fundo.service.ColabService;
import com.bridgelabz.fundo.service.ElasticService;
import com.bridgelabz.fundo.service.NoteServiceImpl;
import com.bridgelabz.fundo.service.UserServiceImpl;
import com.bridgelabz.fundo.util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestNoteServiceImpl {

	@Mock
	private UserRepositoryImpl userdaoimpl;

	private static String tokenu = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJJZCI6MX0.dL6z9dPcxpXnrQgKN_3b8yRKVuaNGMC2-0o9W3SMY7oPGTizuoKkPp2MHJbCQ3Uv5S4IDfDpmhHbodVRU_mh5g";
	@Mock
	private UserRepository userDao;

	@Mock
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	@Mock
	private ModelMapper modelmapper;
	@Mock
	private JavaMailSender emailSender;
	@Autowired
	private Util util;
	@Mock
	private RedisTemplate<String, Object> redisTemplate;
	@Mock
	private ColabService colabservice;

	@Mock
	private Util token;

    @Spy
	@InjectMocks
	private UserServiceImpl userServiceImpl;
    
    @Spy
    @InjectMocks
    private NoteServiceImpl noteService;
	
    @Mock
    private NoteRepository noteDao;

    @Mock
    private ElasticService elasticSearchService;
    
	@Test
	public void retriveUserFromDatabaseTest() {			
		UserDetailsForRegistration user=new UserDetailsForRegistration();
			 	user.setEmail("DSFHDHSFHDSKFH");
		List<UserDetailsForRegistration> details =new ArrayList<UserDetailsForRegistration>();		
		details.add(user);
		System.out.println(details.toString());
		when(userdaoimpl.retriveUserDetails()).thenReturn(details);	
       assertEquals(1, userServiceImpl.retriveUserFromDatabase().size());
	}
		
	
	@Test
	public void testSaveToDatabase() {
		NoteDto notedto=new NoteDto();
		notedto.setDescription("abc");
		notedto.setTitle("def");
		Note note=new Note();
		Date date = new Date();
		Timestamp timeStamp = new Timestamp(date.getTime());
		note.setDescription("desc");
		note.setTitle("title");
		note.setCreatedOn(timeStamp);
		UserDetailsForRegistration user=new UserDetailsForRegistration();
	 	user.setId(1);
	 
        List<UserDetailsForRegistration> details =new ArrayList<UserDetailsForRegistration>();		
        details.add(user);
		when(userDao.getUserbyId(anyInt())).thenReturn(details);
		
		// ARGUMENT  MATCHER
		//NICE MOCK BEHAVIOUR,YOU HAVE TO ASSIGN  int ID AS ANYINT(),
		
		user.addNote(note);
		noteService.createANote(notedto, tokenu);
		verify(noteDao).saveNote(user);
		//verify(elasticSearchService).save(note);
			}	
	
	@Test
	public void deletNote() {
		when(userdaoimpl.isValidUser(anyInt())).thenReturn(true);
		noteService.deleteNote(tokenu, 1);
		verify(noteDao).deleteNote(01);
		verify(elasticSearchService).delete(01);
	}

	
	@Test
	public void testupdatemethod()
	{
		NoteDto newnote=new NoteDto();
		newnote.setDescription("abc");
		Note note=new Note();
		note.setDescription("hi");
		note.setId(1);
		when(userdaoimpl.isValidUser(anyInt())).thenReturn(true);
		when(noteDao.getNotebyNoteId(anyInt())).thenReturn(note);
		noteService.updateNote(newnote, tokenu, 1);
		
		verify(noteDao).updateNote(1, note);
		verify(elasticSearchService).update(note);
	}
	
	
	
	@Test
	public void testGetAllNotes() {
		Note note=new Note();
	//	note.setId(1);
		Note note1=new Note();
		
		// note1.setId(3);
		
		//NO NEED TO ASSIGN ANY DADTA EVEN AFTER CREATING A OBJECT
		
		List<Note> notes=new ArrayList<Note>();
		notes.add(note);
		when(noteDao.getNotebyUserId(anyInt())).thenReturn(notes);
		List<Note> colabnotes=new ArrayList<Note>();
		
		notes.addAll(colabnotes);
		when(colabservice.getCollaboratedNoteList(anyInt())).thenReturn(notes);
		assertEquals(notes,noteService.getAllNotes(tokenu));
		
		
	}
	
	
	
	
	
	
	

}