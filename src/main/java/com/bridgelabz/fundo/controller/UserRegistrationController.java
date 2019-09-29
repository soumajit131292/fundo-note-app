package com.bridgelabz.fundo.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundo.dto.ResetPassword;
import com.bridgelabz.fundo.dto.UserDto;
import com.bridgelabz.fundo.exception.ErrorResponse;
import com.bridgelabz.fundo.exception.UserNotFoundException;
import com.bridgelabz.fundo.model.LoginUser;
import com.bridgelabz.fundo.service.UserService;
import com.bridgelabz.fundo.util.Util;

@RestController
@RequestMapping("/api")
public class UserRegistrationController {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private UserService userService;

	@GetMapping("/get")
	public List<UserDto> getDetails() {
		if (userService.retriveUserFromDatabase().size() > 0) {
			return userService.retriveUserFromDatabase();
		} else
			throw new UserNotFoundException("no data found");
	}

	@PostMapping("/users")
	public ResponseEntity<ErrorResponse> saveToDatabase(@RequestBody UserDto userDetails) throws MessagingException {
		if (userService.saveToDatabase(userDetails) > 0)
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null), HttpStatus.OK);
		else
			throw new UserNotFoundException("no data found");
	}

	@PostMapping("/forgot-password/{id}")
	public ResponseEntity<ErrorResponse> forgotPassword(@PathVariable("id") Integer Id,
			@RequestBody ResetPassword userDetails) throws MessagingException {
		if (userService.isUserPresent(Id)) {
			userService.forgotPassword(Id);
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null), HttpStatus.OK);
		} else
			throw new UserNotFoundException("no data found");
	}

	@GetMapping("/verify/{token}")
	public ResponseEntity<ErrorResponse> verifyUserByMail(@PathVariable("token") String token) {
		if (userService.verifyUser(token)) {
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null), HttpStatus.OK);
		} else {
			throw new UserNotFoundException("no data found");
		}
	}

	@PostMapping("/login")
	public ResponseEntity<ErrorResponse> login(@RequestBody LoginUser loginUser) {
		userService.doLogin(loginUser);
			Integer id = userService.findIdOfCurrentUser(loginUser.getEmail());
			String JwtToken = Util.generateToken(id);
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", JwtToken), HttpStatus.OK);
	}

	@DeleteMapping("/deleteuser/{id}")
	public ResponseEntity<ErrorResponse> deleteUserById(@PathVariable("id") Integer id) {
		userService.deleteFromDatabase(id); 
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null), HttpStatus.OK);
		 
	}

	@PutMapping("/updateuser/{token}")
	public ResponseEntity<ErrorResponse> updateUser(@PathVariable("token") String token,
			@RequestBody ResetPassword userDetails) {
		userService.updateUser(token, userDetails);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null), HttpStatus.OK);
	}
}