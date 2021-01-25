package com.aws.sqs.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;

@Component
public class SQSService {

	@Autowired
	@Qualifier("SimpleSQS")
	private AmazonSQS sqsClient;

	@Autowired
	@Qualifier("AsyncSQS")
	private AmazonSQSAsync sqsAsyncClient;
	
	@Autowired
	@Qualifier("SimpleSNS")
	private AmazonSNS snsClient;

	public String postMessage() {

		String queueUrl = "https://sqs.us-east-1.amazonaws.com/986046130078/FirstQueue";
		final SendMessageRequest request = new SendMessageRequest();
		final String body = "Your message :" + new Timestamp(System.currentTimeMillis());
		request.setMessageBody(body);
		request.setQueueUrl(queueUrl);
		sqsClient.sendMessage(request);

		final ReceiveMessageRequest receiveRequest = new ReceiveMessageRequest().withMaxNumberOfMessages(1);
		receiveRequest.setQueueUrl(queueUrl);
		final ReceiveMessageResult result = sqsClient.receiveMessage(receiveRequest);
		return result.getMessages().toString();

	}

	public String postAsyncMessage() {
		String queueUrl = "https://sqs.us-east-1.amazonaws.com/986046130078/FirstQueue";
		final SendMessageRequest request = new SendMessageRequest();
		final String body = "Your Async :" + new Timestamp(System.currentTimeMillis());
		request.setMessageBody(body);
		request.setQueueUrl(queueUrl);
		sqsAsyncClient.sendMessage(request);

		final ReceiveMessageRequest receiveRequest = new ReceiveMessageRequest().withMaxNumberOfMessages(1);
		receiveRequest.setQueueUrl(queueUrl);
		final ReceiveMessageResult result = sqsAsyncClient.receiveMessage(receiveRequest);
		return result.getMessages().toString();
	}
	
	public String snsGenerateTopic()
	{
		CreateTopicRequest request=new CreateTopicRequest("AwsTraining");
		CreateTopicResult result=snsClient.createTopic(request);
		System.out.println("TopicARN:"+result.getTopicArn());
		return result.getTopicArn();
	}
	
	public String snsPublishMessage(String message)
	{
		String topicArn="arn:aws:sns:us-east-1:986046130078:AwsTraining";
		PublishRequest request=new PublishRequest(topicArn, message);
		PublishResult result=snsClient.publish(request);
		return result.getMessageId();
	}
	
	public String snsSubscribeMessage()
	{
		String topicArn="arn:aws:sns:us-east-1:986046130078:AwsTraining";
		SubscribeRequest request=new SubscribeRequest(topicArn, "email", "makhijanaresh@hotmail.com");
		snsClient.subscribe(request);
		System.out.println("Metadata:"+snsClient.getCachedResponseMetadata(request));
		return snsClient.getCachedResponseMetadata(request).getRequestId();
	}
}
