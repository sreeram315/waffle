package com.mybatisSample.student.models;

import java.util.Objects;

public class Student {
    int id;
    String name;

    private Student() {}

    public static Student with(int id, String name) {
        Student s = new Student();
        s.id = id;
        s.name = name;
        return s;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
