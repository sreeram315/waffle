package com.mybatisSample.queue.service;

import com.mybatisSample.queue.models.QueueMessage;
import com.mybatisSample.student.models.Student;
import org.springframework.messaging.Message;

import java.util.UUID;

public interface QueueService<T> {

    public UUID send(QueueMessage<T> message);

    public void listen(Message<QueueMessage<Student>> message);
}
