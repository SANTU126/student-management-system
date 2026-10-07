package com.example.student_management.controller;

import com.example.student_management.entity.Course;
import com.example.student_management.entity.Teacher;
import com.example.student_management.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    // Create Teacher
    @PostMapping
    public ResponseEntity<Teacher> createTeacher(
            @RequestBody Teacher teacher) {

        return ResponseEntity.ok(
                teacherService.createTeacher(teacher)
        );
    }

    // Get All Teachers
    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {

        return ResponseEntity.ok(
                teacherService.getAllTeachers()
        );
    }

    // Get Teacher By ID
    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                teacherService.getTeacherById(id)
        );
    }

    // Update Teacher
    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(
            @PathVariable Long id,
            @RequestBody Teacher teacher) {

        return ResponseEntity.ok(
                teacherService.updateTeacher(id, teacher)
        );
    }

    // Delete Teacher
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTeacher(
            @PathVariable Long id) {

        teacherService.deleteTeacher(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Teacher deleted successfully");

        return ResponseEntity.ok(response);
    }
    @GetMapping("/{teacherId}/courses")
    public ResponseEntity<List<Course>> getCoursesOfTeacher(
            @PathVariable Long teacherId) {

        List<Course> courses =
                teacherService.getCoursesOfTeacher(teacherId);

        return ResponseEntity.ok(courses);
    }


}