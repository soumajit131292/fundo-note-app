package com.bridgelabz.fundo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;
import com.bridgelabz.fundo.service.AwsBucket;

@RestController
@RequestMapping("/storage")
@CrossOrigin(allowedHeaders = "*", origins = "*", exposedHeaders = { "token" })
public class AwsS3BucketStorage {
	@Autowired
	private AwsBucket awsBucket;

	@PostMapping(value="/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String uploadFile(@RequestPart(value = "pic") MultipartFile pic,@RequestHeader(value="token") String token) {
		System.out.println("in upload controlller");
		return this.awsBucket.uploadFile(pic,token);
	}

	@GetMapping("/deleteFile/{fileUrl}")
	public S3Object deleteFile(@RequestParam(value="fileUrl")String fileUrl) {
		return this.awsBucket.retrivePic(fileUrl);
	}
}
