package com.aws.dynamo.db.config;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.amazonaws.services.sqs.AmazonSQSClient;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.aws.dynamo.db.repositories")
public class AWSConfig {

	@Value("${aws.access-key}")
	String accessKey;

	@Value("${aws.secret-key}")
	String secretKey;

	@Value("${aws.dynamodb.endpoint}")
	private String endPoint;

	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		AmazonDynamoDB amazonDynamoDB = new AmazonDynamoDBClient(amazonAWSCredentials());
		if (!StringUtils.isEmpty(endPoint)) {
			amazonDynamoDB.setEndpoint(endPoint);
		}
		return amazonDynamoDB;
	}

	@Bean
	public AWSCredentials amazonAWSCredentials() {
		return new BasicAWSCredentials(accessKey, secretKey);
	}

	@Bean
	@Qualifier("SimpleSQS")
	public AmazonSQS amazonSQS() {
		return new AmazonSQSClient(amazonAWSCredentials()).withRegion(Regions.US_EAST_1);
	}

	@Bean
	@Qualifier("AsyncSQS")
	public AmazonSQSAsync amazonSQSAsync() {
		return new AmazonSQSAsyncClient().asyncBuilder().withRegion(Regions.US_EAST_1)
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
				.build();
	}
	
	@Bean
	@Qualifier("SimpleSNS")
	public AmazonSNS amazonSNS()
	{
		return new AmazonSNSClient().builder().withRegion(Regions.US_EAST_1)
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
				.build();
	}
}
