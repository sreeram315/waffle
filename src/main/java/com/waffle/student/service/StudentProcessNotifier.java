package com.waffle.student.service;

import com.waffle.infra.notification.models.Message;

public interface StudentProcessNotifier {
    void notifyStudentAddition(Message message);
}
