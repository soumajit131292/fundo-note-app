package com.bridgelabz.fundo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundo.service.AwsBucket;

@RestController
@RequestMapping("/storage/")
public class AwsS3BucketStorage {
	@Autowired
	private AwsBucket awsBucket;

	@PostMapping("/uploadFile")
	public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
		return this.awsBucket.uploadFile(file);
	}

//	@DeleteMapping("/deleteFile")
//	public String deleteFile(@RequestPart(value = "url") String fileUrl) {
//		return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
//	}
}
