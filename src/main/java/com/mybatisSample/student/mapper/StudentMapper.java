package com.mybatisSample.student.mapper;

import com.mybatisSample.student.models.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentMapper {

    @Select("""
            SELECT id, name
            FROM student
            WHERE id = #{id, typeHandler = com.mybatisSample.student.typehandler.TestTypeHandler}
            """)
    Student getStudent(@Param("id") int id);

    @Select("""
            INSERT INTO student(id, name)
            VALUES(#{id}, #{name})
            """)
    void insertStudent(int id, String name);

    @Delete("""
            DELETE FROM student
            WHERE id = #{id}
            """)
    void deleteStudent(int id);
}
