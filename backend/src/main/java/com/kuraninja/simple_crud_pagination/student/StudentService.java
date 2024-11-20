package com.kuraninja.simple_crud_pagination.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository repository;
    private final StudentMapper studentMapper;

    public StudentService(StudentRepository repository, StudentMapper studentMapper) {
        this.repository = repository;
        this.studentMapper = studentMapper;
    }

    public StudentResponseDto saveStudent(StudentDto studentDto) {
        Student student = studentMapper.toStudent(studentDto);
        Student savedStudent = repository.save(student);
        return studentMapper.toStudentResponseDto(savedStudent);
    }

    public Page<StudentResponseDto> findAllStudents(Pageable pageable) {
        return repository.findAll(pageable)
                .map(studentMapper::toStudentResponseDto);
    }

    public StudentResponseDto findStudentById(Integer id) {
        return repository.findById(id)
                .map(studentMapper::toStudentResponseDto)
                .orElse(null);
    }

    public List<StudentResponseDto> findAllStudentByFirstName(String name) {
        return repository.findAllByFirstNameContaining(name)
                .stream()
                .map(studentMapper::toStudentResponseDto)
                .collect(Collectors.toList());
    }

    public void deleteStudentById(Integer id) {
        repository.deleteById(id);
    }
}
