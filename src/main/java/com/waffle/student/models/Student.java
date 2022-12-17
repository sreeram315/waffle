package com.waffle.student.models;

import com.waffle.infra.queue.models.utils.Jsonable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;

public class Student implements Jsonable, Serializable {
    private int id;
    private String name;
    private LocalDateTime insertedAt;

    private Student() {}

    public static Student with(int id, String name) {
        Student s = new Student();
        s.id = id;
        s.name = name;
        s.insertedAt = LocalDateTime.now(ZoneOffset.UTC);
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

    public LocalDateTime getInsertedAt() {
        return insertedAt;
    }

    public void setInsertedAt(LocalDateTime insertedAt) {
        this.insertedAt = insertedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && Objects.equals(name, student.name) && Objects.equals(insertedAt, student.insertedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, insertedAt);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", insertedAt=" + insertedAt +
                '}';
    }
}
