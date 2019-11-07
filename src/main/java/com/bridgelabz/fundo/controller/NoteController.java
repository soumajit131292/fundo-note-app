package com.bridgelabz.fundo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.bridgelabz.fundo.service.NoteService;
import com.fasterxml.jackson.annotation.JsonIgnore;

@RestController
@RequestMapping("/notes")
@CrossOrigin(allowedHeaders = "*", origins = "*", exposedHeaders = { "token" })
public class NoteController {
	@Autowired
	private NoteService noteService;

	@PostMapping("/createnote/{token}")
	public ResponseEntity<ErrorResponse> noteCreate(@RequestBody NoteDto note,@PathVariable("token") String token) {
		System.out.println("hello");
		noteService.createANote(note, token);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null), HttpStatus.OK);
	}

	@PutMapping("/updatenote/{noteId}")
	public ResponseEntity<ErrorResponse> updateNote(@PathVariable("noteId") Integer noteId,@RequestHeader("token") String token,@RequestBody NoteDto note
			) {
		System.out.println("in update method");
		noteService.updateNote(note, token, noteId);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null), HttpStatus.OK);
	}

	@DeleteMapping("/deletenote/{noteId}")
	public ResponseEntity<ErrorResponse> deleteNote(@PathVariable("noteId") Integer noteId,@RequestHeader("token") String token) {
		noteService.deleteNote(token, noteId);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null), HttpStatus.OK);
	}
	
	@GetMapping("/getnotes/{token}")
	public List<Note> getNotes(@PathVariable("token") String token) {
		System.out.println("in controller");
		return noteService.getAllNotes(token);
	}

	@PutMapping("/changePin/{noteId}")
	public void changePin(@RequestBody NoteDto note, @RequestHeader("token") String token,
			@PathVariable("noteId") Integer noteId, @RequestParam boolean status) {
		noteService.changePin(token, noteId, note, status);
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
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null), HttpStatus.OK);
	}
	@GetMapping("/getarchivenotes")
	public List<Note> getArchiveNote(@RequestHeader("token") String token )
	{
		return noteService.getArchiveNote(token);
	}
	@PutMapping("/trash/{noteId}")
	public ResponseEntity<ErrorResponse> trash(@PathVariable("noteId") Integer noteId,@RequestHeader("token") String token )
	{System.out.println("in trash");
		noteService.trash(noteId,token);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null), HttpStatus.OK);
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
}