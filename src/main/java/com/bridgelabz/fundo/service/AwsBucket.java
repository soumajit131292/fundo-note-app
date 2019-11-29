package com.bridgelabz.fundo.service;

import org.springframework.web.multipart.MultipartFile;

public interface AwsBucket {
	String uploadFile(MultipartFile multipartFile);
	
}
