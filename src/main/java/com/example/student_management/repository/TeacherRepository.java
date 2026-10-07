package com.example.student_management.repository;

import com.example.student_management.entity.Course;
import com.example.student_management.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findByCoursesContaining(Course course);
}
