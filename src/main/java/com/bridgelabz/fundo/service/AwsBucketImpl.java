package com.bridgelabz.fundo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
@Service
public class AwsBucketImpl implements AwsBucket {

	@Autowired
	private AmazonS3 s3Client;
	private String endpointUrl = "https://s3.ap-south-1.amazonaws.com";

	private String bucketName = "fundoonote";

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	private String generateFileName(MultipartFile multiPart) {
		return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
	}

	private String uploadFileTos3bucket(String fileName, File file) {
		try {
			
			s3Client.putObject(
					new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (AmazonServiceException e) {
			return "uploadFileTos3bucket().Uploading failed :" + e.getMessage();
		}
		return "Uploading Successfull -> ";

	}

	@Override
	public String uploadFile(MultipartFile multipartFile) {
	
		String fileUrl = "";
		String status="";
	    try {
	        File file = convertMultiPartToFile(multipartFile);
	        String fileName = generateFileName(multipartFile);
	        fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
	        status=uploadFileTos3bucket(fileName, file);
	        System.out.println(status);
	        System.out.println(fileUrl);
	        //file.delete();
	        System.out.println("after uploading");
	    } catch (Exception e) {
	       e.printStackTrace();
	    }
	    return fileUrl;
	}

	@Override
	public void retrivePic(String fileName) {
		
		S3Object file=s3Client.getObject(bucketName, fileName);
		System.out.println("Content-Type: " + file.getObjectMetadata().getContentType());
	}
}
