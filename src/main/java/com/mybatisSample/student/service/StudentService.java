package com.mybatisSample.student.service;

import com.mybatisSample.student.dao.StudentDao;
import com.mybatisSample.student.models.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentService {
    private final Logger LOG = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    StudentDao studentDao;

    @GetMapping("/student/get")
    Student getStudent(Integer id) {
        LOG.info("Get student with id: {}", id);
        return studentDao.getStudent(id);
    }

    @GetMapping("/student/add")
    Student addStudent(int id, String name) {
        LOG.info("inserting student : {}, {}", id, name);
        return studentDao.insert(id, name);
    }

}
