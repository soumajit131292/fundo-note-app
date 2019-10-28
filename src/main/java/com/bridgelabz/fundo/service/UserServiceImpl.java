package com.bridgelabz.fundo.service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundo.dto.ResetPassword;
import com.bridgelabz.fundo.dto.UserDto;
import com.bridgelabz.fundo.exception.UserNotFoundException;
import com.bridgelabz.fundo.model.LoginUser;
import com.bridgelabz.fundo.model.UserDetailsForRegistration;
import com.bridgelabz.fundo.repository.UserRepositoryImpl;
import com.bridgelabz.fundo.util.Util;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepositoryImpl userdaoimpl;
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	@Autowired
	private ModelMapper modelmapper;
	@Autowired
	private JavaMailSender emailSender;
	@Autowired
	private Util token;

	private String hashPassword(String plainTextPassword) {
		return bcryptPasswordEncoder.encode(plainTextPassword);
	}

	@Override
	public void sendEmail(String url, String generatedToken, String emailId) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(emailId);
		helper.setSubject("verify");
		helper.setText(url + generatedToken);
		emailSender.send(message);
	}

	@Override
	public Integer findIdOfCurrentUser(String email) {
	
		List<UserDetailsForRegistration> userDetails = userdaoimpl.getId(email);
		
		if (userDetails.size()>0) {
			return userDetails.get(0).getId();		
		}
		else
		throw new UserNotFoundException("user is not valid");
		
	}

	@Override
	public void doLogin(LoginUser loginUser) {
		Integer id = findIdOfCurrentUser(loginUser.getEmail());
		if (id != 0) {
			List<UserDetailsForRegistration> result = userdaoimpl.checkUser(id);
			if (bcryptPasswordEncoder.matches(loginUser.getPassword(), result.get(0).getPassword()))
				System.out.println("hello");
			else
				throw new UserNotFoundException("invalid credientials");
		}
	}

	@Override
	public boolean isUserPresent(Integer Id) {
		return userdaoimpl.isValidUser(Id);
	}

	@Override
	public boolean forgotPassword(Integer id) throws MessagingException {
		
		String generatedToken = token.generateToken(id);
		String url = "http://localhost:3000/resetpassword/";
		sendEmail(url, generatedToken, userdaoimpl.getUserById(id));
		
		return true;
	}

	@Override
	public boolean verifyUser(String fromGeneratedToken) {
		try {
			Integer Id = token.parseToken(fromGeneratedToken);
			if (userdaoimpl.isValidUser(Id)) {
				userdaoimpl.changeStatus(Id);
				return true;
			}
		} catch (Exception exception) {
			throw new UserNotFoundException("user is not valid");
		}
		return false;

	}

	public List<UserDetailsForRegistration> getUserbyId(Integer id) {
		return userdaoimpl.getUserbyId(id);
	}

	@Override
	public List<UserDto> retriveUserFromDatabase() {
		List<UserDto> users = new ArrayList<UserDto>();
		List<UserDetailsForRegistration> details = userdaoimpl.retriveUserDetails();
		for (UserDetailsForRegistration obj : details) {
			UserDto abc = modelmapper.map(obj, UserDto.class);
			users.add(abc);
		}
		return users;
	}

	@Override
	public void deleteFromDatabase(Integer id) {
		if (!userdaoimpl.deletFromDatabase(id))
			throw new UserNotFoundException("no data found");

	}

	public void changeActiveStatus(Integer Id) {
		userdaoimpl.changeStatus(Id);
	}

	@Override
	public int saveToDatabase(UserDto userDetails) throws MessagingException {
		String password = userDetails.getPassword();
		UserDetailsForRegistration userRegistrationDetails = modelmapper.map(userDetails,
				UserDetailsForRegistration.class);
		userRegistrationDetails.setPassword(hashPassword(password));
		String url = "http://localhost:3000/verify/";
		if (userdaoimpl.setToDatabase(userRegistrationDetails) > 0) {
			String generatedToken = token.generateToken(findIdOfCurrentUser(userDetails.getEmail()));
			sendEmail(url, generatedToken, userDetails.getEmail());
			return 1;
		}
		System.out.println("after checking everything now returning to main");
		return 0;
	}

	@Override
	public int updateUser(String generatedtoken, ResetPassword passwordReset) {
		Integer Id = token.parseToken(generatedtoken);
		String encodePassword = hashPassword(passwordReset.getPassword());
		System.out.println(encodePassword);
		passwordReset.setPassword(encodePassword);
		return userdaoimpl.updatePassword(Id, modelmapper.map(passwordReset, UserDetailsForRegistration.class));
	}
}