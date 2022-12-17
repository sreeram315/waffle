package com.waffle.student.service.impl;

import com.waffle.infra.queue.models.utils.Jsonable;
import com.waffle.student.dao.StudentDao;
import com.waffle.student.exceptions.StudentNotFoundException;
import com.waffle.student.models.Student;
import com.waffle.student.queue.StudentCreateQueue;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StudentServiceImplTest {

    private static final StudentServiceImpl studentService = new StudentServiceImpl();
    private static StudentDao studentDao;
    private static StudentCreateQueue studentCreateQueue;

    private static final int STUDENT_ID_MIN = 1;
    private static final int STUDENT_ID_MAX = 1000;

    private static final int STUDENT_NAME_MIN_LENGTH = 3;
    private static final int STUDENT_NAME_MAX_LENGTH = 200;

    @BeforeAll
    public static void before() {
        studentDao = mock(StudentDao.class);
        studentCreateQueue = mock(StudentCreateQueue.class);
        ReflectionTestUtils.setField(studentService, "studentDao", studentDao);
        ReflectionTestUtils.setField(studentService, "studentCreateQueue", studentCreateQueue);
    }

    @Test
    public void getStudentWhenStudentExists() {
        int id = new Random().nextInt(STUDENT_ID_MIN, STUDENT_ID_MAX);
        String name = RandomStringUtils.randomAlphanumeric(STUDENT_NAME_MIN_LENGTH, STUDENT_NAME_MAX_LENGTH);
        when(studentDao.getStudent(id)).thenReturn(Student.with(id, name));
        ResponseEntity<String> response = studentService.getStudent(id);
        String responseString = response.getBody();
        Student student = Jsonable.fromJson(responseString, Student.class);
        Assertions.assertEquals(student.getId(), id);
        Assertions.assertEquals(student.getName(), name);
    }

    @Test
    public void getStudentWhenStudentDoesNotExists() {
        int id = new Random().nextInt(STUDENT_ID_MIN, STUDENT_ID_MAX);
        when(studentDao.getStudent(id)).thenThrow(StudentNotFoundException.with(id));
        ResponseEntity<String> response = studentService.getStudent(id);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void addStudent() {
        int id = new Random().nextInt(STUDENT_ID_MIN, STUDENT_ID_MAX);
        String name = RandomStringUtils.randomAlphanumeric(STUDENT_NAME_MIN_LENGTH, STUDENT_NAME_MAX_LENGTH);
        UUID uuid = UUID.randomUUID();
        when(studentCreateQueue.push(any())).thenReturn(uuid);
        ResponseEntity<String> response = studentService.addStudent(id, name);
        Map<?, ?> responseMap = Jsonable.fromJson(response.getBody(), Map.class);
        Assertions.assertNotNull(responseMap.get("uuid"));
    }

}
