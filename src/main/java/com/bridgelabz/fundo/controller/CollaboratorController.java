package com.bridgelabz.fundo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundo.service.ColabService;

@RestController
@RequestMapping("/collaborator")
@CrossOrigin(allowedHeaders = "*", origins = "*", exposedHeaders = { "token" })
public class CollaboratorController {

	@Autowired
	private ColabService colabService;
	
	@PostMapping("/addcollab/{noteId}/{emailId}")
	public void addCollaborator(@PathVariable("emailId") String emailId,@PathVariable("noteId") Integer noteId,@RequestHeader("token") String token)
	{
		
		colabService.addCollaborator(token,emailId,noteId);
	}
	
	
//	@GetMapping("/colabuser")
//	public List<UserDetailsForRegistration> getCollaboratedList(@RequestHeader("token") String token){
//		
//		return colabService.getCollaboratedList(token);
//	}
	
	}
