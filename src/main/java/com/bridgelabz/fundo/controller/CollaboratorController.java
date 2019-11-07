package com.bridgelabz.fundo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundo.dto.ColabDto;
import com.bridgelabz.fundo.exception.ErrorResponse;
import com.bridgelabz.fundo.service.ColabServiceImpl;

@RestController
@RequestMapping("/colaborator")
@CrossOrigin(allowedHeaders = "*", origins = "*", exposedHeaders = { "token" })
public class CollaboratorController {

	@Autowired
	private ColabServiceImpl colabService;
	@PostMapping("/addcolaborator/{noteId}")
	public ResponseEntity<ErrorResponse> addCollaborator(@RequestBody ColabDto colab,@PathVariable("noteId") Integer noteId,@RequestHeader("token") String token) {
		System.out.println("hello");
		colabService.addColab(colab, noteId,token);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null), HttpStatus.OK);
	}
	@DeleteMapping("/removeColaborator/{noteId}")
	public ResponseEntity<ErrorResponse> removeCollaborator(@RequestBody ColabDto colab,@PathVariable("noteId") Integer noteId) {
		System.out.println("hello");
		colabService.removeColab(colab, noteId);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "1 row affected", null), HttpStatus.OK);
	}
	
	
}
