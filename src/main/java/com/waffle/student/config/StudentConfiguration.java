package com.waffle.student.config;

import com.waffle.infra.notification.service.NotificationService;
import com.waffle.infra.notification.service.impl.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfiguration {

    @Value("${student.cloud.sns.topic.arn}")
    private String topicArn;

    @Bean("${student.notificationService.name}")
    public NotificationService notificationService() {
        return NotificationServiceImpl.with(topicArn);
    }

}
