package com.bridgelabz.fundo;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.bridgelabz.fundo.model.LoginUser;
import com.bridgelabz.fundo.model.UserDetailsForRegistration;
import com.bridgelabz.fundo.repository.UserRepositoryImpl;
import com.bridgelabz.fundo.service.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestLOginRegistration {

	@Mock
	private UserRepositoryImpl userdaoimpl;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	 @Spy
		@InjectMocks
		private UserServiceImpl userServiceImpl;
	
	
	@Test
	public void testLoginForSuccesscase()
	{
		LoginUser loginUser=new LoginUser("soumajit@","123456");
		
		UserDetailsForRegistration user=new UserDetailsForRegistration();
		user.setEmail("soumajit@");
		user.setPassword("123456");
		List<UserDetailsForRegistration> userDetails = new ArrayList<>();
		userDetails.add(user);
	
		
		when(userdaoimpl.getId(anyString())).thenReturn(userDetails);
		
		UserDetailsForRegistration user1=new UserDetailsForRegistration();
		user1.setEmail("soumajit@");
		user1.setPassword("123456");
		List<UserDetailsForRegistration> result = new ArrayList<>();
		result.add(user1);
		
		when(userdaoimpl.checkUser(userDetails.get(0).getId())).thenReturn(result);
		
	
		if(bcryptPasswordEncoder.matches(loginUser.getPassword(), result.get(0).getPassword())) {
			UserDetailsForRegistration a=userServiceImpl.doLogin(loginUser);
			System.out.println(a.toString());
			assertEquals(user1,a);
		}		
		
	}
	
	@Test(expected=RuntimeException.class)
	public void testLoginForFailureCase()
	{
		LoginUser loginUser=new LoginUser("soumajit@","123456");
		
		UserDetailsForRegistration user=new UserDetailsForRegistration();
		user.setEmail("soumajit@");
		user.setPassword("12345");
		List<UserDetailsForRegistration> userDetails = new ArrayList<>();
		userDetails.add(user);
	
		
		when(userdaoimpl.getId(anyString())).thenReturn(userDetails);
		
		UserDetailsForRegistration user1=new UserDetailsForRegistration();
		user1.setEmail("soumajit@");
		user1.setPassword("12345");
		List<UserDetailsForRegistration> result = new ArrayList<>();
		result.add(user1);
		
		when(userdaoimpl.checkUser(userDetails.get(0).getId())).thenReturn(result);

		if(!bcryptPasswordEncoder.matches(loginUser.getPassword(), result.get(0).getPassword())) {
			UserDetailsForRegistration a=userServiceImpl.doLogin(loginUser);
			System.out.println(a.toString());
			assertThatExceptionOfType(RuntimeException.class);
					}		
		
	}
	
	
	
	
}
