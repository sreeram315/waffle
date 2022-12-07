package com.waffle.notice.config;

import com.waffle.infra.notification.service.NotificationService;
import com.waffle.infra.notification.service.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NoticeConfiguration {

    @Value("${notice.cloud.sns.topic.arn}")
    private String topicArn;

    @Bean("${notice.notificationService.name}")
    public NotificationService notificationService() {
        return NotificationServiceImpl.with(topicArn);
    }

}
