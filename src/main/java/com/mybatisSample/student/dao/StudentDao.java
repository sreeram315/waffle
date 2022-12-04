package com.mybatisSample.student.dao;

import com.mybatisSample.student.exceptions.StudentNotFoundException;
import com.mybatisSample.student.mapper.StudentMapper;
import com.mybatisSample.student.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentDao {

    @Autowired
    StudentMapper studentMapper;

    public Student getStudent(int id) {
        Student student = studentMapper.getStudent(id);
        if(student == null) {
            throw StudentNotFoundException.with(id);
        }
        return student;
    }

    public Student insert(int id, String name) {
        studentMapper.insertStudent(id, name);
        return this.getStudent(id);
    }

    public void insert(Student student) {
        this.insert(student.getId(), student.getName());
    }

    public void delete(int id) {
        studentMapper.deleteStudent(id);
    }
}
