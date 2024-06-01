package com.shareit.shareit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

	@Value("${spring.aws.access-key}")
	private String accessKey;

	@Value("${spring.aws.secret-key}")
	private String secretKey;

	@Bean
	public S3Client defaultS3Client() {

		AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKey, secretKey);

		return S3Client.builder()
			.credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
			.region(Region.AP_NORTHEAST_2)
			.build();
	}
}
