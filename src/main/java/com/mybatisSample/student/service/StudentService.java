package com.mybatisSample.student.service;

import com.mybatisSample.queue.models.QueueMessage;
import com.mybatisSample.student.dao.StudentDao;
import com.mybatisSample.student.models.Student;
import com.mybatisSample.student.queue.StudentCreateQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class StudentService {
    private final Logger LOG = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    StudentDao studentDao;

    @Autowired
    StudentCreateQueue studentCreateQueue;

    @GetMapping("/student/get")
    Student getStudent(Integer id) {
        LOG.info("Get student with id: {}", id);
        return studentDao.getStudent(id);
    }

    @GetMapping("/student/add")
    String addStudent(int id, String name) {
        LOG.info("inserting student : {}, {}", id, name);
        Student student = Student.with(id, name);
        UUID uuid = studentCreateQueue.send(student);
        return String.format("Job to add student is submitted. UUID: %s", uuid);
    }

}
