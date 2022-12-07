package com.waffle.student.service;

import com.waffle.student.models.Student;

public interface StudentService {

    Student getStudent(Integer id);

    String addStudent(int id, String name);
}
