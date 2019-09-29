package com.bridgelabz.fundo.repository;

import java.util.List;

import com.bridgelabz.fundo.model.UserDetailsForRegistration;

public interface UserRepository {
	public List<UserDetailsForRegistration> retriveUserDetails();
	public int setToDatabase(UserDetailsForRegistration userDetails);
	public boolean deletFromDatabase(Integer userid);
	public int updatePassword(Integer Id, UserDetailsForRegistration userDetails);
	public List<UserDetailsForRegistration> getUserbyId(Integer id);
	boolean isValidUser(Integer Id);
}
