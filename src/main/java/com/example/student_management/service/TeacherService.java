package com.example.student_management.service;

import com.example.student_management.entity.Course;
import com.example.student_management.entity.Teacher;
import com.example.student_management.repository.CourseRepository;
import com.example.student_management.repository.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;

    public TeacherService(
            TeacherRepository teacherRepository,
            CourseRepository courseRepository) {

        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
    }

    // CREATE TEACHER
    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    // GET ALL TEACHERS
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    // GET TEACHER BY ID
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Teacher not found with id: " + id
                        ));
    }

    // UPDATE TEACHER
    public Teacher updateTeacher(
            Long id,
            Teacher teacherDetails) {

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Teacher not found with id: " + id
                        ));

        teacher.setTeacherName(
                teacherDetails.getTeacherName()
        );

        teacher.setEmail(
                teacherDetails.getEmail()
        );

        return teacherRepository.save(teacher);
    }

    // DELETE TEACHER
    public void deleteTeacher(Long id) {

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Teacher not found with id: " + id
                        ));

        teacherRepository.delete(teacher);
    }

    // GET ALL COURSES OF A TEACHER
    @Transactional(readOnly = true)
    public List<Course> getCoursesOfTeacher(Long teacherId) {

        // Check teacher exists
        teacherRepository.findById(teacherId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Teacher not found with id: "
                                        + teacherId
                        ));

        // Get courses from teacher_course table
        return courseRepository.findCoursesByTeacherId(teacherId);
    }



}