package com.bridgelabz.fundo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonClient {

//	private String endpointUrl = "https://s3.us-east-2.amazonaws.com";
//
//	private String bucketName = "fundoonote";

	@Bean
	public AmazonS3 initializeAmazon() {
		String accessKey = "AKIAQJCIHXAMNLF2IZEX";
		String secretKey = "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";
		String region="ap-south-1";

		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		
		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
				.withRegion(Regions.fromName(region))
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

		return s3client;
	}

}
