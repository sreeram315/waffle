package com.waffle.infra.notification.service;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.waffle.infra.notification.models.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static java.util.Map.entry;


public class NotificationServiceImpl implements NotificationService {
    private static final Logger LOG = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Autowired
    AmazonSNSClient amazonSNSClient;

    private String topicArn;

    public static NotificationServiceImpl with(String topicArn) {
        NotificationServiceImpl notificationService = new NotificationServiceImpl();
        notificationService.topicArn = topicArn;
        return notificationService;
    }

    public Object addEmailSubscriptionToSNSTopic(String email) {
        SubscribeRequest subscribeRequest = new SubscribeRequest(topicArn, "email", email);
        amazonSNSClient.subscribe(subscribeRequest);
        String message = String.format("Subscription request is pending. To confirm the subscription " +
                "please check your email :%s", email);
        return Map.ofEntries(
                    entry("message", message)
                );
    }

    public void publishMessageToSNSTopic(Message message) {
        LOG.info(String.format("Publishing message: %s", message.getPreview()));
        PublishRequest publishRequest = new PublishRequest(
                topicArn,
                message.getBody(),
                message.getSubject()
        );
        amazonSNSClient.publish(publishRequest);
    }

}