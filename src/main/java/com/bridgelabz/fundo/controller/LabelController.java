package com.bridgelabz.fundo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundo.dto.LabelDto;
import com.bridgelabz.fundo.model.Label;
import com.bridgelabz.fundo.service.LabelService;

@RestController("/label")
public class LabelController {
	@Autowired
	private LabelService labelService;

	@PostMapping("/create/{noteId}")
	public void createNoteLabel(@RequestBody LabelDto labelDto, @PathVariable Integer noteId, @RequestParam String token) {
		labelService.createNoteLabel(labelDto, token, noteId);
	}
	@PutMapping("/update/{noteId}")
	public void addNoteLabel(@RequestBody LabelDto labelDto, @PathVariable Integer noteId, @RequestParam String token) {
		labelService.addNoteLabel(labelDto, token, noteId);
	}
	@PostMapping("/create")
	public void createLabel(@RequestBody LabelDto labelDto,@RequestParam String token) {
		labelService.createLabel(labelDto, token);
	}
	@PutMapping("/update/{labelId}")
	public void updateLabel(@RequestBody LabelDto labelDto, @PathVariable Integer labelId, @RequestParam String token) {
		labelService.updateLabel(labelDto, token, labelId);
	}
	@DeleteMapping("/delete")
	@PutMapping("/update/{labelId}")
	public void deleteLabel(@PathVariable Integer labelId, @RequestParam String token) {
		labelService.deleteLabel(token, labelId);
	}
	@GetMapping("/getlabels")
	public List<Label> getAllLabel(@RequestParam String token)
	{
		return labelService.getLabels(token);
	}
	
	
}