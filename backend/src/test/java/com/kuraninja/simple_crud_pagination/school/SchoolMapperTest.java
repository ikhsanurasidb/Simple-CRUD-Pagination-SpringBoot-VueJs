package com.kuraninja.simple_crud_pagination.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchoolMapperTest {
    private SchoolMapper schoolMapper;

    @BeforeEach
    void setUp() {
        schoolMapper = new SchoolMapper();
    }

    @Test
    public void should_map_school_dto_to_school(){
        SchoolRequestDto schoolRequestDto = new SchoolRequestDto(
                "Bina Nusantara"
        );

        School schoolResponse = schoolMapper.toSchool(schoolRequestDto);

        assertEquals(schoolRequestDto.name(), schoolResponse.getName());
    }

    @Test
    public void should_map_school_to_school_dto(){
        School school = new School(
                "Bina Nusantara"
        );

        SchoolResponseDto schoolResponseDto = schoolMapper.toSchoolResponseDto(school);

        assertEquals(schoolResponseDto.name(), school.getName());
    }
}