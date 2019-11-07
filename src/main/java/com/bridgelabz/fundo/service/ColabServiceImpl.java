package com.bridgelabz.fundo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundo.dto.ColabDto;
import com.bridgelabz.fundo.exception.UserNotFoundException;
import com.bridgelabz.fundo.model.Colaborator;
import com.bridgelabz.fundo.model.Note;
import com.bridgelabz.fundo.repository.ColabRepository;
import com.bridgelabz.fundo.repository.NoteRepository;

@Service
public class ColabServiceImpl {

	@Autowired
	private ColabRepository colabRepository;
	@Autowired
	private NoteRepository noteDao;
	

	public void addColab(ColabDto colabDto, Integer noteId) {
		System.out.println("before boolean");
		boolean colabExist = colabRepository.getColabId(colabDto.getUserId(), noteId);
		System.out.println("in colab service");
		Integer userId = colabRepository.getUserIdByEmail(colabDto);
		System.out.println(userId);

		if (!colabExist) {
			Colaborator colaborator = new Colaborator();
			colaborator.setUserId(userId);
			colaborator.setUserEmailId(colabDto.getEmailId());
			Note note =	noteDao.getNotebyNoteId(noteId);
			
			note.addCollaborator(colaborator);
			colabRepository.addColab(note);
		} else
			throw new UserNotFoundException("collaborator alreday existed");
	}

	public void removeColab(ColabDto colabDto, Integer noteId) {
		colabRepository.removeColab(colabDto.getUserId(), noteId);
	}

	public List<String> getNotes(Integer noteId) {
		return colabRepository.getColabUsers(noteId);
		
	}

}
