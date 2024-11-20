package com.kuraninja.simple_crud_pagination.school;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class SchoolController {
    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping("/schools")
    public SchoolRequestDto saveSchool(
            @RequestBody SchoolRequestDto schoolRequestDto
    ) {
        return schoolService.saveSchool(schoolRequestDto);
    }

    @GetMapping("/schools")
    public List<SchoolResponseDto> findAll() {
        return schoolService.findAll();
    }
}