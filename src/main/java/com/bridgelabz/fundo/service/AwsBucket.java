package com.bridgelabz.fundo.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface AwsBucket {
	String uploadFile(MultipartFile multipartFile);
	void retrivePic(String fileName);
}
