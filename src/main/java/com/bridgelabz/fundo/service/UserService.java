package com.bridgelabz.fundo.service;

import java.util.List;

import javax.mail.MessagingException;

import com.bridgelabz.fundo.dto.ResetPassword;
import com.bridgelabz.fundo.dto.UserDto;
import com.bridgelabz.fundo.model.LoginUser;
import com.bridgelabz.fundo.model.UserDetailsForRegistration;

public interface UserService {

	public List<UserDto> retriveUserFromDatabase();

	public void deleteFromDatabase(Integer id);

	public int saveToDatabase(UserDto userDetails) throws MessagingException;

	public boolean verifyUser(String token);

	void doLogin(LoginUser loginUser);

	boolean isUserPresent(Integer Id);

	boolean forgotPassword(Integer Id) throws MessagingException;

	int updateUser(String token, ResetPassword passwordReset);

	public Integer findIdOfCurrentUser(String email);

	void sendEmail(String url, String generatedToken, String emailId) throws MessagingException;

	public List<UserDetailsForRegistration> getUserbyId(Integer id);

}