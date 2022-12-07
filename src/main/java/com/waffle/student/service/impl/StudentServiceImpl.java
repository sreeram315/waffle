package com.waffle.student.service.impl;

import com.waffle.student.dao.StudentDao;
import com.waffle.student.models.Student;
import com.waffle.student.queue.StudentCreateQueue;
import com.waffle.student.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class StudentServiceImpl implements StudentService {
    private final Logger LOG = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    StudentDao studentDao;

    @Autowired
    StudentCreateQueue studentCreateQueue;

    @Override
    @GetMapping("/student/get")
    public Student getStudent(Integer id) {
        LOG.info("Get student with id: {}", id);
        return studentDao.getStudent(id);
    }

    @Override
    @GetMapping("/student/add")
    public String addStudent(int id, String name) {
        LOG.info("inserting student : {}, {}", id, name);
        Student student = Student.with(id, name);
        UUID uuid = studentCreateQueue.send(student);
        return String.format("Job to add student is submitted. UUID: %s", uuid);
    }

}
