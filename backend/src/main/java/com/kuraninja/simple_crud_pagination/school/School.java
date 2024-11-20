package com.kuraninja.simple_crud_pagination.school;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kuraninja.simple_crud_pagination.student.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class School {
    @Id
    @GeneratedValue
    Integer id;
    String name;

    @OneToMany(
            mappedBy = "school"
    )
    @JsonManagedReference
    List<Student> students;

    public School() {
    }

    public School(String name) {
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
}
