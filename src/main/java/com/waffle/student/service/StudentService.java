package com.waffle.student.service;

import org.springframework.http.ResponseEntity;

public interface StudentService {

    ResponseEntity<String> getStudent(Integer id);

    ResponseEntity<String> addStudent(int id, String name);
}
