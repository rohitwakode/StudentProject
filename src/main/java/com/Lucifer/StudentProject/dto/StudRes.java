package com.Lucifer.StudentProject.dto;

import com.Lucifer.StudentProject.enums.Gender;

import java.time.LocalDate;
import java.util.List;

public record StudRes(
        Integer id,

        String firstName,

        String lastName,

        String mobileNumber,

        String email,

        Gender gender,

        LocalDate dateOfBirth,

        List<String> qualifications,

        List<String> techStack,

        String description,

        String profileImage
) {
}
