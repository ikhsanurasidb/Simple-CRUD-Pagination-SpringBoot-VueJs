package com.kuraninja.simple_crud_pagination.student;

import com.kuraninja.simple_crud_pagination.school.School;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class StudentMapper {
    public Student toStudent(StudentDto studentDto) {
        if (studentDto == null) {
            throw  new NullPointerException("The studentDto is null");
        }
        Student student = new Student();
        student.setFirstName(studentDto.firstName());
        student.setLastName(studentDto.lastName());
        student.setEmail(studentDto.email());

        School school = new School();
        school.setId(studentDto.schoolId());

        student.setSchool(school);

        return student;
    }

    public StudentResponseDto toStudentResponseDto(Student student) {
        return new StudentResponseDto(
                student.getFirstName(),
                student.getLastName(),
                student.getEmail()
        );
    }

    public Page<StudentResponseDto> toPage(Page<Student> students) {
        return students.map(this::toStudentResponseDto);
    }
}
