package com.kuraninja.simple_crud_pagination.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SchoolServiceTest {
    @InjectMocks
    private SchoolService schoolService;

    @Mock
    private SchoolRepository schoolRepository;
    @Mock
    private SchoolMapper schoolMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_successfully_save_school() {
        SchoolRequestDto schoolRequestDto = new SchoolRequestDto(
                "Bina Nusantara"
        );
        School school = new School(
                "Bina Nusantara"
        );
        school.setId(1);

        when(schoolMapper.toSchool(schoolRequestDto))
                .thenReturn(school);
        when(schoolRepository.save(school))
                .thenReturn(school);

        SchoolRequestDto response = schoolService.saveSchool(schoolRequestDto);

        assertNotNull(response);
        assertEquals(response.name(), school.getName());
    }

    @Test
    public void should_successfully_find_all_schools() {
        List<School> schools = new ArrayList<>();
        schools.add(new School("Bina Nusantara"));

        when(schoolRepository.findAll())
                .thenReturn(schools);
        when(schoolMapper.toSchoolResponseDto(any(School.class)))
                .thenReturn(new SchoolResponseDto(1, "Bina Nusantara"));

        List<SchoolResponseDto> response = schoolService.findAll();

        assertNotNull(response);
        assertEquals(response.size(), schools.size());

        verify(schoolRepository, times(1)).findAll();
    }
}