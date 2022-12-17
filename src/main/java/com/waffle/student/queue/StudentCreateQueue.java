package com.waffle.student.queue;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.waffle.infra.notification.models.Message;
import com.waffle.infra.queue.dao.QueueLogDao;
import com.waffle.infra.queue.exceptions.JsonConversionExcetion;
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
    public UUID push(Student student){
        LOG.info("Sending message: {}", student);
        QueueMessage<Student> message = QueueMessage.with(student);
        UUID uuid = message.getId();
        queueLogDao.insert(name, uuid);
        queueMessagingTemplate.convertAndSend(endPoint, message.toJson());
        queueLogDao.updatePushAsSuccess(uuid);
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
        queueLogDao.updateAsReceived(uuid);
        // Consume
        Student student = queueMessage.getPayload();
        LOG.info("Adding student to database. Student: {}", student);
        try {
            studentDao.insert(student);
            studentProcessNotifier.notifyStudentAddition(Message.with("Student added", student.toString()));
        } catch (Exception e) {
            LOG.error("Student addition failed with exception", e);
            queueLogDao.updateComment(uuid, e.getMessage());
        }
        queueLogDao.updateAsConsumed(uuid);
        LOG.info("Message consumed UUID: {}", uuid);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private QueueMessage<Student> fromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            QueueMessage queueMessage = mapper.readValue(json, QueueMessage.class);
            String studentJsonString = mapper.readTree(json).get("payload").toString();
            Student student = mapper.readValue(studentJsonString, Student.class);
            queueMessage.setPayload(student);
            return queueMessage;
        }
        catch (JsonProcessingException e){
            throw new JsonConversionExcetion(e.getMessage());
        }
    }

}
