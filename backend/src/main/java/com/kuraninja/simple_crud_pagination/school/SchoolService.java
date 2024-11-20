package com.kuraninja.simple_crud_pagination.school;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final SchoolMapper schoolMapper;

    public SchoolService(SchoolRepository schoolRepository, SchoolMapper schoolMapper) {
        this.schoolRepository = schoolRepository;
        this.schoolMapper = schoolMapper;
    }

    public SchoolRequestDto saveSchool(SchoolRequestDto schoolRequestDto) {
        School school = schoolMapper.toSchool(schoolRequestDto);
        schoolRepository.save(school);
        return schoolRequestDto;
    }

    public List<SchoolResponseDto> findAll() {
        return schoolRepository.findAll()
                .stream()
                .map(schoolMapper::toSchoolResponseDto)
                .collect(Collectors.toList());
    }
}
