package com.waffle.student.service.impl;

import com.waffle.infra.queue.models.utils.Jsonable;
import com.waffle.student.dao.StudentDao;
import com.waffle.student.exceptions.StudentNotFoundException;
import com.waffle.student.models.Student;
import com.waffle.student.queue.StudentCreateQueue;
import com.waffle.student.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
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
    public ResponseEntity<String> getStudent(Integer id) {
        LOG.info("Request: Get student with id: {}", id);
        try {
            Student student = studentDao.getStudent(id);
            return ResponseEntity.ok(student.toJson());
        } catch (StudentNotFoundException notFoundException) {
            return new ResponseEntity<>(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping("/student/add")
    public ResponseEntity<String> addStudent(int id, String name) {
        LOG.info("Request: Insert student: ID - {}, Name - {}", id, name);
        Student student = Student.with(id, name);
        UUID uuid = studentCreateQueue.push(student);
        Map<String, String> response = Map.ofEntries(
                Map.entry("uuid", uuid.toString()),
                Map.entry("message", "Job to add student is submitted")
        );
        return ResponseEntity.ok(Jsonable.toJson(response));
    }

}
