package com.bridgelabz.fundo.controller;
  
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundo.exception.ErrorResponse;
import com.bridgelabz.fundo.model.UserDetailsForRegistration;
import com.bridgelabz.fundo.service.ColabService;

@RestController
@RequestMapping("/collaborator")
@CrossOrigin(allowedHeaders = "*", origins = "*", exposedHeaders = { "token" })
public class CollaboratorController {

	@Autowired
	private ColabService colabService;
	
	@PostMapping("/addcollab/{noteId}/{emailId}")
	public ResponseEntity<ErrorResponse>  addCollaborator(@PathVariable("emailId") String emailId,@PathVariable("noteId") Integer noteId,@RequestHeader("token") String token)
	{	
		System.out.println("in colar controller");
		colabService.addCollaborator(token,emailId,noteId);
		List<UserDetailsForRegistration> colaborator= colabService.getCollaboratedList(token,noteId);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,colaborator), HttpStatus.OK);
	}
	
	
	@GetMapping("/colabuser/{noteId}")
	public List<UserDetailsForRegistration> getCollaboratedList(@RequestHeader("token") String token,@PathVariable("noteId") Integer noteId){
		
		return colabService.getCollaboratedList(token,noteId);
	}
	@DeleteMapping("/deleteuser/{noteId}/{emailId}")
	public ResponseEntity<ErrorResponse> deletetCollaboratedList(@PathVariable("emailId") String emailId,@RequestHeader("token") String token,@PathVariable("noteId") Integer noteId){
		System.out.println("in colab delete");
		colabService.deletCollaboratedList(token,emailId,noteId);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,null), HttpStatus.OK);
	}
	
	}