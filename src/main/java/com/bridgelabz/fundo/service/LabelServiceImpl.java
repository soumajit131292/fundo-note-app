package com.bridgelabz.fundo.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundo.dto.LabelDto;
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

	@Override
public void createNoteLabel(LabelDto labelDto, String token, Integer noteId) {
		Label label = modelMapper.map(labelDto, Label.class);
		Integer id = Util.parseToken(token);
		if (userRepository.isValidUser(id)) {
			List<Note> note = notetRepository.getNotebyNoteId(noteId);
			Note notes = note.get(0);
			notes.addLabel(label);
			labelRepository.saveNoteLabels(notes);
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

	@Override
	public void updateLabel(LabelDto labelDto, String token, Integer labelId) {
		Label label = modelMapper.map(labelDto, Label.class);
		Integer id = Util.parseToken(token);
		if (userRepository.isValidUser(id)) {
			List<Label> getLabel = labelRepository.getLabelByLabelId(labelId);
			getLabel.get(0).setLabelName(label.getLabelName());
			labelRepository.saveLabel(label);
		}
	}

	@Override
	public void addNoteLabel(LabelDto labelDto, String token, Integer noteId) {
		Label label = modelMapper.map(labelDto, Label.class);
		Integer id = Util.parseToken(token);
		if (userRepository.isValidUser(id)) {
			List<Note> getNote = notetRepository.getNotebyNoteId(noteId);
			Note note = getNote.get(0);
			note.addLabel(label);
			labelRepository.saveNoteLabels(note);
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
	public List<Label> getLabels(String token) {
		Integer id = Util.parseToken(token);
		if (userRepository.isValidUser(id)) {
			return labelRepository.getLabel(id);
		}
		return null;

	}
}