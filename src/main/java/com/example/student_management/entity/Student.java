package com.example.student_management.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(schema = "student_management", name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    @ManyToMany
    @JoinTable(
            schema = "student_management",
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

    // Default Constructor
    public Student() {
    }

    // Parameterized Constructor
    public Student(String name, int age, List<Course> courses) {
        this.name = name;
        this.age = age;
        this.courses = courses;
    }

    // Getter for id
    public Long getId() {
        return id;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for age
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Getter and Setter for courses
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }


}