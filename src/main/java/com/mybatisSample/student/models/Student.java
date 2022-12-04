package com.mybatisSample.student.models;

import java.io.Serializable;
import java.util.Objects;

public class Student implements Serializable {
    int id;
    String name;

    public Student() {}

    public Student(int id, String name) {

        this.id = id;
        this.name = name;
    }

    public static Student with(int id, String name) {
        Student s = new Student();
        s.id = id;
        s.name = name;
        return s;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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
