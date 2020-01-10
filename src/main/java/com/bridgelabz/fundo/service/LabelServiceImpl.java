package com.bridgelabz.fundo.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundo.dto.LabelDto;
import com.bridgelabz.fundo.exception.UserNotFoundException;
import com.bridgelabz.fundo.model.Label;
import com.bridgelabz.fundo.model.Note;
import com.bridgelabz.fundo.repository.LabelRepository;
import com.bridgelabz.fundo.repository.NoteRepository;
import com.bridgelabz.fundo.repository.UserRepository;
import com.bridgelabz.fundo.util.Util;

@Service
public class LabelServiceImpl implements LabelService {
	@Autowired
	private NoteRepository notetRepository;
	@Autowired
	private LabelRepository labelRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ElasticService elasticSearchService;
	@Override
	public void createNoteLabel(LabelDto labelDto, String token, Integer noteId) {
		Label label = modelMapper.map(labelDto, Label.class);
		Integer id = Util.parseToken(token);
		if (userRepository.isValidUser(id)) {
			Note note = notetRepository.getNotebyNoteId(noteId);
			// Note notes = note.get(0);
			System.out.println(label.getLabelName());
			label.setUserId(id);
			labelRepository.saveLabel(label);
			
			note.addLabel(label);
			labelRepository.saveNoteLabels(note);
			elasticSearchService.update(note);
		}
	}

	public void createLabel(LabelDto labelDto, String token) {
		Integer id = Util.parseToken(token);
		if (userRepository.isValidUser(id)) {
			Label label = modelMapper.map(labelDto, Label.class);
			label.setUserId(id);
			labelRepository.saveLabel(label);
		}
	}

	public void addExistingLabelOnNote(Integer labelId, Integer noteId, String token) {
		Integer id = Util.parseToken(token);
		Note note = notetRepository.getNotebyNoteId(noteId);

		

		Label label = labelRepository.getLabelByLabelId(labelId);

		note.addLabel(label);
		
		labelRepository.saveNoteLabels(note);
		elasticSearchService.update(note);
		
	}

	@Override
	public void updateLabel(LabelDto labelDto, String token, Integer labelId) {

		Integer id = Util.parseToken(token);

		if (userRepository.isValidUser(id)) {

			Label getLabel = labelRepository.getLabelByLabelId(labelId);

			getLabel.setLabelName(labelDto.getLabelName());
			labelRepository.saveLabel(getLabel);
		}
	}

	@Override
	public void addNoteLabel(String labelDto, Integer noteId, String token) {
		Label label = modelMapper.map(labelDto, Label.class);
		Integer id = Util.parseToken(token);
		if (userRepository.isValidUser(id)) {
			Note note = notetRepository.getNotebyNoteId(noteId);
			
			note.addLabel(label);
			labelRepository.saveNoteLabels(note);
			elasticSearchService.save(note);
		}
	}

	@Override
	public void deleteLabel(String token, Integer labelId) {
		Integer id = Util.parseToken(token);
		if (userRepository.isValidUser(id)) {
			labelRepository.deleteLabel(labelId);
		}
	}

	@Override
	public List<Label> getAllLabels(String token) {
		Integer id = Util.parseToken(token);
		userRepository.isValidUser(id);
		return labelRepository.getLabel(id);

	}



	@Override
	public List<Note> getNotesByLabelId(String labelName) {
		
		return labelRepository.getNoteByLabelId(labelName);
	}

	@Override
	public void removeLabel(Integer labelId, Integer noteId) {
		Note note = notetRepository.getNotebyNoteId(noteId);
		Label label = labelRepository.getLabelByLabelId(labelId);
		note.removLabel(label);
		labelRepository.saveNoteLabels(note);
		elasticSearchService.update(note);

	}
}
