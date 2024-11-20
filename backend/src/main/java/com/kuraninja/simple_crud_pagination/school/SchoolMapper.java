package com.kuraninja.simple_crud_pagination.school;

import org.springframework.stereotype.Service;

@Service
public class SchoolMapper {
    public School toSchool(SchoolRequestDto schoolRequestDto) {
        return new School(schoolRequestDto.name());
    }

    public SchoolResponseDto toSchoolResponseDto(School school) {
        return new SchoolResponseDto(school.getId(), school.getName());
    }
}