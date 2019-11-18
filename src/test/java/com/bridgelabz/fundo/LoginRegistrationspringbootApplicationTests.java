package com.bridgelabz.fundo;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit4.SpringRunner;
import com.bridgelabz.fundo.dto.UserDto;
import com.bridgelabz.fundo.model.LoginUser;
import com.bridgelabz.fundo.model.UserDetailsForRegistration;
import com.bridgelabz.fundo.repository.UserRepositoryImpl;
import com.bridgelabz.fundo.service.UserServiceImpl;
import com.bridgelabz.fundo.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginRegistrationspringbootApplicationTests {
	
	@Mock
	private UserRepositoryImpl userdaoimpl;
//
//	@Mock
//	private BCryptPasswordEncoder bcryptPasswordEncoder;
//	@Mock
//	private ModelMapper modelmapper;
//	@Mock
//	private JavaMailSender emailSender;
//	@Mock
//	private Util token;
//	@Mock
//	private RedisTemplate<String, Object> redisTemplate;
	
//	@Mock
//	private BCryptPasswordEncoder bcryptPasswordEncoder;
	@Mock
	private ModelMapper modelmapper;
//	@Mock
//	private JavaMailSender emailSender;
//	@Mock
//	private Util token;
//	@Mock
//	private RedisTemplate<String, Object> redisTemplate;

//	@InjectMocks
//	UserServiceImpl userServiceImpl;
//
//	@Test
//	public void retriveUserFromDatabaseTest() {	
//		
//		// model class pojo class
//		
//		UserDetailsForRegistration user=new UserDetailsForRegistration();
//			 	user.setEmail("DSFHDHSFHDSKFH");
//			 	
//		List<UserDetailsForRegistration> details =new ArrayList<UserDetailsForRegistration>();		
//		details.add(user);
//		
//		when(userdaoimpl.retriveUserDetails()).thenReturn(details);	
//	
//       assertEquals(1, userServiceImpl.retriveUserFromDatabase().size());
//	}
	
//@Ignore
//	@Test
//	public void doLoginTest() {
//		LoginUser user=new LoginUser();
//		user.setEmail("soumajit131292@gmail.com");
////		user.setPassword("1234567890");
////	when(userServiceImpl.doLogin(any())).thenReturn();	
////    assertEquals(1, userServiceImpl.doLogin(user).size());
//	}
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	@Autowired
    private MockMvc mockMvc;
	@Test
	public void LoginTest() throws Exception {
		mockMvc.perform(post("/user/login")
				.content(asJsonString(new LoginUser("soumajit131292@gmail.com","1234567890")))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}