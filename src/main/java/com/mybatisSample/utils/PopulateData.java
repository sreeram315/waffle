package com.mybatisSample.utils;

import com.mybatisSample.student.dao.StudentDao;
import com.mybatisSample.student.models.Student;

import java.util.List;

// unused for now.
public class PopulateData {
    public static void init(StudentDao studentDao) {
        List<Student> students = List.of(
                Student.with(1, "Dam"),
                Student.with(2, "Wie")
        );
        students.forEach(studentDao::insert);
    }
}
