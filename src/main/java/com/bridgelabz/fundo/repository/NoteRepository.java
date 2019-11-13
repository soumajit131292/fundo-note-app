package com.bridgelabz.fundo.repository;

import java.util.List;

import com.bridgelabz.fundo.model.Note;
import com.bridgelabz.fundo.model.UserDetailsForRegistration;

public interface NoteRepository {
	void saveNote(UserDetailsForRegistration note);

	Note getNotebyNoteId(Integer noteId);

	void updateNote(Integer noteId, Note createdNoteByUser);

	void deleteNote(Integer noteId);

	List<Note> getNotebyUserId(Integer id);

	public void changePinStatus(Integer noteId, boolean status);

	List<Note> getArchiveNotebyUserId(Integer id);

	List<Note> getTrashNotebyUserId(Integer id);

	List<Integer> findNoteIdByUserId(Integer userId);

	Note getNoteByColabId(Integer colab);

	void savenotewithRemainder(Note note);

	List<Note> getNotesByEmailId(String emailId);

	
	

	void saveColab(Note note);

	List<Note> getLabels(Integer id);

	void getRemaindeNotes(Integer userId);

}
