package com.kuraninja.simple_crud_pagination.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {

    private StudentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new StudentMapper();
    }

    @Test
    public void shouldMapStudentDtoToStudent() {
        StudentDto studentDto = new StudentDto(
                "Ikhsan",
                "Nurasid",
                "ikhsan.nurasid@binus.ac.id",
                1
        );

        Student student = mapper.toStudent(studentDto);

        assertEquals(studentDto.firstName(), student.getFirstName());
        assertEquals(studentDto.lastName(), student.getLastName());
        assertEquals(studentDto.email(), student.getEmail());
        assertNotNull(student.getSchool());
        assertEquals(studentDto.schoolId(), student.getSchool().getId());
    }

    @Test
    public void should_throw_null_pointer_exception_when_studentDto_is_null() {
        assertThrows(NullPointerException.class, () -> mapper.toStudent(null));
    }

    @Test
    public void shouldMapStudentToStudentResponseDto() {
        Student student = new Student(
                "Ikhsan",
                "Nurasid",
                "ikhsan.nurasid@binus.ac.id",
                20
        );

        StudentResponseDto studentResponseDto = mapper.toStudentResponseDto(student);

        assertEquals(student.getFirstName(), studentResponseDto.firstName());
        assertEquals(student.getLastName(), studentResponseDto.lastName());
        assertEquals(student.getEmail(), studentResponseDto.email());
    }
}