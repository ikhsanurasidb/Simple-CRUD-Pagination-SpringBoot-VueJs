package com.kuraninja.simple_crud_pagination.studentprofile;

import com.kuraninja.simple_crud_pagination.student.Student;
import jakarta.persistence.*;

@Entity
public class StudentProfile {
    @Id
    @GeneratedValue
    Integer id;
    String name;

    @OneToOne
    @JoinColumn(
            name = "student_id"
    )
    private Student student;

    public StudentProfile() {
    }

    public StudentProfile(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
