package com.Lucifer.StudentProject.dto;

import com.Lucifer.StudentProject.enums.Gender;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record StudReq(
        @NotBlank(message = "First name is required")
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
        String lastName,

        @NotBlank(message = "Mobile number is required")
        @Pattern(
                regexp = "^[6-9]\\d{9}$",
                message = "Mobile number must be a valid 10-digit Indian mobile number"
        )
        String mobileNumber,

        @NotBlank(message = "Email is required")
        @Email(message = "Please provide a valid email address")
        String email,

        @NotNull(message = "Gender is required")
        Gender gender,

        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be in the past")
        LocalDate dateOfBirth,

        @NotEmpty(message = "At least one qualification is required")
        List<@NotBlank(message = "Qualification cannot be blank") String> qualifications,

        @NotEmpty(message = "At least one technology is required")
        List<@NotBlank(message = "Technology cannot be blank") String> techStack,

        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description
) {
}
