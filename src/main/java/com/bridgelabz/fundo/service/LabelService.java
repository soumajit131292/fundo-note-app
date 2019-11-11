package com.bridgelabz.fundo.service;

import java.util.List;

import com.bridgelabz.fundo.dto.LabelDto;
import com.bridgelabz.fundo.model.Label;
import com.bridgelabz.fundo.model.Note;

public interface LabelService {

	void updateLabel(LabelDto labelDto, String token, Integer noteId);

	void createNoteLabel(LabelDto labelDto, String token, Integer noteId);

	void createLabel(LabelDto labelDto, String token);

	

	void deleteLabel(String token, Integer labelId);

	List<Label> getAllLabels(String token);

	void addExistingLabelOnNote(Integer labelId, Integer noteId, String token);

	
	
	List<Note> getNotesByLabelId(String  labelName);

	void removeLabel(Integer labelId, Integer noteId);

	void addNoteLabel(String labelDto, Integer noteId, String token);

}
