package com.bridgelabz.fundo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundo.dto.NoteDto;
import com.bridgelabz.fundo.exception.ErrorResponse;
import com.bridgelabz.fundo.model.Note;
import com.bridgelabz.fundo.model.NoteAndColab;
import com.bridgelabz.fundo.model.UserDetailsForRegistration;
import com.bridgelabz.fundo.service.ColabService;
import com.bridgelabz.fundo.service.NoteService;

@RestController
@RequestMapping("/notes")
@CrossOrigin(allowedHeaders = "*", origins = "*", exposedHeaders = { "token" })
public class NoteController {
	@Autowired
	private NoteService noteService;

	@Autowired
	private ColabService colabService;
	
	@PostMapping("/createnote/{token}")
	public ResponseEntity<ErrorResponse> noteCreate(@RequestBody NoteDto note,@PathVariable("token") String token) {
		System.out.println("hello");
		noteService.createANote(note, token);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,null), HttpStatus.OK);
	}

	@PutMapping("/updatenote/{noteId}")
	public ResponseEntity<ErrorResponse> updateNote(@PathVariable("noteId") Integer noteId,@RequestHeader("token") String token,@RequestBody NoteDto note
			) {
		System.out.println("in update method");
		noteService.updateNote(note, token, noteId);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,null), HttpStatus.OK);
	}

	@DeleteMapping("/deletenote/{noteId}")
	public ResponseEntity<ErrorResponse> deleteNote(@PathVariable("noteId") Integer noteId,@RequestHeader("token") String token) {
		noteService.deleteNote(token, noteId);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,null), HttpStatus.OK);
	}
	
	@GetMapping("/getnotes/{token}")
	public ResponseEntity<ErrorResponse> getNotes(@PathVariable("token") String token) {
		System.out.println("in controller");
	List<NoteAndColab> noteColab=new ArrayList<>();
	List<Note> notes=	noteService.getAllNotes(token);
	
	List<UserDetailsForRegistration> users= new ArrayList<>();
	for(Note n : notes) {
		NoteAndColab c= new NoteAndColab();
		c.setNote(n);
		users=colabService.getCollaboratedList(token, n.getId());
		c.setUser(users);
		noteColab.add(c);
	}
		
	return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,noteColab), HttpStatus.OK);
	}

	@PutMapping("/changePin")
	public void changePin(@RequestHeader("token") String token,
			@RequestParam("noteId") Integer noteId) {
		noteService.changePin(token, noteId);
	}

	@PostMapping("/sortingbydatedecending")
	public void sortByDateDecending(@RequestHeader("token") String token) {
		noteService.sortByDateAscending(token);
	}

	@PostMapping("/sortingbydateascending")
	public void sortByDateAscending(@RequestHeader("token") String token) {
		noteService.sortByDateAscending(token);
	}
	
	@PutMapping("/archive/{noteId}")
	public ResponseEntity<ErrorResponse> doArchive(@PathVariable("noteId") Integer noteId,@RequestHeader("token") String token )
	{
		noteService.doArchive(noteId,token);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,null), HttpStatus.OK);
	}
	@GetMapping("/getarchivenotes")
	public ResponseEntity<ErrorResponse> getArchiveNote(@RequestHeader("token") String token )
	{
		List<Note> archievenote=noteService.getArchiveNote(token);
		System.out.println("in controller");
		List<NoteAndColab> noteColab=new ArrayList<>();
		
		
	List<UserDetailsForRegistration> users= new ArrayList<>();
		for(Note n : archievenote) {
			NoteAndColab c= new NoteAndColab();
			c.setNote(n);
			users=colabService.getCollaboratedList(token, n.getId());
			c.setUser(users);
			noteColab.add(c);
	}
			
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,noteColab), HttpStatus.OK);
	}
	@PutMapping("/trash/{noteId}")
	public ResponseEntity<ErrorResponse> trash(@PathVariable("noteId") Integer noteId,@RequestHeader("token") String token )
	{System.out.println("in trash");
		noteService.trash(noteId,token);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,null), HttpStatus.OK);
	}
	
	@GetMapping("/getrashnotes")
	public List<Note> getTrashNote(@RequestHeader("token") String token )
	{
		return noteService.getTrasheNote(token);
	}
	
	@GetMapping("/searchnotes/{keyword}/{field}")
	public List<Note> searchNotes(@RequestHeader("token") String token,@PathVariable("keyword") String keyword,@PathVariable("field") String field  )
	{
		System.out.println("hello");
		return noteService.searchNotes(token,keyword,field);
	}
	// eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJJZCI6MX0.dL6z9dPcxpXnrQgKN_3b8yRKVuaNGMC2-0o9W3SMY7oPGTizuoKkPp2MHJbCQ3Uv5S4IDfDpmhHbodVRU_mh5g
	@PostMapping("/remainder")
	public ResponseEntity<ErrorResponse> setRemainder(@RequestHeader String token,@RequestParam("datetime") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime datetime,@RequestParam("noteId") Integer noteId){
		System.out.println("in remainder");
		noteService.setRemainder(token,datetime,noteId);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,null), HttpStatus.OK);
		
	}
	@PutMapping("/updateremainder/{noteId}")
	public ResponseEntity<ErrorResponse> updateRemainder(@RequestHeader String token,@RequestParam("datetime") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)LocalDateTime remainder,@PathVariable("noteId") Integer noteId){
		noteService.setRemainder(token,remainder,noteId);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,null), HttpStatus.OK);
		
	}
	@DeleteMapping("/deleteremainder")
	public ResponseEntity<ErrorResponse> deleteRemainder(@RequestHeader String token,@RequestParam("noteId") Integer noteId){
		noteService.deleteRemainder(token,noteId);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,null), HttpStatus.OK);
		
	}
	@PostMapping("/setcolor/{colorCode}/{noteId}")
	public ResponseEntity<ErrorResponse> setColor(@RequestHeader String token,@PathVariable("colorCode") String colorCode,@PathVariable("noteId") Integer noteId){
		System.out.println("in remainder");
		noteService.setColor(token,colorCode,noteId);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,null), HttpStatus.OK);
		
	}
	
}




