package com.kuraninja.simple_crud_pagination.student;

import jakarta.validation.constraints.NotEmpty;

public record StudentDto(
        @NotEmpty(message = "firstName should not be empty")
        String firstName,
        @NotEmpty(message = "lastName should not be empty")
        String lastName,
        String email,
        Integer schoolId
) {
}
