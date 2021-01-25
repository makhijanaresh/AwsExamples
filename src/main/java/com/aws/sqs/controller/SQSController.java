package com.aws.sqs.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aws.sqs.service.SQSService;

@RestController
@RequestMapping("/sqs")
public class SQSController {

	@Autowired
	private SQSService service;
	
	@GetMapping("/post")
	public String post()
	{
		return service.postMessage();
	}
	
	@GetMapping("/post/async")
	public String postAsync()
	{
		return service.postAsyncMessage();
	}
	
	@GetMapping("/sns/topic")
	public String snsTopic()
	{
		return service.snsGenerateTopic();
	}
	
	@GetMapping(path = "/sns/publish/{message}")
	public String snsPublishMessage(@PathVariable String message)
	{
		System.out.println("Message:"+message);
		return service.snsPublishMessage(message);
	}
	
	@GetMapping(path = "/sns/subscribe")
	public String snsPublishMessage()
	{
		return service.snsSubscribeMessage();
	}
}
