package com.example.student_management.repository;

import com.example.student_management.entity.Course;
import com.example.student_management.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("""
            SELECT c
            FROM Course c
            JOIN c.teachers t
            WHERE t.id = :teacherId
            """)
    List<Course> findCoursesByTeacherId(
            @Param("teacherId") Long teacherId
    );

    // Get all teachers of a course
    @Query("""
            SELECT t
            FROM Teacher t
            JOIN t.courses c
            WHERE c.id = :courseId
            """)
    List<Teacher> findTeachersByCourseId(
            @Param("courseId") Long courseId
    );

}