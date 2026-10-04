package com.example.student_management.service;

import com.example.student_management.entity.Course;
import com.example.student_management.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // CREATE
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    // READ ALL
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // READ ONE
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    // UPDATE
    public Optional<Course> updateCourse(Long id, Course courseDetails) {

        Optional<Course> optionalCourse = courseRepository.findById(id);

        if (optionalCourse.isPresent()) {

            Course course = optionalCourse.get();

            course.setCourseName(courseDetails.getCourseName());
            course.setCourseDescription(courseDetails.getCourseDescription());

            Course updatedCourse = courseRepository.save(course);

            return Optional.of(updatedCourse);
        }

        return Optional.empty();
    }

    // DELETE
    public boolean deleteCourse(Long id) {

        if (!courseRepository.existsById(id)) {
            return false;
        }

        courseRepository.deleteById(id);

        return true;
    }
}
