package com.waffle.student.service.impl;

import com.waffle.infra.notification.models.Message;
import com.waffle.infra.notification.service.NotificationService;
import com.waffle.student.service.StudentProcessNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class StudentProcessNotifierImpl implements StudentProcessNotifier {
    private final Logger LOG = LoggerFactory.getLogger(StudentProcessNotifierImpl.class);

    @Autowired
    @Qualifier("${student.notificationService.name}")
    NotificationService studentNotificationService;

    @Override
    public void notifyStudentAddition(Message message) {
        LOG.info("Adding notification: {}", message.getPreview());
        studentNotificationService.publishMessageToSNSTopic(message);
    }
}
