package com.bridgelabz.fundo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundo.dto.LabelDto;
import com.bridgelabz.fundo.exception.ErrorResponse;
import com.bridgelabz.fundo.model.Label;
import com.bridgelabz.fundo.model.Note;
import com.bridgelabz.fundo.model.NoteAndColab;
import com.bridgelabz.fundo.model.UserDetailsForRegistration;
import com.bridgelabz.fundo.service.ColabService;
import com.bridgelabz.fundo.service.LabelService;

@RestController
@RequestMapping("/label")
@CrossOrigin(allowedHeaders = "*", origins = "*", exposedHeaders = { "token" })
public class LabelController {
	@Autowired
	private LabelService labelService;
	@Autowired
	private ColabService colabService;
	@PostMapping("/create/{noteId}")
	public void createNoteLabel(@RequestBody LabelDto labelDto, @PathVariable("noteId") Integer noteId, @RequestHeader String token) {
		labelService.createNoteLabel(labelDto, token, noteId);
	}
	@PostMapping("/createlabel")
	public ResponseEntity<ErrorResponse> createLabel(@RequestHeader String token,@RequestBody LabelDto labelDto) {
		labelService.createLabel(labelDto, token);
		System.out.println("in label controller after finishing visit");
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,null), HttpStatus.OK);
	}
	@PutMapping("/updatebylabelid/{labelId}")
	public ResponseEntity<ErrorResponse> updateLabel( @PathVariable Integer labelId,@RequestBody LabelDto labelDto,@RequestHeader String token ) {
		labelService.updateLabel(labelDto, token, labelId);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,null), HttpStatus.OK);
	}
	@DeleteMapping("/delete/{labelId}")
	public ResponseEntity<ErrorResponse> deleteLabel(@PathVariable Integer labelId, @RequestHeader String token) {
		System.out.println("in label delete");
		labelService.deleteLabel(token, labelId);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,null), HttpStatus.OK);
	}
	@GetMapping("/getlabels/{token}")
	public List<Label> getAllLabel(@PathVariable String token)
	{
		System.out.println("in label controller");
		return labelService.getAllLabels(token);	
	}
	@PutMapping("/update/{labelId}/{noteId}")
	public ResponseEntity<ErrorResponse> addExistingLabelLabelOnNote(@PathVariable Integer labelId,@PathVariable Integer noteId, @RequestHeader String token) {
		System.out.println("done in add label api");
		labelService.addExistingLabelOnNote(labelId,noteId,token);
		System.out.println("OKAY");
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,null), HttpStatus.OK);
	}
	@GetMapping("/notebylabelid/{labelName}")
	public ResponseEntity<ErrorResponse> getNotesByLabelName(@PathVariable String labelName,@RequestHeader("token") String token)
	{ 	  
		List<NoteAndColab> noteColab=new ArrayList<>();
		List<Note> notes=	labelService.getNotesByLabelId(labelName);	
		List<UserDetailsForRegistration> users= new ArrayList<>();
		for(Note n : notes) {
			NoteAndColab c= new NoteAndColab();
			c.setNote(n);
			users=colabService.getCollaboratedList(token, n.getId());
			c.setUser(users);
			noteColab.add(c);
		}
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,noteColab), HttpStatus.OK);
		}
	@DeleteMapping("/deletelable/{labelId}/{noteId}")
	public ResponseEntity<ErrorResponse> deleteLabelOnNote(@PathVariable Integer labelId,@PathVariable Integer noteId) {
		labelService.removeLabel(labelId,noteId);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,null), HttpStatus.OK);
	}
	}