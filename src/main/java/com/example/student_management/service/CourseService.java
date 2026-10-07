package com.example.student_management.service;

import com.example.student_management.entity.Course;
import com.example.student_management.entity.Teacher;
import com.example.student_management.repository.CourseRepository;
import com.example.student_management.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    public CourseService(
            CourseRepository courseRepository,
            TeacherRepository teacherRepository) {

        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
    }

    // CREATE COURSE
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    // GET ALL COURSES
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // GET COURSE BY ID
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    // UPDATE COURSE
    public Optional<Course> updateCourse(
            Long id,
            Course courseDetails) {

        Optional<Course> optionalCourse =
                courseRepository.findById(id);

        if (optionalCourse.isPresent()) {

            Course course = optionalCourse.get();

            course.setCourseName(
                    courseDetails.getCourseName()
            );

            course.setCourseDescription(
                    courseDetails.getCourseDescription()
            );

            Course updatedCourse =
                    courseRepository.save(course);

            return Optional.of(updatedCourse);
        }

        return Optional.empty();
    }

    // DELETE COURSE
    public boolean deleteCourse(Long id) {

        if (!courseRepository.existsById(id)) {
            return false;
        }

        courseRepository.deleteById(id);

        return true;
    }

    // ASSIGN TEACHER TO COURSE
    public Course assignTeacherToCourse(
            Long courseId,
            Long teacherId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Course not found with id: "
                                        + courseId
                        ));

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Teacher not found with id: "
                                        + teacherId
                        ));

        // If courses list is null, create a new list
        if (teacher.getCourses() == null) {
            teacher.setCourses(new ArrayList<>());
        }

        // Add course to teacher
        if (!teacher.getCourses().contains(course)) {
            teacher.getCourses().add(course);
        }

        // Save teacher
        teacherRepository.save(teacher);

        return course;
    }

    // GET ALL TEACHERS OF A COURSE
    public List<Teacher> getTeachersOfCourse(Long courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Course not found with id: "
                                        + courseId
                        ));

        return course.getTeachers();
    }
    // GET NUMBER OF TEACHERS OF A COURSE
    public int getTeacherCountOfCourse(Long courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Course not found with id: " + courseId
                        ));

        if (course.getTeachers() == null) {
            return 0;
        }

        return course.getTeachers().size();
    }

}