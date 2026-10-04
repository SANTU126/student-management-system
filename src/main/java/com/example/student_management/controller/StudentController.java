package com.example.student_management.controller;

import com.example.student_management.entity.Student;
import com.example.student_management.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // CREATE STUDENT
    @PostMapping
    public Student createStudent(
            @RequestBody Student student) {

        return studentService.createStudent(student);
    }

    // GET ALL STUDENTS
    @GetMapping
    public List<Student> getAllStudents() {

        return studentService.getAllStudents();
    }

    // GET STUDENT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(
            @PathVariable Long id) {

        Optional<Student> optionalStudent =
                studentService.getStudentById(id);

        if (optionalStudent.isPresent()) {

            Student student = optionalStudent.get();

            return ResponseEntity.ok(student);
        }

        return ResponseEntity.notFound().build();
    }

    // UPDATE STUDENT
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student studentDetails) {

        Optional<Student> optionalStudent =
                studentService.updateStudent(id, studentDetails);

        if (optionalStudent.isPresent()) {

            Student updatedStudent =
                    optionalStudent.get();

            return ResponseEntity.ok(updatedStudent);
        }

        return ResponseEntity.notFound().build();
    }

    // DELETE STUDENT
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable Long id) {

        boolean deleted =
                studentService.deleteStudent(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    // GET STUDENTS BY AGE
    @GetMapping("/age/{age}")
    public List<Student> getStudentsByAge(
            @PathVariable int age) {

        return studentService.getStudentsByAge(age);
    }
    @GetMapping("/course/{courseName}")
    public List<String> getStudentNamesByCourseName(
            @PathVariable String courseName) {

        return studentService
                .getStudentNamesByCourseName(courseName);
    }
    // REMOVE COURSE FROM STUDENT
    @DeleteMapping("/{studentId}/course/{courseId}")
    public ResponseEntity<String> removeCourseFromStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {

        boolean removed =
                studentService.removeCourseFromStudent(
                        studentId,
                        courseId
                );

        if (!removed) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                "Course removed from student successfully"
        );
    }
    // ADD COURSE TO STUDENT
    @PostMapping("/{studentId}/course/{courseId}")
    public ResponseEntity<String> addCourseToStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {

        boolean added =
                studentService.addCourseToStudent(
                        studentId,
                        courseId
                );

        if (!added) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                "Course added to student successfully"
        );
    }
    @GetMapping("/course/student-count")
    public Map<String, Long> getCourseWiseStudentCount() {

        return studentService.getCourseWiseStudentCount();
    }
}