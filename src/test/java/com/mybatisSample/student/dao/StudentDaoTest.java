package com.mybatisSample.student.dao;

import com.mybatisSample.student.exceptions.StudentNotFoundException;
import com.mybatisSample.student.models.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class StudentDaoTest {

    static final int id = 1;
    static final String name = "test naming";

    @Autowired
    StudentDao studentDao;

    /**
     * Insertion test
     */
    @Test
    void testInsertStudent() {
        int id = 17;
        String name = "Test name";
        Student student = Student.with(id, name);
        studentDao.delete(id);
        studentDao.insert(student);
        student = studentDao.getStudent(student.getId());
        assertEquals(student.getId(), id);
        assertEquals(student.getName(), name);
    }

    /**
     * Get test
     */
    @Test
    void testGetStudent() {
        Student student = Student.with(id, name);
        studentDao.delete(id);
        studentDao.insert(student);
        assertEquals(student.getId(), id);
        assertEquals(student.getName(), name);
    }

    /**
     * Delete test
     */
    @Test
    void testDeleteStudent() {
        // insert and assert exists
        Student student = Student.with(id, name);
        studentDao.delete(id);
        studentDao.insert(student);
        assertEquals(student.getId(), id);
        assertEquals(student.getName(), name);
        // delete
        studentDao.delete(id);
        // assert deleted
        assertThrows(StudentNotFoundException.class, () -> studentDao.getStudent(id));
    }
}
