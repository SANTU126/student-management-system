package com.example.student_management.service;

import com.example.student_management.entity.Course;
import com.example.student_management.entity.Student;
import com.example.student_management.repository.CourseRepository;
import com.example.student_management.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(
            StudentRepository studentRepository,
            CourseRepository courseRepository) {

        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    // CREATE
    public Student createStudent(Student student) {

        List<Course> courses = student.getCourses();

        List<Course> existingCourses = new ArrayList<>();

        for (Course course : courses) {

            Optional<Course> optionalCourse =
                    courseRepository.findById(course.getId());

            if (optionalCourse.isEmpty()) {

                throw new RuntimeException(
                        "Course not found with id: " + course.getId()
                );
            }

            existingCourses.add(optionalCourse.get());
        }

        student.setCourses(existingCourses);

        return studentRepository.save(student);
    }

    // READ ALL
    public List<Student> getAllStudents() {

        return studentRepository.findAll();
    }

    // READ ONE
    public Optional<Student> getStudentById(Long id) {

        return studentRepository.findById(id);
    }

    // UPDATE
    public Optional<Student> updateStudent(
            Long id,
            Student studentDetails) {

        Optional<Student> optionalStudent =
                studentRepository.findById(id);

        if (optionalStudent.isPresent()) {

            Student student = optionalStudent.get();

            student.setName(studentDetails.getName());
            student.setAge(studentDetails.getAge());

            List<Course> courses =
                    studentDetails.getCourses();

            List<Course> existingCourses =
                    new ArrayList<>();

            for (Course course : courses) {

                Optional<Course> optionalCourse =
                        courseRepository.findById(course.getId());

                if (optionalCourse.isEmpty()) {

                    throw new RuntimeException(
                            "Course not found with id: "
                                    + course.getId()
                    );
                }

                existingCourses.add(optionalCourse.get());
            }

            student.setCourses(existingCourses);

            Student updatedStudent =
                    studentRepository.save(student);

            return Optional.of(updatedStudent);
        }

        return Optional.empty();
    }

    // DELETE
    public boolean deleteStudent(Long id) {

        if (!studentRepository.existsById(id)) {
            return false;
        }

        studentRepository.deleteById(id);

        return true;
    }

    // GET STUDENTS BY AGE
    public List<Student> getStudentsByAge(int age) {

        return studentRepository.findByAge(age);
    }
    public List<String> getStudentNamesByCourseName(
            String courseName) {

        return studentRepository
                .findStudentNamesByCourseName(courseName);
    }
    // REMOVE COURSE FROM STUDENT
    public boolean removeCourseFromStudent(Long studentId, Long courseId) {

        Optional<Student> optionalStudent =
                studentRepository.findById(studentId);

        if (optionalStudent.isEmpty()) {
            return false;
        }

        Student student = optionalStudent.get();

        List<Course> courses = student.getCourses();

        Course courseToRemove = null;

        for (Course course : courses) {
            if (course.getId().equals(courseId)) {
                courseToRemove = course;
                break;
            }
        }

        if (courseToRemove == null) {
            return false;
        }

        courses.remove(courseToRemove);

        student.setCourses(courses);

        studentRepository.save(student);

        return true;
    }
    // ADD COURSE TO STUDENT
    public boolean addCourseToStudent(Long studentId, Long courseId) {

        Optional<Student> optionalStudent =
                studentRepository.findById(studentId);

        if (optionalStudent.isEmpty()) {
            return false;
        }

        Optional<Course> optionalCourse =
                courseRepository.findById(courseId);

        if (optionalCourse.isEmpty()) {
            return false;
        }

        Student student = optionalStudent.get();

        Course course = optionalCourse.get();

        List<Course> courses = student.getCourses();

        // Check if student already has this course
        for (Course existingCourse : courses) {

            if (existingCourse.getId().equals(courseId)) {
                return false;
            }
        }

        courses.add(course);

        student.setCourses(courses);

        studentRepository.save(student);

        return true;
    }
    public Map<String, Long> getCourseWiseStudentCount() {

        List<Object[]> results =
                studentRepository.findCourseWiseStudentCount();

        Map<String, Long> courseStudentCount =
                new LinkedHashMap<>();

        for (Object[] result : results) {

            String courseName = (String) result[0];

            Long studentCount = (Long) result[1];

            courseStudentCount.put(
                    courseName,
                    studentCount
            );
        }

        return courseStudentCount;
    }
}