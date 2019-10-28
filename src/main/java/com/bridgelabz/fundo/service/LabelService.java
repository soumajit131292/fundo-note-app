package com.bridgelabz.fundo.service;

import java.util.List;

import com.bridgelabz.fundo.dto.LabelDto;
import com.bridgelabz.fundo.model.Label;
import com.bridgelabz.fundo.model.Note;

public interface LabelService {

	void updateLabel(LabelDto labelDto, String token, Integer noteId);

	void createNoteLabel(LabelDto labelDto, String token, Integer noteId);

	void createLabel(LabelDto labelDto, String token);

	void addNoteLabel(LabelDto labelDto, String token, Integer noteId);

	void deleteLabel(String token, Integer labelId);

	List<Label> getAllLabels(String token);

	void addExistingLabelOnNote(Integer labelId, Integer noteId, String token);

	
	
	List<Note> getNotesByLabelId(Integer id);

}
