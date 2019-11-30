package com.bridgelabz.fundo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundo.service.AwsBucket;

@RestController
@RequestMapping("/storage")
@CrossOrigin(allowedHeaders = "*", origins = "*", exposedHeaders = { "token" })
public class AwsS3BucketStorage {
	@Autowired
	private AwsBucket awsBucket;

	@PostMapping(value="/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String uploadFile(@RequestPart(value = "pic") MultipartFile pic) {
		System.out.println("in upload controlller");
		return this.awsBucket.uploadFile(pic);
	}

	@GetMapping("/deleteFile/{fileUrl}")
	public void deleteFile(@RequestParam(value="fileUrl")String fileUrl) {
		this.awsBucket.retrivePic(fileUrl);
	}
}
