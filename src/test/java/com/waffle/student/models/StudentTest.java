package com.waffle.student.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StudentTest {
    private static final Logger LOG = LoggerFactory.getLogger(StudentTest.class);

    @Test
    void testJesonable() throws JSONException {
        Student student = Student.with(1, "Name Last");
        new JSONObject(student.toJson());
    }
}
