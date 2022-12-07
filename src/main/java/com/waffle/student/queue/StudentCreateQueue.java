package com.waffle.student.queue;


import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.waffle.infra.notification.models.Message;
import com.waffle.infra.queue.dao.QueueLogDao;
import com.waffle.infra.queue.models.QueueMessage;
import com.waffle.infra.queue.service.QueueService;
import com.waffle.student.dao.StudentDao;
import com.waffle.student.models.Student;
import com.waffle.student.service.StudentProcessNotifier;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@ConditionalOnProperty(value="cloud.aws.enabled")
public class StudentCreateQueue implements QueueService<Student> {
    private static final Logger LOG = LoggerFactory.getLogger(StudentCreateQueue.class);

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private QueueLogDao queueLogDao;

    @Autowired
    private StudentProcessNotifier studentProcessNotifier;

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Value("${student.cloud.queue.endPoint}")
    private String endPoint;

    @Value("${student.cloud.queue.name}")
    private String name;

    @Override
    public UUID send(Student student){
        LOG.info("Sending message: {}", student);
        QueueMessage<Student> message = QueueMessage.with(student);
        UUID uuid = message.getId();
        queueLogDao.insert(name, uuid);
        queueMessagingTemplate.convertAndSend(endPoint, message.toJson());
        queueLogDao.update_push_as_success(uuid);
        LOG.info("Message published. UUID: {}", uuid);
        return uuid;
    }

    @Override
    @MessageMapping
    @SqsListener("${student.cloud.queue.name}")
    public void listen(String message) {
        // Receive
        QueueMessage<Student> queueMessage = fromJson(message);
        UUID uuid = queueMessage.getId();
        LOG.info("Received message with ID: {}", uuid);
        queueLogDao.update_as_received(uuid);
        // Consume
        Student student = queueMessage.getPayload();
        LOG.info("Adding student to database. Student: {}", student);
        try {
            studentDao.insert(student);
            studentProcessNotifier.notifyStudentAddition(Message.with("Student added", student.toString()));
        } catch (Exception e) {
            LOG.error("Student addition failed with exception", e);
            queueLogDao.update_comment(uuid, e.getMessage());
        }
        queueLogDao.update_as_consumed(uuid);
        LOG.info("Message consumed UUID: {}", uuid);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private QueueMessage<Student> fromJson(String json) {
        QueueMessage queueMessage = new Gson().fromJson(json, QueueMessage.class);
        Student student = new Gson().fromJson(JsonParser.parseString(json).getAsJsonObject().get("payload").toString(),
                Student.class);
        queueMessage.setPayload(student);
        return queueMessage;
    }

}
