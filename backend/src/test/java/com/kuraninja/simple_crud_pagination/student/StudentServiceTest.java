package com.kuraninja.simple_crud_pagination.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {
    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository repository;
    @Mock
    private StudentMapper studentMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_successfully_save_a_student() {
        StudentDto studentDto = new StudentDto(
                "Ikhsan",
                "Nurasid",
                "ikhsan.nurasid@binus.ac.id",
                1
        );
        Student student = new Student(
                "Ikhsan",
                "Nurasid",
                "ikhsan.nurasid@binus.ac.id",
                20
        );
        Student savedStudent = new Student(
                "Ikhsan",
                "Nurasid",
                "ikhsan.nurasid@binus.ac.id",
                20
        );
        savedStudent.setId(1);

        when(studentMapper.toStudent(studentDto))
                .thenReturn(student);
        when(repository.save(student))
                .thenReturn(savedStudent);
        when(studentMapper.toStudentResponseDto(savedStudent))
                .thenReturn(new StudentResponseDto(
                        "Ikhsan",
                        "Nurasid",
                        "ikhsan.nurasid@binus.ac.id"
                ));

        StudentResponseDto responseDto = studentService.saveStudent(studentDto);

        assertEquals(studentDto.firstName(), responseDto.firstName());
        assertEquals(studentDto.lastName(), responseDto.lastName());
        assertEquals(studentDto.email(), responseDto.email());

        verify(studentMapper, times(1))
                .toStudent(studentDto);
        verify(repository, times(1))
                .save(student);
        verify(studentMapper, times(1))
                .toStudentResponseDto(savedStudent);
    }

    @Test
    public void should_successfully_find_all_students() {
        // Prepare test data
        Student student1 = new Student(
                "Ikhsan",
                "Nurasid",
                "ikhsan.nurasid@binus.ac.id",
                20
        );

        List<Student> students = new ArrayList<>(List.of(student1));

        Page<Student> page = new PageImpl<>(students);

        Pageable pageable = PageRequest.of(0, 10);
        when(repository.findAll(pageable)).thenReturn(page);

        when(studentMapper.toStudentResponseDto(any(Student.class)))
                .thenReturn(new StudentResponseDto(
                        "Ikhsan",
                        "Nurasid",
                        "ikhsan.nurasid@binus.ac.id"
                ));

        Page<StudentResponseDto> responseDtoPage = studentService.findAllStudents(pageable);

        assertEquals(1, responseDtoPage.getTotalElements());
        assertEquals(1, responseDtoPage.getContent().size());
        assertEquals("Ikhsan", responseDtoPage.getContent().get(0).firstName());
        assertEquals("Nurasid", responseDtoPage.getContent().get(0).lastName());
        assertEquals("ikhsan.nurasid@binus.ac.id", responseDtoPage.getContent().get(0).email());

        verify(repository, times(1)).findAll(pageable);
        verify(studentMapper, times(1)).toStudentResponseDto(any(Student.class));
    }


    @Test
    public void should_successfully_find_student_by_id() {
        Integer studentId = 1;
        Student student = new Student(
                "Ikhsan",
                "Nurasid",
                "ikhsan.nurasid@binus.ac.id",
                20
        );

        when(repository.findById(studentId)).thenReturn(Optional.of(student));
        when(studentMapper.toStudentResponseDto(any(Student.class)))
                .thenReturn(new StudentResponseDto(
                        "Ikhsan",
                        "Nurasid",
                        "ikhsan.nurasid@binus.ac.id"
                ));

        StudentResponseDto responseDto = studentService.findStudentById(1);

        assertEquals(student.getFirstName(), responseDto.firstName());
        assertEquals(student.getLastName(), responseDto.lastName());
        assertEquals(student.getEmail(), responseDto.email());
        verify(repository, times(1)).findById(studentId);
        verify(studentMapper, times(1)).toStudentResponseDto(any(Student.class));
    }

    @Test
    public void should_successfully_find_all_students_by_first_name() {
        String firstName = "John";
        Student student = new Student(
                "John",
                "Doe",
                "john.doe@gmail.com",
                21
        );
        List<Student> students = new ArrayList<>(List.of(student));

        when(repository.findAllByFirstNameContaining(firstName))
                .thenReturn(students);
        when(studentMapper.toStudentResponseDto(any(Student.class)))
                .thenReturn(new StudentResponseDto(
                        "John",
                        "Doe",
                        "john.doe@gmail.com"
                ));

        List<StudentResponseDto> responseDto = studentService.findAllStudentByFirstName(firstName);

        assertEquals(students.size(), responseDto.size());
        assertEquals(students.get(0).getFirstName(), responseDto.get(0).firstName());
        assertEquals(students.get(0).getLastName(), responseDto.get(0).lastName());
        assertEquals(students.get(0).getEmail(), responseDto.get(0).email());
        verify(repository, times(1)).findAllByFirstNameContaining(firstName);
        verify(studentMapper, times(1)).toStudentResponseDto(any(Student.class));
    }

    @Test
    public void should_successfully_delete_student_by_id() {
        Integer studentId = 1;

        studentService.deleteStudentById(studentId);

        verify(repository, times(1)).deleteById(studentId);
    }
}