package com.Lucifer.StudentProject.dto;

import com.Lucifer.StudentProject.enums.ROLE;

public record UserReq(

        String email,
        String password
) {
}
