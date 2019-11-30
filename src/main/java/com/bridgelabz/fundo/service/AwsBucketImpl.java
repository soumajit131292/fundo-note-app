package com.bridgelabz.fundo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.bridgelabz.fundo.model.UserDetailsForRegistration;
import com.bridgelabz.fundo.repository.UserRepository;
import com.bridgelabz.fundo.repository.UserRepositoryImpl;
import com.bridgelabz.fundo.util.Util;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AwsBucketImpl implements AwsBucket {

	@Autowired
	private AmazonS3 s3Client;
	@Autowired
	private Util token;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private UserRepositoryImpl userdaoimpl;

	private String bucketName = "fundoonote";

	private File convertMultiPartToFile(MultipartFile file) throws IOException {

		File convFile = new File(file.getOriginalFilename());
		return convFile;
	}

	private String generateFileName(MultipartFile multiPart) {
		return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
	}

	private String uploadFileTos3bucket(String fileName, File file) {
		try {
			log.info("", s3Client.listBuckets());
			s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
		} catch (AmazonServiceException e) {
			return "uploadFileTos3bucket().Uploading failed :" + e.getMessage();
		}
		return "Success";

	}

	@Override
	public String uploadFile(MultipartFile multipartFile, String userToken) {
		String result = "UNSUCCESSFUL";
		String fileUrl = "";
		String status = "";
		try {
			File file = convertMultiPartToFile(multipartFile);
			String fileName = generateFileName(multipartFile);

			status = uploadFileTos3bucket(fileName, file);

			if (status == "Success") {
				GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName,
						fileName).withMethod(HttpMethod.GET);

				URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
				System.out.println(url);
				Integer id = token.parseToken(userToken);
				List<UserDetailsForRegistration> users = userDao.getUserbyId(id);
				users.get(0).setPicture(url);
				userdaoimpl.saveToDatabase(users.get(0));
				result = "UPLOADED PICTURE";
			}
			System.out.println(status);
			System.out.println(fileUrl);
			// file.delete();
			System.out.println("after uploading");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public S3Object retrivePic(String fileName) {

		return null;
	}
}