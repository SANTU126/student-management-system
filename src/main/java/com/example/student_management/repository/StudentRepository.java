package com.example.student_management.repository;




import com.example.student_management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);
    @Query("""
           SELECT s.name
           FROM Student s
           JOIN s.courses c
           WHERE c.courseName = :courseName
           """)
    List<String> findStudentNamesByCourseName(
            @Param("courseName") String courseName
    );
    @Query("""
       SELECT c.courseName, COUNT(s.id)
       FROM Student s
       JOIN s.courses c
       GROUP BY c.id, c.courseName
       ORDER BY c.courseName
       """)
    List<Object[]> findCourseWiseStudentCount();
}
