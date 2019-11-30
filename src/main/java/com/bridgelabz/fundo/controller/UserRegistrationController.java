package com.bridgelabz.fundo.controller;

import java.io.Serializable;
import java.util.List;

import javax.mail.MessagingException;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
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
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundo.dto.ResetPassword;
import com.bridgelabz.fundo.dto.UserDto;
import com.bridgelabz.fundo.exception.ErrorResponse;
import com.bridgelabz.fundo.exception.UserNotFoundException;
import com.bridgelabz.fundo.model.LoginUser;
import com.bridgelabz.fundo.model.UserDetailsForRegistration;
import com.bridgelabz.fundo.repository.UserRepository;
import com.bridgelabz.fundo.service.NoteService;
import com.bridgelabz.fundo.service.UserService;
import com.bridgelabz.fundo.util.Util;

@RestController
@RequestMapping("/user")
@CrossOrigin(allowedHeaders = "*", origins = "*", exposedHeaders = { "token" })
public class UserRegistrationController implements Serializable {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private UserService userService;

	@SuppressWarnings("unused")
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NoteService noteService;

	@GetMapping("/get")
	public List<UserDto> getDetails() {
		if (userService.retriveUserFromDatabase().size() > 0) {
			return userService.retriveUserFromDatabase();
		} else
			throw new UserNotFoundException("no data found");
	}

	@PostMapping("/register")
	public ResponseEntity<ErrorResponse> registerUser(@RequestBody UserDto userDetails) throws MessagingException {
		if (userService.saveToDatabase(userDetails) > 0)
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,null), HttpStatus.OK);
		else
			throw new UserNotFoundException("already registered");
	}

	@PostMapping("/forgotpassword")
	public ResponseEntity<ErrorResponse> forgotPassword(@RequestBody UserDto body) throws MessagingException {

		System.out.println("in forgot controller");
		Integer id = userService.findIdOfCurrentUser(body.getEmail());
		System.out.println("id");
		userService.forgotPassword(id);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,null), HttpStatus.OK);
	}

	@PutMapping("/verify/{token}")
	public ResponseEntity<ErrorResponse> verifyUserByMail(@PathVariable("token") String token) {
		if (userService.verifyUser(token)) {
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,null), HttpStatus.OK);
		} else {
			throw new UserNotFoundException("no data found");
		}
	}

	@PostMapping("/login")
	public ResponseEntity<ErrorResponse> login(@RequestBody LoginUser loginUser) {
		UserDetailsForRegistration obj=userService.doLogin(loginUser);
		Integer id = userService.findIdOfCurrentUser(loginUser.getEmail());
		String JwtToken = Util.generateToken(id);
	/***CORRECCT CODE FOR OBJECT AND RETRIVE OF REDIS TEMPLATE ***/	
		
		
//		List<UserDetailsForRegistration> details = userService.getUserbyId(id);
//		System.out.println(details.get(0).getFirstName());
//		redisTemplate.opsForValue().set("JwtToken", details.get(0));
//		UserDetailsForRegistration user = (UserDetailsForRegistration) redisTemplate.opsForValue().get("JwtToken");
//		System.out.println(user.getFirstName());
		
	/***WILL Implement Later**/	
		
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", JwtToken,obj), HttpStatus.OK);
	}

	@DeleteMapping("/deleteuser/{id}")
	public ResponseEntity<ErrorResponse> deleteUserById(@PathVariable("id") Integer id) {
		userService.deleteFromDatabase(id);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,null), HttpStatus.OK);
	}

	@PutMapping("/resetpassword/{token}")
	public ResponseEntity<ErrorResponse> updateUser(@PathVariable("token") String token,
			@RequestBody ResetPassword userDetails) {
		System.out.println("hello");
		System.out.println(userDetails.getPassword());
		userService.updateUser(token, userDetails);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success", null,null), HttpStatus.OK);
	}

	@GetMapping("/loggedinuser")
	public UserDetailsForRegistration getUser(@RequestHeader("token") String token) {
		return userService.getUser(token);

	}
}