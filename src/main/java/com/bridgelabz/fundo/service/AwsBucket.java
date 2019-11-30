package com.bridgelabz.fundo.service;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;

public interface AwsBucket {
	
	S3Object retrivePic(String fileName);
	String uploadFile(MultipartFile multipartFile, String token);
}
