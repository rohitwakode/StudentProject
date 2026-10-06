package com.Lucifer.StudentProject.dto;

import com.Lucifer.StudentProject.enums.ROLE;

public record UserRes(
        Integer id,
        String email,
        Boolean isAccountVerified

) {
}
