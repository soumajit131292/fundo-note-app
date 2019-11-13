package com.bridgelabz.fundo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundo.dto.NoteDto;
import com.bridgelabz.fundo.exception.ErrorResponse;
import com.bridgelabz.fundo.model.Note;

@Service
public interface NoteService {

	public void updateNote(NoteDto note, String token, Integer noteId);

	void createANote(NoteDto note, String token);

	public void deleteNote(String token, Integer noteId);

	public List<Note> getAllNotes(String token);

	

	public void sortByDateAscending(String token);

	public void sortByDateDescending(String token);

	public List<Note> getArchiveNote(String token);

	public void doArchive(Integer noteId, String token);

	public void trash(Integer noteId, String token);

	public List<Note> getTrasheNote(String token);

	List<Note> searchNotes(String token, String keyword, String field);

	public void setRemainder(String token,LocalDateTime remainder,Integer noteId);

	public void deleteRemainder(String token, Integer noteId);

	void changePin(String token, Integer noteId);

	public void setColor(String token, String colorCode, Integer noteId);

	

	
}
