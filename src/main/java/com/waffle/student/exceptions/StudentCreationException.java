package com.waffle.student.exceptions;


public class StudentCreationException extends RuntimeException {

    public static StudentCreationException with(int id) {
        return new StudentCreationException(id);
    }

    private StudentCreationException(int id) {
        super(String.format("Exception while creating student with id: %s", id));
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
