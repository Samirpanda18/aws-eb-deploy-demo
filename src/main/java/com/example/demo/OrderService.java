package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import java.util.UUID;

@Service
public class OrderService {

    @Value("${sqs.queue.url}")
    private String queueUrl;

    private final SqsClient sqsClient;

    public OrderService(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    public String placeOrder() {
        String orderId = UUID.randomUUID().toString();
        System.out.println("Sending to SQS: " + orderId);


        SendMessageRequest req = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody("New order placed: " + orderId)
                .build();

        SendMessageResponse response = sqsClient.sendMessage(req);
        System.out.println("SQS sent: " + response.messageId());
        return orderId;
    }
}