package com.waffle.infra.notification.service;

import com.waffle.infra.notification.models.Message;

public interface NotificationService {

    Object addEmailSubscriptionToSNSTopic(String email);

    void publishMessageToSNSTopic(Message message);
}
