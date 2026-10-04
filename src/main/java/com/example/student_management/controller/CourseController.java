package com.example.student_management.controller;

import com.example.student_management.entity.Course;
import com.example.student_management.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // CREATE
    @PostMapping
    public Course createCourse(@RequestBody Course course) {

        return courseService.createCourse(course);
    }

    // READ ALL
    @GetMapping
    public List<Course> getAllCourses() {

        return courseService.getAllCourses();
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(
            @PathVariable Long id) {

        Optional<Course> optionalCourse = courseService.getCourseById(id);

        if (optionalCourse.isPresent()) {

            Course course = optionalCourse.get();

            return ResponseEntity.ok(course);
        }

        return ResponseEntity.notFound().build();
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long id,
            @RequestBody Course courseDetails) {

        Optional<Course> optionalCourse =
                courseService.updateCourse(id, courseDetails);

        if (optionalCourse.isPresent()) {

            Course updatedCourse = optionalCourse.get();

            return ResponseEntity.ok(updatedCourse);
        }

        return ResponseEntity.notFound().build();
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(
            @PathVariable Long id) {

        boolean deleted = courseService.deleteCourse(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
