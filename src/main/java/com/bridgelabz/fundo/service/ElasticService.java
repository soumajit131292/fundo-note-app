package com.bridgelabz.fundo.service;

import java.util.List;

import com.bridgelabz.fundo.model.Note;

public interface ElasticService {

	void save(Note object);
	void update(Note note);
	void delete(String noteId);
	List<Note> search(String search, String field);

}
