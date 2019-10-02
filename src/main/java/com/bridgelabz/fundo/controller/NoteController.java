package com.bridgelabz.fundo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.bridgelabz.fundo.model.Note;
import com.bridgelabz.fundo.service.NoteService;

@RestController
@RequestMapping("/notes")
public class NoteController {
	@Autowired
	private NoteService noteService;

	@PutMapping("/createnote")
	public void noteCreate(@RequestBody NoteDto note, @RequestHeader("token") String token) {
		noteService.createANote(note, token);

	}

	@PutMapping("/updatenote/{noteId}")
	public void updateNote(@RequestBody NoteDto note, @RequestHeader("token") String token,
			@PathVariable("noteId") Integer noteId) {
		noteService.updateNote(note, token, noteId);
	}

	@DeleteMapping("/deletenote/{noteId}")
	public void deleteNote(@RequestHeader("token") String token, @PathVariable("noteId") Integer noteId) {
		noteService.deleteNote(token, noteId);
	}

	@GetMapping("/getnotes")
	public List<Note> getNotes(@RequestHeader("token") String token) {
		return noteService.getAllNotes(token);
	}

	@PostMapping("/changePin/{noteId}")
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
}
