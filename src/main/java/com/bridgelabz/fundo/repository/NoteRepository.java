package com.bridgelabz.fundo.repository;

import java.util.List;

import com.bridgelabz.fundo.model.Note;
import com.bridgelabz.fundo.model.UserDetailsForRegistration;

public interface NoteRepository {
	void saveNote(UserDetailsForRegistration note);

	List<Note> getNotebyNoteId(Integer noteId);

	void updateNote(Integer noteId, Note createdNoteByUser);

	void deleteNote(Integer noteId);

	List<Note> getNotebyUserId(Integer id);

	public void changePinStatus(Integer noteId, boolean status);

}
